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
public class Node {
    
    private Edge header;
    private Node next;
    private Node parent;
    private String name;
    private boolean isDiscovered;
    int sizeEdge;

    public Node(String name) {

        header = new Edge(null, null);
        next = null;
        parent =  null;
        isDiscovered = false;
        this.name = name;
        sizeEdge = 0;
    }

    public Edge getEdge(Node adj) {
        
        Edge tmp = header.getNext();
        while ( tmp != null && tmp.getNodeB() != adj ) 
            tmp = tmp.getNext();
        
        return tmp;
        
    }

    public void addEdge(Edge edge) {

        edge.setNext(header.getNext());
        header.setNext(edge);

        sizeEdge++;
    }

    public void removeEdge(Node adjacent) {

        if ( sizeEdge == 0) {
            System.out.println("Rimozione fallita: non sono presenti archi");
        }

        Edge tmp = header;
        for ( int i = 0; i < sizeEdge - 1; i++ ) {
            
            if ( tmp.getNext().getNodeB() == adjacent ) 
                tmp.setNext(tmp.getNext().getNext());

          
            tmp = tmp.getNext();
        }

        sizeEdge--;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Edge getHeader() {
        return header;
    }

     public String getName() {
        return name;
    }
     
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public boolean isDiscovered() {
        return isDiscovered;
    }

    public void setIsDiscovered(boolean isDiscovered) {
        this.isDiscovered = isDiscovered;
    }
}
