/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokerGame;

import Log.Log;
import PokerDeck.CardDeck;
import PlayerInfo.AIPlayer;
import UI.BlackJackUINew;
import javax.mail.MessagingException;

/**
 *
 * @author Administrator
 */
public class BlackJackPlay {

    private int nRound;
    private BlackJackPlayRound blackJackPlayRound;
    static private CardDeck deck;

    AIPlayer pPlayer;
    AIPlayer pAI;

    final BlackJackUINew UI;

    public BlackJackPlay(BlackJackUINew ui) {
        if (deck == null) {
            deck = new CardDeck();
        }
        nRound = 0;
        int nStartMoney = 1000;
        pPlayer = new AIPlayer(nStartMoney, false);
        pAI = new AIPlayer(nStartMoney, true);
        UI = ui;
        UI.RefreshMoneyOfBothPlayer();
    }

    public void ResetHand() {
        //Logic
        pPlayer.ResetHand();
        pAI.ResetHand();
    }

    public void GameBegin() throws InterruptedException, MessagingException {
        PlayNewRound();
    }

    public AIPlayer getPlayer() {
        return pPlayer;
    }

    public AIPlayer getAI() {
        return pAI;
    }

    public boolean GameEnd() {
        //Logic
        if (pPlayer.getBalance() < 50) {
            Log.getInstance().Log(1, "A.I Wins in " + nRound + "Round!");
            return true;
        } else if (pAI.getBalance() < 50) {
            Log.getInstance().Log(1, "You Wins in " + nRound + "Round!");
            return true;
        }

        return false;
    }

    public BlackJackPlayRound getCurrentPlayRound() {
        return this.blackJackPlayRound;
    }

    public int getNumOfRound() {
        return this.nRound;
    }

    public void PlayNewRound() throws InterruptedException, MessagingException {
        //Shuffle When Number Reduce Slow
        nRound++;
        ResetHand();
        //洗洗更健康
        if (deck.getNumber() < 10) {
            deck.RebuildDeck();
        }
        //Check Game Is End
        if (GameEnd()) {
            UI.GameEndProcedure();
        } else {
            UI.RestoreControlOfPlayer();
            UI.InitialBoardsBetweenRounds();
            blackJackPlayRound = new BlackJackPlayRound(pPlayer, pAI, deck, UI, this);
            blackJackPlayRound.SendFirstTwoCardsToBothPlayer();
            UI.setRoundInfo(blackJackPlayRound);
        }
    }

    public void PrintLog() {

        if (blackJackPlayRound.GetWinPlayer() == pAI) {
            Log.getInstance().Log(1, "Round " + nRound + ": AI Wins " + blackJackPlayRound.getMoneyOfRoundth() + " Dollars");

        } else {
            Log.getInstance().Log(1, "Round " + nRound + ": You Wins " + blackJackPlayRound.getMoneyOfRoundth() + " Dollars");
        }
        Log.getInstance().Log(1, "AI Hand:      " + pAI.printCardInHand());
        Log.getInstance().Log(1, "Player Hand : " + pPlayer.printCardInHand());
        Log.getInstance().Log(1, "-----------------------------------------------");

       // UI.RefreshLog(Log.getInstance().getLog());
    }
}
