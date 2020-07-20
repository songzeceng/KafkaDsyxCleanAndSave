package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;
import com.profile.util.JsonUtils;

public class PageVisitCleaner {
    public static boolean clean(JSONObject jsonObject) {
        final boolean generaDataValid = JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sPageVisitNecessaryKeys) &&
                JsonUtils.verifyIntegerStringValuesWithKey(jsonObject,
                        "optState", CleanConfig.sPageVisitOptStateValidValues);

        final String event = jsonObject.getString("eventId");
        switch (event) {
            case CleanConfig.sLauncherVisitEvent:
                return generaDataValid
                        && JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sLauncherVisitNecessaryKeys)
                        && JsonUtils.verifyStringValuesWithKey(jsonObject, "resType", CleanConfig.sPageVisitResTypeValidValues);
            case CleanConfig.sApplicationURLEvent:
            case CleanConfig.sTopicURLEvent:
            case CleanConfig.sColumnURLEvent:
            case CleanConfig.sResourceClickEvent:
                return generaDataValid;
            case CleanConfig.sDetailURLEvent:
            case CleanConfig.sURLEnterEvent:
                return generaDataValid
                        && JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sClickOrDetailNecessaryKeys)
                        && JsonUtils.verifyStringValuesWithKeyAndSplitter(jsonObject, "resType", /*","*/ ";", CleanConfig.sPageVisitResTypeValidValues);
            default:
                System.err.println("Unrecognized event with eventId " + event);
                return false;
        }
    }
}
