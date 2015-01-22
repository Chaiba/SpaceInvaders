/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;

import java.awt.Point;

/**
 *
 * @author Rory
 */
class InvaderInfo {
    int level, score;
    Point pos = new Point();
    boolean isAlive = true;
    Bullet bullet;
    int[] size;
    
    public InvaderInfo(int level){
        this.level = level;
        size = new int[2];
        
        if(level==1){
            score = 30;
            size[0] = 24;
            size[1] = 24;
        }
        if(level==2){
            score = 20;
            size[0] = 33;
            size[1] = 24;
        }
        if(level==3){
            score = 10;
            size[0] = 36;
            size[1] = 24;
        }
        if(level == 4){
            score = randomScore();
            size[0] = 72;
            size[1] = 31;
            isAlive = false;
            setPosition(700, 65);
        }
        
        bullet = new Bullet();
    }
    
    private int randomScore(){
        int r = 0 + (int)(Math.random()*4);
        switch(r){
                case 0:
                    return 50;
                case 1:
                    return 100;
                case 2:
                    return 150;
                case 3:
                    return 200;
                case 4:
                    return 250;
                default:
                    return 150;
        }
    }
    
    public void setPosition(int x, int y){
        if(!isAlive)
            return;
        
        if(level==4 && pos.x<-72)
            die();
        
        pos.x = x;
        pos.y = y;
    }
    
    public void respawnMothership(){
        score = randomScore();
        isAlive = true;
        setPosition(700, 65);
    }
    
    public int die(){
        isAlive = false;
        return score;
    }
    
    public void shoot(){
        if(!isAlive)
            return;
        if(!bullet.isAlive){
            bullet.isAlive = true;
            bullet.setPos(pos.x + (size[0]-bullet.size[0])/2, pos.y+size[1]);
            //pos.x + (s[0]-bullet.size[0])/2
        }
    }
}
