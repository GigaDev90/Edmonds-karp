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
    public int size;
    private Node source;
    private Node sink;

    public Graph() {

        header = new Node("header");
        size = 0;
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
    
    public Node getNode(String name) {
    	
    	Node tmpNode =  header.getNext();
    	for ( int i = 0; i <= size; i++ ) {
    		if (tmpNode.getName().equals(name))
    			return tmpNode;
    		
    		tmpNode = tmpNode.getNext();
    	}
    	
    	return null;
    }

    public Node addNode(String name) {

        Node node = new Node(name);
        node.setNext(header.getNext());
        header.setNext(node);
        size++;
        
        return node;
    }

    public void removeNode(Node node) {

        if ( size == 0) {
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
        for ( int i = 0; i < this.size; i++ ) {

            if ( prevTmp.getNext() == node ) {
                prevTmp.setNext(node.getNext());
                break;
            } else
                prevTmp = prevTmp.getNext();
        }

        this.size--;
    }

    public Edge connect(Node a, Node adjacent, int capacity, int flow) {

        if (size == 1 || capacity < flow) {
            System.out.println("connessione fallita: esiste un solo nodo o valore flusso non consentito");
            return null;
        } else if ( adjacent == source ) {
            System.out.println("connessione fallita: la sorgente non può avere archi entranti");
            return null;
        } else if ( a == sink ) {
            System.out.println("connessione fallita: il pozzo non può avere archi uscenti");
            return null;
        }

        Edge edge = new Edge (a, adjacent);
        edge.setCapacity(capacity);
        edge.setIsResidual(false);
        edge.setFlow(flow);
        a.addEdge(edge);

        Edge edgeRes = new Edge (adjacent, a);
        edgeRes.setCapacity(capacity);
        edgeRes.setFlow(capacity);
        edgeRes.setIsResidual(true);
        adjacent.addEdge(edgeRes);
        
        return edge;
    }

    public void disconnect(Edge edge) {
        if ( size == 1) {
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
        for ( int i = 0; i < size; i++ ) {
            tmp.setParent(null);
            tmp.setIsDiscovered(false);
            tmp = tmp.getNext();
        }

        Queue<Node> q = new LinkedList<Node>();
        q.add(root);
        root.setIsDiscovered(true);
        root.setParent(null);
        
        System.out.println("BFS Path++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        while ( !q.isEmpty() ) {
            
            Node current = q.remove();
            Edge edge = current.getHeader().getNext();
            
            System.out.println("parent "+current.getName());
            
            for ( int i = 0; i < current.sizeEdge; i++ ) {
                
                if ( !edge.getNodeB().isDiscovered() && edge.getResidual() > 0 ) {
                    edge.getNodeB().setIsDiscovered(true);
                    edge.getNodeB().setParent(current);
                    q.add(edge.getNodeB());
                    System.out.println("Son "+edge.getNodeB().getName());
                }
                edge = edge.getNext();
            }
        }  
    }

    public Node getHeader() {
        return header;
    }

    public void setHeader(Node header) {
        this.header = header;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public void EdmondsKarp() {

        if ( source == null || sink == null) {
            System.out.println("sorgente o pozzo non sono stati assegnati");
            return;
        }

        Node tmpNode = header.getNext();
        for ( int i = 0; i < size; i++ ) {
            Edge tmpEdge = tmpNode.getHeader().getNext();
            for ( int j = 0; j < tmpNode.sizeEdge; j++) {
            	if ( !tmpEdge.isIsResidual() )
            		tmpEdge.setFlow(0);
            	
                tmpEdge = tmpEdge.getNext();
            }
            tmpNode = tmpNode.getNext();
        }

        BFSVisit(source);
        
        while ( sink.getParent() != null ) {
            
            tmpNode = sink.getParent();
            int min = tmpNode.getEdge(sink).getResidual();
           
            
            while (tmpNode.getParent() != null) {
                if (tmpNode.getParent().getEdge(tmpNode).getResidual() < min )
                    min = tmpNode.getParent().getEdge(tmpNode).getResidual();

                tmpNode = tmpNode.getParent();
            }
            System.out.println("Min "+min);
            tmpNode = sink;

            while (tmpNode.getParent() != null) {
            	Edge tmpEdge = tmpNode.getParent().getEdge(tmpNode);
                tmpNode.getParent().getEdge(tmpNode).setFlow( tmpNode.getParent().getEdge(tmpNode).getFlow() + min );
                tmpNode.getParent().getEdge(tmpNode).setInfo( tmpNode.getParent().getEdge(tmpNode).getFlow()+"/10");
                //tmpNode.getEdge(tmpNode.getParent()).setFlow( tmpNode.getParent().getEdge(tmpNode).getFlow() - min );
                System.out.println("Path "+tmpNode.getName());
                tmpNode = tmpNode.getParent();
            }
        
        BFSVisit(source);            
        }
    }
}