package com.sygneto.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PurchaseItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_item_id", unique = true, nullable = false)
    private Long purchaseItemId;

	 @Column(name = "item_id")
	 private Long itemId;
   
	 @Column(name = "item_UOM")
	 private String unitOfMeasurement;
	 
	 @Column(name = "item_code")
	 private String itemCode;
	 
	 @Column(name = "quantity")
    private Long quantity;
	 
	 @Column(name = "item_name")
	 private String itemName;
	 
	 @JsonIgnore
	 @ManyToOne
	 @JoinColumn(name="purchase_item_id", nullable=false)
	 private PurchaseRequest purchaseRequest;
	 
	 
	 

}
