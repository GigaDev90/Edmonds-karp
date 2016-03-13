/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmonds.karp;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author gabriele
 */
public class Graph {

    private Node header;
    private int sizeNode;
    private Node source;
    private Node sink;

    public Graph() {

        header = new Node("header");
        sizeNode = 0;
        source = null;
        sink = null;
    }
    
    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getSink() {
        return sink;
    }

    public void setSink(Node sink) {
        this.sink = sink;
    }

    public Node addNode(String name) {

        Node node = new Node(name);
        node.setNext(header.getNext());
        header.setNext(node);
        sizeNode++;
        
        return node;
    }

    public void removeNode(Node node) {

        if ( sizeNode == 0) {
            System.out.println("rimozione fallita: non sono presenti nodi");
            return;
        }
        int size = node.sizeEdge;
        System.out.println("sizeEdge: "+node.sizeEdge);
        Edge tmpEdge = node.getHeader().getNext();
        for ( int i = 0; i < size; i++ ) {
            disconnect(tmpEdge);
            tmpEdge = tmpEdge.getNext();

        }
            
        System.out.println("sizeEdge: "+node.sizeEdge);
        Node prevTmp = header;
        for ( int i = 0; i < sizeNode; i++ ) {

            if ( prevTmp.getNext() == node ) {
                prevTmp.setNext(node.getNext());
                break;
            } else
                prevTmp = prevTmp.getNext();
        }

        sizeNode--;
    }

    public void connect(Node a, Node adjacent, int capacity, int flow) {

        if (sizeNode == 1 || capacity < flow) {
            System.out.println("connessione fallita: esiste un solo nodo o valore flusso non consentito");
            return;
        } else if ( adjacent == source ) {
            System.out.println("connessione fallita: la sorgente non può avere archi entranti");
            return;
        } else if ( a == sink ) {
            System.out.println("connessione fallita: il pozzo non può avere archi uscenti");
            return;
        }

        Edge edge = new Edge (a, adjacent);
        edge.setCapacity(capacity);
        edge.setIsResidual(false);
        edge.setFlow(flow);
        a.addEdge(edge);

        Edge edgeRes = new Edge (adjacent, a);
        edgeRes.setCapacity(capacity);
        edgeRes.setFlow(flow);
        edgeRes.setIsResidual(true);
        adjacent.addEdge(edgeRes);
    }

    public void disconnect(Edge edge) {
        if ( sizeNode == 1) {
            System.out.println("disconnessione fallita: esiste un solo nodo");
            return;
        }

        Node a = edge.getNodeA();
        Node b = edge.getNodeB();

        a.removeEdge(b);
        b.removeEdge(a);
    }
    
    public void BFSVisit(Node root) {

        Node tmp = header.getNext();
        for ( int i = 0; i < sizeNode; i++ ) {
            tmp.setParent(null);
            tmp.setIsDiscovered(false);
            tmp = tmp.getNext();
        }

        Queue<Node> q = new LinkedList();
        q.add(root);
        root.setIsDiscovered(true);
        root.setParent(null);

        while ( !q.isEmpty() ) {
            
            Node current = q.remove();
            Edge edge = current.getHeader().getNext();
            
           // System.out.println("parent "+current.getName());
            
            for ( int i = 0; i < current.sizeEdge; i++ ) {
                
                if ( !edge.getNodeB().isDiscovered() && edge.getResidual() > 0 ) {
                    edge.getNodeB().setIsDiscovered(true);
                    edge.getNodeB().setParent(current);
                    q.add(edge.getNodeB());
                    //System.out.println(edge.getNodeB().getName());
                }
                edge = edge.getNext();
            }
          //  System.out.println("BFS ");
        }  
    }

    public Node getHeader() {
        return header;
    }

    public void setHeader(Node header) {
        this.header = header;
    }

    public int getSizeNode() {
        return sizeNode;
    }

    public void setSizeNode(int sizeNode) {
        this.sizeNode = sizeNode;
    }
    
    public void EdmondsKarp() {

        if ( source == null || sink == null) {
            System.out.println("sorgente o pozzo non sono stati assegnati");
            return;
        }

        Node tmpNode = header.getNext();
        for ( int i = 0; i < sizeNode; i++ ) {
            Edge tmpEdge = tmpNode.getHeader().getNext();
            for ( int j = 0; j < tmpNode.sizeEdge; j++) {
                tmpEdge.setFlow(0);
                tmpEdge = tmpEdge.getNext();
            }
            tmpNode = tmpNode.getNext();
        }

        BFSVisit(source);
        
        while ( sink.getParent() != null ) {
            
            tmpNode = sink.getParent();
            int min = tmpNode.getEdge(sink).getResidual();
           // System.out.println("min "+min);
            
            while (tmpNode.getParent() != null) {
                if (tmpNode.getParent().getEdge(tmpNode).getResidual() < min )
                    min = tmpNode.getParent().getEdge(tmpNode).getResidual();
               // System.out.println(tmpNode);
                tmpNode = tmpNode.getParent();
            }
            
            tmpNode = sink;

            while (tmpNode.getParent() != null) {
                tmpNode.getParent().getEdge(tmpNode).setFlow( tmpNode.getParent().getEdge(tmpNode).getFlow() + min );
                tmpNode.getEdge(tmpNode.getParent()).setFlow( tmpNode.getParent().getEdge(tmpNode).getFlow() - min );
                tmpNode = tmpNode.getParent();
            }
           // System.out.println("test");
        BFSVisit(source);            
        }
    }

}

class Node {

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

class Edge {

    private Node nodeA;
    private Node nodeB;
    private Edge next;
    private boolean isResidual;
    private int capacity;
    private int flow;

    public Edge(Node a, Node b) {
        nodeA = a;
        nodeB = b;
        next = null;
        isResidual = false;
        setCapacity(-1);
        setFlow(-1);
    }

    public Edge getNext() {
        return next;
    }

    public void setNext(Edge next) {
        this.next = next;
    }

    public int getCapacity() {
        return capacity;
    }
    
    public int getResidual() {
        return capacity - flow;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;//TODO controllo, scalare l'arco residuo
    }

    public boolean isIsResidual() {
        return isResidual;
    }

    public void setIsResidual(boolean isResidual) {
        this.isResidual = isResidual;
    }
    
    public Node getNodeA() {
        return nodeA;
    }

    public void setNodeA(Node nodeA) {
        this.nodeA = nodeA;
    }

    public Node getNodeB() {
        return nodeB;
    }

    public void setNodeB(Node nodeB) {
        this.nodeB = nodeB;
    }
}