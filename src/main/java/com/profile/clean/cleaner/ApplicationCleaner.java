package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;
import com.profile.util.JsonUtils;

public class ApplicationCleaner {
    public static boolean clean(JSONObject jsonObject) {
        boolean generalDataValid = JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sApplicationNecessaryKeys);

        final String event = jsonObject.getString("eventId");
        switch (event) {
            case CleanConfig.sApplicationDownloadEvent:
            case CleanConfig.sApplicationRunningEvent:
                return generalDataValid;
            default:
                System.err.println("Unrecognized event with eventId " + event);
                return false;
        }
    }
}
