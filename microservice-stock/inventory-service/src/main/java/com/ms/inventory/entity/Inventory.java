package com.ms.inventory.entity;

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
@Table(name = "`INVENTORY`")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`ID`")
	private Long id;
	@Column(name = "`SKU_CODE`")
	private String skuCode;
	@Column(name = "`QUANTITY`")
	private Integer quantity;

}
