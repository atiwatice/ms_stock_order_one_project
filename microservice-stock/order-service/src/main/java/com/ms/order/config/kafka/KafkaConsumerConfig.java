package com.ms.order.config.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.ms.order.dto.OrderNotiDto;

@Configuration
public class KafkaConsumerConfig {
	@Value(value = "${kafka.order.address}")
	private String bootstrapAddress;

	@Value(value = "${kafka.order.group-id}")
	private String groupId;

	public ConsumerFactory<String, OrderNotiDto> orderConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
				new JsonDeserializer<>(OrderNotiDto.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, OrderNotiDto> orderKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, OrderNotiDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(orderConsumerFactory());
		return factory;
	}
}
