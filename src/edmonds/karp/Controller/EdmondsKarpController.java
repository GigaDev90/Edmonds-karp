/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmonds.karp.Controller;

import edmonds.karp.BFSVisit;
import edmonds.karp.DFSVisit;
import edmonds.karp.Edge;
import edmonds.karp.GUI.Arrow;
import edmonds.karp.GUI.Circle;
import edmonds.karp.GUI.EdmondsKarpGUI;
import edmonds.karp.Graph;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author gabriele
 */
public class EdmondsKarpController {

    private Graph graph;
    private EdmondsKarpGUI gui;
    private Timer tmr1;
    private int name = 0;
    private int bfVisit;

    public EdmondsKarpController(EdmondsKarpGUI gui) {
        this.gui = gui;
        graph = new Graph();
        setTimer();
        bfVisit = 0;
    }
    public boolean addEdge(Arrow arrow) { 
        return addEdge(arrow, (int) (Math.random() * 100 + 1));
    }

    public boolean addEdge(Arrow arrow, int capacity) {
        Edge e = graph.connect(arrow.getFrom().getName(), arrow.getTo().getName(), capacity, 0);
        if (e != null) {
            arrow.setEdge(e);
            return true;
        } else {
            gui.displayMessage("collegamento non valido");
            return false;
        }
    }

    public void addNode(Circle circle) {
        circle.addNode(graph.addNode("" + name++));
    }

    public void removeNode(Circle circle) {
        graph.removeNode(graph.getNode(circle.getName()));

    }

    public void setSink(Circle circle) {
        if (!graph.setSink(graph.getNode(circle.getName()))) {
            gui.displayMessage("Il pozzo non può avere archi uscenti");
        } else {
            gui.update();
        }
    }

    public void setSource(Circle node) {
        if (!graph.setSource(graph.getNode(node.getName()))) {
            gui.displayMessage("La sorgente non può avere archi entranti");
        } else {
            gui.update();
        }
    }

    public void removeEdge(Arrow edge) {
        graph.disconnect(edge.getEdge());

    }

    public void play() {
        if ( gui.isPlaySelected() )
            tmr1.start();
        else {
            tmr1.stop();
        }
    }

