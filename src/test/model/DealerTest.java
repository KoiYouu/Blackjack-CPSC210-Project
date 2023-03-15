package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DealerTest extends PlayerTest {

    private Dealer dealer;

    @BeforeEach
    void runBefore() {
        dealer = new Dealer();
        player = new Dealer();
    }

    @Test
    void testConstructor() {
        assertFalse(dealer.isStand());
        assertEquals(0, dealer.hand.size());
    }

    @Test
    void testStartingDealerTurn() {
        dealer.startingDealerTurn();
        assertEquals(2 , dealer.hand.size());
        assertFalse(dealer.hand.get(1).getFacingUp());
        assertTrue(dealer.hand.get(0).getFacingUp());
    }

    @Test
    void testDealersTurn() {
        dealer.startingDealerTurn();
        dealer.dealersTurn();
        for (Cards card: dealer.hand) {
            assertTrue(card.getFacingUp());
        }
        assertTrue(dealer.isStand());
        assertTrue(2 <= dealer.hand.size());
    }

    @Test
    void testDealersTurnTotalSumLessThen16() {
        dealer.hitCard();
        dealer.hitCard();
        dealer.hand.get(0).setSpecificCard(0);
        dealer.hand.get(1).setSpecificCard(0);
        dealer.dealersTurn();
        assertTrue(dealer.handValueSoft() >= 16);
    }

    @Test
    void testDealerTurnTotalSumMoreThen16() {
        dealer.hitCard();
        dealer.hitCard();
        dealer.hand.get(0).setSpecificCard(9);
        dealer.hand.get(1).setSpecificCard(9);
        dealer.dealersTurn();
        assertTrue(dealer.handValueHard() >= 16);
        assertEquals(20, dealer.handValueHard());
        assertEquals(2, dealer.hand.size());
    }

    @Test
    void testGetDealersCardsAFaceDownCard() {
        dealer.startingDealerTurn();
        int randNum = dealer.getHand().get(0).getRandNum();
        String compare = " [Name: " + Cards.getDeck(randNum % 13)
                + " Suit: " + Cards.getSuits(randNum % 4) + "]" + " [X]";
        assertEquals(compare, dealer.getDealersCards());

    }

    @Test
    void testGetDealersCardAllFaceUp() {
        dealer.hitCard();
        dealer.hitCard();
        int randNum1 = dealer.getHand().get(0).getRandNum();
        int randNum2 = dealer.getHand().get(1).getRandNum();
        String compare = " [Name: " + Cards.getDeck(randNum1 % 13)
                + " Suit: " + Cards.getSuits(randNum1 % 4) + "]"
                + " [Name: " + Cards.getDeck(randNum2 % 13)
                + " Suit: " + Cards.getSuits(randNum2 % 4) + "]";
        assertEquals(compare, dealer.getDealersCards());
    }
}
