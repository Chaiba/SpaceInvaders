/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javafx.scene.media.AudioClip;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Rory
 */
public class GameData implements ActionListener {
    boolean start;
    boolean playerSpawn = false;
    boolean pause = false;
    boolean nextRoundPause = false;
    boolean nextRound = false;
    boolean dead = false;
    boolean showHighscores = false;
    
    int frame = 0;
    int round = 1;
    int score = 0;
    
    int deadInvader = 55;
    boolean deadInvaderDelay = false;
    
    AudioClip acShoot;
    AudioClip acPlayerDie;
    AudioClip acFlyingMothership;
    AudioClip acGhostDie;
    AudioClip acDieingMothership;
    
    Timer tmNewRound = new Timer(3000, this);
    Timer tmDeadPlayer = new Timer(1000, this);
    Timer tmDeadInvader = new Timer(100, this);
    
    InvaderCoach invaderCoach;
    InvaderInfo mothership;
    PlayerInfo player;
    Shield[] shields;
    Highscore highscore;
    
    public GameData(){
        start = false;
        
        //initialize highscore
        try{
            FileInputStream fin = new FileInputStream("highscores.rvl");
            ObjectInputStream ois = new ObjectInputStream(fin);
            highscore = (Highscore) ois.readObject();
            ois.close();
        }catch(IOException ex){
            highscore = new Highscore();
        } catch (ClassNotFoundException ex) {
            //
        }
        //initialize mothership
        mothership = new InvaderInfo(4);
        mothership.setPosition(700, 65);
        //initialize invaders
        invaderCoach = new InvaderCoach(round);
        //initialize shield
        shields = new Shield[4];
        for(int i=0; i<4; i++)
            shields[i] = new Shield(80+((150*i)), 450);
        //initialize player
        player = new PlayerInfo();
        //sounds
        acShoot = new AudioClip(this.getClass().getResource("/sounds/shoot.wav").toString());
        acPlayerDie = new AudioClip(this.getClass().getResource("/sounds/explosion.wav").toString());
        acFlyingMothership = new AudioClip(this.getClass().getResource("/sounds/flying mothership.aif").toString());
        acGhostDie = new AudioClip(this.getClass().getResource("/sounds/ghost die.wav").toString());
        acDieingMothership = new AudioClip(this.getClass().getResource("/sounds/mothership die.wav").toString());
        
        acFlyingMothership.setCycleCount(5);
        acFlyingMothership.setVolume(acFlyingMothership.getVolume()*0.2);
    };

    public void updateInvader() {
        if (invaderCoach.invaders[invaderCoach.getBottomInvader()].pos.y + invaderCoach.invaders[invaderCoach.getBottomInvader()].size[1] > 450 )
            processPlayerDeath();
        
        if(deadInvaderDelay || nextRoundPause || pause || playerSpawn || !start || dead || showHighscores){
            acFlyingMothership.stop();
            return;
        }
        
        frame++;
        
        //move invaders
        invaderCoach.moveInvaders();
        //make invaders attack
        if((int)(Math.random()*40) < 10)
            invaderCoach.attack();
        //spawn mothership
        if((int)(Math.random()*500) < 10 && !mothership.isAlive){
            mothership.respawnMothership();
            if(!acFlyingMothership.isPlaying())
                acFlyingMothership.play();
        }
    }
    
