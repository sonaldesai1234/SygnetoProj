package com.sygneto.service.impl;

import com.sygneto.service.PurchaseRequestService;
import com.sygneto.domain.PurchaseRequest;
import com.sygneto.repository.PurchaseRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PurchaseRequest}.
 */
@Service
@Transactional
public class PurchaseRequestServiceImpl implements PurchaseRequestService {

    private final Logger log = LoggerFactory.getLogger(PurchaseRequestServiceImpl.class);

    private final PurchaseRequestRepository purchaseRequestRepository;

    public PurchaseRequestServiceImpl(PurchaseRequestRepository purchaseRequestRepository) {
        this.purchaseRequestRepository = purchaseRequestRepository;
    }

    /**
     * Save a purchaseRequest.
     *
     * @param purchaseRequest the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PurchaseRequest save(PurchaseRequest purchaseRequest) {
        log.debug("Request to save PurchaseRequest : {}", purchaseRequest);
        return purchaseRequestRepository.save(purchaseRequest);
    }

    /**
     * Get all the purchaseRequests.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PurchaseRequest> findAll() {
        log.debug("Request to get all PurchaseRequests");
        return purchaseRequestRepository.findAll();
    }


    /**
     * Get one purchaseRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseRequest> findOne(Long id) {
        log.debug("Request to get PurchaseRequest : {}", id);
        return purchaseRequestRepository.findById(id);
    }

    /**
     * Delete the purchaseRequest by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseRequest : {}", id);
        purchaseRequestRepository.deleteById(id);
    }

	@Override
	public List<PurchaseRequest> saveAll(List<PurchaseRequest> purchaseRequest) {
		// TODO Auto-generated method stub
		return purchaseRequestRepository.saveAll(purchaseRequest);
	}

	@Override
	public List<PurchaseRequest> findAllByStatus(String status) {
		// TODO Auto-generated method stub
		return purchaseRequestRepository.findAllByStatus(status);
	}


	@Override
	public Long findCount() {
		// TODO Auto-generated method stub
		return purchaseRequestRepository.findCount();
	}

	@Override
	public List<PurchaseRequest> findBySalesOrderId(Long salesOrderId) {
		// TODO Auto-generated method stub
		return purchaseRequestRepository.findBySalesOrderId(salesOrderId);
	}

	@Override
	public ArrayList<Map<String, Object>> findTotalDaysPROpen() {
		// TODO Auto-generated method stub
		return purchaseRequestRepository.findTotalDaysPROpen();
	}

	@Override
	public List<PurchaseRequest> findByItemName(String itemName) {
		// TODO Auto-generated method stub
		return purchaseRequestRepository.findByItemName(itemName);
	}

	
	@Override
	public List<PurchaseRequest> findBySalesOrderIdOrItemId(Long salesOrderId, Long itemId) {
		// TODO Auto-generated method stub
		return purchaseRequestRepository.findBySalesOrderIdOrItemId(salesOrderId, itemId) ;
	}

	@Override
	public ArrayList<Map<String, Object>> countOpenClosePR() {
		// TODO Auto-generated method stub
		return purchaseRequestRepository.countOpenClosePR();
	}


	@Override
	public Object getActivePRByMnthWise() {
		// TODO Auto-generated method stub
		return purchaseRequestRepository. getActivePRByMnthWise();
	}

	@Override
	public Object getClosedPRByMnthWise() {
		// TODO Auto-generated method stub
		return purchaseRequestRepository.getClosedPRByMnthWise();
	}

	
}
