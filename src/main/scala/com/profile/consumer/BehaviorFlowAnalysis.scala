//package com.profile.consumer
//
//import com.profile.util.KafkaConfig
//import org.apache.spark.SparkConf
//import org.apache.spark.streaming.kafka.KafkaUtils
//import org.apache.spark.streaming.{Seconds, StreamingContext}
//
//object BehaviorFlowAnalysis {
//  def main(args: Array[String]): Unit = {
//    val conf = new SparkConf()
//    conf.setMaster("local[2]")
//    conf.setAppName("BehaviorAnalysis")
//    val ssc = new StreamingContext(conf, Seconds(10)) // 10秒处理一次输入
//
//    val kafkaInput = KafkaUtils.createStream(ssc, "192.168.57.141:2181",
//      "group2", List((KafkaConfig.sTOPIC_PROFILE, 1)).toMap)
//    kafkaInput.map(x => {
//      print(x)
//    }).print()
//
//    ssc.start()
//    ssc.awaitTermination()
//  }
//}
