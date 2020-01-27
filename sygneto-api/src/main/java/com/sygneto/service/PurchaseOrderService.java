package com.sygneto.service;


import com.sygneto.domain.PurchaseOrder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link PurchaseOrderOld}.
 */
public interface PurchaseOrderService {

    /**
     * Save a purchaseOrder.
     *
     * @param purchaseOrder the entity to save.
     * @return the persisted entity.
     */
	PurchaseOrder save(PurchaseOrder purchaseOrder);

    /**
     * Get all the purchaseOrders.
     *
     * @return the list of entities.
     */
    List<PurchaseOrder> findAll();


    /**
     * Get the "id" purchaseOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseOrder> findOne(Long id);

    /**
     * Delete the "id" purchaseOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	List<PurchaseOrder> findLastMonthPO();

	ArrayList<Map<String, Object>> findByPartyNMonthWiseCount();

	List<PurchaseOrder> findByStatusIn(List<String> str);

	ArrayList<Map<String, Object>> findByPartyAvgDateDiff();

	List<PurchaseOrder> findByPurchaseOrderNumber(String purchaseOrderNumber);

	

	List<PurchaseOrder> findByPurchaseOrderItemsSalesOrderId(Long salesOrderId);


	

	List<PurchaseOrder> findByPurchaseOrderIdOrSalesOrderId(Long purchaseOrderId, Long salesOrderId);

	List<PurchaseOrder> findByPurchaseOrderItemsItemId(Long itemId);

	List<PurchaseOrder> findByPartyId(Long partyId);



	List<PurchaseOrder> findAllByCreatedDateBetween(Instant instantstart, Instant endinstant);

	Object getActivePoByMnthWise();

	Object getClosedPoByMnthWise();

	Map<String, Object> findByStatus();



}
