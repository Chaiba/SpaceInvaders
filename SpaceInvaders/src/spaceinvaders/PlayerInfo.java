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
public class PlayerInfo {
    private String dir;
    public int lives = 4;
    Point pos = new Point();
    Bullet bullet = new Bullet();
    int[] size;
    
    public PlayerInfo(){
        size = new int[2];
        size[0] = 50;
        size[1] = 20;
        
        pos.x = 300;
        pos.y = 550;
        dir = "stop";
        
        setBulletSize(10, 3);
    }
    public PlayerInfo(int lives){
        size = new int[2];
        size[0] = 50;
        size[1] = 20;
        
        pos.x = 300;
        pos.y = 550;
        dir = "stop";
        
        this.lives=lives;
        setBulletSize(10, 3);
    }
    
    public void move(String dir){
        this.dir = dir;
    }

    public Point getPos() {
        return pos;
    }
    public void setPos(int x, int y){
        pos.x = x;
        pos.y = y;
    }
    
    public String getDir(){
        if ( dir != null )
            return dir;
        else
            return "stop";
    }
    public void setDir(String dir){
        this.dir = dir;
    }
    public void shoot(){
        if(!bullet.isAlive){
            bullet.isAlive = true;
            bullet.setPos(pos.x+(size[0]-bullet.size[0])/2, pos.y-5);
        }
    }
    public void movePlayer(){
        if(getDir().equals("left"))
            setPos(getPos().x-8, getPos().y);
        if(getDir().equals("right"))
            setPos(getPos().x+8, getPos().y);
    }
    public boolean die(){
        lives--;
        return lives==0;
    }
    private void setBulletSize(int width, int height){
        bullet.size[0] = width;
        bullet.size[0] = height;
    }
}
