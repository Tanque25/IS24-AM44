package org.example.myversion.messages;

import org.example.myversion.server.model.Coordinates;
import org.example.myversion.server.model.decks.cards.*;
import org.example.myversion.server.model.enumerations.*;
import org.example.myversion.server.serverController.GameState;

import jakarta.json.*;

import java.io.*;
import java.util.*;

import static org.example.myversion.server.serverController.GameController.BACKUP_FILE;

/**
 * The Message class represents a message exchanged between the client and the server.
 * It provides constructors for creating various types of messages and methods for accessing their content.
 */
public class Message implements Serializable {
    private final JsonObject json;

    ///////////////////////////////////////////////////////CONSTRUCTORS////////////////////////////////////////////////////////

    /**
     * Constructs a Message object from a JsonObject.
     *
     * @param jsonObject the JsonObject containing message data.
     */
    public Message(JsonObject jsonObject) {
        this.json = jsonObject;  // Assume jsonObject is valid and properly structured.
    }

    /**
     * Constructor for a normal json message from a file.
     *
     * @param jsonFile the json file.
     * @throws IOException if the file is not found.
     */
    public Message(File jsonFile) throws IOException {
        FileReader reader = new FileReader(jsonFile);
        this.json = Json.createReader(reader).readObject();
    }

