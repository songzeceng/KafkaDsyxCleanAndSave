package com.profile.clean.cleaner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.profile.clean.CleanConfig;
import com.profile.util.*;
import com.profile.clean.collector.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

public class GeneralDataCleaner {
    public static boolean checkGeneral(JSONObject jsonObject) {
        final String bizType = jsonObject.getString("bizType");
        final boolean generalDataValid =
                JsonUtils.verifyNecessaryKeys(jsonObject, CleanConfig.sGeneralNecessaryKeys)
                /*&& JsonUtils.verifySingleValueTypeWithKey(jsonObject, "data",
                        JSONObject.class.getCanonicalName())*/;
        switch (bizType) {
            case CleanConfig.sSystemRunningEvent:
                return generalDataValid &&
                        SystemRunningDataCleaner.clean(jsonObject);
            case CleanConfig.sPageVisitEvent:
                return generalDataValid &&
                        PageVisitCleaner.clean(jsonObject);
            case CleanConfig.sExposureEvent:
                return generalDataValid &&
                        ExposureCleaner.clean(jsonObject);
            case CleanConfig.sSelectPlayEvent:
                return generalDataValid &&
                        SelectPlayCleaner.clean(jsonObject);
            case CleanConfig.sPreferenceEvent:
                return generalDataValid &&
                        PreferenceCleaner.clean(jsonObject);
            case CleanConfig.sBusinessUsingEvent:
                return generalDataValid &&
                        BusinessCleaner.clean(jsonObject);
            case CleanConfig.sLiveEvent:
                return generalDataValid &&
                        LiveCleaner.clean(jsonObject);
            case CleanConfig.sTimeShiftingEvent:
                return generalDataValid &&
                        TimeShiftingCleaner.clean(jsonObject);
            case CleanConfig.sReviewEvent:
                return generalDataValid &&
                        ReviewCleaner.clean(jsonObject);
            case CleanConfig.sApplicationEvent:
                return generalDataValid &&
                        ApplicationCleaner.clean(jsonObject);
            case CleanConfig.sSystemSettingEvent:
                return generalDataValid &&
                        SystemSettingCleaner.clean(jsonObject);
            case CleanConfig.sShoppingEvent:
                return generalDataValid &&
                        ShoppingCleaner.clean(jsonObject);
            case CleanConfig.sTerminalExceptionEvent:
                return generalDataValid &&
                        TerminalExceptionCleaner.clean(jsonObject);
            default:
                System.err.println("Unrecognized event with bizType " + bizType);
                return false;
        }
    }

    public static void main(String[] args) throws IOException {
        String dataFileURL = HdfsUtils.sBEHAVIOR_FLOW_SOURCE_URL;
        FSDataInputStream fsIn = FileSystem.get(URI.create(dataFileURL), new Configuration())
                .open(new Path(dataFileURL));
        BufferedReader bf = new BufferedReader(new InputStreamReader(fsIn));
        String line = null;

        int totalCount = 0, invalidCount = 0;
        StringBuilder builder = new StringBuilder();

        while (!TextUtils.isEmpty((line = bf.readLine()))) {
            String currentTime = TimeUtils.format(System.currentTimeMillis(),
                    "yyyyMMddHHmmssSSS");
            String first = line.substring(0, line.lastIndexOf("}"));
            final String product = first + ", sendTime:\"" + currentTime + "\"}";

            JSONObject object = (JSONObject) JSON.parse(product);
            if (!checkGeneral(object)) {
                // 数据修改
                // 如果是缺少必要字段，剔除 false
                // 如果是值错误，剔除 false
                // 事件未知，剔除 false
                // 如果是值不规范(2.0 2 "2")，规范化 true
                // 分隔符不对？

                // 每个场景一个Modifier
                invalidCount++;
            } else {
                builder.append(object.toJSONString()).append("\n");
            }

            totalCount++;
        }

        builder.append(totalCount - invalidCount).append(" in total\n")
                .append(invalidCount).append(" filtered out\n");
        FileUtils.save(builder.toString(),
                "dataCleaned" + TimeUtils.format(System.currentTimeMillis(),
                        "yyyyMMddHHmmss") + ".txt");

        UncleanDataInfoCollector.save();

        System.out.println("TotalCount:" + totalCount + ", invalidCount:" + invalidCount);

        bf.close();
        fsIn.close();
    }
}
