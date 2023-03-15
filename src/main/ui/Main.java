package ui;

import java.io.FileNotFoundException;

public class Main {
    //EFFECT: Starts a game of blackjack
    public static void main(String[] args) {
        try {
            BlackJackGame game = new BlackJackGame();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }

    }
}