    /**
     * Constructs a Message object representing a single message with the given message code.
     *
     * @param messageCode the identifier of the message.
     */
    public Message(String messageCode) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .build();
    }

    /**
     * Constructs a Message object representing a generic text message with the given message code and text.
     *
     * @param messageCode represents the identifier of the message.
     * @param argument    represents the body of the message.
     */
    public Message(String messageCode, String argument) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("argument", argument)
                .build();
    }

    /**
     * Constructs a Message object representing a player number message with the given message code and maximum number of players.
     *
     * @param messageCode the identifier of the message.
     * @param number      the int value associated with the message.
     */
    public Message(String messageCode, int number) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("Number", number)
                .build();
    }

    /**
     * Constructs a Message object representing a message containing a StarterCard.
     * This constructor is used to send information about a StarterCard.
     *
     * @param messageCode the identifier of the message.
     * @param starterCard the StarterCard to include in the message.
     */
    public Message(String messageCode, StarterCard starterCard) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("starterCard", createStarterCardJson(starterCard))
                .build();
    }

    /**
     * Constructs a Message object representing a message containing information about an objective card.
     * The objective card information includes its objective and card points.
     *
     * @param messageCode   the identifier of the message.
     * @param objectiveCard the objective card for which the message is created.
     */
    public Message(String messageCode, ObjectiveCard objectiveCard) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("objectiveCard", createObjectiveCardJson(objectiveCard))
                .build();
    }

    /**
     * Constructs a Message object representing a message containing information about two objective cards.
     * The objective card information includes its objective and card points.
     * Used both for sending common objectives and secret objectives options.
     *
     * @param messageCode    the identifier of the message: "CommonObjectives", "SecretObjectives"
     * @param objectiveCard1 the first objective card.
     * @param objectiveCard2 the second objective card.
     */
    public Message(String messageCode, ObjectiveCard objectiveCard1, ObjectiveCard objectiveCard2) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("objectiveCard1", createObjectiveCardJson(objectiveCard1))
                .add("objectiveCard2", createObjectiveCardJson(objectiveCard2))
                .build();
    }

    /**
     * Constructs a Message object representing a message containing a PlayableCard.
     * This constructor is used to send information about a PlayableCard.
     *
     * @param messageCode  The identifier of the message.
     * @param playableCard The PlayableCard to include in the message.
     */
    public Message(String messageCode, PlayableCard playableCard) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("playableCard", createPlayableCardJson(playableCard))
                .build();
    }

    /**
     * Constructs a Message object representing a message containing a PlayableCard and its coordinates.
     *
     * @param messageCode The identifier of the message.
     * @param playableCard The PlayableCard to include in the message.
     * @param coordinates The coordinates of the PlayableCard.
     */
    public Message(String messageCode, PlayableCard playableCard, Coordinates coordinates) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("playableCard", createPlayableCardJson(playableCard))
                .add("xCoordinate", coordinates.getX())
                .add("yCoordinate", coordinates.getY())
                .build();
    }

    /**
     * Constructs a Message object representing a message containing a nickname, PlayableCard, and its coordinates.
     *
     * @param messageCode The identifier of the message.
     * @param nickname The nickname associated with the message.
     * @param playableCard The PlayableCard to include in the message.
     * @param coordinates The coordinates of the PlayableCard.
     */
    public Message(String messageCode, String nickname, PlayableCard playableCard, Coordinates coordinates) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("argument", nickname)
                .add("playableCard", createPlayableCardJson(playableCard));

        if (coordinates != null) {
            jsonBuilder.add("xCoordinate", coordinates.getX())
                    .add("yCoordinate", coordinates.getY());
        }

        json = jsonBuilder.build();
    }

    /**
     * Constructs a Message object representing a message containing a GoldCard.
     * This constructor is used to send information about a GoldCard.
     *
     * @param messageCode The identifier of the message.
     * @param goldCard    The GoldCard to include in the message.
     */
    public Message(String messageCode, GoldCard goldCard) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("goldCard", createGoldCardJson(goldCard))
                .build();
    }

    /**
     * Constructs a Message object representing a message containing a GoldCard and its coordinates.
     *
     * @param messageCode The identifier of the message.
     * @param goldCard The GoldCard to include in the message.
     * @param coordinates The coordinates of the GoldCard.
     */
    public Message(String messageCode, GoldCard goldCard, Coordinates coordinates) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("goldCard", createGoldCardJson(goldCard))
                .add("xCoordinate", coordinates.getX())
                .add("yCoordinate", coordinates.getY())
                .build();
    }

    /**
     * Constructs a Message object representing a message containing a nickname, GoldCard, and its coordinates.
     *
     * @param messageCode The identifier of the message.
     * @param nickname The nickname associated with the message.
     * @param goldCard The GoldCard to include in the message.
     * @param coordinates The coordinates of the GoldCard.
     */
    public Message(String messageCode, String nickname, GoldCard goldCard, Coordinates coordinates) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("argument", nickname)
                .add("goldCard", createGoldCardJson(goldCard));

        if (coordinates != null) {
            jsonBuilder.add("xCoordinate", coordinates.getX())
                    .add("yCoordinate", coordinates.getY());
        }

        json = jsonBuilder.build();
    }

    /**
     * Constructs a Message object containing starter cards and player hands.
     * Used when sending game start conditions.
     *
     * @param messageCode The identifier of the message.
     * @param starterCards The map of starter cards.
     * @param playersHands The map of player hands.
     */
    public Message(String messageCode, Map<String, StarterCard> starterCards, Map<String, List<PlayableCard>> playersHands) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("messageCode", messageCode);

        // Serialize the starterCards map
        JsonObjectBuilder starterCardsJson = Json.createObjectBuilder();
        for (Map.Entry<String, StarterCard> entry : starterCards.entrySet()) {
            starterCardsJson.add(entry.getKey(), createStarterCardJson(entry.getValue()));
        }
        jsonBuilder.add("starterCards", starterCardsJson);

        // Serialize the playersHands map
        JsonObjectBuilder playersHandsJson = Json.createObjectBuilder();
        for (Map.Entry<String, List<PlayableCard>> entry : playersHands.entrySet()) {
            playersHandsJson.add(entry.getKey(), createHandJson(entry.getValue()));
        }
        jsonBuilder.add("playersHands", playersHandsJson);

        // Build the final JSON object
        json = jsonBuilder.build();
    }

    /**
     * Constructs a Message object representing the draw options.
     *
     * @param messageCode The identifier of the message.
     * @param visibleResourceCards The list of visible resource cards.
     * @param coveredResourceCard The covered resource card.
     * @param coveredGoldCard The covered gold card.
     * @param visibleGoldCards The list of visible gold cards.
     */
    public Message(String messageCode, List<PlayableCard> visibleResourceCards, PlayableCard coveredResourceCard, List<GoldCard> visibleGoldCards, GoldCard coveredGoldCard) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("messageCode", messageCode);

        // Serialize the visible resource cards
        jsonBuilder.add("playableCard1", createPlayableCardJson(visibleResourceCards.getFirst()));
        jsonBuilder.add("playableCard2", createPlayableCardJson(visibleResourceCards.getLast()));
        jsonBuilder.add("playableCard", createPlayableCardJson(coveredResourceCard)); // Top card of the resource deck

        // Serialize the visible gold cards
        jsonBuilder.add("goldCard1", createGoldCardJson(visibleGoldCards.getFirst()));
        jsonBuilder.add("goldCard2", createGoldCardJson(visibleGoldCards.getLast()));
        jsonBuilder.add("goldCard", createGoldCardJson(coveredGoldCard)); // Top card of the gold deck

        json = jsonBuilder.build();
    }

    /**
     * Constructs a Message object representing a message containing coordinates.
     * This constructor is used to send information about coordinates where to place the card.
     *
     * @param messageCode The identifier of the message.
     * @param xCoordinate The x-coordinate value.
     * @param yCoordinate The y-coordinate value.
     */
    public Message(String messageCode, int xCoordinate, int yCoordinate) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("xCoordinate", xCoordinate)
                .add("yCoordinate", yCoordinate)
                .build();
    }

    /**
     * Constructs a Message object with a message code and a map of scores.
     *
     * @param messageCode the identifier of the message.
     * @param scores      the map of scores.
     */
    public Message(String messageCode, Map<String, Integer> scores) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                .add("messageCode", messageCode);

        JsonObjectBuilder scoresBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            scoresBuilder.add(entry.getKey(), entry.getValue());
        }

        jsonBuilder.add("scores", scoresBuilder.build());
        this.json = jsonBuilder.build();
    }

    /**
     * Constructs a game backup message.
     *
     * @param messageCode The identifier of the message.
     * @param scores The map of player scores.
     * @param commonObjectiveCard1 The first common objective card.
     * @param commonObjectiveCard2 The second common objective card.
     * @param secretObjectiveCards The map of secret objective cards.
     * @param resourceDeck The resource deck stack.
     * @param goldDeck The gold deck stack.
     * @param visibleResourceCards The list of visible resource cards.
     * @param visibleGoldCards The list of visible gold cards.
     * @param playersHands The map of players' hands.
     * @param playAreas The map of players' play areas.
     * @param currentPlayer The current player.
     * @param lastPlayer The last player.
     * @param lastRound The last game state.
     */
    public Message(String messageCode, Map<String, Integer> scores,
                   ObjectiveCard commonObjectiveCard1, ObjectiveCard commonObjectiveCard2,
                   Map<String, ObjectiveCard> secretObjectiveCards,
                   Stack<PlayableCard> resourceDeck, Stack<GoldCard> goldDeck,
                   List<PlayableCard> visibleResourceCards,
                   List<GoldCard> visibleGoldCards,
                   Map<String, List<PlayableCard>> playersHands, Map<String, Card[][]> playAreas,
                   String currentPlayer, String lastPlayer, GameState lastRound,
                   Map<String, Map<CornerContent, Integer>> playersStock) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                .add("messageCode", messageCode);

        // Save scores
        JsonObjectBuilder scoresBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            scoresBuilder.add(entry.getKey(), entry.getValue());
        }
        jsonBuilder.add("scores", scoresBuilder.build());

        // Save common objectives
        jsonBuilder.add("objectiveCard1", createObjectiveCardJson(commonObjectiveCard1));
        jsonBuilder.add("objectiveCard2", createObjectiveCardJson(commonObjectiveCard2));

        // Save secret objectives
        JsonObjectBuilder secretObjectivesBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, ObjectiveCard> entry : secretObjectiveCards.entrySet()) {
            secretObjectivesBuilder.add(entry.getKey(), createObjectiveCardJson(entry.getValue()));
        }
        jsonBuilder.add("secretObjectives", secretObjectivesBuilder.build());

        // Save resource deck
        jsonBuilder.add("resourceDeck", createResourceDeckJson(resourceDeck));

        // Save gold deck
        jsonBuilder.add("goldDeck", createGoldDeckJson(goldDeck));

        // Save cards to draw options
        // Serialize the visible resource cards
        jsonBuilder.add("playableCard1", createPlayableCardJson(visibleResourceCards.getFirst()));
        jsonBuilder.add("playableCard2", createPlayableCardJson(visibleResourceCards.getLast()));
        // Serialize the visible gold cards
        jsonBuilder.add("goldCard1", createGoldCardJson(visibleGoldCards.getFirst()));
        jsonBuilder.add("goldCard2", createGoldCardJson(visibleGoldCards.getLast()));

        // Save the players' hands
        JsonObjectBuilder playersHandsBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, List<PlayableCard>> entry : playersHands.entrySet()) {
            playersHandsBuilder.add(entry.getKey(), createHandJson(entry.getValue()));
        }
        jsonBuilder.add("playersHands", playersHandsBuilder);

        // save players' play areas
        JsonObjectBuilder playAreasBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, Card[][]> entry : playAreas.entrySet()) {
            playAreasBuilder.add(entry.getKey(), createPlayAreaJson(entry.getValue()));
        }
        jsonBuilder.add("playAreas", playAreasBuilder.build());

        // Save turn info
        jsonBuilder.add("currentPlayer", currentPlayer);
        jsonBuilder.add("lastPlayer", lastPlayer);
        jsonBuilder.add("lastRound", lastRound.name());

        // Save players' stock
        jsonBuilder.add("playersStock", createPlayersStockJson(playersStock));

        this.json = jsonBuilder.build();

        try (PrintWriter out = new PrintWriter(new FileWriter(BACKUP_FILE))) {
            out.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs a message to send the restored game to all the players.
     *
     * @param messageCode The identifier of the message.
     * @param scores The map of player scores.
     * @param playersHands The map of players' hands.
     * @param playAreas The map of players' play areas.
     */
    public Message(String messageCode, Map<String, Integer> scores, Map<String, List<PlayableCard>> playersHands, Map<String, Card[][]> playAreas) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                .add("messageCode", messageCode);

        // Send the scores
        JsonObjectBuilder scoresBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            scoresBuilder.add(entry.getKey(), entry.getValue());
        }
        jsonBuilder.add("scores", scoresBuilder.build());

        // Send the players' hands
        JsonObjectBuilder playersHandsBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, List<PlayableCard>> entry : playersHands.entrySet()) {
            playersHandsBuilder.add(entry.getKey(), createHandJson(entry.getValue()));
        }
        jsonBuilder.add("playersHands", playersHandsBuilder);

        // Send players' play areas
        JsonObjectBuilder playAreasBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, Card[][]> entry : playAreas.entrySet()) {
            playAreasBuilder.add(entry.getKey(), createPlayAreaJson(entry.getValue()));
        }
        jsonBuilder.add("playAreas", playAreasBuilder.build());

        this.json = jsonBuilder.build();
    }

