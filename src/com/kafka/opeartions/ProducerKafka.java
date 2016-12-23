package com.kafka.opeartions;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import com.kafka.constant.AppConstant;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class ProducerKafka {

	public static void main(String[] args) {
		long events = 2;
        Random rnd = new Random();
 
        Properties props = new Properties();
        props.put("metadata.broker.list", AppConstant.KAFKA_SERVER);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "com.kafka.partition.SimplePartitioner");
        props.put("request.required.acks", "1");
 
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);
 
        for (long nEvents = 0; nEvents < events; nEvents++) { 
               long runtime = new Date().getTime();  
               String ip = "192.168.2." + rnd.nextInt(255); 
               String msg = runtime + ",www.example.com," + ip; 
               KeyedMessage<String, String> data = new KeyedMessage<String, String>(AppConstant.TOPIC_NAME, ip, msg);
               producer.send(data);
        }
        producer.close();
    }

	}

