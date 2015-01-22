/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;

/**
 *
 * @author Rory
 */
public class InvaderCoach {
    InvaderInfo[] invaders;
    int[] frontInvaders;
    int rightInvader, leftInvader, bottomInvader;
    int invaderDelay, minimumDelay;
    int deadInvaders;
    
    int rowCount, currentCount, startCount;
    
    String dir = "right";
    
    public InvaderCoach(int round){
        //calculate minimum delay
        int mD=160;
        for(int i=0;i<round;i++){
            mD-=10;
        }
        invaderDelay = 200;
        //initialize delay
        if(mD>30)
            minimumDelay=mD;
        else
            minimumDelay=30;
        
        //initialize invader
        invaders = new InvaderInfo[55];
        
        int count =0;
        
        for(int i=0;i<11;i++){
            invaders[count] = new InvaderInfo(1);
            count++;
        }
        for(int i=0;i<22;i++){
            invaders[count] = new InvaderInfo(2);
            count++;
        }
        for(int i=0;i<22;i++){
            invaders[count] = new InvaderInfo(3);
            count++;
        }
        //initialize invader locations
        int countC = 0;
        int x = 20, y = 100;
        for(int i=0; i<11;i++){
            invaders[i].setPosition(x+((invaders[54].size[0]-invaders[0].size[0])/2), y);
            countC++;
            if ( countC == 11) {
                y += 40;
                x = 20;
                countC = 0;
            }else
                x += 45;
        }
        for(int i=11;i<33;i++){
            invaders[i].setPosition(x+((invaders[54].size[0]-invaders[11].size[0])/2), y);
            countC++;
            if ( countC == 11) {
                y += 40;
                x = 20;
                countC = 0;
            }else
                x += 45;
        }
        for(int i=33;i<55;i++){
            invaders[i].setPosition(x, y);
            countC++;
            if ( countC == 11) {
                y += 40;
                x = 20;
                countC = 0;
            }else
                x += 45;
        }
        //initialize scout invaders
        rightInvader = 54;
        leftInvader = 44;
        bottomInvader = 54;
        
        //initialize front invaders
        frontInvaders = new int[11];
        int c = 0;
        for(int i = 44; i< 55; i++){
            frontInvaders[c] = i;
            c++;
        }
    }
    public int getRightInvader(){
        startCount = 54;
        currentCount = 54;
        rowCount = 0;

        while(true){
            if ( rowCount < 5 ) {
                if(currentCount<0)
                    return 0;
                if(invaders[currentCount].isAlive)
                    return currentCount;
                currentCount -= 11;
                rowCount++;
            }else if ( rowCount == 5 ) {
                startCount--;
                currentCount = startCount;
                rowCount = 0;
            }
        }
    }
    public int getLeftInvader(){
        currentCount = 44; 
        startCount = 44;
        rowCount=0;

        while(true) {
            if ( rowCount < 5 ) {
                if(currentCount > 54)
                    return 0;
                if(invaders[currentCount].isAlive) {
                    return currentCount;
                }
                currentCount -= 11;
                rowCount++;
            }else if ( rowCount == 5 ) {
                startCount++;
                currentCount = startCount;
                rowCount = 0;
            }
        }
    }

    public int getBottomInvader(){
        currentCount = 54;
        while(true){
            if(currentCount == 0)
                return bottomInvader;  
            
            if(invaders[currentCount].isAlive){
                return currentCount;
            }
            currentCount--;
        }
    }
    
    int bottom;
    String getDir(){
        bottom = 0;
        if ( invaders[rightInvader].pos.x >= 639 && invaders[rightInvader].pos.x <= 650 ){
            moveFaster();
            dir = "left";
            bottom = 10;
        }
        if ( invaders[leftInvader].pos.x > 0 && invaders[leftInvader].pos.x < 11 ) {
            moveFaster();
            dir = "right";
            bottom = 10;
        }
        return dir;
    }

    public void moveFaster(){
        if(invaderDelay>minimumDelay)
            invaderDelay -= 10;
    }
    
    public int killInvader(int invader){
        deadInvaders++;
        return invaders[invader].die();
    }
    
    public void bodyCheck(int a){
        if(a == rightInvader)
            rightInvader = getRightInvader();
        if(a == leftInvader)
            leftInvader = getLeftInvader();
        if(a == bottomInvader)
            bottomInvader = getBottomInvader();
        
        for(int i=0;i<11;i++){
            if( a == frontInvaders[i]){
                if(a<11)
                    frontInvaders[i] = 55;
                else
                    frontInvaders[i] -= 11;
            }
        }
    }
    public void attack(){
        int row = (int) (Math.random() * 10);
        if(frontInvaders[row]!=55 && !invaders[frontInvaders[row]].bullet.isAlive)
            invaders[frontInvaders[row]].shoot();
        else if(row<9){
            row+=2;
            attack(row);
        } 
        else{
            row = 0;
            attack(row);
        }
    }
    public void attack(int row){
        if(frontInvaders[row]!=55 && !invaders[frontInvaders[row]].bullet.isAlive)
            invaders[frontInvaders[row]].shoot();
        else if(row<11)
            row++;
        else{
            row = 0;
            attack(row);
        }
    }

    public void moveInvaders(){
        getDir();
        for ( int i = 0; i < 55; i++ ) {
            if(dir.equals("left") && invaders[i].isAlive)
                invaders[i].setPosition(invaders[i].pos.x-10, invaders[i].pos.y+=bottom);
            else if(dir.equals("right") && invaders[i].isAlive)
                invaders[i].setPosition(invaders[i].pos.x+10, invaders[i].pos.y+=bottom);
        }
    }
}
