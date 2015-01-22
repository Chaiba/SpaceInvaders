/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;
/**
 *
 * @author Rory
 */
public class Canvas extends JComponent implements ComponentListener {
	private Game game;
	
	public Canvas(Game g) {
		game = g;
		addKeyListener(game);
		addComponentListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		game.draw((Graphics2D)g);
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		game.resize(e.getComponent().getWidth(), e.getComponent().getHeight());
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