    public void updateField(){
        if(nextRound) nextRound();
        
        if(nextRoundPause || pause || !start || dead || showHighscores) return;
                
        //move mothership
        if(mothership.isAlive){
            if(mothership.pos.x<-72)
                acFlyingMothership.stop();
            else if(!acFlyingMothership.isPlaying())
                acFlyingMothership.play();
            mothership.setPosition(mothership.pos.x-5, mothership.pos.y);
        }else
            acFlyingMothership.stop();
        
        //move player
        if(!playerSpawn)
            player.movePlayer();
        
        //move player bullet
        if ( player.bullet.isAlive ) {
            player.bullet.setPos(player.bullet.pos.x, player.bullet.pos.y-15);
            //check mothership hit
            if(mothership.isAlive && player.bullet.touches(mothership.pos, mothership.size)){
                acFlyingMothership.stop();
                score += mothership.die();
                acDieingMothership.play();
                return;
            }
            //check shields hit
            for(int i=0;i<4;i++){
                if(shields[i].isAlive && player.bullet.touches(shields[i].pos, shields[i].size)){
                    shields[i].hit();
                    return;
                }
            }
            //check invaders hit
            for(int i=0;i<55;i++){
                if(invaderCoach.invaders[i].isAlive && player.bullet.touches(invaderCoach.invaders[i].pos, invaderCoach.invaders[i].size)){
                    processInvaderDeath(i);
                    acGhostDie.play();
                    return;
                }
            }
        }
        
        //move front invader bullets
        for(int i=0;i<11;i++){
            if ( invaderCoach.frontInvaders[i]!=55 && invaderCoach.invaders[invaderCoach.frontInvaders[i]].bullet.isAlive ) {
                InvaderInfo invader = invaderCoach.invaders[invaderCoach.frontInvaders[i]];
                
                invader.bullet.setPos(invader.bullet.pos.x, invader.bullet.pos.y+10);
                //check shields hit
                for(int s=0;s<4;s++){
                    if(shields[s].isAlive && invader.bullet.touches(shields[s].pos, shields[s].size)){
                        shields[s].hit();
                        return;
                    }
                }
                //check player bullet hit
                if(invaderCoach.invaders[invaderCoach.frontInvaders[i]].bullet.touches(player.bullet.pos, player.bullet.size)){
                    player.bullet.die();
                    return;
                }
                //check player hit
                if(invaderCoach.invaders[invaderCoach.frontInvaders[i]].bullet.touches(player.pos, player.size) && !playerSpawn){
                    processPlayerDeath();
                    acPlayerDie.play();
                    return;
                }
            }
        }
    }
    private void nextRound(){
        round++;
        mothership = new InvaderInfo(4);
        invaderCoach = new InvaderCoach(round);
        nextRound = false;
        tmNewRound.stop();
    }
    
    private void processInvaderDeath(int invader){
            score += invaderCoach.killInvader(invader);
            invaderCoach.bodyCheck(invader);
            
            deadInvader = invader;
            tmDeadInvader.start();
            deadInvaderDelay = true;
            
            if(invaderCoach.deadInvaders==55){
                acFlyingMothership.stop();
                nextRoundPause = true;
                tmNewRound.start();
            }
    }
    
    private void processPlayerDeath(){
        if(player.die()){
            acFlyingMothership.stop();
            dead = true;
            checkScore();
        }
        else
            spawnPlayer();
    }
    
    public void movePlayer(String reqDir) {
        if(player.pos.x<20 && reqDir.equals("left"))
            player.setDir("stop");
        else if(player.pos.x+player.size[0]>660 && reqDir.equals("right"))
            player.setDir("stop");
        else
            player.setDir(reqDir);
    }
    
    public void spawnPlayer(){
        tmDeadPlayer.start();
        playerSpawn = true;
    }
    
    public void playerShoot(){
        if(!playerSpawn && !nextRoundPause && !dead){
            if(!player.bullet.isAlive)
                acShoot.play();
            player.shoot();
        }
    }
    
    public void checkScore(){
        if(highscore.checkScore(score)){
            String inputValue = JOptionPane.showInputDialog("You made it into the highscores! What's your name?");
            if(inputValue!=null)
                highscore.addScore(score, inputValue);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) { 
        if(ae.getSource() == tmDeadInvader){
            tmDeadInvader.stop();
            deadInvaderDelay = false;
            deadInvader = 55;
        }
        if(ae.getSource() == tmDeadPlayer){
            tmDeadPlayer.stop();
            player.setPos(300, 550);
            playerSpawn = false; 
        }
        
        if(ae.getSource() == tmNewRound){
            acFlyingMothership.stop();
            nextRound = true;
            nextRoundPause = false;
        }
    }
}
