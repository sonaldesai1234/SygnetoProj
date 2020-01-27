package com.sygneto.service;

import com.sygneto.domain.PurchaseRequest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link PurchaseRequest}.
 */
public interface PurchaseRequestService {

    /**
     * Save a purchaseRequest.
     *
     * @param purchaseRequest the entity to save.
     * @return the persisted entity.
     */
    PurchaseRequest save(PurchaseRequest purchaseRequest);

    /**
     * Get all the purchaseRequests.
     *
     * @return the list of entities.
     */
    List<PurchaseRequest> findAll();


    /**
     * Get the "id" purchaseRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseRequest> findOne(Long id);

    /**
     * Delete the "id" purchaseRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	List<PurchaseRequest> saveAll(List<PurchaseRequest> purchaseRequest);

	List<PurchaseRequest> findAllByStatus(String status);

	Long findCount();

	List<PurchaseRequest> findBySalesOrderId(Long salesOrderId);

	ArrayList<Map<String, Object>> findTotalDaysPROpen();

	List<PurchaseRequest> findByItemName(String itemName);


	List<PurchaseRequest> findBySalesOrderIdOrItemId(Long salesOrderId, Long itemId);

	ArrayList<Map<String, Object>> countOpenClosePR();


	Object getActivePRByMnthWise();

	Object getClosedPRByMnthWise();



	
	


	

	

}
