package com.profile.db;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;

public class MongoHelper {
    private final Mongo mMongo;
    private final DBCollection mBehaviorFlowCollection;

    private static MongoHelper sInstance;

    private MongoHelper(String[] args) {
        String ip = "192.168.57.141";
        int port = 27017;
        String dbName = "profile";
        String collectionName = "behaviorFlow";

        if (args != null) {
            ip = args.length > 0 ? args[0] : ip;
            port = args.length > 1 ? Integer.parseInt(args[1]) : port;
            dbName = args.length > 2 ? args[2] : dbName;
            collectionName = args.length > 3 ? args[3] : collectionName;
        }

        mMongo = new Mongo(ip, port);

        mBehaviorFlowCollection = mMongo.getDB(dbName).getCollection(collectionName);
    }

    public static MongoHelper getInstance(String[] args) {
        if (sInstance == null) {
            synchronized (HBaseHelper.class) {
                if (sInstance == null) {
                    try {
                        sInstance = new MongoHelper(args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return sInstance;
    }

    public ArrayList<String> getValuesWithKey(String key) {
        BasicDBObject group_obj = new BasicDBObject();
        group_obj.put("_id", "$" + key);

        BasicDBObject match = new BasicDBObject("$group", group_obj);

        List<BasicDBObject> aggregateList = new ArrayList<BasicDBObject>();
        aggregateList.add(match);

        ArrayList<String> results = new ArrayList<String>();
        Iterable<DBObject> aggResults = mBehaviorFlowCollection.aggregate(aggregateList).results();

        for (DBObject result : aggResults) {
            if (!result.containsField("_id") || result.get("_id") == null) {
               continue;
            }

            results.add(result.get("_id").toString());
        }

        return results;
    }

    public boolean valueExists(String key, String value, ArrayList<String> values) {
        DBObject result = mBehaviorFlowCollection.findOne(new BasicDBObject(key, value));
        return result != null && result.containsField(key)
                && values.contains(result.get(key).toString());
    }

    public void updateValue(String key, Object value) {
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.append("$unset", new BasicDBObject(key, ""));
        mBehaviorFlowCollection.update(new BasicDBObject(), dbObject, false ,true);

        dbObject = new BasicDBObject(key, value);
        mBehaviorFlowCollection.insert(dbObject);
    }

    public void insertOne(JSONObject jsonRecord) {
        ArrayList<String> users = getValuesWithKey("userId");
        ArrayList<String> apps = getValuesWithKey("appProduct");

        boolean userExists = valueExists("userId",
                jsonRecord.getString("userId"), users);
        boolean appExists = valueExists("appProduct",
                jsonRecord.getString("appProduct"), apps);

        if (!userExists) {
            updateValue("userCount", users.size() + 1);
        }

        if (!appExists) {
            updateValue("appCount", apps.size() + 1);
        }

        BasicDBObject dbObject = new BasicDBObject("appProduct", jsonRecord.getString("appProduct"));
        for (String s : jsonRecord.keySet()) {
            dbObject = dbObject.append(s, jsonRecord.get(s).toString());
        }

        mBehaviorFlowCollection.insert(dbObject);
    }

    public void close() {
        mMongo.close();
    }
}
