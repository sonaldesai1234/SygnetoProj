package com.sygneto.service.impl;

import com.sygneto.service.ActivityMgtService;
import com.sygneto.domain.ActivityMgt;
import com.sygneto.repository.ActivityMgtRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ActivityMgt}.
 */
@Service
@Transactional
public class ActivityMgtServiceImpl implements ActivityMgtService {

    private final Logger log = LoggerFactory.getLogger(ActivityMgtServiceImpl.class);

    private final ActivityMgtRepository activityMgtRepository;

    public ActivityMgtServiceImpl(ActivityMgtRepository activityMgtRepository) {
        this.activityMgtRepository = activityMgtRepository;
    }

    /**
     * Save a taskMgt.
     *
     * @param taskMgt the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ActivityMgt save(ActivityMgt activityMgt) {
        log.debug("Request to save activityMgt : {}", activityMgt);
        return activityMgtRepository.save(activityMgt);
    }

    /**
     * Get all the taskMgt.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ActivityMgt> findAll() {
        log.debug("Request to get all activityMgt");
        return activityMgtRepository.findAll();
    }


    /**
     * Get one taskMgt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActivityMgt> findOne(Long id) {
        log.debug("Request to get activityMgt : {}", id);
        return activityMgtRepository.findById(id);
    }

    /**
     * Delete the activityMgt by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete activityMgt : {}", id);
        activityMgtRepository.deleteById(id);
    }

	@Override
	public List<ActivityMgt> findByReferenceId(Long id) {
		// TODO Auto-generated method stub
		return activityMgtRepository.findByReferenceId(id);
	}



}
