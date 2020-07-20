package com.profile.util;

public class HdfsUtils {
    private static final String sHOST = "192.168.57.141";
    private static final String sDIR = "/profile/";
    private static final String sBEHAVIOR_FLOW_FILE_NAME = "dsyx0630.txt";
    private static final String sMEDIA_SOLID_FILE_NAME = "媒资.json";

    public static final String sBEHAVIOR_FLOW_SOURCE_URL = "hdfs://" + sHOST +
            sDIR + sBEHAVIOR_FLOW_FILE_NAME;
    public static final String sMEDIA_SOLID_SOURCE_URL = "hdfs://" + sHOST +
            sDIR + sMEDIA_SOLID_FILE_NAME;
}
