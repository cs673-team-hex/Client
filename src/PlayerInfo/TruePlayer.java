/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PlayerInfo;


/**
 *
 * @author Wei
 */
public class TruePlayer extends Player {

    private String NickName;
    private int Rank;
    private int Credit;
    private int Userid;
    private double Factor1;
    private double Factor2;
    private double Factor3;
    
    private static TruePlayer player = null; 
    
    public static void initial(double balance, String nickname, int rank, int credit, int userid, double factor1, double factor2, double factor3){
        player = new TruePlayer(balance, nickname, rank, credit, userid, factor1, factor2, factor3);
    }
    
    public static TruePlayer GetPlayer(){
        return player;
    }

    public void SetNickName(String nickname){
        this.NickName = nickname;
    }
    
    public String GetNickName(){
        return this.NickName;
    }
    
    public void SetRank(int rank){
        this.Rank = rank;
    }
    
    public int GetRank(){
        return this.Rank;
    }
    
    public void SetCredit(int credit){
        this.Credit = credit;
    }
    
    public int GetCredit(){
        return this.Credit;
    }
    
    public void SetUserId(int userid){
        this.Userid = userid;
    }
    
    public int GetUserId(){
        return this.Userid;
    }
    
    public void SetFactor1(double factor1){
        this.Factor1 = factor1;
    }
    
    public double GetFactor1(){
        return this.Factor1;
    }
     
    public void SetFactor2(double factor2){
        this.Factor1 = factor2;
    }
    
    public double GetFactor2(){
        return this.Factor2;
    }
    
    public void SetFactor3(double factor3){
        this.Factor1 = factor3;
    }
    
    public double GetFactor3(){
        return this.Factor3;
    }
            
    private TruePlayer(double balance, String nickname, int rank, int credit, int userid, double factor1, double factor2, double factor3){
        this.dBalance = balance;
        this.NickName = nickname;
        this.Rank = rank;
        this.Credit = credit;
        this.Userid = userid;
        this.Factor1 = factor1;
        this.Factor2 = factor2;
        this.Factor3 = factor3;
    }
}
