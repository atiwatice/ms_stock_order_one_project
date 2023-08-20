package com.ms.order.dto.request;

import java.util.List;

import com.ms.order.dto.OrderLineItemsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
	private List<OrderLineItemsDto> orderLineItemsDtoList;
}
