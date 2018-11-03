package com.nazer.manager.client;

public class AppConfig {

    public enum AppMode {
        LOCAL, DEV, LIVE
    }

    private static AppMode appMode = AppMode.DEV;

    /**
     * Initialize all the variable in this method
     */
    public static String getBaseURL() {

        switch (appMode) {
            case LOCAL:
                return "http://racestakeserverdev.ignivastaginglocal.com/";
            case DEV:
                return "http://racestakeserverdev.ignivastagingdev.com/";
            case LIVE:
                return "http://racestakeserverdev.ignivastaging.com/";
        }
        return "http://racestakeserverdev.ignivastaging.com/";
    }

}
