/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_14062696.server;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tong - 14062696
 */
public class Deck {
//    Builds a list of list with all the values of a card.
    public static List<List<String>> buildDeck(){
        String position[] = {};
        List<List<String>> deck = new ArrayList<>();
        String [] suits = {"spades", "hearts", "clovers", "diamonds"};
        String [] cards = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        for(int i=0;i<cards.length;i++){
            for(int j=0;j<suits.length;j++){
                List<String> card = new ArrayList<>();
                card.add(cards[i]);
                card.add(suits[j]);
                card.add(cardValue(cards[i]));
                deck.add(card);    
            }  
        } 
        return deck;
    }
        
//  Used to add values to the card types.    
    public static String cardValue(String card) {
        if (card == "Jack") {
            return "10";
        }
        else if (card == "Queen") {
            return "10";
        }
        else if (card == "King") {
            return "10";
        }
        else if (card == "Ace") {
            return "11";
        }
        else {
            return card;
        }
    }    
    
}
