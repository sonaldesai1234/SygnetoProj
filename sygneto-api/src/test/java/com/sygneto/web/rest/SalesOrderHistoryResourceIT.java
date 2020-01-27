/*package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.SalesOrderHistory;
import com.sygneto.repository.SalesOrderHistoryRepository;
import com.sygneto.service.SalesOrderHistoryService;
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

*//**
 * Integration tests for the {@link SalesOrderHistoryResource} REST controller.
 *//*
@SpringBootTest(classes = SygnetoApiApp.class)
public class SalesOrderHistoryResourceIT {

    private static final String DEFAULT_N = "AAAAAAAAAA";
    private static final String UPDATED_N = "BBBBBBBBBB";

    @Autowired
    private SalesOrderHistoryRepository salesOrderHistoryRepository;

    @Autowired
    private SalesOrderHistoryService salesOrderHistoryService;

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

    private MockMvc restSalesOrderHistoryMockMvc;

    private SalesOrderHistory salesOrderHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalesOrderHistoryResource salesOrderHistoryResource = new SalesOrderHistoryResource(salesOrderHistoryService);
        this.restSalesOrderHistoryMockMvc = MockMvcBuilders.standaloneSetup(salesOrderHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    *//**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static SalesOrderHistory createEntity(EntityManager em) {
        
        return ;
    }
    *//**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static SalesOrderHistory createUpdatedEntity(EntityManager em) {
        SalesOrderHistory salesOrderHistory = new SalesOrderHistory()
            .n(UPDATED_N);
        return salesOrderHistory;
    }

    @BeforeEach
    public void initTest() {
        salesOrderHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesOrderHistory() throws Exception {
        int databaseSizeBeforeCreate = salesOrderHistoryRepository.findAll().size();

        // Create the SalesOrderHistory
        restSalesOrderHistoryMockMvc.perform(post("/api/sales-order-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderHistory)))
            .andExpect(status().isCreated());

        // Validate the SalesOrderHistory in the database
        List<SalesOrderHistory> salesOrderHistoryList = salesOrderHistoryRepository.findAll();
        assertThat(salesOrderHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrderHistory testSalesOrderHistory = salesOrderHistoryList.get(salesOrderHistoryList.size() - 1);
        assertThat(testSalesOrderHistory.getN()).isEqualTo(DEFAULT_N);
    }

    @Test
    @Transactional
    public void createSalesOrderHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesOrderHistoryRepository.findAll().size();

        // Create the SalesOrderHistory with an existing ID
        salesOrderHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesOrderHistoryMockMvc.perform(post("/api/sales-order-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderHistory)))
            .andExpect(status().isBadRequest());

        // Validate the SalesOrderHistory in the database
        List<SalesOrderHistory> salesOrderHistoryList = salesOrderHistoryRepository.findAll();
        assertThat(salesOrderHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSalesOrderHistories() throws Exception {
        // Initialize the database
        salesOrderHistoryRepository.saveAndFlush(salesOrderHistory);

        // Get all the salesOrderHistoryList
        restSalesOrderHistoryMockMvc.perform(get("/api/sales-order-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrderHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].n").value(hasItem(DEFAULT_N)));
    }
    
    @Test
    @Transactional
    public void getSalesOrderHistory() throws Exception {
        // Initialize the database
        salesOrderHistoryRepository.saveAndFlush(salesOrderHistory);

        // Get the salesOrderHistory
        restSalesOrderHistoryMockMvc.perform(get("/api/sales-order-histories/{id}", salesOrderHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salesOrderHistory.getId().intValue()))
            .andExpect(jsonPath("$.n").value(DEFAULT_N));
    }

    @Test
    @Transactional
    public void getNonExistingSalesOrderHistory() throws Exception {
        // Get the salesOrderHistory
        restSalesOrderHistoryMockMvc.perform(get("/api/sales-order-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesOrderHistory() throws Exception {
        // Initialize the database
        salesOrderHistoryService.save(salesOrderHistory);

        int databaseSizeBeforeUpdate = salesOrderHistoryRepository.findAll().size();

        // Update the salesOrderHistory
        SalesOrderHistory updatedSalesOrderHistory = salesOrderHistoryRepository.findById(salesOrderHistory.getId()).get();
        // Disconnect from session so that the updates on updatedSalesOrderHistory are not directly saved in db
        em.detach(updatedSalesOrderHistory);
        updatedSalesOrderHistory
            .n(UPDATED_N);

        restSalesOrderHistoryMockMvc.perform(put("/api/sales-order-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalesOrderHistory)))
            .andExpect(status().isOk());

        // Validate the SalesOrderHistory in the database
        List<SalesOrderHistory> salesOrderHistoryList = salesOrderHistoryRepository.findAll();
        assertThat(salesOrderHistoryList).hasSize(databaseSizeBeforeUpdate);
        SalesOrderHistory testSalesOrderHistory = salesOrderHistoryList.get(salesOrderHistoryList.size() - 1);
        assertThat(testSalesOrderHistory.getN()).isEqualTo(UPDATED_N);
    }

    @Test
    @Transactional
    public void updateNonExistingSalesOrderHistory() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderHistoryRepository.findAll().size();

        // Create the SalesOrderHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesOrderHistoryMockMvc.perform(put("/api/sales-order-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderHistory)))
            .andExpect(status().isBadRequest());

        // Validate the SalesOrderHistory in the database
        List<SalesOrderHistory> salesOrderHistoryList = salesOrderHistoryRepository.findAll();
        assertThat(salesOrderHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalesOrderHistory() throws Exception {
        // Initialize the database
        salesOrderHistoryService.save(salesOrderHistory);

        int databaseSizeBeforeDelete = salesOrderHistoryRepository.findAll().size();

        // Delete the salesOrderHistory
        restSalesOrderHistoryMockMvc.perform(delete("/api/sales-order-histories/{id}", salesOrderHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalesOrderHistory> salesOrderHistoryList = salesOrderHistoryRepository.findAll();
        assertThat(salesOrderHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/