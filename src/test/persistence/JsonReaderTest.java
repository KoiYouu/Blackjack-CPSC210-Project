package persistence;

import model.ListOfGamblers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ListOfGamblers gamblers = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyListOfGamblers() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyListOfGamblers.json");
        try {
            ListOfGamblers gamblers = reader.read();
            assertEquals(0, gamblers.getGamblers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralListOfGamblers() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralListOfGamblers.json");
        try {
            ListOfGamblers gamblers = reader.read();
            assertEquals(3, gamblers.getGamblers().size());
            assertEquals(1, gamblers.getGamblers(0).getGamblerID());
            assertEquals(0, gamblers.getGamblers(0).getBalance());
            assertEquals(0, gamblers.getGamblers(0).getWins());
            assertEquals(1, gamblers.getGamblers(0).getLosses());
            assertEquals(1, gamblers.getGamblers(0).getDraws());

            assertEquals(2, gamblers.getGamblers(1).getGamblerID());
            assertEquals(600, gamblers.getGamblers(1).getBalance());
            assertEquals(0, gamblers.getGamblers(1).getWins());
            assertEquals(0, gamblers.getGamblers(1).getDraws());
            assertEquals(2, gamblers.getGamblers(1).getLosses());

            assertEquals(3, gamblers.getGamblers(2).getGamblerID());
            assertEquals(1200, gamblers.getGamblers(2).getBalance());
            assertEquals(1, gamblers.getGamblers(2).getWins());
            assertEquals(0, gamblers.getGamblers(2).getDraws());
            assertEquals(1, gamblers.getGamblers(2).getLosses());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
