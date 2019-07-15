/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brainfuck;

import brainfuck.gui.InputFrame;
import java.awt.EventQueue;

/**
 *
 * @author aardvocate
 */
public class BrainFuck {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EventQueue.invokeLater(() -> {
           new InputFrame().setVisible(true); 
        });
    }
    
}
