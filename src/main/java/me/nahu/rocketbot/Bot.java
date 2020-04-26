package me.nahu.rocketbot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.nahu.launchlibrary.LaunchLibrary;
import me.nahu.rocketbot.response.Response;
import me.nahu.rocketbot.response.adapters.AgencyResponse;
import me.nahu.rocketbot.response.adapters.LaunchResponse;
import me.nahu.rocketbot.response.adapters.MissionResponse;
import me.nahu.rocketbot.response.adapters.RocketResponse;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static me.nahu.rocketbot.Main.LOGGER;

public class Bot {
    private static final JsonParser JSON_PARSER = new JsonParser();
    private final Map<String, Response> responses = new HashMap<>();

    public Bot(int port) {
        LaunchLibrary launchLibrary = new LaunchLibrary.Builder().build();

        registerResponses(new LaunchResponse(launchLibrary),
                new AgencyResponse(launchLibrary),
                new MissionResponse(launchLibrary),
                new RocketResponse(launchLibrary));

        Spark.get("/collect", (req, res) -> {
            String memory = req.queryParams("Memory");
            if (memory != null) {
                JsonObject object = getObject(memory);
                String nodeName = object.getAsJsonObject("action_type").getAsJsonObject("answer").getAsString();

                return responses.get(nodeName).createMessage(object);
            }
            return "There was an error processing your request. (Empty memory)";
        });

        Spark.port(port);
    }

    private void registerResponses(Response... responses) {
        Stream.of(responses).forEach(this::registerResponse);
    }

    private void registerResponse(Response response) {
        responses.put(response.getName(), response);
        LOGGER.info("Added response: " + response.getName());
    }

    private JsonObject getObject(String memory) {
        JsonObject root = JSON_PARSER.parse(memory).getAsJsonObject();
        return root.getAsJsonObject("twilio")
                .getAsJsonObject("collected_data")
                .getAsJsonObject("collect_type")
                .getAsJsonObject("answers");
    }
}
