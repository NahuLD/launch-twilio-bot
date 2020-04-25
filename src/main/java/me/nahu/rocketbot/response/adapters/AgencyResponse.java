package me.nahu.rocketbot.response.adapters;

import com.google.gson.JsonObject;
import me.nahu.launchlibrary.LaunchLibrary;
import me.nahu.launchlibrary.entities.agency.AgencyQuery;
import me.nahu.rocketbot.response.Response;

public class AgencyResponse extends Response {
    public AgencyResponse(LaunchLibrary launchLibrary) {
        super(launchLibrary);
    }

    @Override
    public String getPath() {
        return "/agency";
    }

    @Override
    public String getName() {
        return "agency";
    }

    @Override
    public String createMessage(JsonObject object) throws Throwable {
        String agencyName = parseAnswer(object, "answer-name");
        AgencyQuery.Agency agency = first(sync(getLaunchLibrary().getAgenciesByName(agencyName)).getAgencies());
        return String.format("%s: Located on %s. For more info check: %s", agency.getName(), agency.getCountryCode(), agency.getWikiUrl());
    }
}
