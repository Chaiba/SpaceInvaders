/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Rory
 */
public class GameApplication {
    public static void start(final Game game) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override 
            public void run() {
                JFrame frame = new JFrame(game.getTitle());
                frame.setSize(game.getWidth(), game.getHeight());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);

                Canvas c = new Canvas(game);
                frame.add(c);
                frame.setVisible(true);
                c.requestFocus();

                InvaderEngine e = new InvaderEngine(game, c);
                PlayerEngine pe = new PlayerEngine(game, c);
                
                e.start();
                pe.start();
            }
        });
    }
}
