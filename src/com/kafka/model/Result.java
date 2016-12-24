package com.kafka.model;

public class Result {
	
	private int positiveSentiment = 0;
	private int negativeSentiment = 0;
	private int neutralSentiment = 0;
	
	
	public int getPositiveSentiment() {
		return positiveSentiment;
	}
	public void setPositiveSentiment(int positiveSentiment) {
		this.positiveSentiment = positiveSentiment;
	}
	public int getNegativeSentiment() {
		return negativeSentiment;
	}
	public void setNegativeSentiment(int negativeSentiment) {
		this.negativeSentiment = negativeSentiment;
	}
	public int getNeutralSentiment() {
		return neutralSentiment;
	}
	public void setNeutralSentiment(int neutralSentiment) {
		this.neutralSentiment = neutralSentiment;
	}

}
