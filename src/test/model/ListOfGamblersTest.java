package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfGamblersTest {

    private ListOfGamblers gamblers;

    @BeforeEach
    void runBefore() {
        gamblers = new ListOfGamblers(2, 1000);
    }

    @Test
    void testConstructor() {
        assertEquals(2, gamblers.getGamblers().size());
        assertEquals(1000, gamblers.getGamblers(0).getBalance());
        assertEquals(1000, gamblers.getGamblers(1).getBalance());
    }

    @Test
    void testGetPlayers() {
        assertEquals(53, gamblers.getGamblers(0).getGamblerID());
        assertEquals(54, gamblers.getGamblers(1).getGamblerID());
    }

    @Test
    void testGetAllPlayersCards() {
        gamblers.getGamblers(0).hitCard();
        gamblers.getGamblers(1).hitCard();
        int randNum1 = gamblers.getGamblers(0).hand.get(0).getRandNum();
        int randNum2 = gamblers.getGamblers(1).hand.get(0).getRandNum();
        String compare1 = "Player " + gamblers.getGamblers(0).getGamblerID()
                + " has " + " [Name: " + Cards.getDeck(randNum1 % 13)
                + " Suit: " + Cards.getSuits(randNum1 % 4) + "]";
        String compare2 = " Player " + gamblers.getGamblers(1).getGamblerID()
                + " has " + " [Name: " + Cards.getDeck(randNum2 % 13)
                + " Suit: " + Cards.getSuits(randNum2 % 4) + "] ";
        String compare = compare1 + compare2;
        assertEquals(compare, gamblers.getAllGamblersCards());
    }

    @Test
    void testToJson() {
        gamblers.getGamblers(0).addDraw();
        gamblers.getGamblers(0).addLoss();
        gamblers.getGamblers(0).addWin();
        gamblers.getGamblers(0).setBalance(500);
        gamblers.getGamblers(1).addLoss();
        gamblers.getGamblers(1).addLoss();
        gamblers.getGamblers(1).addLoss();
        String jsonObject = gamblers.toJson().toString();
        assertEquals("{\"gambler\":[{\"wins\":1,\"balance\":500,\"draws\":1,\"gamblerID\":"
                + gamblers.getGamblers(0).getGamblerID() + ",\"losses\":1},"
                + "{\"wins\":0,\"balance\":1000,\"draws\":0,\"gamblerID\":"
                + gamblers.getGamblers(1).getGamblerID() + ",\"losses\":3}]}", jsonObject);
    }
}
