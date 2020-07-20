package com.profile.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static String readHDFSFile(String fileUrl) throws IOException {
        FSDataInputStream fsIn = FileSystem.get(URI.create(fileUrl), new Configuration())
                .open(new Path(fileUrl));
        BufferedInputStream inputStream = new BufferedInputStream(fsIn);

        byte[] bytes = new byte[1024 * 10];
        int len;
        StringBuilder builder = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            builder.append(new String(bytes, 0, len));
        }

        return builder.toString();
    }

    public static void saveUncleanDataInfo(String errInfo) throws IOException {
        File file = new File("unclean"
                + TimeUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss")
                + ".txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(errInfo.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
    }

    public static void save(String info, String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(info.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
    }
}
