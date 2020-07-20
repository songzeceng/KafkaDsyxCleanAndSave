package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;
import com.profile.util.JsonUtils;

public class TimeShiftingCleaner {
    public static boolean clean(JSONObject jsonObject) {
        final String event = jsonObject.getString("eventId");
        final boolean generalDataValid =
                JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sTimeShiftingNecessaryKeys) &&
                JsonUtils.verifyStringValuesWithKey(jsonObject,
                        "resType", CleanConfig.sTimeShiftingResTypeValidValues);
        switch (event) {
            case CleanConfig.sEnterTimeShiftingEvent:
            case CleanConfig.sTimeShiftingPauseEvent:
            case CleanConfig.sTimeShiftingRestoreEvent:
            case CleanConfig.sTimeShiftingToggleEvent:
            case CleanConfig.sTimeShiftingFastForwardEvent:
            case CleanConfig.sTimeShiftingFastBackwardEvent:
            case CleanConfig.sTimeShiftingExitEvent:
            case CleanConfig.sTimeShiftingExceptEvent:
            case CleanConfig.sTimeShiftingVolumeUpEvent:
            case CleanConfig.sTimeShiftingVolumeDownEvent:
            case CleanConfig.sTimeShiftingPlayDoneEvent:
            case CleanConfig.sTimeShiftingPlayBeatEvent:
            case CleanConfig.sTimeShiftingSilenceEvent:
            case CleanConfig.sTimeShiftingSelectTimeEvent:
                return generalDataValid;
            default:
                System.err.println("Unrecognized event with eventId " + event);
                return false;
        }
    }
}
