/*package com.sygneto.service.impl;

import com.sygneto.service.EmpAddressService;
import com.sygneto.domain.MandatoryAddress;
import com.sygneto.repository.EmpAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

*//**
 * Service Implementation for managing {@link MandatoryAddress}.
 *//*
@Service
@Transactional
public class EmpAddressServiceImpl implements EmpAddressService {

    private final Logger log = LoggerFactory.getLogger(EmpAddressServiceImpl.class);

    private final EmpAddressRepository empAddressRepository;

    public EmpAddressServiceImpl(EmpAddressRepository empAddressRepository) {
        this.empAddressRepository = empAddressRepository;
    }

    *//**
     * Save a empAddress.
     *
     * @param mandatoryAddress the entity to save.
     * @return the persisted entity.
     *//*
    @Override
    public MandatoryAddress save(MandatoryAddress mandatoryAddress) {
        log.debug("Request to save MandatoryAddress : {}", mandatoryAddress);
        return empAddressRepository.save(mandatoryAddress);
    }

    *//**
     * Get all the empAddresses.
     *
     * @return the list of entities.
     *//*
    @Override
    @Transactional(readOnly = true)
    public List<MandatoryAddress> findAll() {
        log.debug("Request to get all EmpAddresses");
        return empAddressRepository.findAll();
    }


    *//**
     * Get one empAddress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     *//*
    @Override
    @Transactional(readOnly = true)
    public Optional<MandatoryAddress> findOne(Long id) {
        log.debug("Request to get MandatoryAddress : {}", id);
        return empAddressRepository.findById(id);
    }

    *//**
     * Delete the empAddress by id.
     *
     * @param id the id of the entity.
     *//*
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MandatoryAddress : {}", id);
        empAddressRepository.deleteById(id);
    }
}
*/