package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Notification;
import com.sygneto.domain.Sector;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.service.NotificationService;
import com.sygneto.service.SectorService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sygneto.domain.Sector}.
 */
@RestController
@RequestMapping("/api")
public class NotificationResource {

    private final Logger log = LoggerFactory.getLogger(NotificationResource.class);

    private static final String ENTITY_NAME = "Notification";


    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationService notificationService;

    public NotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @Autowired
	CustomResponse customeResponce;
	 


    /**
     * {@code POST  /sectors} : Create a new notification.
     *
     * @param sector the notification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sector, or with status {@code 400 (Bad Request)} if the sector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notification")
    //@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object createSector(@RequestBody Notification notification) throws URISyntaxException {
        log.debug("REST request to save Notification : {}", notification);
      
        Notification result = notificationService.save(notification);
        return customeResponce.success("notification created", result);
        
    }

    /**
     * {@code PUT  /sectors} : Updates an existing notification.
     *
     * @param sector the notification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sector,
     * or with status {@code 400 (Bad Request)} if the sector is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sector couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notification")
    //@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object updateSector(@RequestBody Notification notification) throws URISyntaxException {
        log.debug("REST request to update notification : {}", notification);
        CustomResponse customResponse=new CustomResponse();
        Notification result = notificationService.save(notification);
        return customeResponce.success("notification updated", result);
       
    }

    /**
     * {@code GET  /sectors} : get all the notification.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notification in body.
     */
    @GetMapping("/notification")
   // @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object getAllSectors() {
        log.debug("REST request to get all notification");
        List<Notification>result= notificationService.findAll();
        if(result !=null && !result.isEmpty()) {
        	return customeResponce.success("notification found", result);
        
        }else {
        	return customeResponce.failure(404, "notification not found");
        
        }
		
    }

    /**
     * {@code GET  /notification/:id} : get the "id" notification.
     *
     * @param id the id of the notification to retrieve.
     * @return 
     */
    @GetMapping("/notification/{notification}")
   // @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object getSector(@PathVariable Long notification){
        log.debug("REST request to get notification : {}", notification);
        Optional<Notification> result = notificationService.findOne(notification);
        if(result !=null && result.isPresent()) {
        	return customeResponce.success("notification found", result);
        
        }else {
        	return customeResponce.failure(404, "notification not found");
        
        }
	
    }


    /**
     * {@code DELETE  /notification/:id} : delete the "id" notification.
     *
     * @param id the id of the notification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
	@DeleteMapping("/notification/{notification}")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object deleteSector(@PathVariable Long notification) {
		log.debug("REST request to delete notification : {}", notification);
		Optional<Notification> result = notificationService.findOne(notification);
		if (result != null && result.isPresent()) {

			try {
				notificationService.delete(notification);
				return customeResponce.success("notification deleted", null);
			} catch (Exception e) {
				return customeResponce.failure(400, "notification not deleted");
			}

		} else {
			return customeResponce.failure(404, "notification not found");

		}
	}
}
