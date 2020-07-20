package com.profile.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.profile.clean.collector.*;

import java.util.Set;

public class JsonUtils {
    public static boolean verifyNecessaryKeys(JSONObject jsonObject, String[] necessaryKeys) {
        if (jsonObject == null) {
            return false;
        }

        if (necessaryKeys == null) {
            return true;
        }

        Set<String> keySet = jsonObject.keySet();
        for (String necessaryKey : necessaryKeys) {
            if (!keySet.contains(necessaryKey)) {
                UncleanDataInfoCollector.appendError("No necessary key: " + necessaryKey
                        + " for " + jsonObject.toString());
                return false;
            }
        }

        return true;
    }

    public static boolean verifyStringValuesWithKey(JSONObject jsonObject, String key,
                                                    String[] validValues) {
        if (jsonObject == null || TextUtils.isEmpty(key) || !jsonObject.containsKey(key)) {
            return false;
        }

        if (validValues == null) {
            return true;
        }

        String value = jsonObject.get(key).toString();
        if (TextUtils.isEmpty(value)) {
            return false;
        }

        if (TextUtils.stringInArray(value, validValues)) {
            return true;
        }

        UncleanDataInfoCollector.appendError("Invalid value: " + value + " with key:" + key
                + " for " + jsonObject.toString());
        return false;
    }

    public static boolean verifyStringValuesWithKeyAndSplitter(JSONObject jsonObject,
                                                               String key,
                                                               String splitter,
                                                               String[] validValues) {
        if (jsonObject == null || TextUtils.isEmpty(key) || !jsonObject.containsKey(key)) {
            return false;
        }

        if (validValues == null) {
            return true;
        }

        if (TextUtils.isEmpty(splitter)) {
            return verifyStringValuesWithKey(jsonObject, key, validValues);
        }

        String value = jsonObject.get(key).toString();
        if (TextUtils.isEmpty(value)) {
            return false;
        }

        for (String s : value.split(splitter)) {
            if (TextUtils.stringInArray(s, validValues)) {
                return true;
            }
        }

        UncleanDataInfoCollector.appendError("Invalid value: " + value
                + " with key:" + key + " and splitter: " + splitter
                + " for " + jsonObject.toString());
        return false;
    }

    public static boolean verifyIntegerStringValuesWithKey(JSONObject jsonObject, String key,
                                                           String[] validValues) {
        if (jsonObject == null || TextUtils.isEmpty(key) || !jsonObject.containsKey(key)) {
            return false;
        }

        if (validValues == null) {
            return true;
        }

        String value = jsonObject.get(key).toString();
        if (TextUtils.isEmpty(value)) {
            return false;
        }

        if (TextUtils.stringInArray(value, validValues)) {
            return true;
        }

        if (!value.contains(".")) {
            UncleanDataInfoCollector.appendError("Invalid value: " + value + " with key:" + key
                    + " for " + jsonObject.toString());
            return false;
        }

        int pointIndex = value.indexOf('.');
        String integerPart = value.substring(0, pointIndex);

        if (pointIndex == validValues.length - 1
                && TextUtils.stringInArray(integerPart, validValues)) {

            jsonObject.put(key, value.substring(0, pointIndex));
            UncleanDataInfoCollector.appendError("Unformatted value: " + value + " with key:" + key
                    + " for " + jsonObject.toString());
            return true;
        }

        String fractionPart = value.substring(pointIndex + 1);

        if (TextUtils.stringInArray(integerPart, validValues)
                && Integer.parseInt(fractionPart) == 0) {

            jsonObject.put(key, value.substring(0, pointIndex));
            UncleanDataInfoCollector.appendError("Unformatted value: " + value + " with key:" + key
                    + " for " + jsonObject.toString());
            return true;
        }

        UncleanDataInfoCollector.appendError("Invalid value: " + value + " with key:" + key
                + " for " + jsonObject.toString());
        return false;
    }

    public static boolean verifyIntegerValuesStringWithKeyAndSplitter(JSONObject jsonObject,
                                                               String key,
                                                               String splitter,
                                                               String[] validValues) {
        if (jsonObject == null || TextUtils.isEmpty(key) || !jsonObject.containsKey(key)) {
            return false;
        }

        if (validValues == null) {
            return true;
        }

        if (TextUtils.isEmpty(splitter)) {
            return verifyIntegerStringValuesWithKey(jsonObject, key, validValues);
        }

        String value = jsonObject.get(key).toString();
        if (TextUtils.isEmpty(value)) {
            return false;
        }

        for (String s : value.split(splitter)) {
            if (TextUtils.stringInArray(s, validValues)) {
                return true;
            }

            if (!value.contains(".")) {
                UncleanDataInfoCollector.appendError("Invalid value: " + value + " with key:" + key
                        + " for " + jsonObject.toString());
                return false;
            }

            int pointIndex = s.indexOf('.');
            String integerPart = s.substring(0, pointIndex);

            if (pointIndex == validValues.length - 1
                    && TextUtils.stringInArray(integerPart, validValues)) {

                jsonObject.put(key, value.substring(0, pointIndex));

                UncleanDataInfoCollector.appendError("Unformatted value: " + value + " with key:" + key
                        + " for " + jsonObject.toString());
                return true;
            }

            String fractionPart = s.substring(pointIndex + 1);

            if (TextUtils.stringInArray(integerPart, validValues)
                    && Integer.parseInt(fractionPart) == 0) {

                jsonObject.put(key, value.substring(0, pointIndex));

                UncleanDataInfoCollector.appendError("Unformatted value: " + value + " with key:" + key
                        + " for " + jsonObject.toString());
                return true;
            }
        }

        UncleanDataInfoCollector.appendError("Invalid value: " + value
                + " with key:" + key + " and splitter: " + splitter
                + " for " + jsonObject.toString());
        return false;
    }

//    public static void modifyFloatValueString2IntString(JSONObject jsonObject, String key) {
//        if (jsonObject == null || TextUtils.isEmpty(key) || !jsonObject.containsKey(key)) {
//            return;
//        }
//
//        String value = jsonObject.get(key).toString();
//        if (value.contains(".")) {
//            value = value.substring(0, value.indexOf('.'));
//        }
//        jsonObject.put(key, value);
//    }

    public static boolean verifySingleValueTypeWithKey(JSONObject jsonObject, String key, String typeName) {
        if (jsonObject == null || !jsonObject.containsKey(key)) {
            return false;
        }

        if (TextUtils.isEmpty(typeName)) {
            return true;
        }

        String valueType = jsonObject.get(key).getClass().getCanonicalName();
        boolean valid = valueType.equals(typeName);
        if (!valid) {
            UncleanDataInfoCollector.appendError("Invalid type: " + valueType
                    + " with key:" + key + " and expected type: " + typeName
                    + " for " + jsonObject.toString());
        }
        return valid;
    }

    public static boolean verifyListValueTypeWithKey(JSONObject jsonObject, String key, String typeName) {
        if (jsonObject == null || !jsonObject.containsKey(key)) {
            return false;
        }

        if (TextUtils.isEmpty(typeName)) {
            return true;
        }

        Object value = jsonObject.get(key);
        if (!(value instanceof JSONArray)) {
            return false;
        }

        for (Object o : (JSONArray) value) {
            String valueType = o.getClass().getCanonicalName();
            if (!valueType.equals(typeName)) {
                UncleanDataInfoCollector.appendError("Invalid type: " + valueType
                        + " with key:" + key + " and expected type: " + typeName
                        + " for " + jsonObject.toString());
                return false;
            }
        }

        return true;
    }
}
