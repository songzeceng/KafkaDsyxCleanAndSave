package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;
import com.profile.util.JsonUtils;

public class SystemRunningDataCleaner {
    public static boolean clean(JSONObject jsonObject) {
        String event = jsonObject.getString("eventId");
        switch (event) {
            case CleanConfig.sBootEvent:
            case CleanConfig.sShutdownEvent:
            case CleanConfig.sRebootEvent:
                return true;
            case CleanConfig.sUpgradeEvent:
                return JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sUpgradeNecessaryKeys)
                        && JsonUtils.verifyIntegerStringValuesWithKey(jsonObject, "upgradeType",
                        CleanConfig.sUpgradeValidValues);
            default:
                System.err.println("Unrecognized event with eventId " + event);
                return false;
        }
    }
}
