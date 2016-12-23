package com.kafka.opeartions;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.kafka.constant.AppConstant;

public class ConsumerGroup {
	@SuppressWarnings("all")
	public static void main(String[] args) {
		String topic = "topic-tweet-data";
		//TODO remove the warning
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(setProperties());
		consumer.subscribe(Arrays.asList(topic));
		System.out.println("Subscribed to topic " + topic);

		int positiveSentiment = 0;
		int negativeSentiment = 0;
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				System.out.println("Data :" + record.value());
				if (record.value().contains("good")) {
					positiveSentiment = positiveSentiment + 1;
				}
				if (record.value().contains("bad") ||record.value().contains("inhuman")||record.value().contains("unpatriotic") ) {
					negativeSentiment = negativeSentiment + 1;
				}
				System.out.println("Postive Sentiment : " + positiveSentiment + " Negative Sentiment : " + negativeSentiment);
			}
		}

	}

	private static Properties setProperties() {		
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
}
