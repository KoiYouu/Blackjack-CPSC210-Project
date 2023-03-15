package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GamblerTest extends PlayerTest {

    private Gambler gambler;

    @BeforeEach
    void runBefore() {
        gambler = new Gambler(1000);
        player = new Gambler(1000);
    }

    @Test
    void testConstructor() {
        assertEquals(49, gambler.getGamblerID());
        assertEquals(1000, gambler.getBalance());
        assertEquals(0, gambler.getWins());
        assertEquals(0, gambler.getDraws());
        assertEquals(0, gambler.getLosses());
        assertEquals(0, gambler.getBet());
        assertFalse(gambler.isStand());
        assertEquals(0, gambler.getHand().size());
    }

    @Test
    void testAddWin() {
        gambler.addWin();
        assertEquals(1, gambler.getWins());
    }

    @Test
    void testAddDraw() {
        gambler.addDraw();
        assertEquals(1, gambler.getDraws());
    }

    @Test
    void testAddLoss() {
        gambler.addLoss();
        assertEquals(1, gambler.getLosses());
    }

    @Test
    void testAddBalance() {
        gambler.addBalance(1000);
        assertEquals(2000, gambler.getBalance());
        assertEquals(2420, gambler.addBalance(420));
    }

    @Test
    void testRemoveBalance() {
        gambler.removeBalance(400);
        assertEquals(600, gambler.getBalance());
        assertEquals(0, gambler.removeBalance(600));
    }

    @Test
    void testPlayerDouble() {
        gambler.setBet(50);
        assertEquals(50, gambler.getBet());
        assertEquals(950, gambler.getBalance());
        gambler.gamblerDouble();
        assertEquals(100, gambler.getBet());
        assertEquals(900, gambler.getBalance());
        assertEquals(1, gambler.getHand().size());
    }

    @Test
    void testGetAllCards() {
        gambler.hitCard();
        gambler.hitCard();
        int randNum1 = gambler.getHand().get(0).getRandNum();
        int randNum2 = gambler.getHand().get(1).getRandNum();
        String compare = " [Name: " + Cards.getDeck(randNum1 % 13) +" Suit: "
                + Cards.getSuits(randNum1 % 4) + "]  [Name: "
                + Cards.getDeck(randNum2 % 13) + " Suit: "
                + Cards.getSuits(randNum2 % 4) + "] ";
        assertEquals(compare, gambler.getAllCards());
    }

    @Test
    void testSetBet() {
        gambler.setBet(150);
        assertEquals(150, gambler.getBet());
        assertEquals(850, gambler.getBalance());
    }

    @Test
    void testPlayerWin() {
        gambler.hitCard();
        gambler.setStand();
        gambler.setBet(50);
        assertEquals(1, gambler.getHand().size());
        assertTrue(gambler.isStand());
        assertEquals(50, gambler.getBet());
        assertEquals(950, gambler.getBalance());
        gambler.gamblerWin();
        assertEquals(0, gambler.getHand().size());
        assertFalse(gambler.isStand());
        assertEquals(0, gambler.getBet());
        assertEquals(1050, gambler.getBalance());
    }

    @Test
    void testPlayerPush() {
        gambler.hitCard();
        gambler.setStand();
        gambler.setBet(50);
        assertEquals(1, gambler.getHand().size());
        assertTrue(gambler.isStand());
        assertEquals(50, gambler.getBet());
        assertEquals(950, gambler.getBalance());
        gambler.gamblerPush();
        assertEquals(0, gambler.getHand().size());
        assertFalse(gambler.isStand());
        assertEquals(0, gambler.getBet());
        assertEquals(1000, gambler.getBalance());
    }

    @Test
    void testPlayerLose() {
        gambler.hitCard();
        gambler.setStand();
        gambler.setBet(50);
        assertEquals(1, gambler.getHand().size());
        assertTrue(gambler.isStand());
        assertEquals(50, gambler.getBet());
        assertEquals(950, gambler.getBalance());
        gambler.gamblerLoss();
        assertEquals(0, gambler.getHand().size());
        assertFalse(gambler.isStand());
        assertEquals(0, gambler.getBet());
        assertEquals(950, gambler.getBalance());
    }

    @Test
    void testToJson() {
        gambler.setDraws(420);
        gambler.setWins(69);
        gambler.setLosses(21);
        gambler.setGamblerID(1);
        String jsonObject = gambler.toJson().toString();
        assertEquals("{\"wins\":69,\"balance\":1000,\"draws\":420,\"gamblerID\":1,\"losses\":21}"
                , jsonObject);
    }
}
