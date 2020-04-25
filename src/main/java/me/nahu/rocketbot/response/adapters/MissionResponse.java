package me.nahu.rocketbot.response.adapters;

import com.google.gson.JsonObject;
import me.nahu.launchlibrary.LaunchLibrary;
import me.nahu.launchlibrary.entities.mission.MissionQuery;
import me.nahu.rocketbot.response.Response;

import java.util.List;

public class MissionResponse extends Response {
    public MissionResponse(LaunchLibrary launchLibrary) {
        super(launchLibrary);
    }

    @Override
    public String getPath() {
        return "/mission";
    }

    @Override
    public String getName() {
        return "mission";
    }

    @Override
    public String createMessage(JsonObject object) throws Throwable {
        String missionName = parseAnswer(object, "mission-name");
        List<MissionQuery.Mission> missions = sync(getLaunchLibrary().getMissionsByName(missionName)).getMissions();

        String formatted = String.format("Found %d missions going by %s", missions.size(), missionName);
        if (missions.isEmpty())
            return formatted;

        MissionQuery.Mission first = missions.get(0);
        return formatted.concat("\n").concat(String.format("First entry %s: %s", first.getName(), first.getDescription()));
    }
}
