package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.SalesOrder;
import com.sygneto.repository.SalesOrderRepository;
import com.sygneto.service.SalesOrderService;
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
 * Integration tests for the {@link SalesOrderResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class SalesOrderResourceIT {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private SalesOrderService salesOrderService;

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

    private MockMvc restSalesOrderMockMvc;

    private SalesOrder salesOrder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalesOrderResource salesOrderResource = new SalesOrderResource(salesOrderService);
        this.restSalesOrderMockMvc = MockMvcBuilders.standaloneSetup(salesOrderResource)
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
    public static SalesOrder createEntity(EntityManager em) {
        SalesOrder salesOrder = new SalesOrder();
        return salesOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesOrder createUpdatedEntity(EntityManager em) {
        SalesOrder salesOrder = new SalesOrder();
        return salesOrder;
    }

    @BeforeEach
    public void initTest() {
        salesOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesOrder() throws Exception {
        int databaseSizeBeforeCreate = salesOrderRepository.findAll().size();

        // Create the SalesOrder
        restSalesOrderMockMvc.perform(post("/api/sales-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrder)))
            .andExpect(status().isCreated());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrder testSalesOrder = salesOrderList.get(salesOrderList.size() - 1);
    }

    @Test
    @Transactional
    public void createSalesOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesOrderRepository.findAll().size();

        // Create the SalesOrder with an existing ID
        salesOrder.setSalesOrderId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesOrderMockMvc.perform(post("/api/sales-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrder)))
            .andExpect(status().isBadRequest());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSalesOrders() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get all the salesOrderList
        restSalesOrderMockMvc.perform(get("/api/sales-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrder.getSalesOrderId().intValue())));
    }
    
    @Test
    @Transactional
    public void getSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get the salesOrder
        restSalesOrderMockMvc.perform(get("/api/sales-orders/{id}", salesOrder.getSalesOrderId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salesOrder.getSalesOrderId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSalesOrder() throws Exception {
        // Get the salesOrder
        restSalesOrderMockMvc.perform(get("/api/sales-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesOrder() throws Exception {
        // Initialize the database
        salesOrderService.save(salesOrder);

        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();

        // Update the salesOrder
        SalesOrder updatedSalesOrder = salesOrderRepository.findById(salesOrder.getSalesOrderId()).get();
        // Disconnect from session so that the updates on updatedSalesOrder are not directly saved in db
        em.detach(updatedSalesOrder);

        restSalesOrderMockMvc.perform(put("/api/sales-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalesOrder)))
            .andExpect(status().isOk());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);
        SalesOrder testSalesOrder = salesOrderList.get(salesOrderList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSalesOrder() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();

        // Create the SalesOrder

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesOrderMockMvc.perform(put("/api/sales-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrder)))
            .andExpect(status().isBadRequest());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalesOrder() throws Exception {
        // Initialize the database
        salesOrderService.save(salesOrder);

        int databaseSizeBeforeDelete = salesOrderRepository.findAll().size();

        // Delete the salesOrder
        restSalesOrderMockMvc.perform(delete("/api/sales-orders/{id}", salesOrder.getSalesOrderId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesOrder.class);
        SalesOrder salesOrder1 = new SalesOrder();
        salesOrder1.setSalesOrderId(1L);
        SalesOrder salesOrder2 = new SalesOrder();
        salesOrder2.setSalesOrderId(salesOrder1.getSalesOrderId());
        assertThat(salesOrder1).isEqualTo(salesOrder2);
        salesOrder2.setSalesOrderId(2L);
        assertThat(salesOrder1).isNotEqualTo(salesOrder2);
        salesOrder1.setSalesOrderId(null);
        assertThat(salesOrder1).isNotEqualTo(salesOrder2);
    }
}
