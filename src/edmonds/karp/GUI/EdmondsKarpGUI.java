/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmonds.karp.GUI;

import edmonds.karp.Controller.EdmondsKarpController;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RepaintManager;
import org.json.JSONException;
/**
 *
 * @author gabriele
 */
public class EdmondsKarpGUI extends javax.swing.JFrame {

    private ArrayList<Circle> circles;
    private int MODE = 0;
    private int test = 0;
    private boolean isSecond;
    private boolean isInDragging;
    private Circle shapeTmp;
    private final int DRAW = 0;
    private final int ERASE = 3;
    private Graphics2D graphics;
    private BufferedImage bf;
    private EdmondsKarpController controller;
    private Point2D pointTmp;
    private JFileChooser chooser = new JFileChooser();

    private EdmondsKarpGUI() {
        initComponents();
        RepaintManager.currentManager(this).markCompletelyClean(jPanel2);
        circles = new ArrayList<>();
        bf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics = bf.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.setFont(new Font("Ubuntu", Font.HANGING_BASELINE, 15));
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        controller = new EdmondsKarpController(this);
        isSecond = false;
        isInDragging = false;
        try {
            String tmp = openGraph("/home/gabriele/CanonicalGraph.txt");
            if ( tmp != null )
                controller.open(tmp);
        } catch (JSONException ex) {
            Logger.getLogger(EdmondsKarpGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        update();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        pencilButton = new javax.swing.JToggleButton();
        rubberButton = new javax.swing.JToggleButton();
        playButton = new javax.swing.JToggleButton();
        backButton = new javax.swing.JButton();
        forwardButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        runButton = new javax.swing.JButton();
        stopButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jPopupMenu1.setLightWeightPopupEnabled(false);
        jPopupMenu1.setMaximumSize(new java.awt.Dimension(97, 54));
        jPopupMenu1.setMinimumSize(new java.awt.Dimension(97, 54));

        jMenuItem2.setText("set Source");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setSourceActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        jMenuItem3.setText("set Sink");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setSinkActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem3);

        jPopupMenu1.getAccessibleContext().setAccessibleDescription("");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(169, 169, 169));
        jPanel1.setPreferredSize(new java.awt.Dimension(824, 50));

        pencilButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edmonds/karp/GUI/draw.png"))); // NOI18N
        pencilButton.setSelected(true);
        pencilButton.setToolTipText("draw");
        pencilButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pencilButton.setMaximumSize(new java.awt.Dimension(30, 30));
        pencilButton.setMinimumSize(new java.awt.Dimension(24, 24));
        pencilButton.setName(""); // NOI18N
        pencilButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pencilButtonActionPerformed(evt);
            }
        });

        rubberButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edmonds/karp/GUI/remove.png"))); // NOI18N
        rubberButton.setToolTipText("erase");
        rubberButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rubberButton.setMaximumSize(new java.awt.Dimension(30, 30));
        rubberButton.setMinimumSize(new java.awt.Dimension(24, 24));
        rubberButton.setName(""); // NOI18N
        rubberButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rubberButtonActionPerformed(evt);
            }
        });

        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edmonds/karp/GUI/play.png"))); // NOI18N
        playButton.setToolTipText("play");
        playButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playButton.setMaximumSize(new java.awt.Dimension(30, 30));
        playButton.setMinimumSize(new java.awt.Dimension(24, 24));
        playButton.setName(""); // NOI18N
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edmonds/karp/GUI/back.png"))); // NOI18N

        forwardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edmonds/karp/GUI/forward.png"))); // NOI18N
        forwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardButtonActionPerformed(evt);
            }
        });

        stopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edmonds/karp/GUI/square.png"))); // NOI18N
        stopButton.setToolTipText("stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        runButton.setText("Run");
        runButton.setToolTipText("stop");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        stopButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edmonds/karp/GUI/square.png"))); // NOI18N
        stopButton2.setToolTipText("stop");
        stopButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pencilButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rubberButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(forwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(runButton)
                .addGap(127, 127, 127)
                .addComponent(stopButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(373, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(runButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stopButton2)
                            .addComponent(stopButton)
                            .addComponent(forwardButton)
                            .addComponent(backButton)
                            .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rubberButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pencilButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(670, 670, 670))
        );

        runButton.getAccessibleContext().setAccessibleDescription("run");

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
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
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");
        jMenu1.setPreferredSize(new java.awt.Dimension(50, 21));

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("New");
        jMenuItem5.setPreferredSize(new java.awt.Dimension(110, 25));
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Open");
        jMenuItem1.setPreferredSize(new java.awt.Dimension(110, 25));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Save");
        jMenuItem4.setPreferredSize(new java.awt.Dimension(110, 25));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {

            if (MODE == ERASE) {
                eraseShape(evt.getPoint());
            } else if (MODE == DRAW) {

                if (isSecond) {
                    Circle circ = getSelectedCircle(evt.getPoint());
                    if (circ != null && circ != shapeTmp) {
                        addArrow(circ);
                        shapeTmp.setSelect(false);
                        isSecond = false;
                        update();
                        return;
                    }
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

                if (getSelectedCircle(evt.getPoint()) == null && !isSecond) {
                    addCircle(evt.getPoint());
                } else {
                    isSecond = false;
                    shapeTmp.setSelect(false);
                    update();
                }
            }
        } else if (evt.getButton() == MouseEvent.BUTTON3) {
            if (getSelectedCircle(evt.getPoint()) != null) {
                jPopupMenu1.show(jPanel2, evt.getX(), evt.getY());
                pointTmp = evt.getPoint();
            }
        }
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        if (!isInDragging) {

            if (shapeTmp != null) {
                shapeTmp.setSelect(false);
                isSecond = false;
            }

            shapeTmp = getSelectedCircle(evt.getPoint());
            if (shapeTmp != null) {
                shapeTmp.setFirstPoint(evt.getPoint());
                shapeTmp.needUpdate();
                isInDragging = true;
            } else {
                // addCircle(evt.getPoint());
                isInDragging = false;
                shapeTmp = null;
            }

        } else {
            shapeTmp.setFirstPoint(evt.getPoint());
            shapeTmp.needUpdate();
            shapeTmp.updateArrow();
            update();
        }


    }//GEN-LAST:event_jPanel2MouseDragged

    private void jPanel2AncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_jPanel2AncestorResized
        if (graphics != null && (jPanel2.getWidth() != this.getWidth() || jPanel2.getHeight() != this.getHeight())) {
            bf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            graphics = bf.createGraphics();
            graphics.setBackground(Color.WHITE);
            graphics.setFont(new Font("Ubuntu", Font.HANGING_BASELINE, 15));
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            RepaintManager.currentManager(this).markCompletelyClean(jPanel2);
            update();

        }
    }//GEN-LAST:event_jPanel2AncestorResized

    private void jPanel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseReleased
        isInDragging = false;
    }//GEN-LAST:event_jPanel2MouseReleased

    private void pencilButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pencilButtonActionPerformed
        // TODO add your handling code here:
        rubberButton.setSelected(false);
        MODE = DRAW;
    }//GEN-LAST:event_pencilButtonActionPerformed

    private void rubberButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rubberButtonActionPerformed
        // TODO add your handling code here:
        pencilButton.setSelected(false);
        MODE = ERASE;
    }//GEN-LAST:event_rubberButtonActionPerformed

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        // TODO add your handling code here:
        if (playButton.isSelected()) {
            controller.play();
        }
    }//GEN-LAST:event_playButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        // TODO add your handling code here:
        playButton.setSelected(false);
        controller.stop();
    }//GEN-LAST:event_stopButtonActionPerformed

    private void setSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setSourceActionPerformed
        // TODO add your handling code here:
        controller.setSource(getSelectedCircle(pointTmp));
    }//GEN-LAST:event_setSourceActionPerformed

    private void setSinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setSinkActionPerformed
        // TODO add your handling code here:
        controller.setSink(getSelectedCircle(pointTmp));
    }//GEN-LAST:event_setSinkActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        // TODO add your handling code here:
        controller.run();
    }//GEN-LAST:event_runButtonActionPerformed

    private void stopButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButton2ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_stopButton2ActionPerformed

    private void forwardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardButtonActionPerformed
        // TODO add your handling code here:
        controller.oneStepForward();
    }//GEN-LAST:event_forwardButtonActionPerformed

    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenActionPerformed
        try {
            // TODO add your handling code here:
            String tmp = openGraph(null);
            if ( tmp != null) {
                System.out.println(tmp);
                controller.open(tmp);
            }
            //update();
        } catch (JSONException ex) {
            Logger.getLogger(EdmondsKarpGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_OpenActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        saveGraph();
    }//GEN-LAST:event_SaveActionPerformed

    private void newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newActionPerformed
        // TODO add your handling code here:
        controller.newGraph();
    }//GEN-LAST:event_newActionPerformed

    public boolean isPlaySelected() {
        return playButton.isSelected();
    }
    
    public void setUpPlayButton() {
        playButton.setSelected(false);
    }

    private void eraseShape(Point2D point) {
        for (Circle circle : circles) {
            if (circle.getShape().contains(point)) {
                circles.remove(circle);
                controller.removeNode(circle);
                circle.removeArrows();
                break;
            } else {
                Arrow arrow = circle.checkForArrow(point);
                if (arrow != null) {
                    arrow.getFrom().removeArrowFrom(arrow);
                    arrow.getTo().removeArrowTo(arrow);
                    controller.removeEdge(arrow);
                }
            }
        }
        update();
    }

    private void drawShape(MyShape shape) {
        shape.draw(graphics);
        shape.drawText(graphics);
        jPanel2.getGraphics().drawImage(bf, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    private Circle getSelectedCircle(Point2D point) {
        for (Circle circle : circles) {
            if (circle.getShape().contains(point)) {
                return circle;
            }
        }

        return null;
    }

    public void update() {
        graphics.clearRect(0, 0, this.getWidth(), this.getHeight());
        for (Circle circle : circles) {
            circle.draw(graphics);
            circle.drawText(graphics);
            circle.updateArrow();
            circle.drawArrows(graphics);
        }
        jPanel2.getGraphics().drawImage(bf, 0, 0, this.getWidth(), this.getHeight(), null);
        //System.out.println("update " + test++);
    }

    private void addCircle(Point point) {
        Circle circle = new Circle();
        circle.setFirstPoint(point);
        circles.add(circle);
        controller.addNode(circle);
        drawShape(circle);
    }

    private void addArrow(Circle circ) {
        Arrow arrow = new Arrow(shapeTmp, circ);
        if (controller.addEdge(arrow)) {
            shapeTmp.addArrowFrom(arrow);
            circ.addArrowTo(arrow);
            drawShape(arrow);
        }
    }

    public void displayMessage(String str) {
        JOptionPane.showMessageDialog(this, str);
    }
    
    public ArrayList getCircles() { return circles; }
    
   
    public void setCircles(ArrayList array) { circles = array; }

    private String openGraph(String defaultGraph) {
        
        String txt = "";
        if (defaultGraph != null) {
           // File f = defaultGraph;
            try {
                FileReader fileReader = new FileReader(defaultGraph);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = "";
                while((line = bufferedReader.readLine()) != null) {
                    txt += line;
                }
                
                return txt;

            } catch (IOException e) {
            }
        }
        
        
        //Visualizzo la finestra di dialogo
        int risposta = chooser.showOpenDialog(this);
        //String txt = "";
        if (risposta == chooser.APPROVE_OPTION) {//Se ho premuto il tasto apri

            //Recupero il file selezionato
            File f = chooser.getSelectedFile();
            try {
                FileReader fileReader = new FileReader(f);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = "";
                while((line = bufferedReader.readLine()) != null) {
                    txt += line;
                }
                
                return txt;

            } catch (IOException e) {
            }
        }
        return null;
    }

    public void saveGraph() {
        
        int option2 = JOptionPane.NO_OPTION;
        File f = null;
        String ext = "txt";
        String str = null;
        while (option2 == JOptionPane.NO_OPTION) //Finche' non decido di salvare
        {
            //Visualizzo la finestra di dialogo
            int option = chooser.showSaveDialog(this);
            if (option == chooser.APPROVE_OPTION) //Se ho premuto il tasto salva
            {
                try {
                    //Recupero il file selezionato
                    f = chooser.getSelectedFile();
                    //Recupero il path del file
                    str = f.getCanonicalPath();
                    //Se il nome del file non contiene l'estensione, la aggiungo io a mano
                    if (!str.toLowerCase().endsWith("." + ext)) {
                        str = str + "." + ext;
                    }
                    //Se il file esiste chiedo se lo voglio sovrascrivere
                    if (f.exists()) {
                        option2 = JOptionPane.showConfirmDialog(this, "Il file esiste gia'.\nLo vuoi sovrascrivere?", "Sovrascrittura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    } else {
                        option2 = JOptionPane.YES_OPTION;
                    }
                } catch (Exception ex) {
                }
            } else {
                option2 = JOptionPane.CANCEL_OPTION;
            }
        }
        if (option2 == JOptionPane.YES_OPTION) {
            try {
                controller.save(str);
            } catch (JSONException ex) {
                Logger.getLogger(EdmondsKarpGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
      

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
    private javax.swing.JButton backButton;
    private javax.swing.JButton forwardButton;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JToggleButton pencilButton;
    private javax.swing.JToggleButton playButton;
    private javax.swing.JToggleButton rubberButton;
    private javax.swing.JButton runButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JButton stopButton2;
    // End of variables declaration//GEN-END:variables
}