///////////////////////////////////////////////////////UTILS////////////////////////////////////////////////////////

    /**
     * Creates a JSON object representing the corners of a card.
     *
     * @param corners the map containing corners and their positions on the card.
     * @return the JSON object representing the corners.
     */
    private JsonObject createCornerObject(Map<CornerPosition, Corner> corners) {
        JsonObjectBuilder cornerBuilder = Json.createObjectBuilder();
        for (Map.Entry<CornerPosition, Corner> entry : corners.entrySet()) {
            CornerPosition position = entry.getKey();
            Corner corner = entry.getValue();
            JsonObject cornerJson = Json.createObjectBuilder()
                    .add("content", corner.getCornerContent().toString())
                    .add("covered", corner.isCovered())
                    .build();
            cornerBuilder.add(position.toString(), cornerJson);
        }
        return cornerBuilder.build();
    }

    /**
     * Creates a JSON object representing a starter card.
     *
     * @param starterCard the starter card to be represented in JSON format.
     * @return the JSON object representing the starter card.
     */
    private JsonObject createStarterCardJson(StarterCard starterCard) {
        return Json.createObjectBuilder()
                .add("id", starterCard.getId())
                .add("resources", createObjectiveObject(starterCard.getResource()))
                .add("corners", createCornerObject(starterCard.getCorners()))
                .add("backCorners", createCornerObject(starterCard.getBackCorner()))
                .add("playedBack", starterCard.isPlayedBack())
                .build();
    }

    /**
     * Creates a JSON object representing an objective card.
     *
     * @param objectiveCard the objective card to be represented in JSON format.
     * @return the JSON object representing the objective card.
     */
    private JsonObject createObjectiveCardJson(ObjectiveCard objectiveCard) {
        String objectiveType;
        JsonArray objectiveJsonArray;

        switch (objectiveCard) {
            case ResourceObjectiveCard resourceObjectiveCard -> {
                objectiveType = "ResourceObjective";
                objectiveJsonArray = createObjectiveObject(resourceObjectiveCard.getObjective()).build();
            }
            case SpecialObjectiveCard specialObjectiveCard -> {
                objectiveType = "SpecialObjective";
                objectiveJsonArray = createObjectiveObject(specialObjectiveCard.getObjective()).build();
            }
            case PatternObjectiveCard patternObjectiveCard -> {
                objectiveType = "PatternObjective";
                objectiveJsonArray = createObjectiveObject(patternObjectiveCard.getObjective()).build();
            }
            case null, default -> throw new IllegalArgumentException("Unsupported type of ObjectiveCard");
        }

        return Json.createObjectBuilder()
                .add("id", objectiveCard.getId())
                .add("objectiveType", objectiveType)
                .add("objective", objectiveJsonArray)
                .add("cardPoints", objectiveCard.getCardPoints())
                .build();
    }

    /**
     * Creates a JSON array representing the resources associated with a starter card or an objective card.
     * Each element in the array represents a resource.
     *
     * @param resources the array of resources associated with the card.
     * @return the JSON array representing the resources.
     */
    private JsonArrayBuilder createObjectiveObject(Resource[] resources) {
        JsonArrayBuilder objectiveObjectBuilder = Json.createArrayBuilder();
        for (Resource resource : resources) {
            objectiveObjectBuilder.add(resource.toString());
        }
        return objectiveObjectBuilder;
    }

    /**
     * Creates a JSON array representing the special objects associated with an objective card.
     * Each element in the array represents a special object.
     *
     * @param objective the array of special objects associated with the objective card.
     * @return the JSON array representing the special objects.
     */
    private JsonArrayBuilder createObjectiveObject(SpecialObject[] objective) {
        JsonArrayBuilder objectiveObjectBuilder = Json.createArrayBuilder();
        for (SpecialObject specialObject : objective) {
            objectiveObjectBuilder.add(specialObject.toString());
        }
        return objectiveObjectBuilder;
    }

    /**
     * Creates a JSON array representing the resources matrix associated with an objective card.
     * Each element in the array represents a row of resources, and each row is itself a JSON array.
     *
     * @param objective the matrix of resources associated with the objective card.
     * @return the JSON array representing the resources' matrix.
     */
    private JsonArrayBuilder createObjectiveObject(Resource[][] objective) {
        JsonArrayBuilder objectiveObjectBuilder = Json.createArrayBuilder();
        for (Resource[] row : objective) {
            JsonArrayBuilder rowArrayBuilder = Json.createArrayBuilder();
            for (Resource resource : row) {
                if (resource != null) {
                    rowArrayBuilder.add(resource.toString());  // Add the resource to the JSON array only if it's not null
                } else {
                    rowArrayBuilder.add(JsonValue.NULL);  // Use JsonValue.NULL for null resources
                }
            }
            objectiveObjectBuilder.add(rowArrayBuilder);
        }
        return objectiveObjectBuilder;
    }

    /**
     * Creates a JSON object representing a playable card.
     *
     * @param playableCard the playable card to be represented in JSON format.
     * @return the JSON object representing the playable card.
     */
    private JsonObject createPlayableCardJson(PlayableCard playableCard) {
        return Json.createObjectBuilder()
                .add("id", playableCard.getId())
                .add("resource", playableCard.getResource().toString())
                .add("corners", createCornerObject(playableCard.getCorners()))
                .add("cardPoints", playableCard.getCardPoints())
                .add("playedBack", playableCard.isPlayedBack())
                .build();
    }

    /**
     * Creates a JSON array representing a player's hand.
     *
     * @param cards the list of playable cards in the player's hand.
     * @return the JSON array representing the player's hand.
     */
    private JsonArray createHandJson(List<PlayableCard> cards) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (PlayableCard card : cards) {
            if (card instanceof GoldCard)
                arrayBuilder.add(createGoldCardJson((GoldCard) card));
            else
                arrayBuilder.add(createPlayableCardJson(card));
        }
        return arrayBuilder.build();
    }

    /**
     * Creates a JSON object representing a gold card.
     *
     * @param goldCard the gold card to be represented in JSON format.
     * @return the JSON object representing the gold card.
     */
    private JsonObject createGoldCardJson(GoldCard goldCard) {
        return Json.createObjectBuilder()
                .add("id", goldCard.getId())
                .add("resource", goldCard.getResource().toString())
                .add("corners", createCornerObject(goldCard.getCorners()))
                .add("cardPoints", goldCard.getCardPoints())
                .add("pointsParameter", goldCard.getPointsParameter().toString())
                .add("cost", createObjectiveObject(goldCard.getCost()))
                .add("playedBack", goldCard.isPlayedBack())
                .build();
    }

    /**
     * Creates a JSON object representing a play area.
     *
     * @param playArea the play area to be represented in JSON format.
     * @return the JSON object representing the play area.
     */
    private JsonObject createPlayAreaJson(Card[][] playArea) {
        JsonObjectBuilder playAreaBuilder = Json.createObjectBuilder();
        for (int x = 0; x < playArea.length; x++) {
            JsonArrayBuilder rowBuilder = Json.createArrayBuilder();
            for (int y = 0; y < playArea[x].length; y++) {
                Card card = playArea[x][y];
                if (card != null) {
                    switch (card) {
                        case StarterCard starterCard -> rowBuilder.add(createStarterCardJson(starterCard));
                        case GoldCard goldCard -> rowBuilder.add(createGoldCardJson(goldCard));
                        case PlayableCard playableCard -> rowBuilder.add(createPlayableCardJson(playableCard));
                        default -> {
                        }
                    }
                } else {
                    rowBuilder.add(JsonValue.NULL);
                }
            }
            playAreaBuilder.add(String.valueOf(x), rowBuilder);
        }
        return playAreaBuilder.build();
    }

    /**
     * Creates a JSON array representing a resource deck.
     *
     * @param resourceDeck the resource deck to be represented in JSON format.
     * @return the JSON array representing the resource deck.
     */
    private JsonArray createResourceDeckJson(Stack<PlayableCard> resourceDeck) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (PlayableCard card : resourceDeck) {
            arrayBuilder.add(createPlayableCardJson(card));
        }
        return arrayBuilder.build();
    }

    /**
     * Creates a JSON array representing a gold deck.
     *
     * @param goldDeck the gold deck to be represented in JSON format.
     * @return the JSON array representing the gold deck.
     */
    private JsonArray createGoldDeckJson(Stack<GoldCard> goldDeck) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (GoldCard card : goldDeck) {
            arrayBuilder.add(createGoldCardJson(card));
        }
        return arrayBuilder.build();
    }

    /**
     * Creates a JSON object representing the players' stock.
     *
     * @param playersStock the map of players' stock to be represented in JSON format.
     * @return the JSON object representing the players' stock.
     */
    private JsonObject createPlayersStockJson(Map<String, Map<CornerContent, Integer>> playersStock) {
        JsonObjectBuilder playersStockBuilder = Json.createObjectBuilder();
        for (Map.Entry<String, Map<CornerContent, Integer>> entry : playersStock.entrySet()) {
            JsonObjectBuilder stockBuilder = Json.createObjectBuilder();
            for (Map.Entry<CornerContent, Integer> stockEntry : entry.getValue().entrySet()) {
                stockBuilder.add(stockEntry.getKey().toString(), stockEntry.getValue());
            }
            playersStockBuilder.add(entry.getKey(), stockBuilder.build());
        }
        return playersStockBuilder.build();
    }

