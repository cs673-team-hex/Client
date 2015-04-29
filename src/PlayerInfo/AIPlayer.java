/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlayerInfo;

import PokerGame.BlackJackRule;
import PokerDeck.Card;
import java.util.ArrayList;

/**
 *
 * @author Boheng
 */
public class AIPlayer extends Player {

    final boolean bAI;
    private double dBalance;

//Constructor
    public AIPlayer(int nInitMoney, boolean bSetAI) {
        bAI = bSetAI;
        dBalance = nInitMoney;
        CardArray = new ArrayList<Card>();
        bDouble = false;
    }

}
