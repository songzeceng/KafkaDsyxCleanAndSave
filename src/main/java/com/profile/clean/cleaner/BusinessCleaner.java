package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;
import com.profile.util.JsonUtils;

public class BusinessCleaner {
    public static boolean clean(JSONObject jsonObject) {
        boolean generalDataValid = JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sBusinessNecessaryKeys);

        final String event = jsonObject.getString("eventId");
        switch (event) {
            case CleanConfig.sSearchEvent:
            case CleanConfig.sSearchSelectEvent:
            case CleanConfig.sFilterEvent:
            case CleanConfig.sFilterSelectEvent:
                return generalDataValid;
            case CleanConfig.sResourceShareEvent:
                return generalDataValid && JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sResourceShareNecessaryKeys);
            case CleanConfig.sApplicationShareEvent:
                return generalDataValid && JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sApplicationShareNecessaryKeys);
            default:
                System.err.println("Unrecognized event with eventId " + event);
                return false;
        }
    }
}
