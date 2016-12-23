package com.kafka.opeartions;

import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;

public class CreateTopic {

	public static void main(String[] args) {
		String zookeeperConnect = "127.0.0.1:2181";
	    int sessionTimeoutMs = 10 * 1000;
	    int connectionTimeoutMs = 8 * 1000;
	    ZkClient zkClient = new ZkClient(
	            zookeeperConnect,
	            sessionTimeoutMs,
	            connectionTimeoutMs,
	            ZKStringSerializer$.MODULE$);
	    boolean isSecureKafkaCluster = false;
	    ZkUtils zkUtils = new ZkUtils(zkClient, new ZkConnection(zookeeperConnect), isSecureKafkaCluster);
	    String topic = "topic-tweet-data";
	    int partitions = 2;
	    int replication = 2;
	    Properties topicConfig = new Properties(); // add per-topic configurations settings here
	    AdminUtils.createTopic(zkUtils, topic, partitions, replication, topicConfig);
	    
	    zkClient.close();

	}

}
