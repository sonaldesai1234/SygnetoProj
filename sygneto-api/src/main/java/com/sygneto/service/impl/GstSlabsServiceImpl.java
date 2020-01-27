package com.sygneto.service.impl;

import com.sygneto.service.GstSlabsService;
import com.sygneto.domain.GstSlabs;
import com.sygneto.repository.GstSlabsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link GstSlabs}.
 */
@Service
@Transactional
public class GstSlabsServiceImpl implements GstSlabsService {

    private final Logger log = LoggerFactory.getLogger(GstSlabsServiceImpl.class);

    private final GstSlabsRepository gstSlabsRepository;

    public GstSlabsServiceImpl(GstSlabsRepository gstSlabsRepository) {
        this.gstSlabsRepository = gstSlabsRepository;
    }

    /**
     * Save a gstSlabs.
     *
     * @param gstSlabs the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GstSlabs save(GstSlabs gstSlabs) {
        log.debug("Request to save GstSlabs : {}", gstSlabs);
        return gstSlabsRepository.save(gstSlabs);
    }

    /**
     * Get all the gstSlabs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<GstSlabs> findAll() {
        log.debug("Request to get all GstSlabs");
        return gstSlabsRepository.findAll();
    }


    /**
     * Get one gstSlabs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GstSlabs> findOne(Long id) {
        log.debug("Request to get GstSlabs : {}", id);
        return gstSlabsRepository.findById(id);
    }

    /**
     * Delete the gstSlabs by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GstSlabs : {}", id);
        gstSlabsRepository.deleteById(id);
    }
}
