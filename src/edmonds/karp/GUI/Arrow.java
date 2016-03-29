/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmonds.karp.GUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Observable;
import java.util.Observer;

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
        points = new Point2D[2];
        draw = new DrawBorder();
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
    public void setFirstPoint(Point2D p) {
        points[0] = p;
        points[1] = new Point2D.Double(p.getX() + scale, p.getY() + scale);
        pointText = p;
        makeShape();
    }

    @Override
    public void setStile(int stile) {
        switch (stile) {
            case 0: {
                draw = new DrawBorder();
            }
            break;
            case 1: {
                draw = new DrawBorder();
            }
            break;
            case 2: {
                draw = new DrawWhiteBorder();
            }
        }
    }

    @Override
    public void drawText(Graphics2D g2) {
        //g2.drawString(text,(int) pointText.getX(),(int) pointText.getY());
        //AttributedString tmp = new AttributedString(text);
        
        AffineTransform orig = g2.getTransform();
       // g2.rotate(Math.PI/2);
        g2.setColor(Color.BLACK);
        g2.drawString(text,(int) pointText.getX(),(int) pointText.getY());
        g2.setTransform(orig);
        
       //g2.setTransform(orig);
       
        //g2.drawString( tmp.getIterator(), (int) pointText.getX(),(int) pointText.getY());
    }

    @Override
    public void draw(Graphics2D g2) {
        draw.draw(shape, g2, c);
        draw.draw(head1, g2, c);
        draw.draw(head2, g2, c);
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
        Point2D[] tmp2 = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2 + 40, sinX, cosX );
        
        double catA = 7 * sinX;
        double catC = 7 * cosX;
        double catA2 = 20 * sinX;
        double catC2 = 20 * cosX;
        double x0 = tmp[1].getX();
        double x1 = tmp[1].getX();
        double y0 = tmp[1].getY();
        double y1 = tmp[1].getY();
        double x2 = tmp2[1].getX();
        double y2 = tmp2[1].getY();
        

        if ( tmp[1].getY() > points[1].getY() ) {
            x0 += catA;
            x1 -= catA;
            x2 += catA2;
            
        } else {
            x0 -= catA;
            x1 += catA;
            x2 -= catA2;
            
        }
        
        if ( tmp[1].getX() > points[1].getX() ) {
            y0 -= catC;
            y1 += catC;
            y2 -= catC2;
        } else {
            y0 += catC;
            y1 -= catC;
            y2 += catC2;
        }
        
        Point2D[] pointsTmp = new Point2D.Double[2];
        
        pointsTmp[0] = new Point2D.Double(x0, y0);
        pointsTmp[1] = new Point2D.Double(x1, y1);
        pointText = new Point2D.Double(x2, y2);
        
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
        
        points = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2, sinX, cosX );
        Point2D[] tmp = calcPointsHead(sinX, cosX);
        
        ((Line2D) head1).setLine(points[1], tmp[0]);
        ((Line2D) head2).setLine(points[1], tmp[1]);
        
        makeShape();
    }
}
