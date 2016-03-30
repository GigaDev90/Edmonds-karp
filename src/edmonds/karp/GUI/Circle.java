/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmonds.karp.GUI;

import java.awt.Color;
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
    
    public Circle() {
        shape = new Ellipse2D.Double();
        points = new Point2D[2];
        scale = 30;
        select = false;
        arrowsFrom = new ArrayList<>();
        arrowsTo = new ArrayList<>();
     
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
            arrow.to.removeArrowTo(arrow);
        }
        
        for (Arrow arrow : arrowsTo) {
            arrow.from.removeArrowFrom(arrow);
        }
        
        arrowsFrom.clear();
        arrowsTo.clear();
    }
    @Override
    public void makeShape() {
        ( (Ellipse2D) shape).setFrameFromDiagonal(points[0], points[1] );
    }
    
    public boolean isSelect() {
        return select;
    }

    public void setFirstPoint(Point2D point) {
        Point2D temp1 = new Point2D.Double( point.getX() + scale, point.getY() + scale );
        Point2D temp2 = new Point2D.Double( point.getX() - scale, point.getY() - scale );
        Point2D temp3 = new Point2D.Double( (int) point.getX() - 3,(int) point.getY() + 5 );
        points[1] = temp1;
        points[0] = temp2;
        pointText = temp3;
        makeShape();
    }
    
    public Point2D getCenter() {
        Point2D tmp = new Point2D.Double( ((Ellipse2D)shape).getCenterX(), ((Ellipse2D)shape).getCenterY() );
        return tmp;        
    }
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(c);
        g2.draw(shape);
        if ( select ) {
            double scale2 = scale - 10;
            Point2D temp0 = this.getCenter();
            Point2D temp1 = new Point2D.Double( temp0.getX() + scale2, temp0.getY() + scale2 );
            Point2D temp2 = new Point2D.Double( temp0.getX() - scale2, temp0.getY() - scale2 );
            Ellipse2D circle = new Ellipse2D.Double();
            circle.setFrameFromDiagonal(temp1, temp2 );
            g2.setColor(Color.BLUE);
            g2.draw(circle);
        } 
    }
    
    public void drawArrows(Graphics2D g2) {
        for (Arrow arrow : arrowsFrom) {
            arrow.draw(g2);
            arrow.drawText(g2);
        }
    }
    @Override
    public void drawText(Graphics2D g2) {
        g2.drawString(text, (int)pointText.getX(), (int)pointText.getY() );
    }

    public void updateArrow() {
        for (Arrow arrow : arrowsFrom) {
            arrow.update();
        }
    }

    public Arrow checkForArrow(Point2D point) {
        for (Arrow arrow : arrowsFrom)
            if (arrow.getShape().getBounds().contains(point))
                return arrow;
        
        return null;
    }

}


