package com.sygneto.service.impl;

import com.sygneto.service.AddressTypeService;
import com.sygneto.domain.AddressType;
import com.sygneto.repository.AddressTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AddressType}.
 */
@Service
@Transactional
public class AddressTypeServiceImpl implements AddressTypeService {

    private final Logger log = LoggerFactory.getLogger(AddressTypeServiceImpl.class);

    private final AddressTypeRepository addressTypeRepository;

    public AddressTypeServiceImpl(AddressTypeRepository addressTypeRepository) {
        this.addressTypeRepository = addressTypeRepository;
    }

    /**
     * Save a addressType.
     *
     * @param addressType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AddressType save(AddressType addressType) {
        log.debug("Request to save AddressType : {}", addressType);
        return addressTypeRepository.save(addressType);
    }

    /**
     * Get all the addressTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddressType> findAll() {
        log.debug("Request to get all AddressTypes");
        return addressTypeRepository.findAll();
    }


    /**
     * Get one addressType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AddressType> findOne(Long id) {
        log.debug("Request to get AddressType : {}", id);
        return addressTypeRepository.findById(id);
    }

    /**
     * Delete the addressType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AddressType : {}", id);
        addressTypeRepository.deleteById(id);
    }
}
