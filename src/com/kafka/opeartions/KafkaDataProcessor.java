package com.kafka.opeartions;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.kafka.constant.AppConstant;
import com.kafka.model.Result;
import com.sentiment.SentimentAnalysis;

public class KafkaDataProcessor implements Runnable{
	
	private Result result;
	public KafkaDataProcessor(Result result){
		this.result = result;
	}
	
	@SuppressWarnings("all")

	public void processDataFromKafka() {
		String topic = "topic-tweet-data";
		// TODO remove the warning
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(setProperties());
		consumer.subscribe(Arrays.asList(topic));
		System.out.println("Subscribed to topic " + topic);
		int positiveSentiment = 0;
		int negativeSentiment = 0;
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				String text = record.value();
				System.out.println("Statement :" + text);
				SentimentAnalysis.analyze(text, result);
			}
			
		}
	}

	private Properties setProperties() {
		String group = "checkdata";
		Properties props = new Properties();
		props.put("bootstrap.servers", AppConstant.KAFKA_SERVER);
		props.put("group.id", group);
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		return props;
	}

	@Override
	public void run() {
		System.out.println("******************Kafka thread started**********************");
		processDataFromKafka();
	}
}