///////////////////////////////////////////////////////GETTERS////////////////////////////////////////////////////////

    /**
     * Parses a JSON string representation of a Message object into a JsonObject.
     *
     * @param messageString A string containing JSON representation of a Message object.
     * @return A JsonObject representing the parsed Message object.
     */
    public Message parseMessageString(String messageString) {
        return new Message(Json.createReader(new StringReader(messageString)).readObject());
    }

    public JsonObject getJson() {
        return json;
    }

    /**
     * Retrieves the identifier of the message.
     *
     * @return The message code.
     */
    public String getMessageCode() {
        return json.getString("messageCode");
    }

    /**
     * Retrieves the argument of the message.
     *
     * @return The body of the message.
     */
    public String getArgument() {
        return json.getString("argument", null);
    }

    /**
     * Retrieves the maximum number of players associated with the message.
     *
     * @return The maximum number of players.
     */
    public int getNumber() {
        return json.getInt("Number", -1);
    }

    /**
     * Retrieves the StarterCard associated with the message.
     *
     * @return The StarterCard.
     */
    public StarterCard getStarterCard() {
        JsonObject starterCardJson = json.getJsonObject("starterCard");
        return createStarterCardFromJson(starterCardJson);
    }

    /**
     * Retrieves the map of starter cards from the message.
     *
     * @return The map of starter cards.
     */
    public Map<String, StarterCard> getStarterCardsMap() {
        Map<String, StarterCard> starterCards = new HashMap<>();
        JsonObject starterCardsJson = json.getJsonObject("starterCards");
        if (starterCardsJson == null) {
            throw new IllegalStateException("Starter cards data is missing in the JSON object");
        }

        for (String key : starterCardsJson.keySet()) {
            JsonObject cardJson = starterCardsJson.getJsonObject(key);
            if (cardJson != null) {
                StarterCard card = createStarterCardFromJson(cardJson);
                starterCards.put(key, card);
            } else {
                throw new IllegalStateException("Starter card data is corrupted or missing for key: " + key);
            }
        }

        return starterCards;
    }

    /**
     * Creates a StarterCard object from its JSON representation.
     *
     * @param starterCardJson the JSON object representing a starter card.
     * @return the StarterCard object.
     */
    private StarterCard createStarterCardFromJson(JsonObject starterCardJson) {
        if (starterCardJson == null) {
            throw new IllegalArgumentException("Starter card data is missing in the JSON object");
        }

        int id = starterCardJson.getInt("id");
        Resource[] resources = getResourceArray(starterCardJson.getJsonArray("resources"));
        Map<CornerPosition, Corner> corners = getCornerMap(starterCardJson.getJsonObject("corners"));
        Map<CornerPosition, Corner> backCorners = getCornerMap(starterCardJson.getJsonObject("backCorners"));
        boolean playedBack = starterCardJson.getBoolean("playedBack", false);  // Handling optional boolean

        // Construct and return the StarterCard with all properties
        StarterCard starterCard = new StarterCard(resources, corners, backCorners, id);
        starterCard.setPlayedBack(playedBack);

        return starterCard;
    }

    /**
     * Retrieves a list of resource cards from the message.
     *
     * @return the list of resource cards.
     */
    public List<PlayableCard> getResourceCards() {
        List<PlayableCard> resourceCards = new ArrayList<>();

        // Process the first card
        JsonObject resourceCard1 = json.getJsonObject("playableCard1");
        PlayableCard card1 = createPlayableCardFromJson(resourceCard1);
        resourceCards.add(card1);

        // Process the second card
        JsonObject resourceCard2 = json.getJsonObject("playableCard2");
        PlayableCard card2 = createPlayableCardFromJson(resourceCard2);
        resourceCards.add(card2);

        return resourceCards;
    }

    /**
     * Retrieves a list of gold cards from the message.
     *
     * @return the list of gold cards.
     */
    public List<GoldCard> getGoldCards() {
        List<GoldCard> goldCards = new ArrayList<>();

        // Process the first card
        JsonObject goldCard1 = json.getJsonObject("goldCard1");
        GoldCard card1 = createGoldCardFromJson(goldCard1);
        goldCards.add(card1);

        // Process the second card
        JsonObject resourceCard2 = json.getJsonObject("goldCard2");
        GoldCard card2 = createGoldCardFromJson(resourceCard2);
        goldCards.add(card2);

        return goldCards;
    }

    /**
     * Retrieves an objective card from the message.
     *
     * @return the objective card.
     */
    public ObjectiveCard getObjectiveCard() {
        JsonObject objectiveCard1 = json.getJsonObject("objectiveCard");
        return createObjectiveCardFromJson(objectiveCard1);
    }

    /**
     * Retrieves a list of objective cards from the message.
     *
     * @return the list of objective cards.
     */
    public List<ObjectiveCard> getObjectiveCards() {
        List<ObjectiveCard> objectiveCards = new ArrayList<>();

        // Process the first card
        JsonObject objectiveCard1 = json.getJsonObject("objectiveCard1");
        ObjectiveCard card1 = createObjectiveCardFromJson(objectiveCard1);
        objectiveCards.add(card1);

        // Process the second card
        JsonObject objectiveCard2 = json.getJsonObject("objectiveCard2");
        ObjectiveCard card2 = createObjectiveCardFromJson(objectiveCard2);
        objectiveCards.add(card2);

        return objectiveCards;
    }

    /**
     * Retrieves a map of secret objective cards from the message.
     *
     * @return the map of secret objective cards.
     */
    public Map<String, ObjectiveCard> getSecretObjectiveCards() {
        Map<String, ObjectiveCard> secretObjectives = new HashMap<>();
        JsonObject secretObjectivesJson = json.getJsonObject("secretObjectives");

        for (String key : secretObjectivesJson.keySet()) {
            JsonObject objectiveCardJson = secretObjectivesJson.getJsonObject(key);
            ObjectiveCard objectiveCard = createObjectiveCardFromJson(objectiveCardJson);
            secretObjectives.put(key, objectiveCard);
        }

        return secretObjectives;
    }

    /**
     * Creates an ObjectiveCard object from its JSON representation.
     *
     * @param objectiveCardJson the JSON object representing an objective card.
     * @return the ObjectiveCard object.
     */
    private ObjectiveCard createObjectiveCardFromJson(JsonObject objectiveCardJson) {
        if (objectiveCardJson == null) {
            throw new IllegalArgumentException("Card JSON object is missing");
        }

        int id = objectiveCardJson.getInt("id");
        JsonString typeJsonString = objectiveCardJson.getJsonString("objectiveType");
        if (typeJsonString == null) {
            throw new IllegalArgumentException("Objective type is missing or null in JSON");
        }
        String objectiveType = typeJsonString.getString();
        int cardPoints = objectiveCardJson.getInt("cardPoints");
        JsonArray objectiveData = objectiveCardJson.getJsonArray("objective");

        return switch (objectiveType) {
            case "ResourceObjective" -> new ResourceObjectiveCard(cardPoints, getResourceArray(objectiveData), id);
            case "SpecialObjective" -> new SpecialObjectiveCard(cardPoints, getSpecialObjectArray(objectiveData), id);
            case "PatternObjective" -> new PatternObjectiveCard(cardPoints, getResourceMatrix(objectiveData), id);
            default -> throw new IllegalArgumentException("Unsupported objective type: " + objectiveType);
        };
    }

    /**
     * Retrieves the PlayableCard object from the Message.
     *
     * @return The PlayableCard object contained in the Message.
     */
    public PlayableCard getPlayableCard() {
        JsonObject playableCardJson = json.getJsonObject("playableCard");
        return createPlayableCardFromJson(playableCardJson);
    }

    /**
     * Retrieves the map of players' hands from the message.
     *
     * @return the map of players' hands.
     */
    public Map<String, List<PlayableCard>> getPlayersHandsMap() {
        Map<String, List<PlayableCard>> playersHands = new HashMap<>();
        JsonObject playersHandsJson = json.getJsonObject("playersHands");
        if (playersHandsJson == null) {
            throw new IllegalStateException("Player hands data is missing in the JSON object");
        }

        for (String key : playersHandsJson.keySet()) {
            JsonArray handArray = playersHandsJson.getJsonArray(key);
            if (handArray != null) {
                List<PlayableCard> cards = createHandFromJson(handArray);
                playersHands.put(key, cards);
            } else {
                throw new IllegalStateException("Playable cards data is corrupted or missing for key: " + key);
            }
        }

        return playersHands;
    }

    /**
     * Creates a list of PlayableCard objects from their JSON representation.
     *
     * @param jsonArray the JSON array representing a list of playable cards.
     * @return the list of PlayableCard objects.
     */
    private List<PlayableCard> createHandFromJson(JsonArray jsonArray) {
        List<PlayableCard> cards = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject cardJson = jsonArray.getJsonObject(i);
            if (cardJson.getInt("id") <= 40)
                cards.add(createPlayableCardFromJson(cardJson));
            else cards.add(createGoldCardFromJson(cardJson));
        }

        return cards;
    }

    /**
     * Creates a PlayableCard object from its JSON representation.
     *
     * @param playableCardJson the JSON object representing a playable card.
     * @return the PlayableCard object.
     */
    private PlayableCard createPlayableCardFromJson(JsonObject playableCardJson) {
        if (playableCardJson == null) {
            throw new IllegalArgumentException("Playable card data is missing in the JSON object");
        }

        // Extract the Resource, corners, and card points from the playableCardJson
        int id = playableCardJson.getInt("id");
        Resource resource = Resource.valueOf(playableCardJson.getString("resource"));
        Map<CornerPosition, Corner> corners = getCornerMap(playableCardJson.getJsonObject("corners"));
        int cardPoints = playableCardJson.getInt("cardPoints");
        boolean playedBack = playableCardJson.getBoolean("playedBack", false);

        // Create and return the PlayableCard object with all properties
        PlayableCard playableCard = new PlayableCard(resource, corners, cardPoints, id);
        playableCard.setPlayedBack(playedBack);

        return playableCard;
    }

    /**
     * Retrieves the GoldCard object from the Message.
     *
     * @return The GoldCard object contained in the Message.
     */
    public GoldCard getGoldCard() {
        JsonObject goldCardJson = json.getJsonObject("goldCard");
        return createGoldCardFromJson(goldCardJson);
    }

    /**
     * Creates a GoldCard object from its JSON representation.
     *
     * @param goldCardJson the JSON object representing a gold card.
     * @return the GoldCard object.
     */
    public GoldCard createGoldCardFromJson(JsonObject goldCardJson) {
        if (goldCardJson == null) {
            throw new IllegalArgumentException("Gold card data is missing in the JSON object");
        }

        // Extract the properties of the GoldCard from the nested JSON object
        int id = goldCardJson.getInt("id");
        Resource resource = Resource.valueOf(goldCardJson.getString("resource"));
        Map<CornerPosition, Corner> corners = getCornerMap(goldCardJson.getJsonObject("corners"));
        int cardPoints = goldCardJson.getInt("cardPoints");
        PointsParameter pointsParameter = createPointsParameter(goldCardJson.getString("pointsParameter"));
        Resource[] cost = getResourceArray(goldCardJson.getJsonArray("cost"));
        boolean playedBack = goldCardJson.getBoolean("playedBack", false);

        // Construct and return the GoldCard with all properties
        GoldCard goldCard = new GoldCard(resource, corners, cardPoints, cost, pointsParameter, id);
        goldCard.setPlayedBack(playedBack);

        return goldCard;
    }

    /**
     * Converts a JsonArray to an array of Resource enum values.
     *
     * @param jsonArray The JsonArray containing string representations of Resource enum values.
     * @return An array of Resource enum values parsed from the JsonArray.
     */
    private Resource[] getResourceArray(JsonArray jsonArray) {
        // Convert JsonArray to array of Resource enum values
        Resource[] resources = new Resource[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            resources[i] = Resource.valueOf(jsonArray.getString(i));
        }
        return resources;
    }

    /**
     * Converts a JsonArray to an array of SpecialObject enum values.
     *
     * @param jsonArray The JsonArray containing string representations of SpecialObject enum values.
     * @return An array of SpecialObject enum values parsed from the JsonArray.
     */
    private SpecialObject[] getSpecialObjectArray(JsonArray jsonArray) {
        // Convert JsonArray to array of SpecialObject enum values
        SpecialObject[] specialObjects = new SpecialObject[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            specialObjects[i] = SpecialObject.valueOf(jsonArray.getString(i));
        }
        return specialObjects;
    }

    /**
     * Converts a JsonArray to a matrix of Resource enum values.
     *
     * @param jsonArray The JsonArray containing arrays of string representations of Resource enum values.
     * @return A matrix of Resource enum values parsed from the JsonArray.
     */
    private Resource[][] getResourceMatrix(JsonArray jsonArray) {
        Resource[][] resourceMatrix = new Resource[jsonArray.size()][];
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonArray rowArray = jsonArray.getJsonArray(i);
            resourceMatrix[i] = new Resource[rowArray.size()];
            for (int j = 0; j < rowArray.size(); j++) {
                JsonValue value = rowArray.get(j);
                // Check if the value is a string and convert it to the appropriate enum
                if (value.getValueType() == JsonValue.ValueType.STRING) {
                    String resourceString = ((JsonString) value).getString();
                    resourceMatrix[i][j] = Resource.valueOf(resourceString);
                } else if (value.getValueType() == JsonValue.ValueType.NULL) {
                    // Handle null values explicitly
                    resourceMatrix[i][j] = null;
                } else {
                    // Optional: Log or handle unexpected value types
                    throw new IllegalArgumentException("Invalid resource type in JSON array at position [" + i + "][" + j +
                            "], expected a string or null.");
                }
            }
        }
        return resourceMatrix;
    }

    /**
     * Converts a JsonObject to a map of CornerPosition to Corner objects.
     *
     * @param jsonObject The JsonObject containing corner positions and their corresponding content.
     * @return A map of CornerPosition to Corner objects parsed from the JsonObject.
     */
    private Map<CornerPosition, Corner> getCornerMap(JsonObject jsonObject) {
        Map<CornerPosition, Corner> corners = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            JsonObject cornerJson = jsonObject.getJsonObject(key);
            Corner corner = createCornerFromJson(cornerJson);
            corners.put(CornerPosition.valueOf(key), corner);
        }
        return corners;
    }

    /**
     * Creates a Corner object from its JSON representation.
     *
     * @param cornerJson the JSON object representing a corner.
     * @return the Corner object.
     */
    private Corner createCornerFromJson(JsonObject cornerJson) {
        CornerContent cornerContent = createCornerContent(cornerJson.getString("content"));
        boolean covered = cornerJson.getBoolean("covered");
        Corner corner = new Corner(cornerContent);
        corner.setCovered(covered);
        return corner;
    }

    /**
     * Creates a CornerContent object based on the provided string representation.
     *
     * @param cornerContent The string representation of the corner content.
     * @return A CornerContent object corresponding to the provided string representation.
     */
    private CornerContent createCornerContent(String cornerContent) {
        // Determine the appropriate enum type based on the contentString
        return switch (cornerContent) {
            case "EMPTY", "FULL" -> CornerVisibility.valueOf(cornerContent);
            case "PLANT_KINGDOM", "ANIMAL_KINGDOM", "FUNGI_KINGDOM", "INSECT_KINGDOM" ->
                    Resource.valueOf(cornerContent);
            case "QUILL", "INKWELL", "MANUSCRIPT" -> SpecialObject.valueOf(cornerContent);
            default -> null;
        };
    }

    /**
     * Creates a PointsParameter object based on the provided string representation.
     *
     * @param pointsParameter The string representation of the points parameter.
     * @return A PointsParameter object corresponding to the provided string representation.
     */
    private PointsParameter createPointsParameter(String pointsParameter) {
        return switch (pointsParameter) {
            case "CORNER", "EMPTY" -> ParameterType.valueOf(pointsParameter);
            case "QUILL", "INKWELL", "MANUSCRIPT" -> SpecialObject.valueOf(pointsParameter);
            default -> null;
        };
    }

    /**
     * Retrieves the coordinates associated with the message.
     *
     * @return The coordinates.
     */
    public Coordinates getCoordinates() {
        return new Coordinates(
                json.getInt("xCoordinate", -1),
                json.getInt("yCoordinate", -1)
        );
    }

    /**
     * Gets the scores map from the JSON object.
     *
     * @return the scores map.
     */
    public HashMap<String, Integer> getScores() {
        HashMap<String, Integer> scores = new HashMap<>();
        JsonObject scoresJson = json.getJsonObject("scores");

        for (String key : scoresJson.keySet()) {
            scores.put(key, scoresJson.getInt(key));
        }

        return scores;
    }

    /**
     * Method to deserialize the play areas.
     *
     * @param playAreaJson the JSON object representing a play area.
     * @return the Card matrix representing the play area.
     */
    private Card[][] createPlayAreaFromJson(JsonObject playAreaJson) {
        Card[][] playArea = new Card[81][81]; // Assuming 81x81 grid as per your model
        for (String key : playAreaJson.keySet()) {
            int x = Integer.parseInt(key);
            JsonArray rowArray = playAreaJson.getJsonArray(key);
            for (int y = 0; y < rowArray.size(); y++) {
                JsonValue jsonValue = rowArray.get(y);
                if (jsonValue != JsonValue.NULL) {
                    JsonObject cardJson = jsonValue.asJsonObject();
                    int id = cardJson.getInt("id");
                    if (id <= 40) {
                        playArea[x][y] = createPlayableCardFromJson(cardJson);
                    } else if (id <= 80) {
                        playArea[x][y] = createGoldCardFromJson(cardJson);
                    } else if (id <= 86) {
                        playArea[x][y] = createStarterCardFromJson(cardJson);
                    }
                }
            }
        }
        return playArea;
    }

    /**
     * Retrieves the map of play areas from the message.
     *
     * @return the map of play areas.
     */
    public Map<String, Card[][]> getPlayAreasMap() {
        Map<String, Card[][]> playAreasMap = new HashMap<>();
        JsonObject playAreasJson = json.getJsonObject("playAreas");

        for (String key : playAreasJson.keySet()) {
            JsonObject playAreaJson = playAreasJson.getJsonObject(key);
            playAreasMap.put(key, createPlayAreaFromJson(playAreaJson));
        }
        return playAreasMap;
    }

    /**
     * Retrieves the resource deck from the message.
     *
     * @return the stack of resource cards.
     */
    public Stack<PlayableCard> getResourceDeck() {
        Stack<PlayableCard> resourceDeck = new Stack<>();
        JsonArray resourceDeckJson = json.getJsonArray("resourceDeck");

        for (int i = 0; i < resourceDeckJson.size(); i++) {
            JsonObject cardJson = resourceDeckJson.getJsonObject(i);
            resourceDeck.push(createPlayableCardFromJson(cardJson));
        }
        return resourceDeck;
    }

    /**
     * Retrieves the gold deck from the message.
     *
     * @return the stack of gold cards.
     */
    public Stack<GoldCard> getGoldDeck() {
        Stack<GoldCard> goldDeck = new Stack<>();
        JsonArray goldDeckJson = json.getJsonArray("goldDeck");

        for (int i = 0; i < goldDeckJson.size(); i++) {
            JsonObject cardJson = goldDeckJson.getJsonObject(i);
            goldDeck.push(createGoldCardFromJson(cardJson));
        }
        return goldDeck;
    }

    /**
     * Retrieves the map of players' stock from the message.
     *
     * @return the map of players' stock.
     */
    public Map<String, Map<CornerContent, Integer>> getPlayersStock() {
        Map<String, Map<CornerContent, Integer>> playersStock = new HashMap<>();
        JsonObject playersStockJson = json.getJsonObject("playersStock");
        for (String key : playersStockJson.keySet()) {
            JsonObject stockJson = playersStockJson.getJsonObject(key);
            Map<CornerContent, Integer> stock = new HashMap<>();
            for (String stockKey : stockJson.keySet()) {
                stock.put(createCornerContent(stockKey), stockJson.getInt(stockKey));
            }
            playersStock.put(key, stock);
        }
        return playersStock;
    }

}