package edmonds.karp.GUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;

public abstract class MyShape {
    //istanza per disegnare con il giusto stile

    protected Draw draw;
    //punti usato per settare dove la figura andrà posizionata
    protected Point2D[] points;
    // punto su cui stampare il nome della figura
    protected Point2D pointText;
    //colore della figura
    protected Color c;
    //Oggetto Shape è quello che andrà realmente ad essere disegnato
    protected Shape shape;
    //dimensione
    protected double scale;
    //booleano per verificare se si tratta di una classe che può essere selezionata
    protected boolean select;
    
    protected String text;

    public MyShape() {}

    public Shape getShape() {
        return shape;
    }
    
    public void setText(String name) { this.text = name; }
    
    public String getText() { return text; }

    public Point2D[] getPoints() { return points; }

    public void setPoints(Point2D a, Point2D b) { points[0] = a; points[1] = b; }
    
    public boolean isSelect() { return select; }
    
    public void setSelect(boolean b) { select = b; }
    
    public void setStile(int stile) {
        switch (stile) {
            case 0: {
                draw = new DrawFill();
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

    public void setColor(Color colorC) { c = colorC; }

    public Color getColor() { return c; }

    public void setScale(int i) { scale = i; }
    
    public double getScale() { return scale; }

    public abstract void setFirstPoint(Point2D point);

    public abstract void makeShape();

    public abstract void draw(Graphics2D g2);

    public abstract void drawName(Graphics2D g2);
    
    

}
