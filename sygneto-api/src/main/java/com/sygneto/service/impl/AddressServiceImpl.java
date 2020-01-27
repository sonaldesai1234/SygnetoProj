package com.sygneto.service.impl;

import com.sygneto.service.AddressService;
import com.sygneto.domain.Address;
import com.sygneto.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link adress}.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepo;

    public AddressServiceImpl(AddressRepository addressRepo) {
        this.addressRepo = addressRepo;
    }

    /**
     * Save a adress.
     *
     * @param adress the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Address save(Address adress) {
        log.debug("Request to save adress : {}", adress);
        return addressRepo.save(adress);
    }

    /**
     * Get all the adresss.
     *
     * @return the list of entities.
     */
    @Override
    
    public List<Address> findAll() {
        log.debug("Request to get all adresss");
        return addressRepo.findAll();
    }


    /**
     * Get one adress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Address> findOne(Long id) {
        log.debug("Request to get adress : {}", id);
        return addressRepo.findById(id);
    }

    /**
     * Delete the adress by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete adress : {}", id);
        addressRepo.deleteById(id);
    }

	@Override
	public List<Address> findAllByPartyId(Long partyId) {
		log.debug("Request to get adress : {}", partyId);
        return addressRepo.findAllByPartyId(partyId);
	}

	@Override
	public Optional<Address> findOneByPartId(Object party) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
