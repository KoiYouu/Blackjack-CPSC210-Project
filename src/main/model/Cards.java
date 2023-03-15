package model;

import java.util.Random;

// represents all the cards in a regular deck
public class Cards {
    private String cardName; // name of the card ex. A, 2, 3, ..., J, Q, K
    private int value; // value of the card in a blackjack game
    private String suit; // suit of the card
    private boolean facingUp; // true if card is face up, false if it is face down
    private int randNum; // random number used to dictate what card to pick

    // possible cards
    private static final String[] deck = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
    private static final int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1}; // value of cards
    private static final String[] suits = {"C", "D", "H", "S"}; // possible suits

    private Random random = new Random();


    // EFFECTS: Creates a playing card with a random cardName and its corresponding value, random suit,
    // and keeps it face up.
    public Cards() {
        randNum = random.nextInt(1000000);
        cardName = deck[randNum % 13];
        value = values[randNum % 13];
        suit = suits[randNum % 4];
        facingUp = true;
    }

    // MODIFIES: this
    // EFFECTS: sets card to be face down
    public void setCardFaceDown() {
        facingUp = false;
    }

    // MODIFIES: this
    // EFFECTS: sets card to be face up
    public void setCardFaceUp() {
        facingUp = true;
    }

    // EFFECTS: return the cards info as a string
    public String getCardInfo() {
        if (this.facingUp) {
            return "[Name: " + this.getCardName()
                     + " Suit: " + this.getSuit() + "]";
        } else {
            return "[X]";
        }
    }

    public int getValue() {
        return value;
    }

    public String getCardName() {
        return cardName;
    }

    public String getSuit() {
        return suit;
    }

    public boolean getFacingUp() {
        return facingUp;
    }

    public int getRandNum() {
        return randNum;
    }

    public void setSpecificCard(int index) {
        cardName = deck[index];
        value = values[index];
        suit = "S";
        facingUp = true;
    }


    // REQUIRES: Index to be from 0-12
    // EFFECTS: Returns the name of the card with the given index
    public static String getDeck(int index) {
        return deck[index];
    }

    // REQUIRES: Index to be from 0-3
    // EFFECTS: Returns the suit of the card with the given index
    public static String getSuits(int index) {
        return suits[index];
    }

    // REQUIRES: Index to be from 0-12
    // EFFECTS: Returns the value of the card with the given index
    public static int getValues(int index) {
        return values[index];
    }
}
