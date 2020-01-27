package com.sygneto.service.impl;

import com.sygneto.service.PartyTypeService;
import com.sygneto.domain.PartyType;
import com.sygneto.repository.PartyTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PartyType}.
 */
@Service
@Transactional
public class PartyTypeServiceImpl implements PartyTypeService {

    private final Logger log = LoggerFactory.getLogger(PartyTypeServiceImpl.class);

    private final PartyTypeRepository partyTypeRepository;

    public PartyTypeServiceImpl(PartyTypeRepository partyTypeRepository) {
        this.partyTypeRepository = partyTypeRepository;
    }

    /**
     * Save a partyType.
     *
     * @param partyType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PartyType save(PartyType partyType) {
        log.debug("Request to save PartyType : {}", partyType);
        return partyTypeRepository.save(partyType);
    }

    /**
     * Get all the partyTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PartyType> findAll() {
        log.debug("Request to get all PartyTypes");
        return partyTypeRepository.findAll();
    }


    /**
     * Get one partyType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartyType> findOne(Long id) {
        log.debug("Request to get PartyType : {}", id);
        return partyTypeRepository.findById(id);
    }

    /**
     * Delete the partyType by id.
     *
     * @param id the id of the entity.
     */
    
	@Override
	public Optional<PartyType> findOneByPartyType(String partyType) {
		// TODO Auto-generated method stub
		return partyTypeRepository.findOneByPartyType(partyType);
	}

	@Override
	public void delete(String partyType) {
		 partyTypeRepository.deleteByPartyType(partyType);

	}

	@Override
	public Long countByPartyType() {
		// TODO Auto-generated method stub
		return partyTypeRepository.countByPartyType();
	}

	
}
