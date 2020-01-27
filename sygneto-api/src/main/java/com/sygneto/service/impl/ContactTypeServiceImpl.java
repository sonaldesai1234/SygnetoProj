package com.sygneto.service.impl;

import com.sygneto.service.ContactTypeService;
import com.sygneto.domain.ContactType;
import com.sygneto.repository.ContactTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ContactType}.
 */
@Service
@Transactional
public class ContactTypeServiceImpl implements ContactTypeService {

    private final Logger log = LoggerFactory.getLogger(ContactTypeServiceImpl.class);

    private final ContactTypeRepository contactTypeRepository;

    public ContactTypeServiceImpl(ContactTypeRepository contactTypeRepository) {
        this.contactTypeRepository = contactTypeRepository;
    }

    /**
     * Save a contactType.
     *
     * @param contactType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ContactType save(ContactType contactType) {
        log.debug("Request to save ContactType : {}", contactType);
        return contactTypeRepository.save(contactType);
    }

    /**
     * Get all the contactTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContactType> findAll() {
        log.debug("Request to get all ContactTypes");
        return contactTypeRepository.findAll();
    }



	@Override
	public void delete(String contactType) {
		  log.debug("Request to delete ContactType : {}", contactType);
	        contactTypeRepository.deleteByContactType(contactType);
		
	}
	@Override
	public Optional<ContactType> findOne(String contactType) {
		 log.debug("Request to get ContactType : {}", contactType);
	        return contactTypeRepository.findByContactType(contactType);
	}

	@Override
	public Optional<ContactType> findOneByContactType(String contactType) {
		// TODO Auto-generated method stub
		return contactTypeRepository.findOneByContactType(contactType);
	}


}
