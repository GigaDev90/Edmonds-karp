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
    
    public EdmondsKarpController(EdmondsKarpGUI gui) {
        this.gui = gui;
        graph = new Graph();
        setTimer();
    }
    
    public boolean addEdge(Arrow arrow) {
       Edge e = graph.connect(arrow.getFrom().getName(), arrow.getTo().getName(),(int)(Math.random() *100 + 1), 0);
       if ( e!= null) {
           arrow.setEdge(e);
           return true;
       } else {
            gui.displayMessage("collegamento non valido");
            return false;
       }
    }
    
    public void addNode(Circle circle ) {
        circle.addNode(graph.addNode(""+name++));
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
//        
//        while ( gui.isPlaySelected());
//        if (gui.isPlaySelected())
//            play();
    }
    
    public void setTimer() {
        int delay = 2000; //milliseconds
        ActionListener taskPerformer1 = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                graph.BFSVisit(graph.getSource());
                gui.update();
                if ( gui.isPlaySelected() )
                    tmr2.start();
            }
        };
        
        tmr1 = new Timer(delay, taskPerformer1);
        tmr1.setRepeats(false);
        
        ActionListener taskPerformer2 = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                graph.EdmondsKarpOneStep();
                gui.update();
                if ( gui.isPlaySelected() )
                    tmr1.start();
            }
        };
        tmr2 = new Timer(delay, taskPerformer2);
        tmr2.setRepeats(false);
    }
    
    public void stop() {
        graph.cleanGraph();
        gui.update();
    }
    
    public void run() {
        graph.cleanGraph();
        if ( !graph.EdmondsKarp() ) {
            gui.displayMessage("sorgente o pozzo non sono stati assegnati");
        }
        gui.update();
    }
    
    public void oneStepForward() {
        graph.BFSVisit(graph.getSource());
        gui.update();
        int delay = 5000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                graph.EdmondsKarpOneStep();
            }
        };
        new Timer(delay, taskPerformer).start();
        gui.update();

    }
    
    public void open(String s) throws JSONException {
        System.out.println(s);
        Map<String, Circle> circles = new HashMap<String, Circle>();
        graph = new Graph();
        gui.getCircles().clear();

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
            //aggiungo al grafo l'arco
            //Edge edge = new Edge(graph.getNode(jEdge.getString("From")), graph.getNode(jEdge.getString("To")));
            //edge.setCapacity(jEdge.getInt("Capacity"));
            
            Arrow arrow = new Arrow(circles.get(jEdge.getString("From")), circles.get(jEdge.getString("To")));
            this.addEdge(arrow);
            circles.get(jEdge.getString("From")).addArrowFrom(arrow);
            circles.get(jEdge.getString("To")).addArrowTo(arrow);
            arrow.getEdge().setCapacity(jEdge.getInt("Capacity"));
        }
        
        gui.setCircles(new ArrayList(circles.values()));
        gui.update();

    }

    public String save() throws JSONException {

        JSONArray nodeJArray = new JSONArray();
        JSONArray edgeJArray = new JSONArray();
        
        ArrayList<Circle> arrayCircle = gui.getCircles();
        ArrayList<Arrow> arrayArrow;
        
         
        for ( Circle circle : arrayCircle ) {
            arrayArrow = circle.getArrowFrom();
            JSONObject jNode = new JSONObject();
            
            jNode.put("ID", circle.getNode().getName());
            jNode.put("PosX", circle.getCenter().getX());
            jNode.put("PosY", circle.getCenter().getY());
            
            for ( Arrow arrow : arrayArrow ) {
                
                if ( !arrow.getEdge().isIsResidual() ) {
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
        
       //System.out.println(jNodeEdge.toString()); 
       return jNodeEdge.toString();
    }
}
