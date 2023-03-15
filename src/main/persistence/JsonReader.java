package persistence;

import model.Gambler;
import model.ListOfGamblers;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads gambler data from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfGamblers read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfGamblers(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses listOfGamblers from JSON object and returns it
    private ListOfGamblers parseListOfGamblers(JSONObject jsonObject) {
        ListOfGamblers gamblers = new ListOfGamblers(0,0);
        addGamblers(gamblers, jsonObject);
        return gamblers;
    }

    // MODIFIES: gamblers
    // EFFECTS: parses gamblers from JSON object and adds them to listOfGamblers
    private void addGamblers(ListOfGamblers gamblers, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("gambler");
        for (Object json : jsonArray) {
            JSONObject nextGambler = (JSONObject) json;
            addGambler(gamblers, nextGambler);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses gambler from JSON object and adds it to gamblers
    private void addGambler(ListOfGamblers gamblers, JSONObject jsonObject) {
        int gamblerID = jsonObject.getInt("gamblerID");
        int balance = jsonObject.getInt("balance");
        int wins = jsonObject.getInt("wins");
        int draws = jsonObject.getInt("draws");
        int losses = jsonObject.getInt("losses");
        Gambler gambler = new Gambler(balance);
        gambler.setGamblerID(gamblerID);
        gambler.setWins(wins);
        gambler.setLosses(losses);
        gambler.setDraws(draws);
        gamblers.getGamblers().add(gambler);
    }
}
