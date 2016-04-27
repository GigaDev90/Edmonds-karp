/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edmondskarp.Controller;

import edmondskarp.Gui.EdmondsKarpGui;

/**
 *
 * @author gabriele
 */
public class Main {

    public static void main(String[] args) {
        
        EdmondsKarpController controller = new EdmondsKarpController(EdmondsKarpGui.getGui());
        EdmondsKarpGui.getGui().setController(controller);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EdmondsKarpGui.getGui().setVisible(true);
            }
        });
        
        controller.searchDefaultPreference();
        
        
    }
}
