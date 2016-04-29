/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmondskarp.Gui;

import edmondskarp.Model.Node;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author gabriele
 */
public class Circle extends MyShape {

    ArrayList<Arrow> arrowsFrom;
    ArrayList<Arrow> arrowsTo;
    private boolean isUpdate;
    private Node node;

    public Circle() {
        shape = new Ellipse2D.Double();
        points = new Point2D[2];
        select = false;
        arrowsFrom = new ArrayList<>();
        arrowsTo = new ArrayList<>();
        pointText = new Point2D[2];
        isUpdate = false;
        c = Color.BLACK;
    }

    public String getName() {
        return node.getName();
    }

    public ArrayList getArrowFrom() {
        return arrowsFrom;
    }

    public Node getNode() {
        return node;
    }

    public void addNode(Node node) {
        this.node = node;
    }

    public void addArrowFrom(Arrow arrow) {
        arrowsFrom.add(arrow);
    }

    public void addArrowTo(Arrow arrow) {
        arrowsTo.add(arrow);
    }

    public void removeArrowFrom(Arrow arrow) {
        arrowsFrom.remove(arrow);
    }

    public void removeArrowTo(Arrow arrow) {
        arrowsTo.remove(arrow);
    }

    public void removeArrows() {
        for (Arrow arrow : arrowsFrom) {
            arrow.getTo().removeArrowTo(arrow);
        }

        for (Arrow arrow : arrowsTo) {
            arrow.getFrom().removeArrowFrom(arrow);
        }

        arrowsFrom.clear();
        arrowsTo.clear();
    }

    public void setFirstPoint(Point2D point) {
        Point2D temp1 = new Point2D.Double(point.getX() + Config.getConfig().getDimCircle(), point.getY() + Config.getConfig().getDimCircle());
        Point2D temp2 = new Point2D.Double(point.getX() - Config.getConfig().getDimCircle(), point.getY() - Config.getConfig().getDimCircle());
        Point2D temp3 = new Point2D.Double((int) point.getX() - 7, (int) point.getY() + 7);
        points[1] = temp1;
        points[0] = temp2;
        pointText[0] = temp3;
        ((Ellipse2D) shape).setFrameFromDiagonal(points[0], points[1]);
    }

    public Point2D getCenter() {
        Point2D tmp = new Point2D.Double(((Ellipse2D) shape).getCenterX(), ((Ellipse2D) shape).getCenterY());
        return tmp;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke((float) (Config.getConfig().getStrokeCircle() * 0.1)));
        if (Config.getConfig().isNeedUpdate()) {
            setFirstPoint(getCenter());
        }
        g2.setColor(c);
        g2.draw(shape);
        if (select) {
            double scale2 = Config.getConfig().getDimCircle() - 10;
            Point2D temp0 = this.getCenter();
            Point2D temp1 = new Point2D.Double(temp0.getX() + scale2, temp0.getY() + scale2);
            Point2D temp2 = new Point2D.Double(temp0.getX() - scale2, temp0.getY() - scale2);
            Ellipse2D circle = new Ellipse2D.Double();
            circle.setFrameFromDiagonal(temp1, temp2);
            g2.setColor(Color.BLUE);
            g2.draw(circle);
            g2.setColor(c);
        }

        g2.setStroke(new BasicStroke(1));
    }

    public void drawArrows(Graphics2D g2) {
        g2.setStroke(new BasicStroke((float) (Config.getConfig().getStrokeArrow() * 0.1)));
        for (Arrow arrow : arrowsFrom) {
            arrow.draw(g2);
            arrow.drawText(g2);
        }

    }

    @Override
    public void drawText(Graphics2D g2) {
        g2.setFont(new Font("Ubuntu", Font.HANGING_BASELINE, 20));
        if (node.getName().length() > 2) {
            g2.drawString(node.getName(), (int) pointText[0].getX() - 10, (int) pointText[0].getY());
        } else if (node.getName().length() > 1) {
            g2.drawString(node.getName(), (int) pointText[0].getX() - 5, (int) pointText[0].getY());
        } else {
            g2.drawString(node.getName(), (int) pointText[0].getX(), (int) pointText[0].getY());
        }

        if (node.isSource()) {
            g2.setFont(new Font("Ubuntu", Font.BOLD, 15));
            g2.drawString("S", (int) pointText[0].getX() + 4, (int) pointText[0].getY() - 18);
            g2.setFont(new Font("Ubuntu", Font.HANGING_BASELINE, 15));
        } else if (node.isSink()) {
            g2.setFont(new Font("Ubuntu", Font.BOLD, 15));
            g2.drawString("T", (int) pointText[0].getX() + 4, (int) pointText[0].getY() - 18);
            g2.setFont(new Font("Ubuntu", Font.HANGING_BASELINE, 15));
        }
    }

    public void needUpdate() {
        isUpdate = false;
    }

    public void updateArrow() {
        if (!isUpdate || Config.getConfig().isNeedUpdate()) {
            for (Arrow arrow : arrowsFrom) {
                arrow.update();
            }

            for (Arrow arrow : arrowsTo) {
                arrow.update();
            }
            isUpdate = true;
        }
    }

    public Arrow checkForArrow(Point2D point) {
        for (Arrow arrow : arrowsFrom) {

            if (arrow.getSelectedEdge(point) == 1 || arrow.getSelectedEdge(point) == 0) {
                return arrow;
            }
        }

        return null;
    }
}
