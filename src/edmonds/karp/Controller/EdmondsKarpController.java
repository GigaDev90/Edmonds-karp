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
import java.util.Iterator;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
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
    
    public EdmondsKarpController(EdmondsKarpGUI gui) {
        this.gui = gui;
        graph = new Graph();
        setTimer();
    }
    
    public boolean addEdge(Arrow edge) {
       Edge e = graph.connect(edge.getFrom().getName(), edge.getTo().getName(),(int)(Math.random() *100 + 1), 0);
       if ( e!= null) {
           edge.setEdge(e);
           return true;
       } else {
            gui.displayMessage("collegamento non valido");
            return false;
       }
    }
    
    public void addNode(Circle node) {
        node.addNode(graph.addNode(node.getName()));
    }
    
    public void removeNode(Circle node) {
        graph.removeNode(graph.getNode(node.getName()));
        
    }
    
    public void setSink(Circle node) {
        if (!graph.setSink(graph.getNode(node.getName()))) {
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
        ArrayList<Circle> circles = new ArrayList();

        JSONObject rootObject = new JSONObject(s); // Parse the JSON to a JSONObject
        JSONArray rows = rootObject.getJSONArray("Graph");
        
        for (int i = 0; i < rows.length(); i++) { // Loop over each each row
            Circle circle = new Circle();
            Point point = new Point();
            JSONObject row = rows.getJSONObject(i);
            JSONArray elements = row.getJSONArray("Edge"); 
            circle.setName(row.getString("ID"));
            point.setLocation(row.getInt("posX"), row.getInt("posY"));
            circle.setFirstPoint(point);
            
            for (int j = 0; j < elements.length(); j++) {
                
                JSONObject element = elements.getJSONObject(j);
                
                System.out.println("adj: " + element.getString("Adj")); 
            }
        }

    }

    public void save() throws JSONException {

        JSONArray jArray1 = new JSONArray();
        ArrayList<Circle> arrayCircle = gui.getCircles();
        ArrayList<Arrow> arrayArrow;
        
         
        for ( Circle circle : arrayCircle ) {
            arrayArrow = circle.getArrowFrom();
            JSONObject jObj1 = new JSONObject();
            JSONArray jArray2 = new JSONArray();
            jObj1.put("Edge", jArray2);
            jObj1.put("ID", circle.getNode().getName());
            jObj1.put("posX", circle.getCenter().getX());
            jObj1.put("posY", circle.getCenter().getY());
            
            for ( Arrow arrow : arrayArrow ) {
                if ( !arrow.getEdge().isIsResidual() ) {
                    JSONObject jObj2 = new JSONObject();
                    jObj2.put("Adj", arrow.getEdge().getNodeB().getName());
                    jObj2.put("Capacity", arrow.getEdge().getCapacity());
                    jArray2.put(jObj2);
                }
            }
            jArray1.put(jObj1);
        }
        
        JSONObject jObj3 = new JSONObject();
        jObj3.put("Graph", jArray1);
        
//        for (  int i = 0; i < arrayNode.size(); i++ ) {
//            Arrow edge = node.getHeader().getNext();
//            JSONObject jObj1 = new JSONObject();
//            JSONArray jArray2 = new JSONArray();
//            jObj1.put("ID", node.getName());
//           
//            for( int j = 0; j < node.sizeEdge; j++ ) {
//                if ( !edge.isIsResidual() ) {
//                    JSONObject jObj2 = new JSONObject();
//                    jObj2.put("Adj", edge.getNodeB().getName());
//                    jObj2.put("Capacity", edge.getCapacity());
//                    jArray2.put(jObj2);
//                }
//                edge = edge.getNext();
//            }
//            jObj1.put("Edge", jArray2);
//            jArray1.put(jObj1);
//            node = node.getNext();
//            
//        }
        
//        JSONObject jObj3 = new JSONObject();
//        jObj3.put("Graph", jArray1);
        
        
       System.out.println(jObj3.toString()); 
       open(jObj3.toString());
    }
}
