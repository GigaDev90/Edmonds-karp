/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmonds.karp.GUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
/**
 *
 * @author gabriele
 */
public class Arrow extends MyShape {
    
    protected Circle from;
    protected Circle to;
    private Line2D.Double head1;
    private Line2D.Double head2;
    

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

    @Override
    public void makeShape() {
        ((Line2D) shape).setLine(points[0], points[1]);
        
    }
    @Override
    public void drawText(Graphics2D g2) {
        double angle = Math.asin(calcSinx(from.getCenter(), to.getCenter()));
        Point2D tmp = pointText[0];
        if ( from.getCenter().getX() > to.getCenter().getX() ) {
            angle = -angle;
            tmp = pointText[1];
        }
        if ( from.getCenter().getY() > to.getCenter().getY() ) {
           angle = -angle;
           //tmp = pointText[1];
        }
      
        g2.translate(tmp.getX(), tmp.getY());
        g2.rotate(angle);
        g2.drawString(text,0,0);
        g2.rotate(-angle);
        g2.translate(-(float)tmp.getX(),-(float)tmp.getY());
    }
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(c);
        g2.draw(shape);
        g2.draw(head1);
        g2.draw(head2);
    }
    
    private double calcSinx(Point2D from, Point2D to) {
        double deltaX = Math.abs(from.getX() - to.getX());
        double deltaY = Math.abs(from.getY() - to.getY());
        double dnm = Math.sqrt(Math.pow(deltaX, 2)+ Math.pow(deltaY, 2)) + 0.0000001;
        double sinx = deltaY / dnm;
        //System.out.println("sinx "+sinx);
        return sinx;
    }
    private double calcCosx(Point2D from, Point2D to) {
        double deltaX = Math.abs(from.getX() - to.getX());
        double deltaY = Math.abs(from.getY() - to.getY());
        double dnm = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) + 0.0000001;
        double cosx = deltaX / dnm;
        //System.out.println("cosx "+cosx);
        return cosx;
    }
    
    private Point2D[] calcPointsHead(double sinX, double cosX) {
        
        Point2D[] tmp = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2 + 10, sinX, cosX );
        Point2D[] tmp2 = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2 + 50, sinX, cosX );
        Point2D[] tmp3 = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2 + 31, sinX, cosX );
      
        double catA = 7 * sinX;
        double catC = 7 * cosX;
        double catA2 = 16 * sinX;
        double catC2 = 16 * cosX;
        double catA3 = 8 * sinX;
        double catC3 = 8 * cosX;
        double x0 = tmp[1].getX();
        double x1 = tmp[1].getX();
        double y0 = tmp[1].getY();
        double y1 = tmp[1].getY();
        double x2 = tmp2[1].getX();
        double y2 = tmp2[1].getY();
        double x3 = tmp3[1].getX();
        double y3 = tmp3[1].getY();
        

        if ( tmp[1].getY() > points[1].getY() ) {
            x0 += catA;
            x1 -= catA;
            x2 += catA2;
            x3 += catA3;
            
        } else {
            x0 -= catA;
            x1 += catA;
            x2 -= catA2;
            x3 -= catA3;
            
        }
        
        if ( tmp[1].getX() > points[1].getX() ) {
            y0 -= catC;
            y1 += catC;
            y2 -= catC2;
            y3 -= catC3;
        } else {
            y0 += catC;
            y1 -= catC;
            y2 += catC2;
            y3 += catC3;
        }
        
        Point2D[] pointsTmp = new Point2D.Double[2];
        
        pointsTmp[0] = new Point2D.Double(x0, y0);
        pointsTmp[1] = new Point2D.Double(x1, y1);
        pointText[0] = new Point2D.Double(x2, y2);
        pointText[1] = new Point2D.Double(x3, y3);
        
        return pointsTmp;
    }
    
    

    private Point2D[] calcPoints(Point2D from, Point2D to, double r, double sinX, double cosX) {
        
        //System.out.println("r "+r);
        double catA = r * sinX;
        //System.out.println("catA "+catA);
        double catB = r * cosX;
        //System.out.println("catB "+catB);

        double x0 = from.getX();
        double x1 = to.getX();
        double y0 = from.getY();
        double y1 = to.getY();
        
        if ( to.getX() > from.getX() ) {
            x0 += catB;
            x1 -= catB;
        } else {
            x0 -= catB;
            x1 += catB;
        }
        
        if ( to.getY() > from.getY() ) {
            y0 += catA;
            y1 -= catA;
        } else {
            y0 -= catA;
            y1 += catA;
        }
        
        Point2D[] points = new Point2D.Double[2];
        
        points[0] = new Point2D.Double(x0, y0);
        points[1] = new Point2D.Double(x1, y1);
        
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
