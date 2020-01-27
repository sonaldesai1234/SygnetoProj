/*package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.PurchaseRequest;
import com.sygneto.repository.PurchaseRequestRepository;
import com.sygneto.service.PurchaseRequestService;
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

@SpringBootTest(classes = SygnetoApiApp.class)
public class PurchaseRequestResourceIT {

    @Autowired
    private PurchaseRequestRepository purchaseRequestRepository;

    @Autowired
    private PurchaseRequestService purchaseRequestService;

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

    private MockMvc restPurchaseRequestMockMvc;

    private PurchaseRequest purchaseRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseRequestResource purchaseRequestResource = new PurchaseRequestResource(purchaseRequestService);
        this.restPurchaseRequestMockMvc = MockMvcBuilders.standaloneSetup(purchaseRequestResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

  
    public static PurchaseRequest createEntity(EntityManager em) {
        PurchaseRequest purchaseRequest = new PurchaseRequest();
        return purchaseRequest;
    }
   
    public static PurchaseRequest createUpdatedEntity(EntityManager em) {
        PurchaseRequest purchaseRequest = new PurchaseRequest();
        return purchaseRequest;
    }

    @BeforeEach
    public void initTest() {
        purchaseRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseRequest() throws Exception {
        int databaseSizeBeforeCreate = purchaseRequestRepository.findAll().size();

        // Create the PurchaseRequest
        restPurchaseRequestMockMvc.perform(post("/api/purchase-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseRequest)))
            .andExpect(status().isCreated());

        // Validate the PurchaseRequest in the database
        List<PurchaseRequest> purchaseRequestList = purchaseRequestRepository.findAll();
        assertThat(purchaseRequestList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseRequest testPurchaseRequest = purchaseRequestList.get(purchaseRequestList.size() - 1);
    }

    @Test
    @Transactional
    public void createPurchaseRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseRequestRepository.findAll().size();

        // Create the PurchaseRequest with an existing ID
        purchaseRequest.setPurchaseRequestId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseRequestMockMvc.perform(post("/api/purchase-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseRequest)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseRequest in the database
        List<PurchaseRequest> purchaseRequestList = purchaseRequestRepository.findAll();
        assertThat(purchaseRequestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPurchaseRequests() throws Exception {
        // Initialize the database
        purchaseRequestRepository.saveAndFlush(purchaseRequest);

        // Get all the purchaseRequestList
        restPurchaseRequestMockMvc.perform(get("/api/purchase-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseRequest.getPurchaseRequestId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPurchaseRequest() throws Exception {
        // Initialize the database
        purchaseRequestRepository.saveAndFlush(purchaseRequest);

        // Get the purchaseRequest
        restPurchaseRequestMockMvc.perform(get("/api/purchase-requests/{id}", purchaseRequest.getPurchaseRequestId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseRequest.getPurchaseRequestId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseRequest() throws Exception {
        // Get the purchaseRequest
        restPurchaseRequestMockMvc.perform(get("/api/purchase-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseRequest() throws Exception {
        // Initialize the database
        purchaseRequestService.save(purchaseRequest);

        int databaseSizeBeforeUpdate = purchaseRequestRepository.findAll().size();

        // Update the purchaseRequest
        PurchaseRequest updatedPurchaseRequest = purchaseRequestRepository.findById(purchaseRequest.getPurchaseRequestId()).get();
        // Disconnect from session so that the updates on updatedPurchaseRequest are not directly saved in db
        em.detach(updatedPurchaseRequest);

        restPurchaseRequestMockMvc.perform(put("/api/purchase-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchaseRequest)))
            .andExpect(status().isOk());

        // Validate the PurchaseRequest in the database
        List<PurchaseRequest> purchaseRequestList = purchaseRequestRepository.findAll();
        assertThat(purchaseRequestList).hasSize(databaseSizeBeforeUpdate);
        PurchaseRequest testPurchaseRequest = purchaseRequestList.get(purchaseRequestList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseRequest() throws Exception {
        int databaseSizeBeforeUpdate = purchaseRequestRepository.findAll().size();

        // Create the PurchaseRequest

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseRequestMockMvc.perform(put("/api/purchase-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseRequest)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseRequest in the database
        List<PurchaseRequest> purchaseRequestList = purchaseRequestRepository.findAll();
        assertThat(purchaseRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchaseRequest() throws Exception {
        // Initialize the database
        purchaseRequestService.save(purchaseRequest);

        int databaseSizeBeforeDelete = purchaseRequestRepository.findAll().size();

        // Delete the purchaseRequest
        restPurchaseRequestMockMvc.perform(delete("/api/purchase-requests/{id}", purchaseRequest.getPurchaseRequestId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseRequest> purchaseRequestList = purchaseRequestRepository.findAll();
        assertThat(purchaseRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/