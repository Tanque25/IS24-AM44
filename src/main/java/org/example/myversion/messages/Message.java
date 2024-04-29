package org.example.myversion.messages;

import org.example.myversion.server.model.Coordinates;
import org.example.myversion.server.model.decks.GoldDeck;
import org.example.myversion.server.model.decks.cards.*;
import org.example.myversion.server.model.enumerations.*;

import javax.json.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The Message class represents a message exchanged between the client and the server.
 * It provides constructors for creating various types of messages and methods for accessing their content.
 */
public class Message implements Serializable {
    private JsonObject json;

    ///////////////////////////////////////////////////////CONSTRUCTORS////////////////////////////////////////////////////////

    /**
     * Constructs a Message object representing a single message with the given message code.
     *
     * @param messageCode the identifier of the message.
     */
    public Message(int messageCode) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .build();
    }

    /**
     * Constructs a Message object representing a generic text message with the given message code and text.
     * It is used by the server to ask for the nickname:
     * It is used by the client to send the chosen nickname:
     * It is used by the server to report an error:
     *
     * @param messageCode represents the identifier of the message.
     * @param argument represents the body of the message.
     */
    public Message(int messageCode, String argument) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("argument", argument)
                .build();
    }

    /**
     * Constructs a Message object representing a player number message with the given message code and maximum number of players.
     *
     * @param messageCode the identifier of the message.
     * @param maxPlayers  the maximum number of players.
     */
    public Message(int messageCode, int maxPlayers) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("maxPlayers", maxPlayers)
                .build();
    }

    /**
     * Constructs a Message object representing a message containing a StarterCard.
     * This constructor is used to send information about a StarterCard.
     *
     * @param messageCode the identifier of the message.
     * @param starterCard the StarterCard to include in the message.
     */
    public Message(int messageCode, StarterCard starterCard) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("resources", createObjectiveObject(starterCard.getResource()))
                .add("corners", createCornerObject(starterCard.getCorners()))
                .add("backCorners", createCornerObject(starterCard.getBackCorner()))
                .add("playedBack", starterCard.isPlayedBack())
                .build();
    }

    /**
     * Constructs a Message object representing a message containing information about an objective card.
     * The objective card information includes its objective and card points.
     *
     * @param messageCode    the identifier of the message.
     * @param objectiveCard  the objective card for which the message is created.
     */
    public Message(int messageCode, ObjectiveCard objectiveCard) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("objective", createObjectiveObject(objectiveCard.getObjective()))
                .add("cardPoints", objectiveCard.getCardPoints())
                .build();
    }

    /**
     * Constructs a Message object representing a message containing a PlayableCard.
     * This constructor is used to send information about a PlayableCard.
     *
     * @param messageCode   The identifier of the message.
     * @param playableCard  The PlayableCard to include in the message.
     */
    public Message(int messageCode, PlayableCard playableCard) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("resource", playableCard.getResource().toString())
                .add("corners", createCornerObject(playableCard.getCorners()))
                .add("cardPoints", playableCard.getCardPoints())
                .add("playedBack", playableCard.isPlayedBack())
                .build();
    }

    /**
     * Constructs a Message object representing a message containing a GoldCard.
     * This constructor is used to send information about a GoldCard.
     *
     * @param messageCode   The identifier of the message.
     * @param goldCard      The GoldCard to include in the message.
     */
    public Message(int messageCode, GoldCard goldCard) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("resource", goldCard.getResource().toString())
                .add("corners", createCornerObject(goldCard.getCorners()))
                .add("cardPoints", goldCard.getCardPoints())
                .add("pointsParameter", goldCard.getPointsParameter().toString())
                .add("cost", createObjectiveObject(goldCard.getCost()))
                .build();
    }

    /**
     * Constructs a Message object representing a message containing coordinates.
     * This constructor is used to send information about coordinates where to place the card.
     *
     * @param messageCode   The identifier of the message.
     * @param xCoordinate   The x-coordinate value.
     * @param yCoordinate   The y-coordinate value.
     */
    public Message(int messageCode, int xCoordinate, int yCoordinate) {
        json = Json.createObjectBuilder()
                .add("messageCode", messageCode)
                .add("xCoordinate", xCoordinate)
                .add("yCoordinate", yCoordinate)
                .build();
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
            cornerBuilder.add(position.toString(), corner.getCornerContent().toString());
        }
        return cornerBuilder.build();
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
                rowArrayBuilder.add(resource.toString());
            }
            objectiveObjectBuilder.add(rowArrayBuilder);
        }
        return objectiveObjectBuilder;
    }

