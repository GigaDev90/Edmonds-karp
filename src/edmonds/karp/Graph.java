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
public class Graph {

    private Node header;
    private int sizeNode;

    public Graph() {

        header = new Node("header");
        sizeNode = 0;
    }

    public void addNode(String name) {

        Node node = new Node(name);
        node.setNext(header.getNext());
        header.setNext(node);
        sizeNode++;
    }

    public void removeNode(Node node) {

        if ( sizeNode == 0) {
            System.out.println("rimozione fallita: non sono presenti nodi");
            return;
        }

        for ( int i = 1; i < node.sizeEdge; i++ )
            disconnect(node.getHeader().getNext());

        Node prevTmp = header;
        for ( int i = 1; i <= sizeNode; i++ ) {

            if ( prevTmp.getNext() == node ) {
                prevTmp.setNext(node.getNext());
                break;
            } else
                prevTmp = prevTmp.getNext();
        }

        sizeNode--;
    }

    public void connect(Node a, Node adjacent) {

        if (sizeNode == 1) {
            System.out.println("connessione fallita: esiste un solo nodo");
            return;
        }

        Edge edge = new Edge (a, adjacent);
        a.addEdge(edge);

        Edge edgeRes = new Edge (adjacent, a);
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

}

class Node {

    private Edge header;
    private Node next;
    private String name;
    int sizeEdge;

    public Node(String name) {

        header = new Edge(null, null);
        next = null;
        this.name = name;
        sizeEdge = 0;
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

        for ( int i = 1; i <= sizeEdge; i++ ) {
            Edge tmp = header;
            if ( tmp.getNext() != null && tmp.getNext().getNodeB() == adjacent ) {
                tmp.setNext(tmp.getNext().getNext());

            } else
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