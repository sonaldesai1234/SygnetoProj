package com.sygneto.service.impl;

import com.sygneto.service.BillOfMaterialService;
import com.sygneto.domain.BillOfMaterial;
import com.sygneto.repository.BillOfMaterialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link BillOfMaterialService}.
 */
@Service
@Transactional
public class BillOfMaterialImpl implements BillOfMaterialService {

    private final Logger log = LoggerFactory.getLogger(BillOfMaterialImpl.class);

    private final BillOfMaterialRepository billOfMaterialRepository;

    public BillOfMaterialImpl(BillOfMaterialRepository billOfMaterialRepository) {
        this.billOfMaterialRepository = billOfMaterialRepository;
    }

    /**
     * Save a billOfMaterial.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BillOfMaterial save(BillOfMaterial billOfMaterial) {
        log.debug("Request to save Employee : {}", billOfMaterial);
        return billOfMaterialRepository.save(billOfMaterial);
    }

    /**
     * Get all the BillOfMaterial.
     *
     * @return the list of entities.
     */
    @Override
    public List<BillOfMaterial> findAll() {
        log.debug("Request to get all billOfMaterial");
        return billOfMaterialRepository.findAll();
    }


    /**
     * Get one BillOfMaterial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<BillOfMaterial> findOne(Long id) {
        log.debug("Request to get billOfMaterial : {}", id);
        
        return billOfMaterialRepository.findById(id);
    }

    /**
     * Delete the BillOfMaterial by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete billOfMaterial : {}", id);
        billOfMaterialRepository.deleteById(id);
    }

	@Override
	public ArrayList<Map<String, String>> findAllByLetestVersion() {
		// TODO Auto-generated method stub
		return billOfMaterialRepository.findAllByLetestVersion();
	}

	@Override
	public BillOfMaterial findByProductNversion(Long long1, float f) {
		// TODO Auto-generated method stub
		return billOfMaterialRepository.findByProductNversion(long1, f);
	}

	@Override
	public List<BillOfMaterial> findByProductProductId(Long productId) {
		// TODO Auto-generated method stub
		return billOfMaterialRepository.findByProductProductId(productId);
	}


	/*@Override
	public List<BillOfMaterial> saveOrUpdate(List<BillOfMaterial> billOfMaterialList) {
		// TODO Auto-generated method stub
		List<BillOfMaterial> toReturn = new ArrayList<BillOfMaterial>();
		if (billOfMaterialList != null && billOfMaterialList.size()> 0) {
			for (BillOfMaterial billOfMaterialObj : billOfMaterialList) {
				Optional<BillOfMaterial> billOfMaterial = billOfMaterialRepository.findById(billOfMaterialObj.getProductBomId());
				if (billOfMaterial.isPresent()) {
					billOfMaterial.get().setQuantity(billOfMaterialObj.getQuantity());
				    billOfMaterialRepository.save(billOfMaterial.get());
				    toReturn.add(billOfMaterial.get());
				} else {
					billOfMaterialRepository.save(billOfMaterialObj);
					toReturn.add(billOfMaterialObj);
				}
			}
		}
		
		return toReturn;
	}*/
}
