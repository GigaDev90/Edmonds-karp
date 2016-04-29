/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmondskarp.Gui;

import edmondskarp.Model.Edge;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author gabriele
 */
public class Arrow extends MyShape {

    private Circle from;
    private Circle to;
    private final Line2D.Double centerPointer1;
    private final Line2D.Double centerPointer2;
    private final Line2D.Double BAArrow;
    private final Line2D.Double BAPointer1;
    private final Line2D.Double BAPointer2;
    private final Line2D.Double ABArrow;
    private final Line2D.Double ABPointer1;
    private final Line2D.Double ABPointer2;
    private final Point2D[] textInverseABArrow;
    private final Point2D[] textInverseBAArrow;
    protected Edge edge;

    public Arrow(Circle from, Circle to) {
        shape = new Line2D.Double();
        centerPointer1 = new Line2D.Double();
        BAPointer1 = new Line2D.Double();
        BAPointer2 = new Line2D.Double();
        centerPointer2 = new Line2D.Double();
        ABPointer1 = new Line2D.Double();
        ABPointer2 = new Line2D.Double();
        BAArrow = new Line2D.Double();
        ABArrow = new Line2D.Double();
        pointText = new Point2D[2];
        points = new Point2D.Double[2];
        textInverseABArrow = new Point2D.Double[2];
        textInverseBAArrow = new Point2D.Double[2];
        select = false;
        this.from = from;
        this.to = to;
        c = Color.BLACK;
        update();
    }

    public Circle getFrom() {
        return from;
    }

    public Circle getTo() {
        return to;
    }

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    @Override
    public void drawText(Graphics2D g2) {
        double angle = Math.asin(calcSinx(from.getCenter(), to.getCenter()));
        Point2D ABtmp = textInverseABArrow[1];
        Point2D BAtmp = textInverseBAArrow[1];
        Point2D centerText = pointText[1];

        if (from.getCenter().getX() < to.getCenter().getX()) {
            angle = -angle;
            ABtmp = textInverseABArrow[0];
            BAtmp = textInverseBAArrow[0];
            centerText = pointText[0];
        }

        if (Config.getConfig().getResidualMode()) {
            if (edge.getResidual() != 0 && edge.getInverse().getResidual() != 0) {
                g2.setColor(getColor(edge));
                drawRotateText(g2, ABtmp, "<" + edge.getResidual() + ">", angle, false);
                g2.setColor(getColor(edge.getInverse()));
                drawRotateText(g2, BAtmp, "<" + edge.getInverse().getResidual() + ">", angle, false);
            } else if (edge.getInverse().getResidual() != 0) {
                Circle tmp = from; //hack
                from = to;
                to = tmp;
                update();
                Point2D tmpText = pointText[1];
                if (from.getCenter().getX() < to.getCenter().getX()) {
                    tmpText = pointText[0];
                }
                g2.setColor(getColor(edge.getInverse()));
                drawRotateText(g2, tmpText, "<" + edge.getInverse().getResidual() + ">", angle, false);
                to = from;
                from = tmp;
                update();
            } else {
                g2.setColor(getColor(edge));
                drawRotateText(g2, centerText, "<" + edge.getResidual() + ">", angle, false);
            }
        } else if (edge.getInverse().isDiscovered()) {
            g2.setColor(getColor(edge));
            drawRotateText(g2, ABtmp, edge.getFlow() + "/" + edge.getCapacity(), angle, false);
            g2.setColor(getColor(edge.getInverse()));
            drawRotateText(g2, BAtmp, edge.getInverse().getFlow() + "/" + edge.getInverse().getCapacity(), angle, false);
        } else if (!edge.getInverse().isResidual()) {
            g2.setColor(getColor(edge));
            drawRotateText(g2, ABtmp, edge.getFlow() + "/" + edge.getCapacity(), angle, false);
            g2.setColor(getColor(edge.getInverse()));
            drawRotateText(g2, BAtmp, edge.getInverse().getFlow() + "/" + edge.getInverse().getCapacity(), angle, false);
        } else {
            g2.setColor(getColor(edge));
            drawRotateText(g2, centerText, edge.getFlow() + "/" + edge.getCapacity(), angle, edge.getFlow() == edge.getCapacity());
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        if (Config.getConfig().getResidualMode()) {
            if (edge.getResidual() > 0 && edge.getInverse().getResidual() > 0) {
                drawDoubleArrow(g2);
            } else if (edge.getResidual() > 0) {
                drawSingleArrow(g2);
            } else {
                drawSingleInvertedArrow(g2);
            }
        } else if (edge.getInverse().isDiscovered()) {
            drawDoubleArrow(g2);
        } else if (!edge.getInverse().isResidual()) {
            drawDoubleArrow(g2);
        } else {
            drawSingleArrow(g2);
        }

    }

    private void drawSingleArrow(Graphics2D g2) {
        g2.setColor(getColor(edge));
        g2.draw(shape);
        g2.draw(centerPointer1);
        g2.draw(centerPointer2);
    }

    private void drawSingleInvertedArrow(Graphics2D g2) {
        Circle tmp = from; //hack
        from = to;
        to = tmp;
        update();
        g2.setColor(getColor(edge.getInverse()));
        g2.draw(centerPointer1);
        g2.draw(centerPointer2);
        if (edge.getInverse().isResidual()) {
            drawDashed(g2, (Line2D) shape);
        } else {
            g2.draw(shape);
        }
        to = from;
        from = tmp;
        update();
    }

    private void drawDoubleArrow(Graphics2D g2) {
        g2.setColor(getColor(edge));
        g2.draw(ABArrow);
        g2.draw(ABPointer1);
        g2.draw(ABPointer2);

        g2.setColor(getColor(edge.getInverse()));
        g2.draw(BAPointer1);
        g2.draw(BAPointer2);

        if (edge.getInverse().isResidual()) {
            drawDashed(g2, BAArrow);
        } else {
            g2.draw(BAArrow);
        }
    }

    private void drawRotateText(Graphics2D g2, Point2D point, String text, double angle, boolean bold) {
        g2.translate(point.getX(), point.getY());
        g2.rotate(angle);
        g2.setFont(new Font("Ubuntu", Font.HANGING_BASELINE, Config.getConfig().getDimText()));
        if (bold) {
            g2.setFont(new Font("Ubuntu", Font.BOLD, Config.getConfig().getDimText()));
        }
        g2.drawString(text, 0, 0);
        g2.setFont(new Font("Ubuntu", Font.HANGING_BASELINE, 15));
        g2.rotate(-angle);
        g2.translate(-point.getX(), -point.getY());
    }

    private void drawDashed(Graphics2D g2, Line2D line) {
        Stroke tmp = g2.getStroke();
        BasicStroke bs = new BasicStroke((float) (Config.getConfig().getStrokeArrow() * 0.1), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 10, new float[]{5, 5, 5}, 7);
        g2.setStroke(bs);
        g2.draw(line);
        g2.setStroke(tmp);
    }

    private Color getColor(Edge edge) {
        if (edge.getFlow() == edge.getCapacity()) {
            return Config.getConfig().getFilledArrow();
        } else if (edge.isDiscovered()) {
            return Config.getConfig().getSelectedArrow();
        } else if (edge.getFlow() != 0) {
            return Config.getConfig().getUsedArrow();
        } else {
            return Config.getConfig().getDefaultArrow();
        }
    }

    private double calcSinx(Point2D from, Point2D to) {
        double deltaX = (from.getX() - to.getX());
        double deltaY = (from.getY() - to.getY());
        double dnm = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) + 0.0000001;
        double sinx = deltaY / dnm;
        //System.out.println("sinx "+sinx);
        return sinx;
    }

