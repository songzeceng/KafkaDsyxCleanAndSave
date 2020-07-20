package com.profile.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.profile.db.MongoHelper;
import com.profile.util.TimeUtils;
import com.profile.util.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Collections;

public class BehaviorFlowConsumerBootstrap {
    public static void main(String[] args) {
        String bootstrapServer = args.length > 0 ? args[0] : KafkaConfig.sBOOTSTRAP_SERVER;
        String topic = args.length > 1 ? args[1] : KafkaConfig.sTOPIC_PROFILE;
        String groupId = args.length > 2 ? args[2] : "group_flow";

        String[] mongoArgs = args.length > 3 ? Arrays.copyOfRange(args, 3, args.length) : null;
        MongoHelper mongoHelper = MongoHelper.getInstance(mongoArgs);

        KafkaConsumer<String, String> consumer = KafkaConfig.
                generateGeneralConsumer(bootstrapServer, groupId);

        consumer.subscribe(Collections.singleton(topic));

        boolean exit = false;
        while (!exit) {
            boolean newDataArrived = false;
            long startTime = System.currentTimeMillis();
            int dataCount = 0;

            for (ConsumerRecord<String, String> record : consumer.poll(10 * 1000L)) {
                newDataArrived = true;
                System.out.println(record.value());

                JSONObject jsonObject = (JSONObject) JSON.parse(record.value());
                // 清洗数据
//                if (!GeneralDataChecker.checkGeneral(jsonObject)) {
//                    System.out.println("....");
//                }

                // 存入monogo
                mongoHelper.insertOne(jsonObject);

                dataCount += 1;
            }

            if (newDataArrived) {
                long endTime = System.currentTimeMillis();
                System.out.println("Data count: " + dataCount);
                System.out.println(TimeUtils.getTimeInterval(startTime, endTime));
            }
        }

        consumer.close();
        mongoHelper.close();
    }
}
