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
public class Edge {

    private Node nodeA;
    private Node nodeB;
    private Edge next;
    private boolean isResidual;
    private boolean isDiscovered;
    private int capacity;
    private int flow;

    public Edge(Node a, Node b) {
        nodeA = a;
        nodeB = b;
        next = null;
        isResidual = false;
        isDiscovered = false;
        setCapacity(-1);
        setFlow(-1);
    }
    
    public void setIsDiscovered(boolean b) {
        isDiscovered = b;
    }
    
    public boolean isDiscovered() { return isDiscovered; }

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
