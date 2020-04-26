package me.nahu.rocketbot.response;

import com.google.gson.JsonObject;
import me.nahu.launchlibrary.LaunchLibrary;
import me.nahu.launchlibrary.Request;

import java.util.List;

public abstract class Response {
    private final LaunchLibrary launchLibrary;

    public Response(final LaunchLibrary launchLibrary) {
        this.launchLibrary = launchLibrary;
    }

    public abstract String getName();

    public abstract String createMessage(JsonObject object);

    protected String parseAnswer(JsonObject object, String nodeName) {
        return object.getAsJsonObject(nodeName)
                .getAsJsonPrimitive("answer")
                .getAsString();
    }

    protected <T> T first(List<T> list) {
        return list.get(0);
    }

    protected <T> T sync(Request<T> it) {
        try {
            return it.executeSync();
        } catch (Throwable throwable) {
            return null;
        }
    }

    protected LaunchLibrary getLaunchLibrary() {
        return launchLibrary;
    }
}
