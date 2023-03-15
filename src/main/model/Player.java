package model;

import java.util.ArrayList;

// represents an abstract class player which has the general similarities between a gambler and dealer
public class Player {
    protected ArrayList<Cards> hand; // players hand with all its cards
    protected boolean stand; // True if player is standing, false if player is not

    // EFFECT: Super constructor for dealer and player with an empty hand of cards and not standing
    public Player() {
        hand = new ArrayList<>();
        stand = false;
    }

    // MODIFIES: this
    // EFFECTS: adds a new random card to our hand and returns what card we got
    public Cards hitCard() {
        Cards newCard = new Cards();
        hand.add(newCard);
        return newCard;
    }

    // EFFECTS: checks if there is an ace in the players hand, returns true if there is false otherwise
    public boolean checkAceInHand() {
        boolean aceExists = false;
        for (Cards card : hand) {
            if (card.getCardName() == "A") {
                aceExists = true;
            }
        }
        return aceExists;
    }

    // EFFECTS: adds up all cards in hand and returns their total blackjack hard value
    public int handValueHard() {
        int handValueHard = 0;
        for (Cards card : hand) {
            handValueHard += card.getValue();
        }
        return handValueHard;
    }

    public int handValueSoft() {
        int handValueSoft = handValueHard();
        if (handValueHard() < 12 && checkAceInHand()) {
            handValueSoft += 10;
        }
        return handValueSoft;
    }

    // MODIFIES: this
    // EFFECTS: clears hand of all cards
    public void clearHand() {
        hand.clear();
    }

    // REQUIRES index to be >=0 and < hand.size()
    // EFFECTS: returns the info of the card at our given position
    public String getCards(int index) {
        return hand.get(index).getCardInfo();
    }

    // MODIFIES: This
    // EFFECTS: Sets the player to be standing
    public void setStand() {
        stand = true;
    }

    // MODIFIES: This
    // EFFECTS: Sets the player to not be standing
    public void setNotStand() {
        stand = false;
    }


    public ArrayList<Cards> getHand() {
        return hand;
    }

    public boolean isStand() {
        return stand;
    }
}
