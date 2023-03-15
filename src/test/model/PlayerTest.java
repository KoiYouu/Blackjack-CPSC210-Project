package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class PlayerTest {

    protected Player player;

    @Test
    void testConstructor() {
        assertFalse(player.isStand());
        assertEquals(0, player.hand.size());
    }

    @Test
    void testHitCard() {
        player.hitCard();
        int randNum = player.hand.get(0).getRandNum();
        assertEquals(Cards.getValues(randNum  % 13), player.hand.get(0).getValue());
        assertEquals(Cards.getDeck(randNum % 13), player.hand.get(0).getCardName());
        assertEquals(Cards.getSuits(randNum % 4), player.hand.get(0).getSuit());
    }

    @Test
    void testClearHand(){
        player.hitCard();
        player.hitCard();
        assertEquals(2, player.getHand().size());
        player.clearHand();
        assertEquals(0, player.getHand().size());
    }

    @Test
    void testSetStandAndNotStand() {
        assertFalse(player.isStand());
        player.setStand();
        assertTrue(player.isStand());
        player.setNotStand();
        assertFalse(player.isStand());
    }

    @Test
    void testGetCard() {
        player.hitCard();
        int randNum = player.hand.get(0).getRandNum();
        String cardInfo = "[Name: " + Cards.getDeck(randNum  % 13)
                + " Suit: " + Cards.getSuits(randNum % 4) + "]";
        assertEquals(cardInfo , player.getCards(0));
    }

    @Test
    void testHandValue1Card() {
        player.hitCard();
        player.hand.get(0).setSpecificCard(2);
        assertEquals(Cards.getValues(2), player.handValueHard());
    }

    @Test
    void testHandValueMultipleCards() {
        player.hitCard();
        player.hitCard();
        player.hitCard();
        player.hand.get(0).setSpecificCard(2);
        player.hand.get(1).setSpecificCard(6);
        player.hand.get(2).setSpecificCard(10);
        int totalValue = Cards.getValues(2) + Cards.getValues(6) + Cards.getValues(10);
        assertEquals(totalValue, player.handValueHard());
    }

    @Test
    void testHandValueWithAceSoft() {
        player.hitCard();
        player.hitCard();
        player.hand.get(0).setSpecificCard(2);
        player.hand.get(1).setSpecificCard(12);
        int totalValue = Cards.getValues(2) + Cards.getValues(12);
        assertEquals(totalValue + 10, player.handValueSoft());
        assertEquals(totalValue, player.handValueHard());
    }

    @Test
    void testHandValueWithAceHard() {
        player.hitCard();
        player.hitCard();
        player.hitCard();
        player.hand.get(0).setSpecificCard(5);
        player.hand.get(1).setSpecificCard(12);
        player.hand.get(2).setSpecificCard(6);
        int totalValue = Cards.getValues(5) + Cards.getValues(12) + Cards.getValues(6);
        assertEquals(totalValue, player.handValueHard());
        assertEquals(totalValue, player.handValueSoft());

    }
}
