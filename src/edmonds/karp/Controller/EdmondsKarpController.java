/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmonds.karp.Controller;

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
    
    public void addEdge(Arrow edge) {
        edge.setEdge(graph.connect(edge.getFrom().getName(), edge.getTo().getName(), 0, 0));
    }
    
    public void addNode(Circle node) {
        node.addNode(graph.addNode(node.getName()));
    }
    
    public void removeNode() {
        
    }
    
    public void setSink() {
        
    }
    
    public void setSource() {
        
    }
    
    public void removeEdge() {
        
    }
    public void play() {
        
    }
}
