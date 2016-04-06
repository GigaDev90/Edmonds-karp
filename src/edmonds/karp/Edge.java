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
    private Edge inverse;
    private boolean isResidual;
    private boolean isDiscovered;
    private int capacity;
    private int flow;

    public Edge(Node a, Node b) {
        nodeA = a;
        nodeB = b;
        isResidual = false;
        isDiscovered = false;
    }

    public void setInverse(Edge inverse) {
        this.inverse = inverse;
    }

    public Edge getInverse() {
        return inverse;
    }

    public void setIsDiscovered(boolean b) {
        isDiscovered = b;
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getResidual() {
        return capacity - flow;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        if (flow < 0) {
            System.out.println("minore di 0");
        }
        System.out.println("flow "+flow);
        //inverse.setFlow(-flow);//to verify
        this.flow = flow;
    }

    public boolean isResidual() {
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
