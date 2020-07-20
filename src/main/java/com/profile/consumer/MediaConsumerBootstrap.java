package com.profile.consumer;

import com.alibaba.fastjson.JSON;
import com.profile.db.HBaseHelper;
import com.profile.util.TimeUtils;
import com.profile.util.FileUtils;
import com.profile.util.HdfsUtils;

import java.io.IOException;
import java.util.Arrays;

public class MediaConsumerBootstrap {
    public static void main(String[] args) throws IOException {
        String mediaFileURL = args.length > 0 ? args[0] : HdfsUtils.sMEDIA_SOLID_SOURCE_URL;
        String[] hbaseArgs = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : null;

        HBaseHelper instance = HBaseHelper.getInstance(hbaseArgs);

        long startTime = System.currentTimeMillis();
        instance.insertMedias(JSON.parseObject(FileUtils.readHDFSFile(mediaFileURL))
                .getJSONArray("RECORDS"));
        long endTime = System.currentTimeMillis();
        System.out.println("Media data insert time: "
                + TimeUtils.getTimeInterval(startTime, endTime));

        startTime = System.currentTimeMillis();
        instance.scanMedias();
        endTime = System.currentTimeMillis();
        System.out.println("Media data retrieve time: " +
                TimeUtils.getTimeInterval(startTime, endTime));

        instance.close();
    }
}
