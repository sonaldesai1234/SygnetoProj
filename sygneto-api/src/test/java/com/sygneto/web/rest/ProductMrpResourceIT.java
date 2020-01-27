package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.ProductMrp;
import com.sygneto.repository.ProductMrpRepository;
import com.sygneto.service.ProductMrpService;
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
 * Integration tests for the {@link ProductMrpResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class ProductMrpResourceIT {

    private static final String DEFAULT_N = "AAAAAAAAAA";
    private static final String UPDATED_N = "BBBBBBBBBB";

    @Autowired
    private ProductMrpRepository productMrpRepository;

    @Autowired
    private ProductMrpService productMrpService;

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

    private MockMvc restProductMrpMockMvc;

    private ProductMrp productMrp;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductMrpResource productMrpResource = new ProductMrpResource(productMrpService);
        this.restProductMrpMockMvc = MockMvcBuilders.standaloneSetup(productMrpResource)
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
    public static ProductMrp createEntity(EntityManager em) {
        ProductMrp productMrp = new ProductMrp();
         return productMrp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMrp createUpdatedEntity(EntityManager em) {
        ProductMrp productMrp = new ProductMrp();
        return productMrp;
    }

    @BeforeEach
    public void initTest() {
        productMrp = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductMrp() throws Exception {
        int databaseSizeBeforeCreate = productMrpRepository.findAll().size();

        // Create the ProductMrp
        restProductMrpMockMvc.perform(post("/api/product-mrps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productMrp)))
            .andExpect(status().isCreated());

        // Validate the ProductMrp in the database
        List<ProductMrp> productMrpList = productMrpRepository.findAll();
        assertThat(productMrpList).hasSize(databaseSizeBeforeCreate + 1);
        ProductMrp testProductMrp = productMrpList.get(productMrpList.size() - 1);
        assertThat(testProductMrp.getProductMrpId()).isEqualTo(DEFAULT_N);
    }

    @Test
    @Transactional
    public void createProductMrpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productMrpRepository.findAll().size();

        // Create the ProductMrp with an existing ID
        productMrp.setProductMrpId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMrpMockMvc.perform(post("/api/product-mrps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productMrp)))
            .andExpect(status().isBadRequest());

        // Validate the ProductMrp in the database
        List<ProductMrp> productMrpList = productMrpRepository.findAll();
        assertThat(productMrpList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductMrps() throws Exception {
        // Initialize the database
        productMrpRepository.saveAndFlush(productMrp);

        // Get all the productMrpList
        restProductMrpMockMvc.perform(get("/api/product-mrps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productMrp.getProductMrpId().intValue())))
            .andExpect(jsonPath("$.[*].n").value(hasItem(DEFAULT_N)));
    }
    
    @Test
    @Transactional
    public void getProductMrp() throws Exception {
        // Initialize the database
        productMrpRepository.saveAndFlush(productMrp);

        // Get the productMrp
        restProductMrpMockMvc.perform(get("/api/product-mrps/{id}", productMrp.getProductMrpId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productMrp.getProductMrpId().intValue()))
            .andExpect(jsonPath("$.n").value(DEFAULT_N));
    }

    @Test
    @Transactional
    public void getNonExistingProductMrp() throws Exception {
        // Get the productMrp
        restProductMrpMockMvc.perform(get("/api/product-mrps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductMrp() throws Exception {
        // Initialize the database
        productMrpService.save(productMrp);

        int databaseSizeBeforeUpdate = productMrpRepository.findAll().size();

        // Update the productMrp
        ProductMrp updatedProductMrp = productMrpRepository.findById(productMrp.getProductMrpId()).get();
        // Disconnect from session so that the updates on updatedProductMrp are not directly saved in db
        em.detach(updatedProductMrp);
        

        restProductMrpMockMvc.perform(put("/api/product-mrps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductMrp)))
            .andExpect(status().isOk());

        // Validate the ProductMrp in the database
        List<ProductMrp> productMrpList = productMrpRepository.findAll();
        assertThat(productMrpList).hasSize(databaseSizeBeforeUpdate);
        ProductMrp testProductMrp = productMrpList.get(productMrpList.size() - 1);
        assertThat(testProductMrp.getProductMrpId()).isEqualTo(UPDATED_N);
    }

    @Test
    @Transactional
    public void updateNonExistingProductMrp() throws Exception {
        int databaseSizeBeforeUpdate = productMrpRepository.findAll().size();

        // Create the ProductMrp

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMrpMockMvc.perform(put("/api/product-mrps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productMrp)))
            .andExpect(status().isBadRequest());

        // Validate the ProductMrp in the database
        List<ProductMrp> productMrpList = productMrpRepository.findAll();
        assertThat(productMrpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductMrp() throws Exception {
        // Initialize the database
        productMrpService.save(productMrp);

        int databaseSizeBeforeDelete = productMrpRepository.findAll().size();

        // Delete the productMrp
        restProductMrpMockMvc.perform(delete("/api/product-mrps/{id}", productMrp.getProductMrpId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductMrp> productMrpList = productMrpRepository.findAll();
        assertThat(productMrpList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductMrp.class);
        ProductMrp productMrp1 = new ProductMrp();
        productMrp1.setProductMrpId(1L);
        ProductMrp productMrp2 = new ProductMrp();
        productMrp2.setProductMrpId(productMrp1.getProductMrpId());
        assertThat(productMrp1).isEqualTo(productMrp2);
        productMrp2.setProductMrpId(2L);
        assertThat(productMrp1).isNotEqualTo(productMrp2);
        productMrp1.setProductMrpId(null);
        assertThat(productMrp1).isNotEqualTo(productMrp2);
    }
}
