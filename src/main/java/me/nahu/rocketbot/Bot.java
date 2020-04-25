package me.nahu.rocketbot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.nahu.launchlibrary.LaunchLibrary;
import me.nahu.rocketbot.response.Response;
import me.nahu.rocketbot.response.adapters.LaunchResponse;
import spark.Spark;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static me.nahu.rocketbot.Main.LOGGER;

public class Bot {
    private static final JsonParser JSON_PARSER = new JsonParser();
    private final Set<Response> responses = new HashSet<>();

    public Bot(int port) {
        LaunchLibrary launchLibrary = new LaunchLibrary.Builder().build();

        registerResponses(new LaunchResponse(launchLibrary));

        responses.forEach(response -> Spark.get(response.getPath(), (req, res) -> {
            String memory = req.queryParams("Memory");
            if (memory != null) {

                try {
                    return response.createMessage(getObject(memory, response.getName()));
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }
            return "There was an error processing your request. (Empty memory)";
        }));

        Spark.port(port);
    }

    private void registerResponses(Response... responses) {
        Stream.of(responses).forEach(this::registerResponse);
    }

    private void registerResponse(Response response) {
        responses.add(response);
        LOGGER.info("Added response: " + response.getName());
    }

    private JsonObject getObject(String memory, String name) {
        JsonObject root = JSON_PARSER.parse(memory).getAsJsonObject();
        return root.getAsJsonObject("twilio")
                .getAsJsonObject("collected_data")
                .getAsJsonObject(name)
                .getAsJsonObject("answers");
    }
}
