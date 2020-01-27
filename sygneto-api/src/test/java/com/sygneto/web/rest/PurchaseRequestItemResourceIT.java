package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.PurchaseRequestItem;
import com.sygneto.repository.PurchaseRequestItemRepository;
import com.sygneto.service.PurchaseRequestItemService;
import com.sygneto.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sygneto.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PurchaseRequestItemResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class PurchaseRequestItemResourceIT {

    private static final String DEFAULT_N = "AAAAAAAAAA";
    private static final String UPDATED_N = "BBBBBBBBBB";

    @Autowired
    private PurchaseRequestItemRepository purchaseRequestItemRepository;

    @Autowired
    private PurchaseRequestItemService purchaseRequestItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPurchaseRequestItemMockMvc;

    private PurchaseRequestItem purchaseRequestItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseRequestItemResource purchaseRequestItemResource = new PurchaseRequestItemResource(purchaseRequestItemService);
        this.restPurchaseRequestItemMockMvc = MockMvcBuilders.standaloneSetup(purchaseRequestItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseRequestItem createEntity(EntityManager em) {
        PurchaseRequestItem purchaseRequestItem = new PurchaseRequestItem();
          
        return purchaseRequestItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseRequestItem createUpdatedEntity(EntityManager em) {
        PurchaseRequestItem purchaseRequestItem = new PurchaseRequestItem();
        
        return purchaseRequestItem;
    }

    @BeforeEach
    public void initTest() {
        purchaseRequestItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseRequestItem() throws Exception {
        int databaseSizeBeforeCreate = purchaseRequestItemRepository.findAll().size();

        // Create the PurchaseRequestItem
        restPurchaseRequestItemMockMvc.perform(post("/api/purchase-request-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseRequestItem)))
            .andExpect(status().isCreated());

        // Validate the PurchaseRequestItem in the database
        List<PurchaseRequestItem> purchaseRequestItemList = purchaseRequestItemRepository.findAll();
        assertThat(purchaseRequestItemList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseRequestItem testPurchaseRequestItem = purchaseRequestItemList.get(purchaseRequestItemList.size() - 1);
        assertThat(testPurchaseRequestItem.getPurchaseRequestItemId()).isEqualTo(DEFAULT_N);
    }

    @Test
    @Transactional
    public void createPurchaseRequestItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseRequestItemRepository.findAll().size();

        // Create the PurchaseRequestItem with an existing ID
        purchaseRequestItem.setPurchaseRequestItemId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseRequestItemMockMvc.perform(post("/api/purchase-request-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseRequestItem)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseRequestItem in the database
        List<PurchaseRequestItem> purchaseRequestItemList = purchaseRequestItemRepository.findAll();
        assertThat(purchaseRequestItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPurchaseRequestItems() throws Exception {
        // Initialize the database
        purchaseRequestItemRepository.saveAndFlush(purchaseRequestItem);

        // Get all the purchaseRequestItemList
        restPurchaseRequestItemMockMvc.perform(get("/api/purchase-request-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseRequestItem.getPurchaseRequestItemId().intValue())))
            .andExpect(jsonPath("$.[*].n").value(hasItem(DEFAULT_N)));
    }
    
    @Test
    @Transactional
    public void getPurchaseRequestItem() throws Exception {
        // Initialize the database
        purchaseRequestItemRepository.saveAndFlush(purchaseRequestItem);

        // Get the purchaseRequestItem
        restPurchaseRequestItemMockMvc.perform(get("/api/purchase-request-items/{id}", purchaseRequestItem.getPurchaseRequestItemId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseRequestItem.getPurchaseRequestItemId().intValue()))
            .andExpect(jsonPath("$.n").value(DEFAULT_N));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseRequestItem() throws Exception {
        // Get the purchaseRequestItem
        restPurchaseRequestItemMockMvc.perform(get("/api/purchase-request-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseRequestItem() throws Exception {
        // Initialize the database
        purchaseRequestItemService.save(purchaseRequestItem);

        int databaseSizeBeforeUpdate = purchaseRequestItemRepository.findAll().size();

        // Update the purchaseRequestItem
        PurchaseRequestItem updatedPurchaseRequestItem = purchaseRequestItemRepository.findById(purchaseRequestItem.getPurchaseRequestItemId()).get();
        // Disconnect from session so that the updates on updatedPurchaseRequestItem are not directly saved in db
        em.detach(updatedPurchaseRequestItem);
    

        restPurchaseRequestItemMockMvc.perform(put("/api/purchase-request-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchaseRequestItem)))
            .andExpect(status().isOk());

        // Validate the PurchaseRequestItem in the database
        List<PurchaseRequestItem> purchaseRequestItemList = purchaseRequestItemRepository.findAll();
        assertThat(purchaseRequestItemList).hasSize(databaseSizeBeforeUpdate);
        PurchaseRequestItem testPurchaseRequestItem = purchaseRequestItemList.get(purchaseRequestItemList.size() - 1);
        assertThat(testPurchaseRequestItem.getPurchaseRequestItemId()).isEqualTo(UPDATED_N);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseRequestItem() throws Exception {
        int databaseSizeBeforeUpdate = purchaseRequestItemRepository.findAll().size();

        // Create the PurchaseRequestItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseRequestItemMockMvc.perform(put("/api/purchase-request-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseRequestItem)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseRequestItem in the database
        List<PurchaseRequestItem> purchaseRequestItemList = purchaseRequestItemRepository.findAll();
        assertThat(purchaseRequestItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchaseRequestItem() throws Exception {
        // Initialize the database
        purchaseRequestItemService.save(purchaseRequestItem);

        int databaseSizeBeforeDelete = purchaseRequestItemRepository.findAll().size();

        // Delete the purchaseRequestItem
        restPurchaseRequestItemMockMvc.perform(delete("/api/purchase-request-items/{id}", purchaseRequestItem.getPurchaseRequestItemId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseRequestItem> purchaseRequestItemList = purchaseRequestItemRepository.findAll();
        assertThat(purchaseRequestItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
