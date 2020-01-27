package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Item;
import com.sygneto.domain.ItemQuotation;
import com.sygneto.domain.Party;
import com.sygneto.domain.Quotation;
import com.sygneto.domain.SalesOrderBOMItem;
import com.sygneto.service.ItemQuotationService;
import com.sygneto.service.ItemService;
import com.sygneto.service.PartyService;
import com.sygneto.service.QuotationService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sygneto.domain.Quotation}.
 */
@RestController
@RequestMapping("/api")
public class QuotationResource {



    private final Logger log = LoggerFactory.getLogger(QuotationResource.class);

    @Autowired
    private  PartyService partyService;
    private static final String ENTITY_NAME = "quotation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuotationService quotationService;

 
    @Autowired
    CustomResponse customResponse;
    
    @Autowired
    ItemQuotationService itemQuotationService;
    @Autowired
    ItemService itemService;
    public QuotationResource(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    /**
     * {@code POST  /quotations} : Create a new quotation.
     *
     * @param quotation the quotation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quotation, or with status {@code 400 (Bad Request)} if the quotation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quotations")
    public Object createQuotation(@RequestBody Quotation quotation) throws URISyntaxException {
        log.debug("REST request to save Quotation : {}", quotation);
        if (quotation.getQuotationId() != null) {
            throw new BadRequestAlertException("A new quotation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        //rentDays= diff bet 2 dates
        Instant startDate = quotation.getQuoteStartDate(); // Set start date
        Instant endDate   = quotation.getQuoteEndDate();
        
        //ChronoUnit(LocalDate dateBefore, LocalDate dateAfter)
        
        long rentDays = ChronoUnit.DAYS.between(endDate,startDate);
        
        quotation.setRentalDays(rentDays);

        for(ItemQuotation itemQuotation : quotation.getItemQuotation())
        {
        	
	
        	float totalPrice = itemQuotation.getQuantity() * itemQuotation.getUnitPrice();
        	itemQuotation.setTotalPrice(totalPrice);
        	
        	
        	float discount=itemQuotation.getDiscount();
        	
        	float discountedPrice= (totalPrice/100 * discount);
        	
        	itemQuotation.setDiscountedPrice(discountedPrice);
        	
        	float total=totalPrice - discountedPrice;
        	itemQuotation.setTotal(total);
        	
        	
        	float gstSlabs=itemQuotation.getGstSlabs().getGstSlabs();
        	float price=itemQuotation.getTotal();
        	
     	    float igst= (price*gstSlabs)/100;
     	    
     	    itemQuotation.setIgst(igst);	    
     	    itemQuotation.setSgst((float)igst/2);
     	    itemQuotation.setCgst((float)igst/2);
     	    itemQuotation.setSubTotal(igst+price);

        	   
        	}

        float sum = 0;
        for(ItemQuotation itemQuotation : quotation.getItemQuotation())
        {
        	sum+=itemQuotation.getSubTotal();
    		
        }
        quotation.setGrandTotal(sum);
        Quotation result = quotationService.save(quotation);
        
        
        return customResponse.success("Quotation created", result);

}



	/**
     * {@code PUT  /quotations} : Updates an existing quotation.
     *
     * @param quotation the quotation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotation,
     * or with status {@code 400 (Bad Request)} if the quotation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quotation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quotations")
    public ResponseEntity<Quotation> updateQuotation(@RequestBody Quotation quotation) throws URISyntaxException {
        log.debug("REST request to update Quotation : {}", quotation);
        if (quotation.getQuotationId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Quotation result = quotationService.save(quotation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, quotation.getQuotationId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /quotations} : get all the quotations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quotations in body.
     */
    @GetMapping("/quotations")
    public List<Quotation> getAllQuotations() {
        log.debug("REST request to get all Quotations");
        return quotationService.findAll();
    }
   
    /**
     * {@code GET  /quotations/:id} : get the "id" quotation.
     *
     * @param id the id of the quotation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quotation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quotations/{id}")
    public ResponseEntity<Quotation> getQuotation(@PathVariable Long id) {
        log.debug("REST request to get Quotation : {}", id);
        
        Optional<Quotation> quotation = quotationService.findOne(id);
		Optional<Party> result = partyService.findOne(quotation.get().getParty().getPartyId());
		quotation.get().setParty(result.get());
        
		System.out.println("&&&"+quotation);
        return ResponseUtil.wrapOrNotFound(quotation);
    }

    /**
     * {@code DELETE  /quotations/:id} : delete the "id" quotation.
     *
     * @param id the id of the quotation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quotations/{id}")
    public ResponseEntity<Void> deleteQuotation(@PathVariable Long id) {
        log.debug("REST request to delete Quotation : {}", id);
        quotationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
