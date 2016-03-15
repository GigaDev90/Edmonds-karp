/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmonds.karp;

/**
 *
 * @author gabriele
 */
public class EdmondsKarp {

    /**
     * @param args the command line arguments
     */
    public static Graph graph;
    
    public static void makeTestGraph(int element) {
        
        graph =  new Graph();
        
        for ( int i = 1; i <= element; i++ ) {
            graph.addNode(""+i);
        }
        
//        graph.setSource(graph.getNode("1"));
//        graph.setSink(graph.getNode("4"));
//        
//        graph.connect(graph.getNode("1"), graph.getNode("2"), 100000, 0);
//        graph.connect(graph.getNode("1"), graph.getNode("3"), 100000, 0);
//        graph.connect(graph.getNode("2"), graph.getNode("4"), 100000, 0);
//        graph.connect(graph.getNode("3"), graph.getNode("4"), 100000, 0);
//        graph.connect(graph.getNode("2"), graph.getNode("3"), 1, 0);
        
        graph.setSource(graph.getNode("1"));
        graph.setSink(graph.getNode("6"));
        
        graph.connect(graph.getNode("2"), graph.getNode("4"), 4, 0);
        graph.connect(graph.getNode("3"), graph.getNode("5"), 5, 0);
        graph.connect(graph.getNode("1"), graph.getNode("2"), 3, 0);
        graph.connect(graph.getNode("1"), graph.getNode("3"), 4, 0);
        graph.connect(graph.getNode("1"), graph.getNode("5"), 5, 0);
        graph.connect(graph.getNode("1"), graph.getNode("4"), 4, 0);
        graph.connect(graph.getNode("2"), graph.getNode("6"), 4, 0);
        graph.connect(graph.getNode("4"), graph.getNode("6"), 3, 0);
        graph.connect(graph.getNode("3"), graph.getNode("6"), 5, 0);
        graph.connect(graph.getNode("5"), graph.getNode("6"), 4, 0);
        
        Edge edge = graph.getNode("1").getEdge(graph.getNode("4"));
        System.out.println(edge.getNodeB().getName());
        

    }
    
    public static void main(String[] args) {
        
        makeTestGraph(6);
        graph.EdmondsKarp();
        
        Node current =  graph.getHeader().getNext();
        
        for ( int i = 0; i < graph.getSizeNode() ; i++ ) {
            
            System.out.print(current.getName()+" ");
            Edge edge = current.getHeader().getNext();
            
            for ( int j = 0; j < current.sizeEdge; j++ ) {
                if ( !edge.isIsResidual())
                    System.out.println("Flow "+edge.getFlow()+" "+edge.getCapacity()+" "+edge.getNodeB().getName());
          
                edge = edge.getNext();
            }
            current = current.getNext();
        }
    }
    
}
