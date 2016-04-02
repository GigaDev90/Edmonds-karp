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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

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
}
