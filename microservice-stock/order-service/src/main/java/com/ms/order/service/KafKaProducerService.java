package com.ms.order.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.ms.order.dto.OrderNotiDto;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafKaProducerService {
	@Value(value = "${kafka.order.topic-name}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, OrderNotiDto> kafkaTemplate;

	@Autowired
	private ObservationRegistry observationRegistry;

	public void saveCreateUserLog(OrderNotiDto req) {
		log.info("Order Placed Event Received, Sending OrderPlacedEvent to notificationTopic: {}",
				req.getOrderNumber());
		try {
			Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {

				CompletableFuture<SendResult<String, OrderNotiDto>> future = (CompletableFuture<SendResult<String, OrderNotiDto>>) this.kafkaTemplate
						.send(topicName, req);

				return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
			}).get();
		} catch (InterruptedException | ExecutionException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Error while sending message to Kafka", e);
		}

//		@SuppressWarnings("unchecked")
//		ListenableFuture<SendResult<String, OrderNotiDto>> future = (ListenableFuture<SendResult<String, OrderNotiDto>>) this.kafkaTemplate
//				.send(topicName, req);
//
//		log.info("Send msg success => " + future.toString());
	}
}
