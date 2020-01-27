package com.sygneto.service.impl;

import com.sygneto.service.PartyService;
import com.sygneto.domain.Party;
import com.sygneto.repository.PartyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Party}.
 */
@Service
@Transactional
public class PartyServiceImpl implements PartyService {

    private final Logger log = LoggerFactory.getLogger(PartyServiceImpl.class);

    private final PartyRepository partyRepository;

    public PartyServiceImpl(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    /**
     * Save a party.
     *
     * @param party the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Party save(Party party) {
        log.debug("Request to save Party : {}", party);
        return partyRepository.save(party);
    }

    /**
     * Get all the parties.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Party> findAll() {
        log.debug("Request to get all Parties");
        return partyRepository.findAll();
    }


    /**
     * Get one party by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Party> findOne(Long id) {
        log.debug("Request to get Party : {}", id);
        return partyRepository.findById(id);
    }

    /**
     * Delete the party by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Party : {}", id);
        partyRepository.deleteById(id);
    }

	@Override
	public Optional<Party> findOneByPartyId(Long partyId) {
		
		return partyRepository. findOneByPartyId(partyId);
	}

	@Override
	public Optional<Party> findOneByPartyName(String partyName) {
		return partyRepository.findOneByPartyName(partyName);
	}

	@Override
	public List<Party> findAllByIsCustomer(String isCustomer) {
		return partyRepository.findAllByIsCustomer(isCustomer);
	}

	@Override
	public Long countByPartyId() {
		
		return partyRepository.countByPartyId();
	}

	@Override
	public Long countByIsSupplier() {
		// TODO Auto-generated method stub
		return partyRepository.countByIsSupplier();
	}

	@Override
	public Long countByIsCustomer() {
		// TODO Auto-generated method stub
		return partyRepository.countByIsCustomer();
	}

	
}
