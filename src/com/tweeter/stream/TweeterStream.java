package com.tweeter.stream;

import java.util.Properties;

import com.kafka.constant.AppConstant;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweeterStream {

	public static void main(String[] args) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(AppConstant.OAUTH_CONSUMERKEY)
				.setOAuthConsumerSecret(AppConstant.OAUTH_CONSUMERSECRET)
				.setOAuthAccessToken(AppConstant.OAUTH_ACCESSTOKEN)
				.setOAuthAccessTokenSecret(AppConstant.OAUTH_ACCESSTOKEN_SECRET);
		
		Properties props = new Properties();
        props.put("metadata.broker.list", AppConstant.KAFKA_SERVER);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "com.kafka.partition.SimplePartitioner");
        props.put("request.required.acks", "1");
 
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);
        
		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		FilterQuery tweetFilterQuery = new FilterQuery();  
		tweetFilterQuery.track(new String[]{AppConstant.FILTER_TEXT});
		tweetFilterQuery.language(new String[]{AppConstant.FILTER_LANG}); 
		
	    StatusListener listener = new StatusListener(){
	    	Integer i = 1;
	        public void onStatus(Status status) {
	           	   String message = status.getUser().getName() + " : " + status.getText();
	           	   String key = String.valueOf(i+1);
	           	   System.out.println(message);
	               KeyedMessage<String, String> data = new KeyedMessage<String, String>(AppConstant.TOPIC_NAME,key, message);
	               producer.send(data);
	        }
	        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
	        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
	        public void onException(Exception ex) {
	            ex.printStackTrace();
	        }
			@Override
			public void onScrubGeo(long arg0, long arg1) {
				
				
			}
			@Override
			public void onStallWarning(StallWarning arg0) {
				
				
			}
	    };
	    twitterStream.addListener(listener);
	    twitterStream.filter(tweetFilterQuery);

	}

}
