package com.ms.order.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ms.order.dto.OrderLineItemsDto;
import com.ms.order.dto.OrderNotiDto;
import com.ms.order.dto.request.OrderRequest;
import com.ms.order.dto.response.InventoryResponse;
import com.ms.order.entity.Order;
import com.ms.order.entity.OrderLineItems;
import com.ms.order.repository.OrderItemsRepository;
import com.ms.order.repository.OrderRepository;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class OrderService {
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderItemsRepository orderItemsRepository;

	@Autowired
	WebClient.Builder webClientBuilder;
	
	@Autowired
	ObservationRegistry observationRegistry;
	
	@Autowired
	KafKaProducerService kafKaProducerService;

	public String placeOrder(OrderRequest orderRequest) {
		Order order = new Order();

		String orderNumber = UUID.randomUUID().toString();
		order.setOrderNumber(orderNumber);

		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto)
				.toList();

		List<String> skuCodes = orderLineItems.stream().map(OrderLineItems::getSkuCode).toList();


        // Call Inventory Service, and place order if product is in
        // stock
        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
                this.observationRegistry);
        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");
        return inventoryServiceObservation.observe(() -> {
		
		InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
				.uri("http://ms-inventory-service/api/inventory",
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
				.retrieve().bodyToMono(InventoryResponse[].class).block();

		boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

		if (allProductsInStock) {
			Order orderEntity = orderRepository.saveAndFlush(order);
			Long orderId = orderEntity.getId();

			orderLineItems.stream().forEach(orderItem -> orderItem.setOrderId(orderId));
			orderItemsRepository.saveAllAndFlush(orderLineItems);
			
			kafKaProducerService.saveCreateUserLog(OrderNotiDto.builder().orderNumber(orderNumber).build());

			return "Order Placed";
		} else {
			throw new IllegalArgumentException("Product is not in stock, please try again later");
		}
		
        });

	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}
}