    private double calcCosx(Point2D from, Point2D to) {
        double deltaX = (from.getX() - to.getX());
        double deltaY = (from.getY() - to.getY());
        double dnm = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) + 0.0000001;
        double cosx = deltaX / dnm;
        //System.out.println("cosx "+cosx);
        return cosx;
    }

    private Point2D[] calcDoublePoints(double r, double sinX, double cosX) {

        double base = r * (Math.sin(Math.asin(sinX) + Math.PI / 8));
        double base2 = r * (Math.sin(Math.asin(sinX) - Math.PI / 8));
        double altezza = r * (Math.cos(Math.acos(cosX) + Math.PI / 8));
        double altezza2 = r * (Math.cos(Math.acos(cosX) - Math.PI / 8));

        Point2D[] tmpPoints = new Point2D.Double[4];
        if (from.getCenter().getY() >= to.getCenter().getY() && from.getCenter().getX() < to.getCenter().getX()) {
            tmpPoints[0] = new Point2D.Double(from.getCenter().getX() - altezza2, from.getCenter().getY() - base);
            tmpPoints[1] = new Point2D.Double(to.getCenter().getX() + altezza, to.getCenter().getY() + base2);
            tmpPoints[2] = new Point2D.Double(from.getCenter().getX() - altezza, from.getCenter().getY() - base2);
            tmpPoints[3] = new Point2D.Double(to.getCenter().getX() + altezza2, to.getCenter().getY() + base);
        } else if (from.getCenter().getX() >= to.getCenter().getX() && from.getCenter().getY() < to.getCenter().getY()) {
            tmpPoints[0] = new Point2D.Double(from.getCenter().getX() - altezza, from.getCenter().getY() - base2);
            tmpPoints[1] = new Point2D.Double(to.getCenter().getX() + altezza2, to.getCenter().getY() + base);
            tmpPoints[2] = new Point2D.Double(from.getCenter().getX() - altezza2, from.getCenter().getY() - base);
            tmpPoints[3] = new Point2D.Double(to.getCenter().getX() + altezza, to.getCenter().getY() + base2);
        } else if (from.getCenter().getX() < to.getCenter().getX() && from.getCenter().getY() < to.getCenter().getY()) {
            tmpPoints[0] = new Point2D.Double(from.getCenter().getX() - altezza, from.getCenter().getY() - base);
            tmpPoints[1] = new Point2D.Double(to.getCenter().getX() + altezza2, to.getCenter().getY() + base2);
            tmpPoints[2] = new Point2D.Double(from.getCenter().getX() - altezza2, from.getCenter().getY() - base2);
            tmpPoints[3] = new Point2D.Double(to.getCenter().getX() + altezza, to.getCenter().getY() + base);
        } else {
            tmpPoints[0] = new Point2D.Double(from.getCenter().getX() - altezza2, from.getCenter().getY() - base2);
            tmpPoints[1] = new Point2D.Double(to.getCenter().getX() + altezza, to.getCenter().getY() + base);
            tmpPoints[2] = new Point2D.Double(from.getCenter().getX() - altezza, from.getCenter().getY() - base);
            tmpPoints[3] = new Point2D.Double(to.getCenter().getX() + altezza2, to.getCenter().getY() + base2);
        }

        return tmpPoints;
    }

    private Point2D[] calcPointsHead(double sinX, double cosX) {

        Point2D[] tmp = calcPoints(from.getCenter(), to.getCenter(), ((Ellipse2D) from.getShape()).getHeight() / 2 + 10, sinX, cosX);
        Point2D[] tmp2 = calcPoints(from.getCenter(), to.getCenter(), ((Ellipse2D) from.getShape()).getHeight() / 2 + (Config.getConfig().getPosText() + 3.5 * Config.getConfig().getDimText() / 5 - 5), sinX, cosX);
        Point2D[] tmp3 = calcPoints(from.getCenter(), to.getCenter(), ((Ellipse2D) from.getShape()).getHeight() / 2 + (Config.getConfig().getPosText() - 3.5 * Config.getConfig().getDimText() / 5) - 22, sinX, cosX); //28

        double catA = 7 * sinX;
        double catC = 7 * cosX;
        double catA2 = (15 * Config.getConfig().getDimText() / 14) * sinX;
        double catC2 = (15 * Config.getConfig().getDimText() / 14) * cosX;
        double catA3 = (7 * Config.getConfig().getDimText() / 14) * sinX;
        double catC3 = (7 * Config.getConfig().getDimText() / 14) * cosX;

        Point2D[] pointsTmp = new Point2D.Double[2];

        pointsTmp[0] = new Point2D.Double(tmp[1].getX() + catA, tmp[1].getY() - catC);
        pointsTmp[1] = new Point2D.Double(tmp[1].getX() - catA, tmp[1].getY() + catC);
        pointText[0] = new Point2D.Double(tmp2[1].getX() + catA2, tmp2[1].getY() - catC2);
        pointText[1] = new Point2D.Double(tmp3[1].getX() + catA3, tmp3[1].getY() - catC3);

        return pointsTmp;
    }

    private Point2D[] calcPointsHeadInverseArrow(double sinX, double cosX) {

        Point2D[] tmp = (Point2D.Double[]) calcDoublePoints(((Ellipse2D) from.getShape()).getHeight() / 2, sinX, cosX);
        double h = 10 * cosX;
        double b = 10 * sinX;

        tmp[0].setLocation(tmp[0].getX() - h, tmp[0].getY() - b);
        tmp[3].setLocation(tmp[3].getX() + h, tmp[3].getY() + b);

        double catA = 7 * sinX;
        double catC = 7 * cosX;

        Point2D[] pointsTmp = new Point2D.Double[4];

        pointsTmp[0] = new Point2D.Double(tmp[0].getX() + catA, tmp[0].getY() - catC);
        pointsTmp[1] = new Point2D.Double(tmp[0].getX() - catA, tmp[0].getY() + catC);
        pointsTmp[2] = new Point2D.Double(tmp[3].getX() - catA, tmp[3].getY() + catC);
        pointsTmp[3] = new Point2D.Double(tmp[3].getX() + catA, tmp[3].getY() - catC);

        return pointsTmp;
    }

    private void calcTextPointInverseArrow(double sinX, double cosX) {
        Point2D[] tmp = (Point2D.Double[]) calcDoublePoints(((Ellipse2D) from.getShape()).getHeight() / 2, sinX, cosX);
        Point2D[] tmp2 = (Point2D.Double[]) calcDoublePoints(((Ellipse2D) from.getShape()).getHeight() / 2, sinX, cosX);

        double h = (Config.getConfig().getPosText() + 3.5 * Config.getConfig().getDimText() / 5 - 5) * cosX;
        double b = (Config.getConfig().getPosText() + 3.5 * Config.getConfig().getDimText() / 5 - 5) * sinX;
        tmp[0].setLocation(tmp[0].getX() - h, tmp[0].getY() - b);
        tmp[3].setLocation(tmp[3].getX() + h, tmp[3].getY() + b);

        double h2 = ((-Config.getConfig().getPosText() + 3.5 * Config.getConfig().getDimText() / 5) + 5) * cosX;
        double b2 = ((-Config.getConfig().getPosText() + 3.5 * Config.getConfig().getDimText() / 5) + 5) * sinX;
        tmp2[0].setLocation(tmp2[0].getX() + h2, tmp2[0].getY() + b2);
        tmp2[3].setLocation(tmp2[3].getX() - h2, tmp2[3].getY() - b2);

        double catA2 = (15 * Config.getConfig().getDimText() / 14) * sinX;
        double catC2 = (15 * Config.getConfig().getDimText() / 14) * cosX;
        double catA3 = (7 * Config.getConfig().getDimText() / 14) * sinX;
        double catC3 = (7 * Config.getConfig().getDimText() / 14) * cosX;

        textInverseBAArrow[0] = new Point2D.Double(tmp2[0].getX() - catA3, tmp2[0].getY() + catC3);
        textInverseBAArrow[1] = new Point2D.Double(tmp[0].getX() - catA2, tmp[0].getY() + catC2);

        textInverseABArrow[0] = new Point2D.Double(tmp[3].getX() + catA2, tmp[3].getY() - catC2);
        textInverseABArrow[1] = new Point2D.Double(tmp2[3].getX() + catA3, tmp2[3].getY() - catC3);

    }

    private Point2D[] calcPoints(Point2D from, Point2D to, double r, double sinX, double cosX) {

        //System.out.println("r "+r);
        double catA = r * sinX;
        //System.out.println("catA "+catA);
        double catB = r * cosX;

        Point2D[] points = new Point2D.Double[2];

        points[0] = new Point2D.Double(from.getX() - catB, from.getY() - catA);
        points[1] = new Point2D.Double(to.getX() + catB, to.getY() + catA);

        return points;
    }

    public void update() {

        double sinX = calcSinx(from.getCenter(), to.getCenter());
        double cosX = calcCosx(from.getCenter(), to.getCenter());
        points = calcPoints(from.getCenter(), to.getCenter(), ((Ellipse2D) from.getShape()).getHeight() / 2, sinX, cosX);
        calcTextPointInverseArrow(sinX, cosX);
        Point2D[] tmp = calcPointsHead(sinX, cosX);
        Point2D[] tmp2 = calcDoublePoints(((Ellipse2D) from.getShape()).getHeight() / 2, sinX, cosX);
        Point2D[] tmp3 = calcPointsHeadInverseArrow(sinX, cosX);

        centerPointer1.setLine(points[1], tmp[0]);
        centerPointer2.setLine(points[1], tmp[1]);

        BAArrow.setLine(tmp2[0], tmp2[1]);
        ABArrow.setLine(tmp2[2], tmp2[3]);

        BAPointer1.setLine(tmp2[0], tmp3[0]);
        BAPointer2.setLine(tmp2[0], tmp3[1]);
        ABPointer1.setLine(tmp2[3], tmp3[2]);
        ABPointer2.setLine(tmp2[3], tmp3[3]);

        ((Line2D) shape).setLine(points[0], points[1]);
    }

    public int getSelectedEdge(Point2D point) {
        int boxX = (int) (point.getX() - 30 / 2);
        int boxY = (int) (point.getY() - 30 / 2);

        int width = 30;
        int height = 30;

        if (!edge.getInverse().isResidual()) {

            if (ABArrow.intersects(boxX, boxY, width, height)) {
                return 0;
            } else if (BAArrow.intersects(boxX, boxY, width, height)) {
                return 1;
            }

        } else if (((Line2D) shape).intersects(boxX, boxY, width, height)) {
            return 0;
        }
        return -1;
    }
}
