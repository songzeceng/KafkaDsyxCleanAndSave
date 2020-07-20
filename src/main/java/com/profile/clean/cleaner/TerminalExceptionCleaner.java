package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;
import com.profile.util.JsonUtils;

public class TerminalExceptionCleaner {
    public static boolean clean(JSONObject jsonObject) {
        final boolean generaDataValid = JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sTerminalExceptionNecessaryKeys);

        final String event = jsonObject.getString("eventId");
        switch (event) {
            case CleanConfig.sROMExceptionEvent:
            case CleanConfig.sApplicationExceptionEvent:
                return generaDataValid;
            default:
                System.err.println("Unrecognized event with eventId " + event);
                return false;
        }
    }
}
