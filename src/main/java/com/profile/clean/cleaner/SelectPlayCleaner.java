package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;
import com.profile.util.JsonUtils;

public class SelectPlayCleaner {
    public static boolean clean(JSONObject jsonObject) {
        final boolean generaDataValid = JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sSelectPlayNecessaryKeys) &&
                JsonUtils.verifyStringValuesWithKey(jsonObject,
                        "fromPlat", CleanConfig.sSelectPlayFromPlatValidValues) &&
                JsonUtils.verifyStringValuesWithKey(jsonObject,
                        "resType", CleanConfig.sSelectPlayResTypeValidValues) &&
                JsonUtils.verifyIntegerValuesStringWithKeyAndSplitter(jsonObject,
                        "recomType", /*","*/ ";", CleanConfig.sSelectPlayRecomTypeValidValues);

        final String event = jsonObject.getString("eventId");
        switch (event) {
            case CleanConfig.sSelectPlayPlayEvent:
            case CleanConfig.sSelectPlayPauseEvent:
            case CleanConfig.sSelectPlayRestoreEvent:
            case CleanConfig.sSelectPlayToggleEvent:
            case CleanConfig.sSelectPlayFastForwardEvent:
            case CleanConfig.sSelectPlayFastBackwardEvent:
            case CleanConfig.sSelectPlayExitEvent:
            case CleanConfig.sSelectPlayVolumeUpEvent:
            case CleanConfig.sSelectPlayVolumeDownEvent:
            case CleanConfig.sSelectPlayPlayDoneEvent:
            case CleanConfig.sSelectPlayPlayBeatEvent:
            case CleanConfig.sSelectPlayOrderEvent:
            case CleanConfig.sSelectPlaySilenceEvent:
            case CleanConfig.sSelectPlaySelectTimeEvent:
                return generaDataValid;
            default:
                System.err.println("Unrecognized event with eventId " + event);
                return false;
        }
    }
}
