package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;
import com.profile.util.JsonUtils;

public class ShoppingCleaner {
    public static boolean clean(JSONObject jsonObject) {
        final String event = jsonObject.getString("eventId");
        switch (event) {
            case CleanConfig.sItemPageVisitEvent:
                return JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sItemPageVisitNecessaryKeys)
                        && JsonUtils.verifyIntegerStringValuesWithKey(jsonObject, "optState", CleanConfig.sShoppingOptStateValidValues);
            case CleanConfig.sItemListExposureEvent:
                return JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sItemListExposureNecessaryKeys) &&
                        JsonUtils.verifyListValueTypeWithKey(jsonObject, "ids", String.class.getCanonicalName()) &&
                        (JsonUtils.verifySingleValueTypeWithKey(jsonObject, "rowNum", int.class.getCanonicalName()) ||
                                JsonUtils.verifySingleValueTypeWithKey(jsonObject, "rowNum", Integer.class.getCanonicalName()));
            case CleanConfig.sItemDetailClickEvent:
                return JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sItemDetailClickNecessaryKeys);
            case CleanConfig.sItemDetailExitEvent:
                return JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sItemDetailExitNecessaryKeys) &&
                        (JsonUtils.verifySingleValueTypeWithKey(jsonObject, "duration", int.class.getCanonicalName()) ||
                                JsonUtils.verifySingleValueTypeWithKey(jsonObject, "duration", Integer.class.getCanonicalName()));
            default:
                System.err.println("Unrecognized event with eventId " + event);
                return false;
        }
    }
}
