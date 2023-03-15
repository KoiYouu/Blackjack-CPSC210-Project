package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardsTest {

    private Cards card1;
    private Cards card2;

    @BeforeEach
    void runBefore() {
        card1 = new Cards();
        card2 = new Cards();
    }

    @Test
    void testCardsConstructor() {
        assertTrue(card1.getFacingUp());
        assertEquals(Cards.getDeck(card1.getRandNum() % 13), card1.getCardName());
        assertEquals(Cards.getValues(card1.getRandNum() % 13), card1.getValue());
        assertEquals(Cards.getSuits(card1.getRandNum() % 4), card1.getSuit());

        assertTrue(card2.getFacingUp());
        assertEquals(Cards.getDeck(card2.getRandNum() % 13), card2.getCardName());
        assertEquals(Cards.getValues(card2.getRandNum() % 13), card2.getValue());
        assertEquals(Cards.getSuits(card2.getRandNum() % 4), card2.getSuit());
    }

    @Test
    void testSetCardFaceDownAndUp() {
        assertTrue(card1.getFacingUp());
        card1.setCardFaceDown();
        assertFalse(card1.getFacingUp());
        card1.setCardFaceUp();
        assertTrue(card1.getFacingUp());
    }

    @Test
    void testGetCardInfoFacingUp() {
        int x = card1.getRandNum();
        String test = "[Name: " + Cards.getDeck(x  % 13)
                + " Suit: " + Cards.getSuits(x % 4) + "]";
        assertEquals(test, card1.getCardInfo());
    }

    @Test
    void testGetCardInfoFacingDown() {
        card2.setCardFaceDown();
        assertEquals("[X]", card2.getCardInfo());
    }

    @Test
    void testSetSpecificCard() {
        card1.setSpecificCard(0);
        assertTrue(card1.getFacingUp());
        assertEquals("S", card1.getSuit());
        assertEquals(2, card1.getValue());
        assertEquals("2", card1.getCardName());
    }


}