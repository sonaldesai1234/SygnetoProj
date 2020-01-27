package com.sygneto.service.impl;

import com.sygneto.service.TaskMgtService;
import com.sygneto.domain.TaskMgt;
import com.sygneto.repository.TaskMgtRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskMgt}.
 */
@Service
@Transactional
public class TaskMgtServiceImpl implements TaskMgtService {

    private final Logger log = LoggerFactory.getLogger(TaskMgtServiceImpl.class);

    private final TaskMgtRepository taskMgtRepository;

    public TaskMgtServiceImpl(TaskMgtRepository taskMgtRepository) {
        this.taskMgtRepository = taskMgtRepository;
    }

    /**
     * Save a taskMgt.
     *
     * @param taskMgt the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TaskMgt save(TaskMgt taskMgt) {
        log.debug("Request to save TaskMgt : {}", taskMgt);
        return taskMgtRepository.save(taskMgt);
    }

    /**
     * Get all the taskMgt.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TaskMgt> findAll() {
        log.debug("Request to get all taskMgt");
        return taskMgtRepository.findAll();
    }


    /**
     * Get one taskMgt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TaskMgt> findOne(Long id) {
        log.debug("Request to get TaskMgt : {}", id);
        return taskMgtRepository.findById(id);
    }

    /**
     * Delete the TaskMgt by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskMgt : {}", id);
        taskMgtRepository.deleteById(id);
    }
}
