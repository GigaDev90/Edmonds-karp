/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmonds.karp.GUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author gabriele
 */
public class EdmondsKarpGUI extends javax.swing.JFrame {

    //  private static Paint paint;
    private static final EdmondsKarpGUI gui = new EdmondsKarpGUI();
    private final ArrayList<Circle> circles;
    private int MODE = 0;
    private int name;
    private boolean isSecond;
    private boolean isInDragging;
    private Circle shapeTmp;
    private final int CIRCLE = 0;
    private final int ERASE = 3;
    private Graphics2D graphics;
    private BufferedImage bf;

    private EdmondsKarpGUI() {
        initComponents();
        circles = new ArrayList<>();
        bf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics = bf.createGraphics();
        graphics.setBackground(Color.WHITE);
        name = 0;
        isSecond = false;
        isInDragging = false;
    }

    public static EdmondsKarpGUI getGui() {
        return gui;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setAlignmentX(0.0F);
        jPanel2.setAlignmentY(0.0F);
        jPanel2.setMaximumSize(new java.awt.Dimension(1920, 1080));
        jPanel2.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel2MouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                jPanel2AncestorResized(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 812, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(169, 169, 169));

        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edmonds/karp/GUI/sistema_informativo_reti_004.png"))); // NOI18N
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edmonds/karp/GUI/rette-perpendicolari-e-orientate-per-il-piano-cartesiano.png"))); // NOI18N
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(494, Short.MAX_VALUE))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        MODE = CIRCLE;
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        if (MODE == ERASE || evt.getButton() == MouseEvent.BUTTON3 ) {
            for (Circle circle : circles) {
                if (circle.getShape().contains(evt.getPoint())) {
                    circles.remove(circle);
                    circle.removeArrows();
                    break;
                } else {
                    Arrow arrow = circle.checkForArrow(evt.getPoint());
                    if (arrow != null) {
                        arrow.from.removeArrowFrom(arrow);
                        arrow.to.removeArrowTo(arrow);
                    }
                }
            }
            update();
            return;
        }
        
        if (isSecond) {
            addArrow(evt.getPoint());
            shapeTmp.setSelect(false);
            isSecond = false;
            update();
            return;
        } else {
            Circle circ = getSelectedCircle(evt.getPoint());
            if (circ != null) {
                shapeTmp = circ;
                shapeTmp.setSelect(true);
                isSecond = true;
                update();
                return;
            }
            
        }

        if (getSelectedCircle(evt.getPoint()) == null) {
            addCircle(evt.getPoint());
            
        }
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        if (!isInDragging) {
            if ( shapeTmp != null) {
                shapeTmp.setSelect(false);
                isSecond = false;
            }
            shapeTmp = getSelectedCircle(evt.getPoint());
            if (shapeTmp != null) {
                shapeTmp.setFirstPoint(evt.getPoint());
                isInDragging = true;
            } else {
                addCircle(evt.getPoint());
            }
                
        } else {
            shapeTmp.setFirstPoint(evt.getPoint());
            shapeTmp.updateArrow();
        }
        update();
    }//GEN-LAST:event_jPanel2MouseDragged

    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
        MODE = ERASE;

    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void jPanel2AncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_jPanel2AncestorResized
        if (graphics != null && (jPanel2.getWidth() != this.getWidth() || jPanel2.getHeight() != this.getHeight())) {
            bf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            graphics = bf.createGraphics();
            graphics.setBackground(Color.WHITE);
            update();
        }
    }//GEN-LAST:event_jPanel2AncestorResized

    private void jPanel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseReleased
        isInDragging = false;
    }//GEN-LAST:event_jPanel2MouseReleased

    private void drawShape(MyShape shape) {
        shape.draw((Graphics2D) jPanel2.getGraphics());
        shape.drawText((Graphics2D) jPanel2.getGraphics());
    }

    private void eraseShape(MyShape shape) {

    }

    private Circle getSelectedCircle(Point point) {
        for (Circle circle : circles) {
            if (circle.getShape().contains(point)) {
                return circle;
            }
        }

        return null;
    }

    private void update() {
        graphics.clearRect(0, 0, this.getWidth(), this.getHeight());
        for (Circle circle : circles) {
            circle.draw(graphics);
            circle.drawText(graphics);
            circle.updateArrow();
            circle.drawArrows(graphics);
        }
        jPanel2.getGraphics().drawImage(bf, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    private void addCircle(Point point) {
        Circle circle = new Circle();
        circle.setFirstPoint(point);
        circle.setColor(Color.black);
        circle.setText("" + circles.size()); //TODO 
        circles.add(circle);
        drawShape(circle);
    }

    private void addArrow(Point point) {
        Circle circ = getSelectedCircle(point);
        if (circ != null && circ != shapeTmp) {
            Arrow arrow = new Arrow(shapeTmp, circ);
            arrow.setText("0/" + name++); //TODO
            shapeTmp.addArrowFrom(arrow);
            circ.addArrowTo(arrow);
            shapeTmp.setSelect(false);
            drawShape(arrow);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EdmondsKarpGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EdmondsKarpGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EdmondsKarpGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EdmondsKarpGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EdmondsKarpGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton4;
    // End of variables declaration//GEN-END:variables
}