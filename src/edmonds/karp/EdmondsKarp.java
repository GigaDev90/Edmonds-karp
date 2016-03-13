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
        
        graph.setSink(graph.addNode("0"));
        
        for ( int i = 1; i < element - 1; i++ ) {
            graph.addNode(""+i);
        }
        
        graph.setSource(graph.addNode((""+(element - 1))));

        Node tmp =  graph.getHeader().getNext();
        Node tmp2 = graph.getHeader().getNext();
         
        for ( int i = 0; i < graph.getSizeNode() - 1; i++ ) {
            
            graph.connect(tmp, tmp.getNext(), 10, 0);
            System.out.println(tmp.getName());
            tmp = tmp.getNext();
        }
        
        tmp =  graph.getHeader().getNext();
        
        for ( int i = 0; i < graph.getSizeNode() - 1; i++ ) {
            
            tmp2 = graph.getHeader().getNext();
            int random = (int) ( Math.random() * graph.getSizeNode() );

            for ( int j = 0; j < random; j++ ) {
                tmp2 = tmp2.getNext();
            }
            
            if ( tmp != tmp2 && tmp != tmp.getNext() && tmp != graph.getSink() && tmp2 != graph.getSource() )
                graph.connect(tmp, tmp2, random, 0);
            
            tmp = tmp.getNext();
        }
        int test = 0;
        tmp =  graph.getHeader().getNext();
//        for ( int i = 0; i < graph.getSizeNode() ; i++ ) {
//            System.out.print(tmp.getName()+" ");
//            System.out.println(tmp.getEdge(tmp));
//            test += tmp.sizeEdge;
//            tmp = tmp.getNext();
//        }
//        System.out.println(test);
    }
    
    public static void main(String[] args) {
        
        makeTestGraph(10);
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
