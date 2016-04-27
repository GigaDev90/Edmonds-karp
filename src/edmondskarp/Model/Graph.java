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
public class Graph {

    private ArrayList<Node> nodes;
    private Node source;
    private Node sink;
    private Visit visit;

    public Graph() {
        nodes = new ArrayList<>();
        source = null;
        sink = null;
        visit = new BFSVisit();
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
        } else if (a.getEdgeBNotResidual(adjacent) != null) {
            return null;
        } else if (adjacent.getEdgeBNotResidual(a) != null) {
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

        if (!a.removeEdge(b)) {
            System.out.println("Errore aliminazione");
        }
        if (!b.removeEdge(edge.getInverse())) //remove residual
        {
            System.out.println("Errore aliminazione");
        }
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
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
        return visit.visitGraph(source);
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
            //System.out.println("Min " + min);
            tmpNode = sink;

            while (tmpNode.getParent() != null) {
                Edge tmpEdge = tmpNode.getParent().getEdgeB(tmpNode);
                tmpEdge.setFlow(tmpEdge.getFlow() + min);
                tmpNode.getEdgeB(tmpNode.getParent()).setFlow(tmpNode.getEdgeB(tmpNode.getParent()).getFlow() - min);
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
                } else {
                    edge.setFlow(edge.getCapacity());
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
            int min = 0;
            if (tmpNode.getEdgeB(sink) != null) {
                min = tmpNode.getEdgeB(sink).getResidual();
            } else {
                return false;
            }

            while (tmpNode.getParent() != null) {
                if (tmpNode.getParent().getEdgeB(tmpNode) != null
                        && tmpNode.getParent().getEdgeB(tmpNode).getResidual() < min) {

                    min = tmpNode.getParent().getEdgeB(tmpNode).getResidual();
                    //System.out.println("Min " + min);
                }
                tmpNode = tmpNode.getParent();
            }

            tmpNode = sink;

            while (tmpNode.getParent() != null) {
                Edge tmpEdge = tmpNode.getParent().getEdgeB(tmpNode);
                if ( tmpEdge != null) {
                    tmpEdge.setFlow(tmpEdge.getFlow() + min);
                    tmpEdge.setIsDiscovered(false);
                    tmpNode.getEdgeB(tmpNode.getParent()).setFlow(tmpNode.getEdgeB(tmpNode.getParent()).getFlow() - min);
                    tmpNode = tmpNode.getParent();
                } else {
                    return false;
                }
            }

            return true;
        }
        return false;
    }
}
