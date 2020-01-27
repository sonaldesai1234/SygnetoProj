package com.sygneto.service.impl;

import com.sygneto.service.SectorService;
import com.sygneto.domain.Sector;
import com.sygneto.repository.SectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Sector}.
 */
@Service
@Transactional
public class SectorServiceImpl implements SectorService {

    private final Logger log = LoggerFactory.getLogger(SectorServiceImpl.class);

    private final SectorRepository sectorRepository;

    public SectorServiceImpl(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    /**
     * Save a sector.
     *
     * @param sector the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Sector save(Sector sector) {
        log.debug("Request to save Sector : {}", sector);
        return sectorRepository.save(sector);
    }

    /**
     * Get all the sectors.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Sector> findAll() {
        log.debug("Request to get all Sectors");
        return sectorRepository.findAll();
    }


    /**
     * Get one sector by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Sector> findOne(String id) {
        log.debug("Request to get Sector : {}", id);
        return sectorRepository.findById(id);
    }

    /**
     * Delete the sector by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Sector : {}", id);
        sectorRepository.deleteById(id);
    }

	

}