///////////////////////////////////////////////////////GETTERS////////////////////////////////////////////////////////

    /**
     * Retrieves the identifier of the message.
     *
     * @return The message code.
     */
    public int getMessageCode() {
        return json.getInt("messageCode", -1);
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
    public int getMaxPlayers() {
        return json.getInt("maxPlayers", -1);
    }

    /**
     * Retrieves the StarterCard associated with the message.
     *
     * @return The StarterCard.
     */
    public StarterCard getStarterCard() {
        Resource[] resources = getResourceArray(json.getJsonArray("resources"));
        Map<CornerPosition, Corner> corners = getCornerMap(json.getJsonObject("corners"));
        Map<CornerPosition, Corner> backCorners = getCornerMap(json.getJsonObject("backCorners"));

        // Construct and return the StarterCard
        return new StarterCard(resources, corners, backCorners);
    }

    /**
     * Retrieves the ObjectiveCard contained in the message.
     *
     * @return The ObjectiveCard included in the message.
     */
    public ObjectiveCard getObjectiveCard() {
        // Get the JSON object representing the objective
        JsonObject objectiveJson = json.getJsonObject("objective");

        // Determine the type of objective card based on the structure of the JSON object
        if (objectiveJson.containsKey("resources")) {
            // If it's a ResourceObjectiveCard, parse the objective from the JSON object
            Resource[] objective = getResourceArray(objectiveJson.getJsonArray("resources"));
            int cardPoints = json.getInt("cardPoints");
            return new ResourceObjectiveCard(cardPoints, objective);
        } else if (objectiveJson.containsKey("specialObjects")) {
            // If it's a SpecialObjectiveCard, parse the objective from the JSON object
            SpecialObject[] objective = getSpecialObjectArray(objectiveJson.getJsonArray("specialObjects"));
            int cardPoints = json.getInt("cardPoints");
            return new SpecialObjectiveCard(cardPoints, objective);
        } else if (objectiveJson.containsKey("pattern")) {
            // If it's a PatternObjectiveCard, parse the objective from the JSON object
            Resource[][] objective = getResourceMatrix(objectiveJson.getJsonArray("pattern"));
            int cardPoints = json.getInt("cardPoints");
            return new PatternObjectiveCard(cardPoints, objective);
        } else {
            return null;
        }
    }

    /**
     * Retrieves the PlayableCard object from the Message.
     *
     * @return The PlayableCard object contained in the Message.
     */
    public PlayableCard getPlayableCard() {
        Resource resource = Resource.valueOf(json.getString("resource"));
        Map<CornerPosition, Corner> corners = getCornerMap(json.getJsonObject("corners"));
        int cardPoints = json.getInt("cardPoints");

        // Create and return the PlayableCard object
        return new PlayableCard(resource, corners, cardPoints);
    }

    /**
     * Retrieves the GoldCard object from the Message.
     *
     * @return The GoldCard object contained in the Message.
     */
    public GoldCard getGoldCard() {
        Resource resource = Resource.valueOf(json.getString("resource"));
        Map<CornerPosition, Corner> corners = getCornerMap(json.getJsonObject("corners"));
        int cardPoints = json.getInt("cardPoints");
        PointsParameter pointsParameter = SpecialObject.valueOf(json.getString("pointsParameter"));
        Resource[] cost = getResourceArray(json.getJsonArray("cost"));

        // Create and return the GoldCard object
        return new GoldCard(resource, corners, cardPoints, cost, pointsParameter);
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
        // Convert JsonArray to matrix of Resource enum values
        Resource[][] resourceMatrix = new Resource[jsonArray.size()][];
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonArray rowArray = jsonArray.getJsonArray(i);
            resourceMatrix[i] = new Resource[rowArray.size()];
            for (int j = 0; j < rowArray.size(); j++) {
                resourceMatrix[i][j] = Resource.valueOf(rowArray.getString(j));
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
            corners.put(CornerPosition.valueOf(key), new Corner(createCornerContent(jsonObject.getString(key))));
        }
        return corners;
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

}