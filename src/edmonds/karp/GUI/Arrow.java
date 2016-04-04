/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmonds.karp.GUI;

import edmonds.karp.Edge;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
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
    private Line2D.Double head1;
    private Line2D.Double head2;
    protected Edge edge;
    

    public Arrow(Circle from, Circle to) {
        shape = new Line2D.Double();
        head1 =  new Line2D.Double();
        head2 =  new Line2D.Double();
        pointText =  new Point2D[2];
        points = new Point2D[2];
        scale = 30;
        select = false;
        this.from = from;
        this.to = to;
        c = Color.BLACK;
        update();
        
    }
    public Circle getFrom() { return from; }
    public Circle getTo() { return to; }
    public Edge getEdge() { return edge; }
    public void setEdge( Edge edge ) { this.edge = edge; }

    @Override
    public void makeShape() {
        ((Line2D) shape).setLine(points[0], points[1]);
        
    }
    @Override
    public void drawText(Graphics2D g2) {
        double angle = Math.asin(calcSinx(from.getCenter(), to.getCenter()));
        Point2D tmp = pointText[0];
        
        if ( from.getCenter().getX() > to.getCenter().getX()) {
            angle = -angle;
            tmp = pointText[1];
        }

        g2.translate(tmp.getX(), tmp.getY());
        g2.rotate(angle);
        if ( edge.getFlow() ==  edge.getCapacity())
            g2.setFont(new Font("Ubuntu", Font.BOLD, 15));
        g2.drawString(edge.getFlow()+"/"+edge.getCapacity(),0,0);
        g2.setFont(new Font("Ubuntu", Font.HANGING_BASELINE, 15));
        g2.rotate(-angle);
        g2.translate(-(float)tmp.getX(),-(float)tmp.getY());
    }
    @Override
    public void draw(Graphics2D g2) {
        if (  edge.getFlow() == edge.getCapacity()) {
            g2.setColor(Color.RED);
            g2.draw(shape);
            g2.draw(head1);
            g2.draw(head2);
        } else if ( edge.getFlow() > 0 ) {
            g2.setColor(Color.BLUE);
            g2.draw(shape);
            g2.draw(head1);
            g2.draw(head2);
        } else if ( edge.isDiscovered() ){
            g2.setColor(Color.green);
            g2.draw(shape);
            g2.draw(head1);
            g2.draw(head2);
        } else {
            g2.setColor(Color.DARK_GRAY);
            g2.draw(shape);
            g2.draw(head1);
            g2.draw(head2);
        }
    }
    
    private double calcSinx(Point2D from, Point2D to) {
        double deltaX = (to.getX() - from.getX());
        double deltaY = (to.getY() - from.getY());
        double dnm = Math.sqrt(Math.pow(deltaX, 2)+ Math.pow(deltaY, 2)) + 0.0000001;
        double sinx = deltaY / dnm;
        //System.out.println("sinx "+sinx);
        return sinx;
    }
    private double calcCosx(Point2D from, Point2D to) {
        double deltaX = (to.getX() - from.getX());
        double deltaY = (to.getY() - from.getY());
        double dnm = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) + 0.0000001;
        double cosx = deltaX / dnm;
        //System.out.println("cosx "+cosx);
        return cosx;
    }
    
    private Point2D[] calcPointsHead(double sinX, double cosX) {
        
        Point2D[] tmp = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2 + 10, sinX, cosX );
        Point2D[] tmp2 = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2 + 50, sinX, cosX );
        Point2D[] tmp3 = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2 + 28, sinX, cosX );
      
        double catA = 7 * sinX;
        double catC = 7 * cosX;
        double catA2 = 16 * sinX;
        double catC2 = 16 * cosX;
        double catA3 = 8 * sinX;
        double catC3 = 8 * cosX;
        
        Point2D[] pointsTmp = new Point2D.Double[2];
        
        pointsTmp[0] = new Point2D.Double(tmp[1].getX() - catA, tmp[1].getY() + catC);
        pointsTmp[1] = new Point2D.Double(tmp[1].getX() + catA, tmp[1].getY() - catC);
        pointText[0] = new Point2D.Double(tmp2[1].getX() - catA2, tmp2[1].getY() + catC2);
        pointText[1] = new Point2D.Double(tmp3[1].getX() - catA3, tmp3[1].getY() + catC3);
        
        return pointsTmp;
    }
    
    

    private Point2D[] calcPoints(Point2D from, Point2D to, double r, double sinX, double cosX) {
        
        //System.out.println("r "+r);
        double catA = r * sinX;
        //System.out.println("catA "+catA);
        double catB = r * cosX;
        
        Point2D[] points = new Point2D.Double[2];
        
        points[0] = new Point2D.Double(from.getX() + catB, from.getY() + catA);
        points[1] = new Point2D.Double(to.getX() - catB, to.getY() - catA);
        
        return points;
    }
    
    public void update() {
        
        double sinX = calcSinx(from.getCenter(), to.getCenter());
        double cosX = calcCosx(from.getCenter(), to.getCenter());
        //System.out.println("sinX"+ sinX+" cosX "+cosX);
        points = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2, sinX, cosX );
        Point2D[] tmp = calcPointsHead(sinX, cosX);
        
        ((Line2D) head1).setLine(points[1], tmp[0]);
        ((Line2D) head2).setLine(points[1], tmp[1]);
        
        makeShape();
    }
}
