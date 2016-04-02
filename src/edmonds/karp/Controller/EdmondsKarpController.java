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

/**
 *
 * @author gabriele
 */
public class EdmondsKarpController {
    
    private Graph graph;
    private EdmondsKarpGUI gui;
    
    public EdmondsKarpController(EdmondsKarpGUI gui) {
        this.gui = gui;
        graph = new Graph();
    }
    
    public boolean addEdge(Arrow edge) {
       Edge e = graph.connect(edge.getFrom().getName(), edge.getTo().getName(),(int)(Math.random() *100), 0);
       if ( e!= null) {
           edge.setEdge(e);
           return true;
       } else {
            gui.lunchMessage("collegamento non valido");
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
            gui.lunchMessage("Il pozzo non può avere archi uscenti");
        } else { 
            gui.update();
        }
    }
    
    public void setSource(Circle node) {
        if (!graph.setSource(graph.getNode(node.getName()))) {
            gui.lunchMessage("La sorgente non può avere archi entranti");
        } else {
            gui.update();
        }
    }
    
    public void removeEdge(Arrow edge) {
        graph.disconnect(edge.getEdge());
        
    }
    public void play() {
        
    }
    
    public void stop() {
        graph.cleanGraph();
        gui.update();
    }
    
    public void run() {
        graph.cleanGraph();
        if ( !graph.EdmondsKarp() ) {
            gui.lunchMessage("sorgente o pozzo non sono stati assegnati");
        }
        gui.update();
    }
    
    public void oneStepForward() {
        
    }
}
