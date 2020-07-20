package com.profile.clean.collector;

import com.alibaba.fastjson.JSONObject;
import com.profile.util.FileUtils;
import com.profile.util.TextUtils;

import java.io.IOException;

public class UncleanDataInfoCollector {
    private static StringBuffer sBuffer = new StringBuffer();

    public static void save() {
        try {
            FileUtils.saveUncleanDataInfo(sBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendError(String errInfo) {
        System.err.println(errInfo);
        if (!TextUtils.isEmpty(errInfo)) {
            sBuffer.append(errInfo).append("\n");
        }
    }

}
