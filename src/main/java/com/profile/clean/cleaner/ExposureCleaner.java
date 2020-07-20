package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;
import com.profile.util.JsonUtils;

public class ExposureCleaner {
    public static boolean clean(JSONObject jsonObject) {
        boolean generalDataValid = JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sExposureNecessaryKeys) &&
                JsonUtils.verifyStringValuesWithKeyAndSplitter(jsonObject,
                        "resType", /*","*/ ";", CleanConfig.sExposureResTypeValidValues) &&
                JsonUtils.verifyIntegerValuesStringWithKeyAndSplitter(jsonObject,
                        "recomType", /*","*/ ";", CleanConfig.sExposureRecomTypeValidValues);

        final String event = jsonObject.getString("eventId");
        switch (event) {
            case CleanConfig.sResourceListExposureEvent:
                return generalDataValid;
           default:
                System.err.println("Unrecognized event with bizType " + event);
                return false;
        }
    }
}
