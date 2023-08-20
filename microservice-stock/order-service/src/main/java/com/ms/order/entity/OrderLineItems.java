package com.ms.order.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "`ORDER_ITEM`")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`ID`")
	private Long id;
	
	@Column(name = "`ORDER_ID`")
	private Long orderId;
	
	@Column(name = "`SKU_CODE`")
	private String skuCode;
	
	@Column(name = "`PRICE`")
	private BigDecimal price;
	
	@Column(name = "`QUANTITY`")
	private Integer quantity;

}
