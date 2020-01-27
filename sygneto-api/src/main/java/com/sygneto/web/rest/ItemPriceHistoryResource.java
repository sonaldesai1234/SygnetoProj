package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.ItemPriceHistory;
import com.sygneto.service.ItemPriceHistoryService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sygneto.domain.ItemPriceHistory}.
 */
@RestController
@RequestMapping("/api")
public class ItemPriceHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ItemPriceHistoryResource.class);

    private static final String ENTITY_NAME = "itemPriceHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemPriceHistoryService itemPriceHistoryService;

    public ItemPriceHistoryResource(ItemPriceHistoryService itemPriceHistoryService) {
        this.itemPriceHistoryService = itemPriceHistoryService;
    }
    
    @Autowired
  	CustomResponse customeResponce;

    /**
     * {@code POST  /item-price-histories} : Create a new itemPriceHistory.
     *
     * @param itemPriceHistory the itemPriceHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemPriceHistory, or with status {@code 400 (Bad Request)} if the itemPriceHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
   @ApiIgnore
    @PostMapping("/itemPriceHistories")
    public ResponseEntity<ItemPriceHistory> createItemPriceHistory(@RequestBody ItemPriceHistory itemPriceHistory) throws URISyntaxException {
        log.debug("REST request to save ItemPriceHistory : {}", itemPriceHistory);
        if (itemPriceHistory.getItemId() != null) {
            throw new BadRequestAlertException("A new itemPriceHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPriceHistory result = itemPriceHistoryService.save(itemPriceHistory);
        return ResponseEntity.created(new URI("/api/item-price-histories/" + result.getItemId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getItemId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-price-histories} : Updates an existing itemPriceHistory.
     *
     * @param itemPriceHistory the itemPriceHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemPriceHistory,
     * or with status {@code 400 (Bad Request)} if the itemPriceHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemPriceHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
   
    @PutMapping("/itemPriceHistories")
    public ResponseEntity<ItemPriceHistory> updateItemPriceHistory(@RequestBody ItemPriceHistory itemPriceHistory) throws URISyntaxException {
        log.debug("REST request to update ItemPriceHistory : {}", itemPriceHistory);
        if (itemPriceHistory.getItemId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemPriceHistory result = itemPriceHistoryService.save(itemPriceHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemPriceHistory.getItemId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-price-histories} : get all the itemPriceHistories.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemPriceHistories in body.
     */
    
    @ApiOperation(value="Get all  Item History")
    @GetMapping("/itemPriceHistories")
    public List<ItemPriceHistory> getAllItemPriceHistories() {
        log.debug("REST request to get all ItemPriceHistories");
        return itemPriceHistoryService.findAll();
    }

    /**
     * {@code GET  /item-price-histories/:id} : get the "id" itemPriceHistory.
     *
     * @param id the id of the itemPriceHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemPriceHistory, or with status {@code 404 (Not Found)}.
     */
    @ApiIgnore
    @GetMapping("/itemPriceHistories/{id}")
    public ResponseEntity<ItemPriceHistory> getItemPriceHistory(@PathVariable Long id) {
        log.debug("REST request to get ItemPriceHistory : {}", id);
        Optional<ItemPriceHistory> itemPriceHistory = itemPriceHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemPriceHistory);
    }
    @ApiOperation(value="Get all  Item History as per Party Id")
    @GetMapping("/itemPriceHistorieByPartyId/{partyId}")
    public ResponseEntity<ItemPriceHistory> getItemPriceHistoryByPartyId(@PathVariable Long partyId) {
        log.debug("REST request to get itemPriceHistorieByPartyId : {}", partyId);
        Optional<ItemPriceHistory> itemPriceHistory = itemPriceHistoryService.findByPartyId(partyId);
        return ResponseUtil.wrapOrNotFound(itemPriceHistory);
    }

    @ApiOperation(value="Get all  Item History as per Item Id")
    @GetMapping("/itemPriceHistorieByItemId/{itemId}")
    public ResponseEntity<ItemPriceHistory> getItemPriceHistoryByItemId(@PathVariable Long itemId) {
        log.debug("REST request to get itemPriceHistorieByPartyId : {}", itemId);
        Optional<ItemPriceHistory> itemPriceHistory = itemPriceHistoryService.findByItemId(itemId);
        return ResponseUtil.wrapOrNotFound(itemPriceHistory);
    }
 
    @ApiOperation(value="Get all  Item History as per Item Id and Party Id")
    @GetMapping("/itemPriceHistorieByItemIdNPartyId/{itemId}/{partyId}")
    public Object getItemPriceHistoryByItemId(@PathVariable Long itemId, @PathVariable Long partyId) {
        log.debug("REST request to get itemPriceHistorieByItemIdNPartyId : {}", itemId, partyId);
        List<ItemPriceHistory> result = itemPriceHistoryService.findByItemIdAndPartyId(itemId, partyId);
        if(result !=null || !result.isEmpty()) {
        	return customeResponce.success("Sector found", result);
        
        }else {
        	return customeResponce.failure(404, "Sector not found");
        
        }
    }
    
   
    /**
     * {@code DELETE  /item-price-histories/:id} : delete the "id" itemPriceHistory.
     *
     * @param id the id of the itemPriceHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @ApiOperation(value="Delete Item History as per Item History Id")
    @DeleteMapping("/itemPriceHistories/{id}")
    public ResponseEntity<Void> deleteItemPriceHistory(@PathVariable Long id) {
        log.debug("REST request to delete ItemPriceHistory : {}", id);
        itemPriceHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
