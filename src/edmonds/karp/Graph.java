/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmonds.karp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author gabriele
 */
public class Graph {

    private ArrayList<Node> nodes;
    private Node source;
    private Node sink;

    public Graph() {
        nodes = new ArrayList<>();
        source = null;
        sink = null;
    }

    public Node getSource() {
        return source;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public int getSize() {
        return nodes.size();
    }

    public boolean setSource(Node source) {
        for (Edge edge : source.getEdges()) {
            if (edge.isResidual() && edge.getNodeA() == source) {
                return false;
            }
        }

        if (this.source != null) {
            this.source.setSource(false);
        }

        this.source = source;
        this.source.setSource(true);

        return true;
    }

    public Node getSink() {
        return sink;
    }

    public boolean setSink(Node sink) {
        for (Edge edge : sink.getEdges()) {
            if (!edge.isResidual() && edge.getNodeA() == sink) {
                return false;
            }
        }

        if (this.sink != null) {
            this.sink.setSink(false);
        }

        this.sink = sink;
        this.sink.setSink(true);

        return true;
    }

    public Node getNode(String name) {
        for (Node node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    public Node addNode(String name) {
        Node node = new Node(name);
        nodes.add(node);

        return node;
    }

    public boolean removeNode(Node node) {
        if (nodes.isEmpty()) {
            System.out.println("rimozione fallita: non sono presenti nodi");
            return false;
        }

        for (int i = 0; i < node.getEdges().size(); i++) {
            disconnect(node.getEdges().get(i));
        }

        if (node == source) {
            source = null;
        } else if (node == sink) {
            sink = null;
        }

        nodes.remove(node);
        return true;
    }

    public Edge connect(Node a, Node adjacent, int capacity, int flow) {
        if (nodes.size() == 1 || capacity < flow) {
            System.out.println("connessione fallita: esiste un solo nodo o valore flusso non consentito");
            return null;
        } else if (adjacent == source) {
            System.out.println("connessione fallita: la sorgente non può avere archi entranti");
            return null;
        } else if (a == sink) {
            System.out.println("connessione fallita: il pozzo non può avere archi uscenti");
            return null;
        } else if (a.getEdgeB(adjacent) != null && !a.getEdgeB(adjacent).isResidual()) {
            return null;
        }

        Edge edge = new Edge(a, adjacent);
        edge.setCapacity(capacity);
        edge.setIsResidual(false);
        edge.setFlow(flow);
        a.addEdge(edge);

        Edge edgeRes = new Edge(adjacent, a);
        edgeRes.setCapacity(capacity);
        edgeRes.setFlow(capacity);
        edgeRes.setIsResidual(true);
        adjacent.addEdge(edgeRes);

        edge.setInverse(edgeRes);
        edgeRes.setInverse(edge);

        return edge;
    }

    public Edge connect(String a, String b, int capacity, int flow) {
        return connect(getNode(a), getNode(b), capacity, flow);
    }

    public void disconnect(Edge edge) {
        if (nodes.size() == 1) {
            System.out.println("disconnessione fallita: esiste un solo nodo");
            return;
        }

        Node a = edge.getNodeA();
        Node b = edge.getNodeB();

        a.removeEdge(b);
        b.removeEdge(edge); //remove residual
    }

    public void BFVisitClear() {
        for (Node node : nodes) {
            node.setParent(null);
            node.setIsDiscovered(false);
        }
    }

    public boolean BFSVisit() {
        if (source == null) {
            return false;
        }

        BFVisitClear();
        Queue<Node> q = new LinkedList<>();
        q.add(source);
        source.setIsDiscovered(true);
        source.setParent(null);

        System.out.println("BFS Path++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        while (!q.isEmpty()) {

            Node current = q.remove();

            System.out.println("parent " + current.getName());

            for (Edge edge : current.getEdges()) {
                if ( !edge.getNodeB().isDiscovered() && edge.getResidual() > 0 ) {
                    edge.getNodeB().setIsDiscovered(true);
                    edge.getNodeB().setParent(current);
                    q.add(edge.getNodeB());
                    System.out.println("Son " + edge.getNodeB().getName());
                }
            }
        }
        return true;
    }

    public boolean EdmondsKarp() {
        if (source == null || sink == null) {
            System.out.println("sorgente o pozzo non sono stati assegnati");
            return false;
        }

        edmondsKarpClear();
        BFSVisit();

        while (sink.getParent() != null) {

            Node tmpNode = sink.getParent();
            int min = tmpNode.getEdgeB(sink).getResidual();

            while (tmpNode.getParent() != null) {
                if (tmpNode.getParent().getEdgeB(tmpNode).getResidual() < min) {
                    min = tmpNode.getParent().getEdgeB(tmpNode).getResidual();
                }

                tmpNode = tmpNode.getParent();
            }
            System.out.println("Min " + min);
            tmpNode = sink;

            while (tmpNode.getParent() != null) {
                Edge tmpEdge = tmpNode.getParent().getEdgeB(tmpNode);
                tmpEdge.setFlow(tmpEdge.getFlow() + min);
                tmpNode.getEdgeB(tmpNode.getParent()).setFlow(tmpEdge.getFlow() - min);
                System.out.println("Path " + tmpNode.getName());
                tmpNode = tmpNode.getParent();
            }

            BFSVisit();
        }
        return true;
    }

    public void edmondsKarpClear() {
        for (Node node : nodes) {
            for (Edge edge : node.getEdges()) {
                if (!edge.isResidual()) {
                    edge.setFlow(0);
                }
                edge.setIsDiscovered(false);
            }
        }
    }

    public void selectPath() {
        Node tmpNode = sink;

        while (tmpNode.getParent() != null) {
            tmpNode.getParent().getEdgeB(tmpNode).setIsDiscovered(true);
            tmpNode = tmpNode.getParent();
        }
    }

    public boolean isSelected() {
        return sink.getParent().getEdgeB(sink).isDiscovered();
    }

    public boolean isSafe() {
        return sink != null && sink.getParent() != null && sink.getParent().getEdgeB(sink) != null;
    }

    public boolean EdmondsKarpOneStep() {
        if (source == null || sink == null) {
            System.out.println("sorgente o pozzo non sono stati assegnati");
            return false;
        }

        if (sink.getParent() != null) {

            Node tmpNode = sink.getParent();
            int min = tmpNode.getEdgeB(sink).getResidual();

            while (tmpNode.getParent() != null) {
                if (tmpNode.getParent().getEdgeB(tmpNode).getResidual() < min) {
                    min = tmpNode.getParent().getEdgeB(tmpNode).getResidual();
                    System.out.println("Min " + min);
                    System.out.println(tmpNode.getParent().getEdgeB(tmpNode).isResidual());
                }
                tmpNode = tmpNode.getParent();
            }

            tmpNode = sink;

            while (tmpNode.getParent() != null) {
                Edge tmpEdge = tmpNode.getParent().getEdgeB(tmpNode);
                tmpEdge.setFlow(tmpEdge.getFlow() + min);
                tmpEdge.setIsDiscovered(false);
                tmpNode.getEdgeB(tmpNode.getParent()).setFlow(Math.abs( (tmpNode.getEdgeB(tmpNode.getParent()).getFlow() - min ) ) ); //temp
                System.out.println("Path " + tmpNode.getName());
                System.out.println("meno" + (tmpEdge.getFlow() - min));
                //System.out.println("Min " + min);
                tmpNode = tmpNode.getParent();
            }

            return true;
        }
        return false;
    }
}
