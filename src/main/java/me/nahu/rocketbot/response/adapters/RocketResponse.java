package me.nahu.rocketbot.response.adapters;

import com.google.gson.JsonObject;
import me.nahu.launchlibrary.LaunchLibrary;
import me.nahu.launchlibrary.entities.rocket.RocketQuery;
import me.nahu.rocketbot.response.Response;

public class RocketResponse extends Response {
    public RocketResponse(LaunchLibrary launchLibrary) {
        super(launchLibrary);
    }

    @Override
    public String getName() {
        return "rocket";
    }

    @Override
    public String createMessage(JsonObject object) {
        String rocketName = parseAnswer(object, "rocket-name");
        RocketQuery.Rocket rocket = first(sync(getLaunchLibrary().getRocketsFromName(rocketName)).getRockets());
        return String.format("%s: Of family %s. For more info check %s", rocket.getName(), rocket.getFamily().getName(), rocket.getWikiUrl());
    }
}
