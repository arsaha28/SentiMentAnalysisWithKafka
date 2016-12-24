package com.sentiment;

import java.util.List;
import java.util.Properties;

import com.kafka.model.Result;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class SentimentAnalysis {
	static Properties props = new Properties();
	static StanfordCoreNLP pipeline ;
	static{
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);

	}
	public static void analyze(String text,Result result) {
        Annotation annotation = pipeline.process(text);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            if(sentiment.equalsIgnoreCase("Negative")){
            	result.setNegativeSentiment(result.getNegativeSentiment()+1);
            }else if(sentiment.equalsIgnoreCase("Positive")){
            	result.setPositiveSentiment(result.getPositiveSentiment()+1);

            }else if(sentiment.equalsIgnoreCase("Neutral")){
            	result.setNeutralSentiment(result.getNeutralSentiment()+1);
            }
        }
	}

}
