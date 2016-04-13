/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmondskarp.Model;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author gabriele
 */
public class BFSVisit extends Visit {

    @Override
    public boolean visitGraph(Node source) {
        Queue<Node> q = new LinkedList<>();
        q.add(source);
        source.setIsDiscovered(true);
        source.setParent(null);
        while (!q.isEmpty()) {
            Node current = q.remove();
            for (Edge edge : current.getEdges()) {
                if (!edge.getNodeB().isDiscovered() && edge.getResidual() > 0) {
                    edge.getNodeB().setIsDiscovered(true);
                    edge.getNodeB().setParent(current);
                    q.add(edge.getNodeB());
                }
            }
        }
        return true;
    }

}
