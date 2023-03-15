package ui;

import model.Dealer;
import model.Gambler;
import model.ListOfGamblers;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// represents the game of blackjack
public class BlackJackGame {
    private Scanner userInput;
    private Dealer dealer;
    private ListOfGamblers listOfGamblers;
    private boolean gameOver = false;
    private static final String SAVELOCATION = "./data/BlackJackGame.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // MODIFIES this
    // EFFECTS: Creates a game of blackjack
    public BlackJackGame() throws FileNotFoundException {
        userInput = new Scanner(System.in);
        jsonWriter = new JsonWriter(SAVELOCATION);
        jsonReader = new JsonReader(SAVELOCATION);
        dealer = new Dealer();
        this.startGame();
    }

    // MODIFIES: this, player, dealer
    // EFFECTS: Starts up the game of blackjack, creates as many players as the user specifies with each
    // player getting the specified balance. Displays the menu options to quit or play, if neither option is chosen
    // a message will appear telling the user to pick a given option. if quit is selected game exits, if play is
    // selected the game starts, with bets being collected and the game playing out. Also has the option to save
    // current data such as wins, losses, draws, balance, and playerID. Can load from a previous save.
    public void startGame() {
        preGameSetUp();
        while (!gameOver) {
            displayMenuOptions();
            String input = userInput.next().toLowerCase();
            if (input.equals("q")) {
                gameOver = true;
            } else if (input.equals("p")) {
                play();
            } else if (input.equals("b")) {
                addBalanceToPlayer();
            } else if (input.equals("n")) {
                addPlayer();
            } else if (input.equals("v")) {
                viewScoreBoard();
            } else if (input.equals("s")) {
                saveGameData();
            } else if (input.equals("l")) {
                loadGameData();
            } else {
                System.out.println("Please choose from given options.");
            }
        }
        System.out.println("See you next time!");
    }

    // MODIFIES: dealer, player, this
    // EFFECTS: runs a standard game of blackjack, gets players bets,
    // plays the game, pays people out, and resets the dealers hands
    public void play() {
        getBets();
        playGame();
        payOut();
        resetDealer();
    }

    // MODIFIES: dealer
    // EFFECTS: clears dealers hand and makes him not stand
    public void resetDealer() {
        dealer.clearHand();
        dealer.setNotStand();
    }

    // EFFECTS: prints out a scoreboard of every players wins losses draws and balance
    public void viewScoreBoard() {
        System.out.println("");
        for (Gambler gambler : listOfGamblers.getGamblers()) {
            System.out.println("Player " + gambler.getGamblerID() + " stats: |Wins "
                    + gambler.getWins() + "| |Losses " + gambler.getLosses()
                        + "| |Draws " + gambler.getDraws() + "| |Balance $" + gambler.getBalance() + "|");
        }
        System.out.println("");
    }

    // REQUIRES: startingBalance > 0
    // MODIFIES: this, listOfPlayers, player
    // EFFECTS: creates a player with the specified starting balance and adds it to the end of listofplayers
    public void addPlayer() {
        System.out.println("Please specify the starting balance of the new player");
        int input = 0;
        while (input < 1) {
            input = userInput.nextInt();
            if (input < 1) {
                System.out.println("Please enter a valid number");
            }
        }
        listOfGamblers.getGamblers().add(new Gambler(input));
        int lastIndex = listOfGamblers.getGamblers().size() - 1;
        System.out.println("Successfully added new player with ID "
                + listOfGamblers.getGamblers(lastIndex).getGamblerID()
                    + " with balance of $" + listOfGamblers.getGamblers(lastIndex).getBalance());

    }

    // REQUIRES: moneyToAdd > 0 and input for player index to be >= 1 and <= listOfPlayers.getGamblers().size()
    // MODIFIES: this, player
    // EFFECTS: adds specified money to the specified player
    public void addBalanceToPlayer() {
        System.out.println("Please specify which player to add money to from [1-" + listOfGamblers.getGamblers().size()
                + "]");
        int input = 0;
        while (input < 1 || input > listOfGamblers.getGamblers().size()) {
            input = userInput.nextInt();
            if (input < 1 || input > listOfGamblers.getGamblers().size()) {
                System.out.println("Please enter a valid number");
            }
        }
        System.out.println("Selected player " + input + "\n");
        System.out.println("Specify how much money to add");
        int moneyToAdd = 0;
        while (moneyToAdd < 1) {
            moneyToAdd = userInput.nextInt();
            if (moneyToAdd < 1) {
                System.out.println("Please enter a valid number");
            }
        }
        System.out.println("Adding $" + moneyToAdd);
        listOfGamblers.getGamblers(input - 1).addBalance(moneyToAdd);
        System.out.println("Player " + listOfGamblers.getGamblers(input - 1).getGamblerID()
                + " has received $" + moneyToAdd + " and their new balance is $"
                    + listOfGamblers.getGamblers(input - 1).getBalance());
    }

