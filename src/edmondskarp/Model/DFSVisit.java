/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmondskarp.Model;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * @author gabriele
 */
public class DFSVisit extends Visit {

    @Override
    public boolean visitGraph(Node source) {
        Deque<Node> stack = new ArrayDeque<>();
        stack.push(source);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (!node.isDiscovered()) {
                node.setIsDiscovered(true);
                for (Edge edge : node.getEdges()) {
                    if (!edge.getNodeB().isDiscovered() && edge.getResidual() > 0) {
                        edge.getNodeB().setParent(node);
                        stack.push(edge.getNodeB());
                    }
                }
            }
        }
        return true;
    }

}
