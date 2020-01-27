package com.sygneto.service.impl;

import com.sygneto.service.ChildrenService;
import com.sygneto.domain.Children;
import com.sygneto.repository.ChildrenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Children.
 */
@Service
@Transactional
public class ChildrenServiceImpl implements ChildrenService {

	private final Logger log = LoggerFactory.getLogger(ChildrenServiceImpl.class);

	private final ChildrenRepository childrenRepository;

	public ChildrenServiceImpl(ChildrenRepository childrenRepository) {
		this.childrenRepository = childrenRepository;
	}

	/**
	 * Save a children.
	 *
	 * @param children
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public Children save(Children children) {
		log.debug("Request to save Children : {}", children);
		return childrenRepository.save(children);
	}

	/**
	 * Get all the children.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Children> findAll() {
		log.debug("Request to get all Children");
		return childrenRepository.findAll();
	}

	/**
	 * Get one children by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Children> findOne(Long id) {
		log.debug("Request to get Children : {}", id);
		return childrenRepository.findById(id);
	}

	/**
	 * Delete the children by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Children : {}", id);
		childrenRepository.deleteById(id);
	}

	@Override
	public void deleteByNextAction(long parseLong) {
		childrenRepository.deleteByNextAction(parseLong);

	}
}
