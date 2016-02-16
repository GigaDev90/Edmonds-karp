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
        
        for ( int i = 0; i < 100; i++ ) {
            graph.addNode(""+i);
            //System.out.println(graph.getSizeNode());
        }
        System.out.println(graph.getSizeNode());

        Node tmp =  graph.getHeader().getNext();
        Node tmp2 = graph.getHeader().getNext();
         
        for ( int i = 0; i < 99; i++ ) {

            tmp2 = graph.getHeader().getNext();
            int random = (int) ( Math.random() * 99 );

            for ( int j = 0; j < random; j++ ) {
                tmp2 = tmp2.getNext();
            }
            graph.connect(tmp, tmp.getNext(), 0, 0);
//            if (tmp != tmp2)
//                graph.connect(tmp, tmp2);
//            tmp = tmp.getNext();
            tmp =  tmp.getNext();
        }
        
        tmp =  graph.getHeader().getNext();
        for ( int i = 0; i < 100; i++ ) {
            
            tmp2 = graph.getHeader().getNext();
            int random = (int) ( Math.random() * 99 );

            for ( int j = 0; j < random; j++ ) {
                tmp2 = tmp2.getNext();
            }
            
            if (tmp != tmp2 && tmp != tmp.getNext())
                graph.connect(tmp, tmp2, 0, 0);
            
            tmp = tmp.getNext();
        }
        
        System.out.println(tmp2.getName());
        graph.removeNode(tmp2);
        tmp =  graph.getHeader().getNext();
        graph.BFSVisit();
        
      
      
//        tmp = graph.getHeader().getNext();
//        while (tmp.getHeader().getNext() != null ) {
//            System.out.println(tmp.sizeEdge);
//            graph.disconnect(tmp.getHeader().getNext());
//        }
//        
        System.out.println(graph.getSizeNode());
        
//        
//        for ( int i = 0; i < 100000; i++ ) {
//            graph.removeNode(graph.getHeader().getNext());
//            System.out.println(graph.getSizeNode());
//        }
        
       
        
        
    }
    
}