    // REQUIRES: bet >= 1 and <= players balance
    // MODIFIES: player
    // EFFECT: sets all the players bets and takes away the bet from their balance
    public void getBets() {
        for (Gambler gambler : listOfGamblers.getGamblers()) {
            System.out.println("Player " + gambler.getGamblerID() + " your current balance is: $"
                    + gambler.getBalance());
            System.out.println("How much would you like to bet? [Must range from 1-" + gambler.getBalance()
                    + "]");
            int bet = 0;
            while (bet <= 0 || bet > gambler.getBalance()) {
                bet = userInput.nextInt();
                if (bet <= 0 || bet > gambler.getBalance()) {
                    System.out.println("Please enter a valid number");
                }
            }
            gambler.setBet(bet);
        }
    }

    // MODIFIES: this, dealer, player
    // EFFECTS: Plays out a single round of blackjack asking for every players actions,
    // and then finally finishing it off with the dealers turn
    public void playGame() {
        beginRound();
        for (Gambler gambler : listOfGamblers.getGamblers()) {
            playersTurn(gambler);
        }
        System.out.println("Dealers turn!");
        dealer.dealersTurn();
        System.out.println("Dealers Cards: " + dealer.getDealersCards()  + "Hand total: " + dealer.handValueSoft());
        System.out.println("Finished that round!");
    }

    // MODIFIES: player
    // EFFECTS: pays out the player if they won according to regular blackjack rules,
    // or does nothing if player did not win, returns players bet if they pushed
    public void payOut() {
        System.out.println("Now to recap who won and lost with their respective payouts! \n");
        for (Gambler gambler : listOfGamblers.getGamblers()) {
            if (gambler.handValueHard() <= 21 && dealer.handValueHard() > 21) {
                System.out.println("Player " + gambler.getGamblerID() + " wins!");
                System.out.println("They earned a total of $" + gambler.getBet() + "\n");
                gambler.gamblerWin();
            } else if (gambler.handValueHard() <= 21 && gambler.handValueHard() > dealer.handValueHard()) {
                System.out.println("Player " + gambler.getGamblerID() + " wins!");
                System.out.println("They earned a total of $" + gambler.getBet() + "\n");
                gambler.gamblerWin();
            } else if (gambler.handValueHard() <= 21 && gambler.handValueHard() == dealer.handValueHard()) {
                System.out.println("Player " + gambler.getGamblerID() + " pushes!");
                System.out.println("They managed to keep their $" + gambler.getBet() + "\n");
                gambler.gamblerPush();
            } else {
                System.out.println("Player " + gambler.getGamblerID() + " loses!");
                System.out.println("They lost a total of $" + gambler.getBet() + "\n");
                gambler.gamblerLoss();
            }
        }
    }

    // REQUIRES: non-null player object, double to be inputted only if player has enough money
    // MODIFIES: player
    // EFFECTS: gets the players input for what to do during their turn and acts accordingly,
    // h for hit, s for stand, d for double, stops allowing user to interact once card total reaches > 21
    // or they stand.
    public void playersTurn(Gambler gambler) {
        System.out.println("Player " + gambler.getGamblerID() + "\'s turn:");
        System.out.println("Dealers cards: " + dealer.getDealersCards() + " Hand total: "
                + dealer.getHand().get(0).getValue() + "\n");
        if (gambler.checkAceInHand() && gambler.handValueHard() < 12) {
            System.out.println("Players cards: " + gambler.getAllCards()  + "Hand total: " + gambler.handValueHard()
                    + "/" + gambler.handValueSoft());
        } else {
            System.out.println("Players cards: " + gambler.getAllCards() + "Hand total: " + gambler.handValueHard());
        }
        while (!gambler.isStand() && gambler.handValueHard() <= 21 && gambler.handValueSoft() != 21) {
            displayGameOptions();
            String input = userInput.next().toLowerCase();
            if (input.equals("h")) {
                playerHit(gambler);
            } else if (input.equals("d")) {
                playerDouble(gambler);
            } else if (input.equals("s")) {
                playerStand(gambler);
            } else {
                System.out.println("Please choose from one of the listed options.");
                displayGameOptions();
            }
        }
    }

    // REQUIRES: non-null player object
    // MODIFIES: player
    // EFFECT: gives player another card and displays all the players current cards
    // gives a message if the hit busts the player
    public void playerHit(Gambler gambler) {
        gambler.hitCard();
        System.out.println("Hit!");
        if ((gambler.checkAceInHand() && gambler.handValueHard() < 12)) {
            System.out.println("Cards: " + gambler.getAllCards()  + "Hand total: " + gambler.handValueHard()
                    + "/" + gambler.handValueSoft());
        } else {
            System.out.println("Cards: " + gambler.getAllCards() + "Hand total: " + gambler.handValueHard());
        }
        if (gambler.handValueHard() > 21) {
            System.out.println("Bust!");
        }
        System.out.println("");
    }

