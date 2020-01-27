package com.sygneto.service.impl;

import com.sygneto.service.SalesOrderService;
import com.sygneto.domain.SalesOrder;
import com.sygneto.repository.SalesOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SalesOrder}.
 */
@Service
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService {

    private final Logger log = LoggerFactory.getLogger(SalesOrderServiceImpl.class);

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    /**
     * Save a salesOrder.
     *
     * @param salesOrder the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SalesOrder save(SalesOrder salesOrder) {
        log.debug("Request to save SalesOrder : {}", salesOrder);
        return salesOrderRepository.save(salesOrder);
    }

    /**
     * Get all the salesOrders.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SalesOrder> findAll() {
        log.debug("Request to get all SalesOrders");
        return salesOrderRepository.findAll();
    }


    /**
     * Get one salesOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
  public Optional<SalesOrder> findOne(Long id) {
        log.debug("Request to get SalesOrder : {}", id);
        return salesOrderRepository.findById(id);
    }
    /**
     * Delete the salesOrder by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SalesOrder : {}", id);
        salesOrderRepository.deleteById(id);
    }

	@Override
	public List<SalesOrder> findByPartyId(Long id) {
		// TODO Auto-generated method stub
		return salesOrderRepository.findByPartyId(id);
	}

	@Override
	public SalesOrder FindBySalesOrderNumber(String salesOrderNumber, float f) {
		// TODO Auto-generated method stub
		return salesOrderRepository.FindBySalesOrderNumber( salesOrderNumber,  f);
	}

	@Override
	public SalesOrder findOneBySalesOrderId(Long salesOrderId) {
		// TODO Auto-generated method stub
		return salesOrderRepository.findOneBySalesOrderId(salesOrderId);
	}

	

	@Override
	public ArrayList<Map<String, Object>> findByIsDeliveredOpen(String status) {
		// TODO Auto-generated method stub
		return salesOrderRepository.findByIsDeliveredOpen( status);
	}

	
	@Override
	public Object getOpenSoByMnthWise() {
		// TODO Auto-generated method stub
		return salesOrderRepository.getOpenSoByMnthWise() ;
	}

	@Override
	public Object getClosedSoByMnthWise() {
		// TODO Auto-generated method stub
		return salesOrderRepository.getClosedSoByMnthWise();
	}

	@Override
	public Map<String, Object> findByStatus() {
		// TODO Auto-generated method stub
		return salesOrderRepository.findByStatus();
	}

	


	
	

}


	

	


	

	

	

