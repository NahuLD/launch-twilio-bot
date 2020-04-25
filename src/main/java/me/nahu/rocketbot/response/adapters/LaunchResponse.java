package me.nahu.rocketbot.response.adapters;

import com.google.gson.JsonObject;
import me.nahu.launchlibrary.LaunchLibrary;
import me.nahu.launchlibrary.entities.launch.LaunchQuery;
import me.nahu.rocketbot.response.Response;

public class LaunchResponse extends Response {
    public LaunchResponse(LaunchLibrary launchLibrary) {
        super(launchLibrary);
    }

    @Override
    public String getPath() {
        return "/launch";
    }

    @Override
    public String getName() {
        return "launch";
    }

    @Override
    public String createMessage(JsonObject object) throws Throwable {
        LaunchQuery.Launch launch = first(sync(getLaunchLibrary().getNextLaunches(1)).getLaunches());
        return String.format("Next launch is scheduled for %s, by agency %s using rocket %s",
                launch.getWindowStart().toString(),
                launch.getLaunchServiceProvider().getAbbreviation(),
                launch.getRocket().getName());
    }
}
