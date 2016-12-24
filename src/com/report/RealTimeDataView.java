package com.report;

import com.kafka.model.Result;
import com.kafka.opeartions.KafkaDataProcessor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RealTimeDataView extends Application {

	final static String POSITIVE = "Positive Sentiment";
	final static String NEGATIVE = "Negative Sentiment";
	final static String NEUTRAL = "Neutral Sentiment";

	@Override
	public void start(Stage stage) {
		Result result = new Result();
		Runnable r = new KafkaDataProcessor(result);
		Thread thread = new Thread(r,"Kafka data Thread");
		thread.start();
		stage.setTitle("Sentiment Analysis");
		Scene scene = new Scene(createChart(result), 800, 600);
		stage.setScene(scene);
		stage.show();

	}

	protected BarChart<String, Number> createChart(final Result result) {
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("Real time sentiment analysis");
		xAxis.setLabel("Sentiment");
		yAxis.setLabel("Value");

		XYChart.Series<String, Number> series1 = new XYChart.Series();
		series1.setName("Real Time View");
		Timeline Updater = new Timeline(new KeyFrame(Duration.millis(10000), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Positive Sentiment : " + result.getPositiveSentiment() + "Negative Sentiment :"
						+ result.getNegativeSentiment() + "Neutral Sentiment :" + result.getNeutralSentiment());
				series1.getData().add(new XYChart.Data(POSITIVE, result.getPositiveSentiment()));
				series1.getData().add(new XYChart.Data(NEGATIVE, result.getNegativeSentiment()));
				series1.getData().add(new XYChart.Data(NEUTRAL, result.getNeutralSentiment()));

			}

		}));
		Updater.setCycleCount(Timeline.INDEFINITE);
		Updater.play();
		bc.getData().add(series1);
		return bc;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
