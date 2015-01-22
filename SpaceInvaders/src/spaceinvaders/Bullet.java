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
public class Bullet {
    Point pos = new Point();
    boolean isAlive;
    int[] size;
    
    public Bullet(){
        size = new int[2];
        size[0] = 6;
        size[1] = 20;
    }
    
    public Point getPos(){
        return pos;
    }
    public void setPos(int x, int y){
        pos.x = x;
        pos.y = y;
        if(y<60)
            die();
        if(y>600-size[1])
            die();
    }
    public void die(){
        isAlive = false;
    }
    public boolean touches(Point a, int[] s){
        if(
                //hoek linksboven
                pos.x>a.x && pos.x<s[0] && 
                pos.y>a.y && pos.y<a.y+s[1]
                //hoek rechtsboven
                || pos.x+size[0]>a.x && pos.x+size[0]<a.x+s[0] && pos.y>a.y && pos.y<a.y+s[1]
                //hoek linksonder
                || pos.x>a.x && pos.x<s[0] && pos.y+size[1]>a.y && pos.y+size[1]<a.y+s[1]
                //hoek rechtsonder
                || pos.x+size[0]>a.x && pos.x+size[0]<a.x+s[0]
                && pos.y+size[1]>a.y && pos.y+size[1]<a.y+s[1]
                ){
            die();
            return true;
        }else
            return false;
    }
}
