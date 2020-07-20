package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;
import com.profile.util.JsonUtils;

public class LiveCleaner {
    public static boolean clean(JSONObject jsonObject) {
        final boolean generalDataValid = JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sLiveNecessaryKeys) &&
                JsonUtils.verifyStringValuesWithKey(jsonObject,
                        "fromPlat", CleanConfig.sLiveFromPlatValidValues) &&
                JsonUtils.verifyStringValuesWithKey(jsonObject,
                        "resType", CleanConfig.sLiveResTypeValidValues) &&
                JsonUtils.verifyIntegerValuesStringWithKeyAndSplitter(jsonObject,
                        "recomType", /*","*/ ";", CleanConfig.sLiveRecomTypeValidValues);

        final String event = jsonObject.getString("eventId");
        switch (event) {
            case CleanConfig.sEnterLiveChannelEvent:
            case CleanConfig.sExit1Event:
            case CleanConfig.sExit2Event:
            case CleanConfig.sLiveVolumeUpEvent:
            case CleanConfig.sLiveVolumeDownEvent:
            case CleanConfig.sLivePlayDoneEvent:
            case CleanConfig.sLivePlayBeatEvent:
            case CleanConfig.sSwitchChannelEvent:
            case CleanConfig.sLiveChannelListEvent:
            case CleanConfig.sLiveSilenceEvent:
            case CleanConfig.sGroup2SingleEvent:
            case CleanConfig.sLiveOrderEvent:
            case CleanConfig.sSingle2GroupEvent:
            case CleanConfig.sOrderCancelEvent:
            case CleanConfig.sSwitchProgrammeEvent:
                return generalDataValid;
            case CleanConfig.sSwitchLiveTypeEvent:
                return generalDataValid && JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sSwitchLiveTypeNecessaryKeys);
            default:
                System.err.println("Unrecognized event with eventId " + event);
                return false;
        }
    }
}
