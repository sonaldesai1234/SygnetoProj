package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.ProductMRPHistory;
import com.sygneto.repository.ProductMRPHistoryRepository;
import com.sygneto.service.ProductMRPHistoryService;
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
 * Integration tests for the {@link ProductMRPHistoryResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class ProductMRPHistoryResourceIT {

    @Autowired
    private ProductMRPHistoryRepository productMRPHistoryRepository;

    @Autowired
    private ProductMRPHistoryService productMRPHistoryService;

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

    private MockMvc restProductMRPHistoryMockMvc;

    private ProductMRPHistory productMRPHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductMRPHistoryResource productMRPHistoryResource = new ProductMRPHistoryResource(productMRPHistoryService);
        this.restProductMRPHistoryMockMvc = MockMvcBuilders.standaloneSetup(productMRPHistoryResource)
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
    public static ProductMRPHistory createEntity(EntityManager em) {
        ProductMRPHistory productMRPHistory = new ProductMRPHistory();
        return productMRPHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMRPHistory createUpdatedEntity(EntityManager em) {
        ProductMRPHistory productMRPHistory = new ProductMRPHistory();
        return productMRPHistory;
    }

    @BeforeEach
    public void initTest() {
        productMRPHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductMRPHistory() throws Exception {
        int databaseSizeBeforeCreate = productMRPHistoryRepository.findAll().size();

        // Create the ProductMRPHistory
        restProductMRPHistoryMockMvc.perform(post("/api/product-mrp-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productMRPHistory)))
            .andExpect(status().isCreated());

        // Validate the ProductMRPHistory in the database
        List<ProductMRPHistory> productMRPHistoryList = productMRPHistoryRepository.findAll();
        assertThat(productMRPHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductMRPHistory testProductMRPHistory = productMRPHistoryList.get(productMRPHistoryList.size() - 1);
    }

    @Test
    @Transactional
    public void createProductMRPHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productMRPHistoryRepository.findAll().size();

        // Create the ProductMRPHistory with an existing ID
        productMRPHistory.setProductMrpHistoryId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMRPHistoryMockMvc.perform(post("/api/product-mrp-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productMRPHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ProductMRPHistory in the database
        List<ProductMRPHistory> productMRPHistoryList = productMRPHistoryRepository.findAll();
        assertThat(productMRPHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductMRPHistories() throws Exception {
        // Initialize the database
        productMRPHistoryRepository.saveAndFlush(productMRPHistory);

        // Get all the productMRPHistoryList
        restProductMRPHistoryMockMvc.perform(get("/api/product-mrp-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productMRPHistory.getProductMrpHistoryId().intValue())));
    }
    
    @Test
    @Transactional
    public void getProductMRPHistory() throws Exception {
        // Initialize the database
        productMRPHistoryRepository.saveAndFlush(productMRPHistory);

        // Get the productMRPHistory
        restProductMRPHistoryMockMvc.perform(get("/api/product-mrp-histories/{id}", productMRPHistory.getProductMrpHistoryId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productMRPHistory.getProductMrpHistoryId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductMRPHistory() throws Exception {
        // Get the productMRPHistory
        restProductMRPHistoryMockMvc.perform(get("/api/product-mrp-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductMRPHistory() throws Exception {
        // Initialize the database
        productMRPHistoryService.save(productMRPHistory);

        int databaseSizeBeforeUpdate = productMRPHistoryRepository.findAll().size();

        // Update the productMRPHistory
        ProductMRPHistory updatedProductMRPHistory = productMRPHistoryRepository.findById(productMRPHistory.getProductMrpHistoryId()).get();
        // Disconnect from session so that the updates on updatedProductMRPHistory are not directly saved in db
        em.detach(updatedProductMRPHistory);

        restProductMRPHistoryMockMvc.perform(put("/api/product-mrp-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductMRPHistory)))
            .andExpect(status().isOk());

        // Validate the ProductMRPHistory in the database
        List<ProductMRPHistory> productMRPHistoryList = productMRPHistoryRepository.findAll();
        assertThat(productMRPHistoryList).hasSize(databaseSizeBeforeUpdate);
        ProductMRPHistory testProductMRPHistory = productMRPHistoryList.get(productMRPHistoryList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingProductMRPHistory() throws Exception {
        int databaseSizeBeforeUpdate = productMRPHistoryRepository.findAll().size();

        // Create the ProductMRPHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMRPHistoryMockMvc.perform(put("/api/product-mrp-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productMRPHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ProductMRPHistory in the database
        List<ProductMRPHistory> productMRPHistoryList = productMRPHistoryRepository.findAll();
        assertThat(productMRPHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductMRPHistory() throws Exception {
        // Initialize the database
        productMRPHistoryService.save(productMRPHistory);

        int databaseSizeBeforeDelete = productMRPHistoryRepository.findAll().size();

        // Delete the productMRPHistory
        restProductMRPHistoryMockMvc.perform(delete("/api/product-mrp-histories/{id}", productMRPHistory.getProductMrpHistoryId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductMRPHistory> productMRPHistoryList = productMRPHistoryRepository.findAll();
        assertThat(productMRPHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
