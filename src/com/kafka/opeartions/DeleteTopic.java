package com.kafka.opeartions;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;

public class DeleteTopic {
	@SuppressWarnings("unused")
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
	    zkClient.deleteRecursive(ZkUtils.getTopicPath("topic-kafka-tutorial"));

	}

}
