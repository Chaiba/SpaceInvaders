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
public class Shield {
    Point pos = new Point();
    int lives = 3;
    boolean isAlive = true;
    int[] size;
    
    public Shield(int x, int y){
        size = new int[2];
        size[0] = 70;
        size[1] = 60;
        
        setPos(x, y);
    }
    
    public void setPos(int x, int y){
        pos.x = x;
        pos.y = y;
    }
    
    public boolean hit(){
        lives--;
        if(lives<1){
            isAlive = false;
            return false;
        }
        else{
            isAlive = true;
            return true;
        }
    }
}
