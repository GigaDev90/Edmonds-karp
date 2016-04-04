/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmonds.karp.Controller;

import edmonds.karp.Edge;
import edmonds.karp.GUI.Arrow;
import edmonds.karp.GUI.Circle;
import edmonds.karp.GUI.EdmondsKarpGUI;
import edmonds.karp.Graph;
import edmonds.karp.Node;
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
import javax.swing.Timer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author gabriele
 */
public class EdmondsKarpController {

    private Graph graph;
    private EdmondsKarpGUI gui;
    private Timer tmr1;
    private Timer tmr2;
    private int name = 0;
    private int bfVisit;

    public EdmondsKarpController(EdmondsKarpGUI gui) {
        this.gui = gui;
        graph = new Graph();
        setTimer();
        bfVisit = 0;
    }

    public boolean addEdge(Arrow arrow) {
        Edge e = graph.connect(arrow.getFrom().getName(), arrow.getTo().getName(), (int) (Math.random() * 100 + 1), 0);
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
        tmr1.start();
    }

    public void setTimer() {
        int delay = 2000; //milliseconds
        ActionListener taskPerformer1 = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (!graph.BFSVisit(graph.getSource())) {
                    gui.displayMessage("Finish");
                    gui.setUpPlayButton();
                    return;
                }
                if (graph.isSafe()) {
                    graph.selectPath();
                } else {
                    gui.displayMessage("Errore: controllare sorgente o pozzo");
                    gui.setUpPlayButton();
                }

                gui.update();
                if (gui.isPlaySelected()) {
                    tmr2.start();
                }
            }
        };

        tmr1 = new Timer(delay, taskPerformer1);
        tmr1.setRepeats(false);

        ActionListener taskPerformer2 = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                graph.EdmondsKarpOneStep();
                gui.update();
                if (gui.isPlaySelected()) {
                    tmr1.start();
                }
            }
        };
        tmr2 = new Timer(delay, taskPerformer2);
        tmr2.setRepeats(false);
    }

    public void stop() {
        graph.edmondsKarpClear();
        graph.BFVisitClear();
        gui.update();
    }

    public void run() {
        graph.edmondsKarpClear();
        if (!graph.EdmondsKarp()) {
            gui.displayMessage("sorgente o pozzo non sono stati assegnati");
        }
        gui.update();
    }

    public void oneStepForward() {
        
        if (!graph.BFSVisit(graph.getSource())) {
            gui.displayMessage("Finish");
            return;
        }
        if ( graph.isSafe() ) {
            if ( !graph.isSelected() ) {
                graph.selectPath();
                System.out.println("search");
            } else {
                if ( !graph.EdmondsKarpOneStep() ) {
                    gui.displayMessage("Errore, controllare sorgente o pozzo");
                }
                System.out.println("EdmondsKarpOneStep");
            }
        } else {
            gui.displayMessage("Errore, controllare sorgente o pozzo");
        }
        

        bfVisit++;
        gui.update();
    }
    
    public void newGraph() {
        gui.getCircles().clear();
        name = 0;
        bfVisit = 0;
        graph = new Graph();
        gui.update();
    }

    public void open(String s) throws JSONException {
        Map<String, Circle> circles = new HashMap<String, Circle>();
        graph = new Graph();
        name = 0;

        JSONObject jNodeEdge = new JSONObject(s); // Parse the JSON to a JSONObject
        JSONArray jNodes = jNodeEdge.getJSONArray("Node");
        JSONArray jEdges = jNodeEdge.getJSONArray("Edge");

        for (int i = 0; i < jNodes.length(); i++) { // Loop over each each row of node
            Circle circle = new Circle();
            Point point = new Point();

            JSONObject jNode = jNodes.getJSONObject(i);

            point.setLocation(jNode.getInt("PosX"), jNode.getInt("PosY"));
            circle.setFirstPoint(point);
            this.addNode(circle);

            circles.put(jNode.getString("ID"), circle);

        }

        for (int i = 0; i < jEdges.length(); i++) { // Loop over each each row of edge
            JSONObject jEdge = jEdges.getJSONObject(i);

            Arrow arrow = new Arrow(circles.get(jEdge.getString("From")), circles.get(jEdge.getString("To")));
            this.addEdge(arrow);
            circles.get(jEdge.getString("From")).addArrowFrom(arrow);
            circles.get(jEdge.getString("To")).addArrowTo(arrow);
            arrow.getEdge().setCapacity(jEdge.getInt("Capacity"));
        }
        //System.out.println(jNodeEdge.getString("Sink"));
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

                if (!arrow.getEdge().isIsResidual()) {
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
}
