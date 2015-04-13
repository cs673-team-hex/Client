/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerGame;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import Log.Log;
import PokerDeck.Card;
import PokerDeck.CardDeck;
import PlayerInfo.AIPlayer;
import PlayerInfo.GamePlayer;
/**
 *
 * @author Administrator
 */
public class BlackJackPlay {

    private BlackJackPlayRound round;
    static private CardDeck deck;
    private final int nNumberOfPlayer;
    private GamePlayer pCurrentPlayer;
    private int nMoney;

    GamePlayer[] pPlayerArray;
    AIPlayer pAI;

    public BlackJackPlay( int nPlayer) {
        if (deck == null) {
            deck = new CardDeck();
        }
        int nStartMoney = 1000;
        nNumberOfPlayer = nPlayer;
        pPlayerArray = new GamePlayer[nNumberOfPlayer];
        if (nPlayer > 0) {
            //should get info from server
            for (int i = 0; i < nNumberOfPlayer; i++) {
                pPlayerArray[i] = new GamePlayer(nStartMoney, "Player" + String.valueOf(i), 1, 1000, i);
                pCurrentPlayer = pPlayerArray[0];
            }
        } else {
            Log.getInstance().Log(2, "PlayerSetToZero");
        }

        pAI = new AIPlayer(nStartMoney, true);
    }

    public void GameBegin() throws InterruptedException {
        PlayNewRound();
    }

    public boolean CheckPlayerLose(GamePlayer pPlayer) {

        if (pPlayer.getBalance() < 50) {
            return true;
        }
        return false;
    }

    public boolean GameEnd() {
        for (int i = 0; i < nNumberOfPlayer; i++) {
            if (!CheckPlayerLose(pPlayerArray[i])) {
                return false;
            }
        }
        return true;
    }


    public void RestorePlayerStatus() {
        for (GamePlayer player : pPlayerArray) {
            player.doDouble(false);
            player.doSurrender(false);
        }
    }

    public void PlayNewRound() throws InterruptedException {

            round = new BlackJackPlayRound(pPlayerArray, pAI, deck,this);
            RestorePlayerStatus();
            
            //Shuffle When Number Reduce Slow
            if (deck.getNumber() < 30) {
                deck.RebuildDeck();
            }

            round.SendFirstTwoCardsToPllayerX();
    }
    
    private void jNextRoundActionPerformed() {//GEN-FIRST:event_jNextRoundActionPerformed


        try {
            PlayNewRound();

        } catch (InterruptedException ex) {
            Logger.getLogger(BlackJackPlay.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }//GEN-LAST:event_jNextRoundActionPerformed

    private void jHitActionPerformed() {
        // TODO add your handling code here:
        if (round == null) {
            return;
        }

        if (getCurrentPlayer().AmIDouble()) {
            //GetOneCardAndStand
            round.PlayerHit();
            //Should not do this, but I am lazy.
            jStandActionPerformed();
            return;
        }

        if (!BlackJackRule.AmIBust(getCurrentPlayer())) {
            round.PlayerHit();
            //Check GameStatus
            if (BlackJackRule.AmIBust(getCurrentPlayer())) {
                //Should not do this, but I am lazy.
                jStandActionPerformed();
            }
            if (BlackJackRule.AmIFiveDragon(getCurrentPlayer())) {
                //Should not do this, but I am lazy.
                jStandActionPerformed();
            }
        }

    }
    
    private void jStandActionPerformed() {
 
        try {
            round.RoundEndByPlayerX();
            //TerminateControlOfPlayer(); 
        } catch (InterruptedException ex) {
            Logger.getLogger(BlackJackPlay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jSurrenderActionPerformed() {

        try {
            round.PlayerXSurrender();
        } catch (InterruptedException ex) {
            Logger.getLogger(BlackJackPlay.class.getName()).log(Level.SEVERE, null, ex);
        }
        getCurrentPlayer().doSurrender(true);
    }

    private void jDoubleActionPerformed() {
        // TODO add your handling code here:
        round.PlayerXDouble();
    }
    public void PrintLog() {
        for (int i = 0; i < nNumberOfPlayer; i++) {
            Log.getInstance().Log(1, "AI Hand:      " + pAI.printCardInHand());
            Log.getInstance().Log(1, "Player[" + i + "]Hand : " + pPlayerArray[i].printCardInHand());
            Log.getInstance().Log(1, "Player[" + i + "]Money : " + pPlayerArray[i].getBalance());
            Log.getInstance().Log(1, "-----------------------------------------------");
        }
        System.out.println(Log.getInstance().getLog());
    }

    public AIPlayer getAI() {
        return round.getAI();
    }

    public GamePlayer getCurrentPlayer() {
        return round.getCurrentPlayer();
    }
    
    
	public static void main(String args[]) {
		BlackJackPlay game = new BlackJackPlay(4);
		try {
			game.GameBegin();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner s = new Scanner(System.in);
		while (true) {
			String temp = s.nextLine();
			switch(temp){
			case "hit": System.out.println("hit");game.jHitActionPerformed();break;
			case "double": System.out.println("double");game.jDoubleActionPerformed();break;
			case "stand": System.out.println("stand");game.jStandActionPerformed();break;
			case "surrender": System.out.println("surrender");game.jSurrenderActionPerformed();break;
			}
			ArrayList<Card> cards = game.getCurrentPlayer().getPlayerCards();
			for(Card card:cards){
				System.out.print(card.printCard()+";");
			}
			
		}
	}
}