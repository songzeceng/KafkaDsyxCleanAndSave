package com.profile.producer;

import com.profile.util.HdfsUtils;
import com.profile.util.KafkaConfig;
import com.profile.util.TextUtils;
import com.profile.util.TimeUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

public class BehaviorFlowProducerBootstrap {
    public static void main(String[] args) throws IOException {
        String bootstrapServer = args.length > 0 ? args[0] : KafkaConfig.sBOOTSTRAP_SERVER;
        String dataFileURL = args.length > 1 ? args[1] : HdfsUtils.sBEHAVIOR_FLOW_SOURCE_URL;
        String topic = args.length > 2 ? args[2] : KafkaConfig.sTOPIC_PROFILE;

        KafkaProducer<String, String> producer = KafkaConfig.generateGeneralProducer(bootstrapServer);

        FSDataInputStream fsIn = FileSystem.get(URI.create(dataFileURL), new Configuration())
                .open(new Path(dataFileURL));
        BufferedReader bf = new BufferedReader(new InputStreamReader(fsIn));
        String line = null;

        while (!TextUtils.isEmpty((line = bf.readLine()))) {
            String currentTime = TimeUtils.format(System.currentTimeMillis(),
                    "yyyyMMddHHmmssSSS");
            String first = line.substring(0, line.lastIndexOf("}"));
            final String product = first + ", sendTime:\"" + currentTime + "\"}";

            producer.send(new ProducerRecord<String, String>(topic, product), new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println(product + " sent finished");
                }
            });
        }

        producer.close();
        bf.close();
        fsIn.close();
    }
}
