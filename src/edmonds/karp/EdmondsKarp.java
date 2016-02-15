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
    public static void main(String[] args) {
        
        
        //TEST
        Graph graph =  new Graph();
        
        for ( int i = 0; i < 100000; i++ ) {
            graph.addNode(""+i);
            //System.out.println(graph.getSizeNode());
        }
        System.out.println(graph.getSizeNode());

        Node tmp =  graph.getHeader().getNext();
        for ( int i = 0; i < 99999; i++ ) {
            graph.connect(graph.getHeader().getNext(), tmp.getNext());
            tmp = tmp.getNext();
            
        }
      
        tmp = graph.getHeader().getNext();
        while (tmp.getHeader().getNext() != null ) {
            System.out.println(tmp.sizeEdge);
            graph.disconnect(tmp.getHeader().getNext());
        }
        
        System.out.println(graph.getSizeNode());
        
//        
//        for ( int i = 0; i < 100000; i++ ) {
//            graph.removeNode(graph.getHeader().getNext());
//            System.out.println(graph.getSizeNode());
//        }
        
       
        
        
    }
    
}
