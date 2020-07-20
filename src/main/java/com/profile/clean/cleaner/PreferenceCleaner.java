package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;
import com.profile.util.JsonUtils;

public class PreferenceCleaner {
    public static boolean clean(JSONObject jsonObject) {
        final boolean generalDataValid =
                JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sPreferenceNecessaryKeys)
                && JsonUtils.verifyStringValuesWithKey(jsonObject, "fromPlat", CleanConfig.sPreferenceFromPlatValidValues)
                && JsonUtils.verifyStringValuesWithKey(jsonObject, "resType", CleanConfig.sPreferenceResTypeValidValues);

        final String event = jsonObject.getString("eventId");
        switch (event) {
            case CleanConfig.sFavoriteEvent:
            case CleanConfig.sChaseEvent:
                return generalDataValid;
            default:
                System.err.println("Unrecognized event with eventId " + event);
                return false;
        }
    }
}
