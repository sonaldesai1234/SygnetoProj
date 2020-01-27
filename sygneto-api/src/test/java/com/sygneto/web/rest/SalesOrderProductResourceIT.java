package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.SalesOrderProduct;
import com.sygneto.repository.SalesOrderProductRepository;
import com.sygneto.service.SalesOrderProductService;
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
 * Integration tests for the {@link SalesOrderProductResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class SalesOrderProductResourceIT {

    @Autowired
    private SalesOrderProductRepository salesOrderProductRepository;

    @Autowired
    private SalesOrderProductService salesOrderProductService;

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

    private MockMvc restSalesOrderProductMockMvc;

    private SalesOrderProduct salesOrderProduct;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalesOrderProductResource salesOrderProductResource = new SalesOrderProductResource(salesOrderProductService);
        this.restSalesOrderProductMockMvc = MockMvcBuilders.standaloneSetup(salesOrderProductResource)
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
    public static SalesOrderProduct createEntity(EntityManager em) {
        SalesOrderProduct salesOrderProduct = new SalesOrderProduct();
        return salesOrderProduct;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesOrderProduct createUpdatedEntity(EntityManager em) {
        SalesOrderProduct salesOrderProduct = new SalesOrderProduct();
        return salesOrderProduct;
    }

    @BeforeEach
    public void initTest() {
        salesOrderProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesOrderProduct() throws Exception {
        int databaseSizeBeforeCreate = salesOrderProductRepository.findAll().size();

        // Create the SalesOrderProduct
        restSalesOrderProductMockMvc.perform(post("/api/sales-order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderProduct)))
            .andExpect(status().isCreated());

        // Validate the SalesOrderProduct in the database
        List<SalesOrderProduct> salesOrderProductList = salesOrderProductRepository.findAll();
        assertThat(salesOrderProductList).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrderProduct testSalesOrderProduct = salesOrderProductList.get(salesOrderProductList.size() - 1);
    }

    @Test
    @Transactional
    public void createSalesOrderProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesOrderProductRepository.findAll().size();

        // Create the SalesOrderProduct with an existing ID
        salesOrderProduct.setSalesOrderProductId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesOrderProductMockMvc.perform(post("/api/sales-order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderProduct)))
            .andExpect(status().isBadRequest());

        // Validate the SalesOrderProduct in the database
        List<SalesOrderProduct> salesOrderProductList = salesOrderProductRepository.findAll();
        assertThat(salesOrderProductList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSalesOrderProducts() throws Exception {
        // Initialize the database
        salesOrderProductRepository.saveAndFlush(salesOrderProduct);

        // Get all the salesOrderProductList
        restSalesOrderProductMockMvc.perform(get("/api/sales-order-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrderProduct.getSalesOrderProductId().intValue())));
    }
    
    @Test
    @Transactional
    public void getSalesOrderProduct() throws Exception {
        // Initialize the database
        salesOrderProductRepository.saveAndFlush(salesOrderProduct);

        // Get the salesOrderProduct
        restSalesOrderProductMockMvc.perform(get("/api/sales-order-products/{id}", salesOrderProduct.getSalesOrderProductId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salesOrderProduct.getSalesOrderProductId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSalesOrderProduct() throws Exception {
        // Get the salesOrderProduct
        restSalesOrderProductMockMvc.perform(get("/api/sales-order-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesOrderProduct() throws Exception {
        // Initialize the database
        salesOrderProductService.save(salesOrderProduct);

        int databaseSizeBeforeUpdate = salesOrderProductRepository.findAll().size();

        // Update the salesOrderProduct
        SalesOrderProduct updatedSalesOrderProduct = salesOrderProductRepository.findById(salesOrderProduct.getSalesOrderProductId()).get();
        // Disconnect from session so that the updates on updatedSalesOrderProduct are not directly saved in db
        em.detach(updatedSalesOrderProduct);

        restSalesOrderProductMockMvc.perform(put("/api/sales-order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalesOrderProduct)))
            .andExpect(status().isOk());

        // Validate the SalesOrderProduct in the database
        List<SalesOrderProduct> salesOrderProductList = salesOrderProductRepository.findAll();
        assertThat(salesOrderProductList).hasSize(databaseSizeBeforeUpdate);
        SalesOrderProduct testSalesOrderProduct = salesOrderProductList.get(salesOrderProductList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSalesOrderProduct() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderProductRepository.findAll().size();

        // Create the SalesOrderProduct

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesOrderProductMockMvc.perform(put("/api/sales-order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrderProduct)))
            .andExpect(status().isBadRequest());

        // Validate the SalesOrderProduct in the database
        List<SalesOrderProduct> salesOrderProductList = salesOrderProductRepository.findAll();
        assertThat(salesOrderProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalesOrderProduct() throws Exception {
        // Initialize the database
        salesOrderProductService.save(salesOrderProduct);

        int databaseSizeBeforeDelete = salesOrderProductRepository.findAll().size();

        // Delete the salesOrderProduct
        restSalesOrderProductMockMvc.perform(delete("/api/sales-order-products/{id}", salesOrderProduct.getSalesOrderProductId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalesOrderProduct> salesOrderProductList = salesOrderProductRepository.findAll();
        assertThat(salesOrderProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesOrderProduct.class);
        SalesOrderProduct salesOrderProduct1 = new SalesOrderProduct();
        salesOrderProduct1.setSalesOrderProductId(1L);
        SalesOrderProduct salesOrderProduct2 = new SalesOrderProduct();
        salesOrderProduct2.setSalesOrderProductId(salesOrderProduct1.getSalesOrderProductId());
        assertThat(salesOrderProduct1).isEqualTo(salesOrderProduct2);
        salesOrderProduct2.setSalesOrderProductId(2L);
        assertThat(salesOrderProduct1).isNotEqualTo(salesOrderProduct2);
        salesOrderProduct1.setSalesOrderProductId(null);
        assertThat(salesOrderProduct1).isNotEqualTo(salesOrderProduct2);
    }
}
