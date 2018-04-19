package astroid;

import java.io.Serializable;

public class Score implements Serializable{
    private int[] score = new int[5];
    private int currentScore = 0;
    public Score(){
        
    }
    public Score(int a,int b,int c,int d,int e){
        score[0] =a;
        score[1] = b;
        score[2] = c;
        score[3] = d;
        score[4] = e;
    }
    
    public int[] getScore(){
        return score;
    }
    
    public void setCurrentScore(int score){
        currentScore = score;
    }
    
    public int getCurrentScore(){
        return currentScore;
    }
    
    public void updateScore(){
        currentScore++;
    }
    
    public void setZeroScore(){
        currentScore = 0;
    }
    
    public void setRanking(){
        for(int i=0;i<5;i++){
            if(currentScore > score[i]){
                for(int j=4;j>i;j--){
                    score[j] = score[j-1];
                }
                score[i] = currentScore;
                break;
            }
        }
    }
}
