package com.sygneto.service.impl;

import com.sygneto.service.NotificationService;
import com.sygneto.domain.Notification;
import com.sygneto.repository.ContactTypeRepository;
import com.sygneto.repository.NotificationRepository;

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
public class NotificationServiceImpl implements NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository contactTypeRepository;

    public NotificationServiceImpl(NotificationRepository contactTypeRepository) {
        this.contactTypeRepository = contactTypeRepository;
    }

    /**
     * Save a NotificationRepository.
     *
     * @param contactType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Notification save(Notification contactType) {
        log.debug("Request to save Notification : {}", contactType);
        return contactTypeRepository.save(contactType);
    }

    /**
     * Get all the contactTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Notification> findAll() {
        log.debug("Request to get all Notification");
        return contactTypeRepository.findAll();
    }

	@Override
	public Optional<Notification> findOne(Long id) {
		// TODO Auto-generated method stub
		return contactTypeRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		contactTypeRepository.deleteById(id);
	}


}
