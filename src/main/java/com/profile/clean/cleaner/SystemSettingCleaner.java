package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;

public class SystemSettingCleaner {
    public static boolean clean(JSONObject jsonObject) {
        final String event = jsonObject.getString("eventId");
        switch (event) {
            case CleanConfig.sWIFISettingEvent:
            case CleanConfig.sDPIEvent:
            case CleanConfig.sSystemUpgradeEvent:
                return true;
//            case sApplicationInfoEvent:
//                return JsonUtils.verifyNecessaryKeys(jsonObject, sApplicationInfoNecessaryKeys);
            default:
                System.err.println("Unrecognized event with eventId " + event);
                return false;
        }
    }
}
