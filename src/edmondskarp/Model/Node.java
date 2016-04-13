/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmondskarp.Model;

import java.util.ArrayList;

/**
 *
 * @author gabriele
 */
public class Node {

    private ArrayList<Edge> edges;
    private Node parent;
    private String name;
    private boolean isSource;
    private boolean isSink;
    private boolean isDiscovered;

    public Node(String name) {
        edges = new ArrayList<>();
        parent = null;
        isDiscovered = false;
        this.name = name;
        isSource = false;
        isSink = false;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setSource(boolean b) {
        isSource = b;
    }

    public void setSink(boolean b) {
        isSink = b;
    }

    public boolean isSource() {
        return isSource;
    }

    public boolean isSink() {
        return isSink;
    }

    public Edge getEdgeB(Node adj) {
        for (Edge edge : edges) {
            if (edge.getNodeB() == adj) {
                return edge;
            }
        }
        return null;
    }

    public Edge getEdgeA(Node adj) {
        for (Edge edge : edges) {
            if (edge.getNodeA() == adj) {
                return edge;
            }
        }
        return null;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public boolean removeEdge(Edge edge) {
        return edges.remove(edge);
    }

    public boolean removeEdge(Node adjacent) {
        if (edges.isEmpty()) {
            System.out.println("Rimozione fallita: non sono presenti archi");
            return false;
        }

        for (Edge edge : edges) {
            if (edge.getNodeB() == adjacent) {
                edges.remove(edge);
                return true;
            }
        }
        return false;
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
