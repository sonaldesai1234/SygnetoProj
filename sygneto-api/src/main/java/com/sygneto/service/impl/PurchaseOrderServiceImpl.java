package com.sygneto.service.impl;

import com.sygneto.service.PurchaseOrderService;
import com.sygneto.domain.PurchaseOrder;
import com.sygneto.repository.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PurchaseOrderOld}.
 */
@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

	@Override
	public PurchaseOrder save(PurchaseOrder purchaseOrder) {
		return purchaseOrderRepository.save(purchaseOrder);	
	}

	@Override
	public List<PurchaseOrder> findAll() {
		// TODO Auto-generated method stub
		return purchaseOrderRepository.findAll();
	}

	@Override
    @Transactional(readOnly = true)
	public Optional<PurchaseOrder> findOne(Long id) {
		// TODO Auto-generated method stub
		return purchaseOrderRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
        purchaseOrderRepository.deleteById(id);
		
	}

	@Override
	public List<PurchaseOrder> findLastMonthPO() {
		return purchaseOrderRepository.findLastMonthPO();
	}

	@Override
	public ArrayList<Map<String, Object>> findByPartyNMonthWiseCount() {
		return purchaseOrderRepository.findByPartyNMonthWiseCount();
	}

	@Override
	public List<PurchaseOrder> findByStatusIn(List<String> str) {
		return purchaseOrderRepository.findByStatusIn(str);
	}

	@Override
	public ArrayList<Map<String, Object>> findByPartyAvgDateDiff() {
		return purchaseOrderRepository.findByPartyAvgDateDiff();
	}

	@Override
	public List<PurchaseOrder> findByPurchaseOrderNumber(String purchaseOrderNumber) {
		// TODO Auto-generated method stub
		return purchaseOrderRepository.findByPurchaseOrderNumber(purchaseOrderNumber);
	}


/*
	@Override
	public List<PurchaseOrder> findByPurchaseOrderItemsSalesOrderId(String salesOrderId) {
		// TODO Auto-generated method stub
		return purchaseOrderRepository.findByPurchaseOrderItemsSalesOrderId(String salesOrderId);
	}
*/


	@Override
	public List<PurchaseOrder> findByPurchaseOrderItemsSalesOrderId(Long salesOrderId) {
		// TODO Auto-generated method stub
		return purchaseOrderRepository.findByPurchaseOrderItemsSalesOrderId(salesOrderId);
	}

	

	

	@Override
	public List<PurchaseOrder> findByPurchaseOrderIdOrSalesOrderId(Long purchaseOrderId, Long salesOrderId) {
		// TODO Auto-generated method stub
		return purchaseOrderRepository.findByPurchaseOrderIdOrSalesOrderId(purchaseOrderId, salesOrderId);
	}

	@Override
	public List<PurchaseOrder> findByPurchaseOrderItemsItemId(Long itemId) {
		// TODO Auto-generated method stub
		return purchaseOrderRepository.findByPurchaseOrderItemsItemId(itemId);
	}


	@Override
	public List<PurchaseOrder> findByPartyId(Long partyId) {
		// TODO Auto-generated method stub
		return purchaseOrderRepository.findByPartyId(partyId);
	}

	@Override
	public List<PurchaseOrder> findAllByCreatedDateBetween(Instant instantstart, Instant endinstant) {
		// TODO Auto-generated method stub
		return purchaseOrderRepository. findAllByCreatedDateBetween(instantstart, endinstant);
	}

	@Override
	public Object getActivePoByMnthWise() {
		// TODO Auto-generated method stub
		return purchaseOrderRepository.getActivePoByMnthWise();
	}

	@Override
	public Object getClosedPoByMnthWise() {
		// TODO Auto-generated method stub
		return purchaseOrderRepository.getClosedPoByMnthWise();
	}

	@Override
	public Map<String, Object> findByStatus() {
		// TODO Auto-generated method stub
		return purchaseOrderRepository.findByStatus() ;
	}




}
