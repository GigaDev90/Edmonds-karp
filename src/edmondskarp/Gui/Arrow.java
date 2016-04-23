/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmondskarp.Gui;

import edmondskarp.Model.Edge;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
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
    private Line2D.Double centerPointer1;
    private Line2D.Double centerPointer2;
    private Line2D.Double downArrow;
    private Line2D.Double downPointer1;
    private Line2D.Double downPointer2;
    private Line2D.Double upArrow;
    private Line2D.Double upPointer1;
    private Line2D.Double upPointer2;
    protected Edge edge;
    

    public Arrow(Circle from, Circle to) {
        shape = new Line2D.Double();
        centerPointer1 =  new Line2D.Double();
        downPointer1 =  new Line2D.Double();
        downPointer2 =  new Line2D.Double();
        centerPointer2 =  new Line2D.Double();
        upPointer1 =  new Line2D.Double();
        upPointer2 =  new Line2D.Double();
        downArrow = new Line2D.Double();
        upArrow = new Line2D.Double();
        pointText =  new Point2D[2];
        points = new Point2D[2];
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
        Point2D tmp = pointText[1];
        
        if ( from.getCenter().getX() < to.getCenter().getX()) {
            angle = -angle;
            tmp = pointText[0];
        }

        g2.translate(tmp.getX(), tmp.getY());
        g2.rotate(angle);
        g2.setFont(new Font("Ubuntu", Font.HANGING_BASELINE, Config.getConfig().getDimText()));
        if ( edge.getFlow() ==  edge.getCapacity())
            g2.setFont(new Font("Ubuntu", Font.BOLD, Config.getConfig().getDimText()));
        g2.drawString(edge.getFlow()+"/"+edge.getCapacity(),0,0);
        g2.setFont(new Font("Ubuntu", Font.HANGING_BASELINE, 15));
        g2.rotate(-angle);
        g2.translate(-(float)tmp.getX(),-(float)tmp.getY());
    }
    @Override
    public void draw(Graphics2D g2) {
        if (  edge.getFlow() == edge.getCapacity()) {
            g2.setColor(Config.getConfig().getFilledArrow());
            g2.draw(shape);
            g2.draw(centerPointer1);
            g2.draw(centerPointer2);
        } else if ( edge.isDiscovered() ) {
            g2.setColor(Config.getConfig().getSelectedArrow());
            g2.draw(shape);
            g2.draw(centerPointer1);
            g2.draw(centerPointer2);
        } else if ( edge.getFlow() > 0 ) {
            g2.setColor(Config.getConfig().getUsedArrow());
            g2.draw(shape);
            g2.draw(centerPointer1);
            g2.draw(centerPointer2);
        } else {
            g2.setColor(Config.getConfig().getDefaultArrow());
            g2.draw(shape);
            g2.draw(centerPointer1);
            g2.draw(centerPointer2);
        }
        
        
       
        
        g2.draw(downArrow);
        g2.draw(upArrow);
        g2.draw(downPointer1);
        g2.draw(downPointer2);
        g2.draw(upPointer1);
        g2.draw(upPointer2);
        
    }
    
    private double calcSinx(Point2D from, Point2D to) {
        double deltaX = (from.getX() - to.getX());
        double deltaY = (from.getY() - to.getY());
        double dnm = Math.sqrt(Math.pow(deltaX, 2)+ Math.pow(deltaY, 2)) + 0.0000001;
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
        
//        double base = r * (Math.sin(Math.asin(sinX) + Math.PI / 4));
//        double base2 = r * (Math.sin(Math.asin(sinX) - Math.PI / 4));
//        double altezza = r * (Math.cos(Math.acos(cosX) + Math.PI / 4));
//        double altezza2 = r * (Math.cos(Math.acos(cosX) - Math.PI / 4));
        double base = 0;
        double base2 = 0;
        double altezza = 0;
        double altezza2 = 0;
        //System.out.println("values " + Math.toDegrees(Math.asin(sinX)) + " " + Math.toDegrees(Math.acos(-cosX)));
        if (Math.abs(Math.abs(Math.asin(sinX)) - Math.abs(Math.acos(cosX))) > 0.01) {
            System.out.println("different");
        }
        if (from.getCenter().getX() > to.getCenter().getX()) {

        } else if (from.getCenter().getX() > to.getCenter().getX()) {
  
        }
//            System.out.println("3 ");
//            base = r * (Math.sin(Math.asin(sinX) + Math.PI / 4));
//            base2 = r * (Math.sin(Math.asin(sinX) - Math.PI / 4));
//            altezza = r * (Math.cos(Math.asin(sinX) + Math.PI / 4));
//            altezza2 = r * (Math.cos(Math.asin(sinX) - Math.PI / 4));
//        }
         
        
//        if ( Math.acos(cosX) > 0 ) {
//            altezza = r * (Math.cos(Math.acos(cosX) + Math.PI / 4));
//            altezza2 = r * (Math.cos(Math.acos(cosX) - Math.PI / 4));
//        } else {
//            altezza = r * (Math.cos(Math.acos(cosX) - Math.PI / 4));
//            altezza2 = r * (Math.cos(Math.acos(cosX) + Math.PI / 4));
//        }
        


        Point2D[] points = new Point2D.Double[4];
//        if (from.getCenter().getY() > to.getCenter().getY() && from.getCenter().getX() < to.getCenter().getX()) {
//            System.out.println("1");
//            points[0] = new Point2D.Double(from.getCenter().getX() + altezza2, from.getCenter().getY() + base);
//            points[1] = new Point2D.Double(to.getCenter().getX() - altezza, to.getCenter().getY() - base2);
//            points[2] = new Point2D.Double(from.getCenter().getX() + altezza, from.getCenter().getY() + base2);
//            points[3] = new Point2D.Double(to.getCenter().getX() - altezza2, to.getCenter().getY() - base);
//        } else if ( from.getCenter().getX() > to.getCenter().getX() && from.getCenter().getY() < to.getCenter().getY()) {
//            System.out.println("2");
//            points[0] = new Point2D.Double(from.getCenter().getX() + altezza, from.getCenter().getY() + base2);
//            points[1] = new Point2D.Double(to.getCenter().getX() - altezza2, to.getCenter().getY() - base);
//            points[2] = new Point2D.Double(from.getCenter().getX() + altezza2, from.getCenter().getY() + base);
//            points[3] = new Point2D.Double(to.getCenter().getX() - altezza, to.getCenter().getY() - base2);
//         
//        } else if ( from.getCenter().getX() < to.getCenter().getX() && from.getCenter().getY() < to.getCenter().getY()){
//            System.out.println("3");
//            points[0] = new Point2D.Double(from.getCenter().getX() + altezza, from.getCenter().getY() + base);
//            points[1] = new Point2D.Double(to.getCenter().getX() - altezza2, to.getCenter().getY() - base2);
//            points[2] = new Point2D.Double(from.getCenter().getX() + altezza2, from.getCenter().getY() + base2);
//            points[3] = new Point2D.Double(to.getCenter().getX() - altezza, to.getCenter().getY() - base);   
//        } else {
//            System.out.println("4");
//            points[0] = new Point2D.Double(from.getCenter().getX() + altezza2, from.getCenter().getY() + base2);
//            points[1] = new Point2D.Double(to.getCenter().getX() - altezza, to.getCenter().getY() - base);
//            points[2] = new Point2D.Double(from.getCenter().getX() + altezza, from.getCenter().getY() + base);
//            points[3] = new Point2D.Double(to.getCenter().getX() - altezza2, to.getCenter().getY() - base2);
//        
//        }

        points[0] = new Point2D.Double(from.getCenter().getX() + altezza, from.getCenter().getY() + base);
        points[1] = new Point2D.Double(to.getCenter().getX() - altezza, to.getCenter().getY() - base);
        points[2] = new Point2D.Double(from.getCenter().getX() + altezza2, from.getCenter().getY() + base2);
        points[3] = new Point2D.Double(to.getCenter().getX() - altezza2, to.getCenter().getY() - base2);

        return points;
    }
    
    private Point2D[] calcPointsHead(double sinX, double cosX) {
        
        Point2D[] tmp = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2 + 10, sinX, cosX );
        Point2D[] tmp2 = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2 + (Config.getConfig().getPosText() + 3.5 * Config.getConfig().getDimText()/5 - 5), sinX, cosX );
        Point2D[] tmp3 = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2 + (Config.getConfig().getPosText() - 3.5 * Config.getConfig().getDimText()/5) - 22, sinX, cosX ); //28
      
        double catA = 7 * sinX;
        double catC = 7 * cosX;
        double catA2 = (15 * Config.getConfig().getDimText()/14) * sinX;
        double catC2 = (15 * Config.getConfig().getDimText()/14) * cosX;
        double catA3 = (7 * Config.getConfig().getDimText()/14) * sinX;
        double catC3 = (7 * Config.getConfig().getDimText()/14) * cosX;
        
        Point2D[] pointsTmp = new Point2D.Double[2];
        
        pointsTmp[0] = new Point2D.Double(tmp[1].getX() + catA, tmp[1].getY() - catC);
        pointsTmp[1] = new Point2D.Double(tmp[1].getX() - catA, tmp[1].getY() + catC);
        pointText[0] = new Point2D.Double(tmp2[1].getX() + catA2, tmp2[1].getY() - catC2);
        pointText[1] = new Point2D.Double(tmp3[1].getX() + catA3, tmp3[1].getY() - catC3);
        
        return pointsTmp;
    }
    
     private Point2D[] calcPointsHeadInverseArrow(double sinX, double cosX) {
        
        Point2D[] tmp = (Point2D.Double[]) calcDoublePoints(((Ellipse2D)from.getShape()).getHeight()/2 , sinX, cosX );
//        Point2D[] tmp2 = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2 + (Config.getConfig().getPosText() + 3.5 * Config.getConfig().getDimText()/5 - 5), sinX, cosX );
//        Point2D[] tmp3 = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2 + (Config.getConfig().getPosText() - 3.5 * Config.getConfig().getDimText()/5) - 22, sinX, cosX ); //28
        double h = 10 * cosX;
        double b = 10 * sinX;
       
        
        tmp[1].setLocation(tmp[1].getX() - h, tmp[1].getY() - b);
        tmp[3].setLocation(tmp[3].getX() - h, tmp[3].getY() - b);
       
        //EdmondsKarpGui.getGui().addCircle(new Point((int)tmp[1].getX(), (int)tmp[1].getY()));

        double catA = 7 * sinX;
        double catC = 7 * cosX;
//        double catA2 = (15 * Config.getConfig().getDimText()/14) * sinX;
//        double catC2 = (15 * Config.getConfig().getDimText()/14) * cosX;
//        double catA3 = (7 * Config.getConfig().getDimText()/14) * sinX;
//        double catC3 = (7 * Config.getConfig().getDimText()/14) * cosX;
//        
        Point2D[] pointsTmp = new Point2D.Double[4];
        
        pointsTmp[0] = new Point2D.Double(tmp[1].getX() - catA, tmp[1].getY() + catC);
        pointsTmp[1] = new Point2D.Double(tmp[1].getX() + catA, tmp[1].getY() - catC);
        pointsTmp[2] = new Point2D.Double(tmp[3].getX() - catA, tmp[3].getY() + catC);
        pointsTmp[3] = new Point2D.Double(tmp[3].getX() + catA, tmp[3].getY() - catC);
        
//        pointText[0] = new Point2D.Double(tmp2[1].getX() - catA2, tmp2[1].getY() + catC2);
//        pointText[1] = new Point2D.Double(tmp3[1].getX() - catA3, tmp3[1].getY() + catC3);
        
        return pointsTmp;
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
        //System.out.println("sinX"+ sinX+" cosX "+cosX);
        points = calcPoints( from.getCenter(), to.getCenter(), ((Ellipse2D)from.getShape()).getHeight()/2, sinX, cosX );
        Point2D[] tmp = calcPointsHead(sinX, cosX);
        
        ((Line2D) centerPointer1).setLine(points[1], tmp[0]);
        ((Line2D) centerPointer2).setLine(points[1], tmp[1]);
        
        Point2D[] tmp2 = calcDoublePoints(((Ellipse2D)from.getShape()).getHeight()/2, sinX, cosX);
        
        downArrow.setLine(tmp2[0], tmp2[1]);
        //upArrow.setLine(tmp2[2], tmp2[3]);
        
        Point2D[] tmp3 = calcPointsHeadInverseArrow(sinX, cosX);
        
       // ((Line2D) downPointer1).setLine(tmp2[1], tmp3[0]);
        ((Line2D) downPointer2).setLine(tmp2[1], tmp3[1]);
        //((Line2D) upPointer1).setLine(tmp2[3], tmp3[2]);
        //((Line2D) upPointer2).setLine(tmp2[3], tmp3[3]);
        
        makeShape();
    }
}
