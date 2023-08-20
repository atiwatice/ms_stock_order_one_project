package com.ms.order.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ms.order.dto.OrderNotiDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafKaConsumerService {
	@KafkaListener(topics = "${kafka.order.topic-name}", groupId = "${kafka.order.group-id}", containerFactory = "orderKafkaListenerContainerFactory")
	public void consume(OrderNotiDto req) {
		log.info(String.format("User created -> %s", req));
	}
}
