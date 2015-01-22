/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Rory
 */
public class Highscore implements Serializable {
    int[] scores;
    String[] names;
    
    
    
    public Highscore(){
        scores = new int[10];
        names = new String[10];
        //initialize scores
        for(int i=0;i<10;i++){
            scores[i] = 0;
        }
    }
    public boolean checkScore(int score){
        for(int i=0;i<10;i++){
            if(scores[i]<score){
                return true;
            }
        }
        return false;
    }
    public void addScore(int score, String name){
        for(int i=9;i>=0;i--){
                
            if(scores[i]<score){
                if(i==0){
                    scores[i+1] = scores[i];
                    names[i+1] = names[i];
                    scores[i] = score;
                    names[i] = name;
                    saveScores();
                    return;
                }
                if(i!=9){
                    scores[i+1] = scores[i];
                    names[i+1] = names[i];
                }
            }else{
                scores[i+1] = score;
                names[i+1] = name;
                saveScores();
                return;
            }
        }
    }
    private void saveScores(){
        try{
            FileOutputStream fout = new FileOutputStream("highscores.rvl");
            ObjectOutputStream oos = new ObjectOutputStream(fout);   
            oos.writeObject(this);
            oos.close();

       }catch(IOException ex){
            System.out.println("save hs IOException");
       }
    }
}
