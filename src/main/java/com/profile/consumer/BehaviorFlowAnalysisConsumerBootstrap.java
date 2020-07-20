package com.profile.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.profile.util.KafkaConfig;
import com.profile.util.TimeUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.serializer.KryoSerializer;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.*;

import java.util.*;

public class BehaviorFlowAnalysisConsumerBootstrap {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf()
                .setMaster("local[2]")
                .setAppName("ActionConsumer")
                .set("spark.serializer", KryoSerializer.class.getCanonicalName())
                .registerKryoClasses(new Class[]{ConsumerRecord.class})
                .set("spark.kryoserializer.buffer.max", "512m");

        // 优雅的关闭，避免在处理数据时yarn kill导致kafka数据重复消费和数据丢失
        conf.set("spark.streaming.stopGracefullyOnShutdown", "true");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaStreamingContext ssc = new JavaStreamingContext(sc, Durations.seconds(1));

        Set<String> topicsSet = Collections.singleton(KafkaConfig.sTOPIC_PROFILE);
        //kafka相关参数，必要！缺了会报错
        Map<String, Object> kafkaParams = new HashMap<String, Object>();
        kafkaParams.put("bootstrap.servers", KafkaConfig.sBOOTSTRAP_SERVER);
        kafkaParams.put("group.id", "group_analysis");
        kafkaParams.put("key.deserializer", StringDeserializer.class.getCanonicalName());
        kafkaParams.put("value.deserializer", StringDeserializer.class.getCanonicalName());
        kafkaParams.put("enable.auto.commit", true);

        final JavaInputDStream<ConsumerRecord<Object, Object>> kafkaStream =
                KafkaUtils.createDirectStream(
                        ssc,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topicsSet, kafkaParams)
                );

        kafkaStream.foreachRDD(new VoidFunction2<JavaRDD<ConsumerRecord<Object, Object>>,
                        Time>() {
            public void call(JavaRDD<ConsumerRecord<Object, Object>> consumerRecordJavaRDD,
                             Time time) throws Exception {
                if (consumerRecordJavaRDD.rdd().count() > 0) {
                    OffsetRange[] offsetRanges = ((HasOffsetRanges)consumerRecordJavaRDD
                            .rdd()).offsetRanges();

                    final long recordCount = consumerRecordJavaRDD.count();
                    List<ConsumerRecord<Object, Object>> records = consumerRecordJavaRDD.take((int) recordCount);

                    long startTime = System.currentTimeMillis();
                    for (ConsumerRecord<Object, Object> record : records) {
                        JSONObject obj = JSON.parseObject(record.value().toString());
                        System.out.println(obj);
                    }
                    long endTime = System.currentTimeMillis();
                    System.out.println(TimeUtils.getTimeInterval(startTime, endTime));

                    ((CanCommitOffsets)kafkaStream.inputDStream()).commitAsync(offsetRanges);
                }
            }
        });

        ssc.start();
        ssc.awaitTermination();
        ssc.close();
    }
}
