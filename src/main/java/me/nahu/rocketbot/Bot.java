package me.nahu.rocketbot;

import com.twilio.Twilio;
import me.nahu.launchlibrary.LaunchLibrary;

public class Bot {
    private LaunchLibrary launchLibrary;

    public Bot() {
        launchLibrary = new LaunchLibrary.Builder().build();
    }
}
