package com.sygneto.service;

import com.sygneto.domain.SalesOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link SalesOrder}.
 */
public interface SalesOrderService {

    /**
     * Save a salesOrder.
     *
     * @param salesOrder the entity to save.
     * @return the persisted entity.
     */
    SalesOrder save(SalesOrder salesOrder);

    /**
     * Get all the salesOrders.
     *
     * @return the list of entities.
     */
    List<SalesOrder> findAll();


    /**
     * Get the "id" salesOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SalesOrder> findOne(Long id);

    /**
     * Delete the "id" salesOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	List<SalesOrder> findByPartyId(Long id);

	SalesOrder FindBySalesOrderNumber(String salesOrderNumber, float f);

	

	SalesOrder findOneBySalesOrderId(Long salesOrderId);


	ArrayList<Map<String, Object>> findByIsDeliveredOpen(String status);


	Object getOpenSoByMnthWise();

	Object getClosedSoByMnthWise();

	Map<String, Object> findByStatus();


	






	

	
}