    public void setTimer() {
        int delay = 2000; //milliseconds
        ActionListener taskPerformer1 = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if ( oneStepForward() ) {
                    tmr1.stop();
                    gui.setUpPlayButton();
                }
                
            }
        };

        tmr1 = new Timer(delay, taskPerformer1);
        tmr1.setRepeats(true);
    }

    public void stop() {
        bfVisit = 0;
        graph.edmondsKarpClear();
        graph.BFVisitClear();
        tmr1.stop();
        gui.update();
    }

    public void run() {
        if ( graph.getSource() != null && graph.getSink() != null) {
            graph.edmondsKarpClear();
            if (!graph.EdmondsKarp()) {
                gui.displayMessage("Errore: controllare sorgente e pozzo");
            }
        } else {
            gui.displayMessage("Errore: controllare sorgente e pozzo");
        }
        gui.update();
    }
    
    public void onStepBack() {
        if ( bfVisit == 0) return;
        else if (bfVisit == 1) {
            stop();
            return;
        }
        graph.edmondsKarpClear();
        graph.BFVisitClear();
        int temp = bfVisit - 1;
        
        for ( int i = 0; i < temp; i++ ) {
            oneStepForward(); 
        }
        bfVisit = temp;
        gui.update();
    }

    public boolean oneStepForward() {
        if ( graph.getSource() != null && graph.getSink() != null) {
            if ( graph.getSink().getParent() == null ) {
                if ( graph.BFSVisit() ) {
                    if ( graph.getSink().getParent() == null ) {
                        gui.displayMessage("Finito: è stato raggiunto il flusso massimo");
                        return true;
                    }
                    graph.selectPath();
                } else {
                    gui.displayMessage("BFS fallita");
                    return true;
                }
            } else {
                if ( graph.EdmondsKarpOneStep() ) {
                    graph.getSink().setParent(null);
                } else {
                    gui.displayMessage("Errore: controllare sorgente e pozzo");
                    return true;
                }
            }
        } else {
            gui.displayMessage("Errore: controllare sorgente e pozzo");
            return true;
        }
        
        bfVisit++;
        gui.update();
        
        return false;
    }
    
    public void newGraph() {
        gui.getCircles().clear();
        name = 0;
        bfVisit = 0;
        graph = new Graph();
        gui.update();
    }
    
    public void setVisit(int visit) {
        if ( visit == 0) {
            graph.setVisit(new BFSVisit());
        } else {
            graph.setVisit(new DFSVisit());
        }
    }

    public void open(String s) throws JSONException {
        Map<String, Circle> circles = new HashMap<String, Circle>();
        newGraph();

        JSONObject jNodeEdge = new JSONObject(s); // Parse the JSON to a JSONObject
        JSONArray jNodes = jNodeEdge.getJSONArray("Node");
        JSONArray jEdges = jNodeEdge.getJSONArray("Edge");

        for (int i = 0; i < jNodes.length(); i++) { // Loop over each each row of node
            Circle circle = new Circle();
            Point point = new Point();

            JSONObject jNode = jNodes.getJSONObject(i);

            point.setLocation(jNode.getInt("PosX"), jNode.getInt("PosY"));
            circle.setFirstPoint(point);
            circle.addNode(graph.addNode("" + jNode.getString("ID")));

            circles.put(jNode.getString("ID"), circle);
        }
        
        name = jNodes.length() + 1;

        for (int i = 0; i < jEdges.length(); i++) { // Loop over each each row of edge
            JSONObject jEdge = jEdges.getJSONObject(i);

            Arrow arrow = new Arrow(circles.get(jEdge.getString("From")), circles.get(jEdge.getString("To")));
            this.addEdge(arrow, jEdge.getInt("Capacity"));
            circles.get(jEdge.getString("From")).addArrowFrom(arrow);
            circles.get(jEdge.getString("To")).addArrowTo(arrow);
        }
        
        if ( !jNodeEdge.getString("Source").equals("") )
            graph.setSource(graph.getNode(jNodeEdge.getString("Source")));
        if ( !jNodeEdge.getString("Sink").equals(""))
            graph.setSink(graph.getNode(jNodeEdge.getString("Sink")));

        gui.setCircles(new ArrayList(circles.values()));
        gui.update();
        System.gc();
    }

    public void save(String path) throws JSONException {

        JSONArray nodeJArray = new JSONArray();
        JSONArray edgeJArray = new JSONArray();

        ArrayList<Circle> arrayCircle = gui.getCircles();
        ArrayList<Arrow> arrayArrow;

        for (Circle circle : arrayCircle) {
            arrayArrow = circle.getArrowFrom();
            JSONObject jNode = new JSONObject();

            jNode.put("ID", circle.getNode().getName());
            jNode.put("PosX", circle.getCenter().getX());
            jNode.put("PosY", circle.getCenter().getY());

            for (Arrow arrow : arrayArrow) {

                if (!arrow.getEdge().isResidual()) {
                    JSONObject jEdge = new JSONObject();
                    jEdge.put("From", arrow.getEdge().getNodeA().getName());
                    jEdge.put("To", arrow.getEdge().getNodeB().getName());
                    jEdge.put("Capacity", arrow.getEdge().getCapacity());
                    edgeJArray.put(jEdge);
                }
            }
            nodeJArray.put(jNode);
        }

        JSONObject jNodeEdge = new JSONObject();
        jNodeEdge.put("Node", nodeJArray);
        jNodeEdge.put("Edge", edgeJArray);
        
        if (graph.getSource() != null)
            jNodeEdge.put("Source", graph.getSource().getName());
        else
            jNodeEdge.put("Source", "");
        if ( graph.getSink() != null )
            jNodeEdge.put("Sink", graph.getSink().getName());
        else
            jNodeEdge.put("Sink", "");

        try {
            FileWriter writer = new FileWriter(path);
            BufferedWriter bWriter = new BufferedWriter(writer);
            bWriter.write(jNodeEdge.toString());
            bWriter.close();
            writer.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return;
    }

    public void setCapacity(Arrow arrow, int capacity) {
        arrow.getEdge().setCapacity(capacity);
        arrow.getEdge().getInverse().setCapacity(capacity);
        arrow.getEdge().getInverse().setFlow(capacity);
    }

    public void setFlow(Arrow arrow, int flow) {
        arrow.getEdge().setFlow(flow);
        arrow.getEdge().getInverse().setFlow(-flow);
    }

    public void loadExample() {
        String example = "{\"Source\":\"0\",\"Node\":[{\"PosY\":261,\"PosX\":643,\"ID\":\"3\"},{\"PosY\":453,\"PosX\":549,\"ID\":\"2\"},"
                + "{\"PosY\":454,\"PosX\":181,\"ID\":\"1\"},{\"PosY\":260,\"PosX\":112,\"ID\":\"0\"},{\"PosY\":59,\"PosX\":548,\"ID\":\"7\"},"
                + "{\"PosY\":58,\"PosX\":163,\"ID\":\"6\"},{\"PosY\":320,\"PosX\":477,\"ID\":\"5\"},{\"PosY\":318,\"PosX\":279,\"ID\":\"4\"},"
                + "{\"PosY\":181,\"PosX\":476,\"ID\":\"9\"},{\"PosY\":181,\"PosX\":278,\"ID\":\"8\"}],\"Sink\":\"3\",\"Edge\":[{\"To\":\"3\",\"Capacity\":4,\"From\":\"2\"},"
                + "{\"To\":\"2\",\"Capacity\":4,\"From\":\"1\"},{\"To\":\"5\",\"Capacity\":5,\"From\":\"1\"},{\"To\":\"1\",\"Capacity\":4,\"From\":\"0\"},"
                + "{\"To\":\"4\",\"Capacity\":8,\"From\":\"0\"},{\"To\":\"6\",\"Capacity\":4,\"From\":\"0\"},{\"To\":\"8\",\"Capacity\":8,\"From\":\"0\"},"
                + "{\"To\":\"3\",\"Capacity\":4,\"From\":\"7\"},{\"To\":\"7\",\"Capacity\":4,\"From\":\"6\"},{\"To\":\"9\",\"Capacity\":5,\"From\":\"6\"},"
                + "{\"To\":\"3\",\"Capacity\":5,\"From\":\"5\"},{\"To\":\"2\",\"Capacity\":5,\"From\":\"4\"},{\"To\":\"9\",\"Capacity\":4,\"From\":\"4\"},"
                + "{\"To\":\"3\",\"Capacity\":5,\"From\":\"9\"},{\"To\":\"7\",\"Capacity\":5,\"From\":\"8\"},{\"To\":\"5\",\"Capacity\":4,\"From\":\"8\"}]}";
        try {
            open(example);
        } catch (JSONException ex) {
            Logger.getLogger(EdmondsKarpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
