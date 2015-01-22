/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine;

/**
 *
 * @author Rory
 */
public class PlayerEngine extends Thread {
        private boolean paused;
	Game game;
	Canvas canvas;

	public PlayerEngine(Game g, Canvas c) {
		super();
		game = g;
		canvas = c;
		paused = false;
	}
	
	public void run() {
		while(!game.isOver()) {
			if(!paused) {
				game.updatePlayer();
                                canvas.repaint();
			}
			try {
				PlayerEngine.sleep(game.getDelay2());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void pauseGame() {
		paused = true;
	}
	
	public void resumeGame() {
		paused = false;
	}
}
