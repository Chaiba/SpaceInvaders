/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;

import engine.GameApplication;
import engine.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Rory
 */
public class SpaceInvaders extends Game {

    public static void main(String[] args) {
        GameApplication.start(new SpaceInvaders());
    }

    BufferedImage image;
    GameData data;
    String reqDir = "stop";
    
    public SpaceInvaders() {
        try {
            image = ImageIO.read(new File("images/spaceinvaders_sheet.png"));
        } catch (IOException ex) {
            Logger.getLogger(SpaceInvaders.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = new GameData();
        title = "Space Invaders";
        height = 670;
        width = 700;
    }
    
    @Override
    public void updateInvader() {
        if(!data.dead){
            data.updateInvader();
            delay = data.invaderCoach.invaderDelay;
        }
    }
    @Override
    public void updatePlayer(){
        if(!data.dead){
            data.movePlayer(reqDir);
            data.updateField();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        //clear the canvas
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        
        //draw mothership
        if(data.mothership.isAlive)
            g.drawImage(image.getSubimage(215, 224, 48, 21), data.mothership.pos.x, data.mothership.pos.y, data.mothership.size[0], data.mothership.size[1], null);
        
        //draw level 1 invaders
        for ( int i = 0; i < 11; i++ ) {
            InvaderInfo invader = data.invaderCoach.invaders[i];
            if(invader.isAlive){
                if(data.frame%2 == 0)
                    g.drawImage(image.getSubimage(7, 225, 16, 16), invader.pos.x, data.invaderCoach.invaders[i].pos.y, invader.size[0], invader.size[1], null);
                else
                    g.drawImage(image.getSubimage(40, 225, 16, 16), invader.pos.x, data.invaderCoach.invaders[i].pos.y, invader.size[0], invader.size[1], null);
            }
        }
        
        //draw level 2 invaders
        for ( int i = 11; i < 33; i++ ) {
            InvaderInfo invader = data.invaderCoach.invaders[i];
            if(invader.isAlive){
                if(data.frame%2 == 0)
                    g.drawImage(image.getSubimage(74, 225, 22, 16), invader.pos.x, invader.pos.y, invader.size[0], invader.size[1], null);
                else
                    g.drawImage(image.getSubimage(107, 225, 22, 16), invader.pos.x, invader.pos.y, invader.size[0], invader.size[1], null);
            }
        }
        //draw level 3 invaders
        for ( int i = 33; i < 55; i++ ) {
            InvaderInfo invader = data.invaderCoach.invaders[i];
            if(invader.isAlive){
                if(data.frame%2 == 0)
                    g.drawImage(image.getSubimage(147, 226, 24, 16), invader.pos.x, invader.pos.y, invader.size[0], invader.size[1], null);
                else
                    g.drawImage(image.getSubimage(179, 226, 24, 16), invader.pos.x, invader.pos.y, invader.size[0], invader.size[1], null);
            }
        }
        
        //draw dead invader
        if(data.deadInvader!=55){
            InvaderInfo invader = data.invaderCoach.invaders[data.deadInvader];
            g.drawImage(image.getSubimage(437, 276, 26, 16), invader.pos.x, invader.pos.y, invader.size[0], invader.size[1], null);
        }
        
        //draw invader bullets
        for(int i=0;i<11;i++){
            if(data.invaderCoach.frontInvaders[i]!=55){
                InvaderInfo invader = data.invaderCoach.invaders[data.invaderCoach.frontInvaders[i]];
                    if(invader.bullet.isAlive)
                        g.drawImage(image.getSubimage(413, 277, 6, 12),invader.bullet.pos.x, invader.bullet.pos.y, invader.bullet.size[0], invader.bullet.size[1], null);
            }
        }
        
        //draw shields
        for(int i=0;i<4;i++){
            Shield shield = data.shields[i];
            if(data.shields[i].lives == 3)
                g.drawImage(image.getSubimage(316, 213, 44, 32), shield.pos.x, shield.pos.y, shield.size[0], shield.size[1], null);
            else if(data.shields[i].lives == 2)
                g.drawImage(image.getSubimage(480, 210, 44, 32), shield.pos.x, shield.pos.y, shield.size[0], shield.size[1], null);
            else if(data.shields[i].lives == 1)
                g.drawImage(image.getSubimage(373, 211, 44, 32), shield.pos.x, shield.pos.y, shield.size[0], shield.size[1], null);
        }
        
        //draw player
        if(!data.playerSpawn && !data.dead)
            g.drawImage(image.getSubimage(277, 228, 26, 16), data.player.pos.x, data.player.pos.y, 50, 20, null);
        else
            g.drawImage(image.getSubimage(367, 275, 30, 16), data.player.pos.x, data.player.pos.y, 50, 20, null);
        
        //draw player bullet
        g.setColor(Color.white);
        if(data.player.bullet.isAlive)
            g.fillRect(data.player.bullet.pos.x, data.player.bullet.pos.y, data.player.bullet.size[0], data.player.bullet.size[1]);
        
        //draw level info
        g.setColor(Color.green);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString(data.round+"", 50, 50);
        
        //draw lives info
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("LIVES", 150, 45);
        for(int i=0;i<data.player.lives;i++)
            g.drawImage(image.getSubimage(277, 228, 26, 16), (i*40)+250, 25, 30, 15, null);
        
        //draw score info
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("SCORE", 430, 45);
        g.setColor(Color.green);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(data.score+"", 550, 45);
        
        //draw top line
        g.setColor(Color.green);
        g.fillRect(10, 59, 660, 1);
        
        //draw bottom line
        g.setColor(Color.green);
        g.fillRect(10, 600, 660, 7);
        
        //draw pause
        if(data.pause){
            g.setColor(new Color(100, 100, 100, 200));
            g.fillRect(0, 0, width, height);
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("PAUSE", 240, 300);
        }
        
        //draw gameover
        if(data.dead){
            g.setColor(new Color(100, 100, 100, 200));
            g.fillRect(0, 0, width, height);
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 200, 300);
        }
        
        //draw start
        if(!data.start){
            g.setColor(Color.black);
            g.drawImage(image.getSubimage(170, 6, 235, 161), 0, 0, 680, 300, null);
            
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("PRESS ANY KEY TO START", 70, 350);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            
            g.drawString("SPACE = SHOOT", 210, 380);
            g.drawString("N = NEW GAME", 220, 410);
            g.drawString("P = PAUSE", 250, 440);
        }
        
        //draw next round pause
        if(data.nextRoundPause){
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("NEXTROUND", 200, 300);
        }
        //draw highscores
        if(data.showHighscores){
            g.setColor(Color.black);
            g.fillRect(0, 0, width, height);
            
            g.setColor(Color.white);
            
            g.setFont(new Font("Arial", Font.BOLD, 70));
            g.drawString("HIGH-SCORES", 100, 70);
            
            int[] scores = data.highscore.scores;
            String[] names = data.highscore.names;
            
            
            g.setFont(new Font("Arial", Font.BOLD, 30));
            for(int i=0;i<10;i++){
                if(names[i]!=null){
                    g.setColor(Color.white);
                    g.drawString(i+1+".", 110, (i+3)*50);
                    g.setColor(Color.green);
                    g.drawString(names[i], 180, (i+3)*50);
                    g.drawString(scores[i]+"", 400, (i+3)*50);
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(!data.start) {
            data.start = true;
            return;
        }
        
        int key = ke.getKeyCode();
    
        if(key == 32) data.playerShoot();
        
        if(key == 37) reqDir = "left";
        if(key == 39) reqDir = "right";
        if(key == 40) data.checkScore();
        if(key == 72) data.showHighscores = !data.showHighscores;
        if(key == 78) data = new GameData();
        if(key == 80) data.pause = !data.pause;
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int key = ke.getKeyCode();
        if(key == 37 && reqDir.equals("left"))  reqDir = "stop";
        if(key == 39 && reqDir.equals("right")) reqDir = "stop";
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {}
    
    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
