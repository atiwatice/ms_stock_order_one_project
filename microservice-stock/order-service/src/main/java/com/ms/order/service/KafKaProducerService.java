package com.ms.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.ListenableFuture;
import com.ms.order.dto.OrderNotiDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafKaProducerService {
	@Value(value = "${kafka.order.topic-name}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, OrderNotiDto> kafkaTemplate;

	public void saveCreateUserLog(OrderNotiDto req) {
		@SuppressWarnings("unchecked")
		ListenableFuture<SendResult<String, OrderNotiDto>> future = (ListenableFuture<SendResult<String, OrderNotiDto>>) this.kafkaTemplate
				.send(topicName, req);

		log.info("Send msg success => " + future.toString());
	}
}
