package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;
import com.profile.util.JsonUtils;

public class ReviewCleaner {
    public static boolean clean(JSONObject jsonObject) {
        final boolean generaDataValid =
                JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sReviewNecessaryKeys) &&
                JsonUtils.verifyStringValuesWithKey(jsonObject,
                        "fromPlat", CleanConfig.sReviewFromPlatValidValues) &&
                JsonUtils.verifyStringValuesWithKey(jsonObject,
                        "resType", CleanConfig.sReviewResTypeValidValues);

        final String event = jsonObject.getString("eventId");
        switch (event) {
            case CleanConfig.sReviewDetailEvent:
            case CleanConfig.sReviewPlayEvent:
            case CleanConfig.sReviewPauseEvent:
            case CleanConfig.sReviewRestoreEvent:
            case CleanConfig.sReviewToggleEvent:
            case CleanConfig.sReviewFastForwardEvent:
            case CleanConfig.sReviewFastBackwardEvent:
            case CleanConfig.sReviewExitEvent:
            case CleanConfig.sReviewExceptEvent:
            case CleanConfig.sReviewVolumeUpEvent:
            case CleanConfig.sReviewVolumeDownEvent:
            case CleanConfig.sReviewPlayDoneEvent:
            case CleanConfig.sReviewPlayBeatEvent:
            case CleanConfig.sReviewChannelListEvent:
            case CleanConfig.sChannelProgrammeListEvent:
            case CleanConfig.sReviewSilenceEvent:
            case CleanConfig.sReviewSelectTimeEvent:
                return generaDataValid;
            default:
                System.err.println("Unrecognized event with eventId " + event);
                return false;
        }
    }
}