    // REQUIRES: non-null player object
    // MODIFIES: player
    // EFFECT: gives player another card, doubles their bet,
    // and displays all the players current cards
    // gives a message if the hit busts the player
    public void playerDouble(Gambler gambler) {
        gambler.gamblerDouble();
        System.out.println("Double!");
        if ((gambler.checkAceInHand() && gambler.handValueHard() < 12)) {
            System.out.println("Cards: " + gambler.getAllCards()  + "Hand total: " + gambler.handValueHard()
                    + "/" + gambler.handValueSoft());
        } else {
            System.out.println("Cards: " + gambler.getAllCards() + "Hand total: " + gambler.handValueHard());
        }
        if (gambler.handValueHard() > 21) {
            System.out.println("Bust!");
        }
        System.out.println("");
    }

    // REQUIRES: non-null player object
    // MODIFIES: player
    // EFFECT: changes the player to be standing and shows all their cards
    public void playerStand(Gambler gambler) {
        gambler.setStand();
        System.out.println("Stand!");
        System.out.println("Cards: " + gambler.getAllCards()  + "Hand total: " + gambler.handValueSoft());
        System.out.println("");
    }

    // REQUIRES: numPlayers >= 1, startingMoney >= 1
    // MODIFIES: ListOfPlayers
    // EFFECTS: Starts the game and sets up the number of players, balance for each player,
    // and initializes the dealer
    public void preGameSetUp() {
        System.out.println("Welcome To BlackJack!");
        System.out.println("This BlackJack table pays 2:1 and the dealer stands on a hard 17 and hits on a soft 17 \n");
        System.out.println("Please enter how many players are playing (Recommended size to be less then 6)");
        int numPlayers = 0;
        while (numPlayers < 1) {
            numPlayers = userInput.nextInt();
            if (numPlayers < 1) {
                System.out.println("Please enter valid number");
            }
        }
        System.out.println("You have selected " + numPlayers + " players \n");
        System.out.println("Please enter how much starting balance to give to each player");
        int startingMoney = 0;
        while (startingMoney < 1) {
            startingMoney = userInput.nextInt();
            if (startingMoney < 1) {
                System.out.println("Please enter valid number");
            }
        }
        System.out.println("You have given everyone $" + startingMoney + " to start with! \n");
        listOfGamblers = new ListOfGamblers(numPlayers, startingMoney);
    }

    // MODIFIES: dealer, player
    // EFFECTS: starts the game of blackjack off by giving the deal 2 cards, 1 face up and another face down, then
    // gives every player 2 cards and displays the current state of the game.
    public void beginRound() {
        System.out.println("Dealers turn!");
        System.out.println("");
        dealer.startingDealerTurn();
        System.out.println("Dealer draws: " + dealer.getDealersCards()  + " Hand total: "
                + dealer.getHand().get(0).getValue() + "\n");
        System.out.println("Now drawing 2 cards for each player:");
        System.out.println("");
        for (int i = 0; i < listOfGamblers.getGamblers().size(); i++) {
            listOfGamblers.getGamblers(i).hitCard();
            listOfGamblers.getGamblers(i).hitCard();
        }
        System.out.println(listOfGamblers.getAllGamblersCards());
        System.out.println("");
    }

    // EFFECTS: displays the menu options
    public void displayMenuOptions() {
        System.out.println("Press \"p\" to play");
        System.out.println("Press \"q\" to quit");
        System.out.println("Press \"b\" to add balance to a player");
        System.out.println("Press \"n\" to add a new player");
        System.out.println("Press \"v\" to view the scoreboard");
        System.out.println("Press \"s\" to save your current game, will overwrite previous save");
        System.out.println("Press \"l\" to load your save, will overwrite your current data");
    }

    // EFFECTS: displays the game options
    public void displayGameOptions() {
        System.out.println("Press \"h\" to hit");
        System.out.println("Press \"s\" to stand");
        System.out.println("Press \"d\" to double");
    }

    // EFFECTS: saves the gambler data to file
    private void saveGameData() {
        try {
            jsonWriter.open();
            jsonWriter.write(listOfGamblers);
            jsonWriter.close();
            System.out.println("Saved gambler data to " + SAVELOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + SAVELOCATION);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads gambler data from file
    private void loadGameData() {
        try {
            listOfGamblers = jsonReader.read();
            System.out.println("Loaded gambler data from " + SAVELOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + SAVELOCATION);
        }
    }
}
