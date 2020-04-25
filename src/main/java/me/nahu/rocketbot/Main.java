package me.nahu.rocketbot;

import java.util.logging.Logger;

public class Main {
    public static final Logger LOGGER = Logger.getLogger("[LaunchBot]");

    public static void main(String[] args) {
        int port = args[0] != null ? Integer.parseInt(args[0]) : 4567;
        LOGGER.info("Listening to port: " + port);
        new Bot(port);
    }
}
