package persistence;

import model.ListOfGamblers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {


    @Test
    void testWriterInvalidFile() {
        try {
            ListOfGamblers gamblers = new ListOfGamblers(1, 100);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testEmptyListOfGamblers() {
        try {
            ListOfGamblers gamblers = new ListOfGamblers(0, 0);
            JsonWriter writer = new JsonWriter("./data/testEmptyListOfGamblers.json");
            writer.open();
            writer.write(gamblers);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyListOfGamblers.json");
            gamblers = reader.read();
            assertEquals(0, gamblers.getGamblers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testGeneralListOfGamblers() {
        try {
            ListOfGamblers gamblers = new ListOfGamblers(2, 1000);
            gamblers.getGamblers(0).addDraw();
            gamblers.getGamblers(0).addLoss();
            gamblers.getGamblers(0).addWin();
            gamblers.getGamblers(0).setBalance(500);
            gamblers.getGamblers(1).addLoss();
            gamblers.getGamblers(1).addLoss();
            gamblers.getGamblers(1).addLoss();
            JsonWriter writer = new JsonWriter("./data/testGeneralListOfGamblers.json");
            writer.open();
            writer.write(gamblers);
            writer.close();

            JsonReader reader = new JsonReader("./data/testGeneralListOfGamblers.json");
            gamblers = reader.read();
            assertEquals(2, gamblers.getGamblers().size());
            assertEquals(500, gamblers.getGamblers(0).getBalance());
            assertEquals(1, gamblers.getGamblers(0).getWins());
            assertEquals(1, gamblers.getGamblers(0).getLosses());
            assertEquals(1, gamblers.getGamblers(0).getDraws());
            assertEquals(4, gamblers.getGamblers(0).getGamblerID());

            assertEquals(1000, gamblers.getGamblers(1).getBalance());
            assertEquals(0, gamblers.getGamblers(1).getWins());
            assertEquals(0, gamblers.getGamblers(1).getDraws());
            assertEquals(3, gamblers.getGamblers(1).getLosses());
            assertEquals(5, gamblers.getGamblers(1).getGamblerID());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
