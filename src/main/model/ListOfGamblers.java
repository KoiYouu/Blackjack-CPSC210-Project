package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// represents multiple gamblers
public class ListOfGamblers {
    private ArrayList<Gambler> gamblers;

    // REQUIRES: numGamblers >= 1, startingBalance >= 1
    // EFFECTS: Creates an array of gamblers with the number of gamblers being the given numGamblers
    // and every gambler has the same balance startingBalance
    public ListOfGamblers(int numGamblers, int startingBalance) {
        gamblers = new ArrayList<>();
        for (int i = 0; i < numGamblers; i++) {
            gamblers.add(new Gambler(startingBalance));
        }
    }

    public ArrayList<Gambler> getGamblers() {
        return gamblers;
    }

    // REQUIRES: index >= 0 and < gamblers.size()
    // EFFECTS: gets the gambler in the listofgamblers at the given index
    public Gambler getGamblers(int index) {
        return gamblers.get(index);
    }

    // EFFECTS: returns a string of each gamblers cards
    public String getAllGamblersCards() {
        String allCards = "";
        for (Gambler gambler : gamblers) {
            allCards = allCards + "Player " + gambler.getGamblerID() + " has "
                    + gambler.getAllCards();
        }
        return allCards;
    }

    // EFFECTS: returns this as a Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("gambler", gamblerToJson());
        return json;
    }

    // EFFECTS: returns gamblers in a listOfGamblers as a JSON array
    private JSONArray gamblerToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Gambler gambler : gamblers) {
            jsonArray.put(gambler.toJson());
        }

        return jsonArray;
    }
}

