package com.sygneto.service.impl;

import com.sygneto.service.MandatoryAddressService;
import com.sygneto.domain.MandatoryAddress;
import com.sygneto.repository.MandatoryAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MandatoryAddress}.
 */
@Service
@Transactional
public class MandatoryAddressServiceImpl implements MandatoryAddressService {

    private final Logger log = LoggerFactory.getLogger(MandatoryAddressServiceImpl.class);

    private final MandatoryAddressRepository mandatoryAddressRepository;

    public MandatoryAddressServiceImpl(MandatoryAddressRepository mandatoryAddressRepository) {
        this.mandatoryAddressRepository = mandatoryAddressRepository;
    }

    /**
     * Save a mandatoryAddress.
     *
     * @param mandatoryAddress the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MandatoryAddress save(MandatoryAddress mandatoryAddress) {
        log.debug("Request to save MandatoryAddress : {}", mandatoryAddress);
        return mandatoryAddressRepository.save(mandatoryAddress);
    }

    /**
     * Get all the mandatoryAddresses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MandatoryAddress> findAll() {
        log.debug("Request to get all MandatoryAddresses");
        return mandatoryAddressRepository.findAll();
    }


    /**
     * Get one mandatoryAddress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MandatoryAddress> findOne(Long id) {
        log.debug("Request to get MandatoryAddress : {}", id);
        return mandatoryAddressRepository.findById(id);
    }

    /**
     * Delete the mandatoryAddress by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MandatoryAddress : {}", id);
        mandatoryAddressRepository.deleteById(id);
    }
}
