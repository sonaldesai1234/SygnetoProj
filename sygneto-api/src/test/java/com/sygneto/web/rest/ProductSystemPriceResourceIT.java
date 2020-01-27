/*package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.ProductSystemPrice;
import com.sygneto.repository.ProductSystemPriceRepository;
import com.sygneto.service.ProductSystemPriceService;
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
 * Integration tests for the {@link ProductSystemPriceResource} REST controller.
 *//*
@SpringBootTest(classes = SygnetoApiApp.class)
public class ProductSystemPriceResourceIT {

    @Autowired
    private ProductSystemPriceRepository productSystemPriceRepository;

    @Autowired
    private ProductSystemPriceService productSystemPriceService;

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

    private MockMvc restProductSystemPriceMockMvc;

    private ProductSystemPrice productSystemPrice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductSystemPriceResource productSystemPriceResource = new ProductSystemPriceResource(productSystemPriceService);
        this.restProductSystemPriceMockMvc = MockMvcBuilders.standaloneSetup(productSystemPriceResource)
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
    public static ProductSystemPrice createEntity(EntityManager em) {
        ProductSystemPrice productSystemPrice = new ProductSystemPrice();
        return productSystemPrice;
    }
    *//**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static ProductSystemPrice createUpdatedEntity(EntityManager em) {
        ProductSystemPrice productSystemPrice = new ProductSystemPrice();
        return productSystemPrice;
    }

    @BeforeEach
    public void initTest() {
        productSystemPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductSystemPrice() throws Exception {
        int databaseSizeBeforeCreate = productSystemPriceRepository.findAll().size();

        // Create the ProductSystemPrice
        restProductSystemPriceMockMvc.perform(post("/api/product-system-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSystemPrice)))
            .andExpect(status().isCreated());

        // Validate the ProductSystemPrice in the database
        List<ProductSystemPrice> productSystemPriceList = productSystemPriceRepository.findAll();
        assertThat(productSystemPriceList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSystemPrice testProductSystemPrice = productSystemPriceList.get(productSystemPriceList.size() - 1);
    }

    @Test
    @Transactional
    public void createProductSystemPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productSystemPriceRepository.findAll().size();

        // Create the ProductSystemPrice with an existing ID
        productSystemPrice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSystemPriceMockMvc.perform(post("/api/product-system-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSystemPrice)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSystemPrice in the database
        List<ProductSystemPrice> productSystemPriceList = productSystemPriceRepository.findAll();
        assertThat(productSystemPriceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductSystemPrices() throws Exception {
        // Initialize the database
        productSystemPriceRepository.saveAndFlush(productSystemPrice);

        // Get all the productSystemPriceList
        restProductSystemPriceMockMvc.perform(get("/api/product-system-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSystemPrice.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getProductSystemPrice() throws Exception {
        // Initialize the database
        productSystemPriceRepository.saveAndFlush(productSystemPrice);

        // Get the productSystemPrice
        restProductSystemPriceMockMvc.perform(get("/api/product-system-prices/{id}", productSystemPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productSystemPrice.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductSystemPrice() throws Exception {
        // Get the productSystemPrice
        restProductSystemPriceMockMvc.perform(get("/api/product-system-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductSystemPrice() throws Exception {
        // Initialize the database
        productSystemPriceService.save(productSystemPrice);

        int databaseSizeBeforeUpdate = productSystemPriceRepository.findAll().size();

        // Update the productSystemPrice
        ProductSystemPrice updatedProductSystemPrice = productSystemPriceRepository.findById(productSystemPrice.getId()).get();
        // Disconnect from session so that the updates on updatedProductSystemPrice are not directly saved in db
        em.detach(updatedProductSystemPrice);

        restProductSystemPriceMockMvc.perform(put("/api/product-system-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductSystemPrice)))
            .andExpect(status().isOk());

        // Validate the ProductSystemPrice in the database
        List<ProductSystemPrice> productSystemPriceList = productSystemPriceRepository.findAll();
        assertThat(productSystemPriceList).hasSize(databaseSizeBeforeUpdate);
        ProductSystemPrice testProductSystemPrice = productSystemPriceList.get(productSystemPriceList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingProductSystemPrice() throws Exception {
        int databaseSizeBeforeUpdate = productSystemPriceRepository.findAll().size();

        // Create the ProductSystemPrice

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSystemPriceMockMvc.perform(put("/api/product-system-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productSystemPrice)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSystemPrice in the database
        List<ProductSystemPrice> productSystemPriceList = productSystemPriceRepository.findAll();
        assertThat(productSystemPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductSystemPrice() throws Exception {
        // Initialize the database
        productSystemPriceService.save(productSystemPrice);

        int databaseSizeBeforeDelete = productSystemPriceRepository.findAll().size();

        // Delete the productSystemPrice
        restProductSystemPriceMockMvc.perform(delete("/api/product-system-prices/{id}", productSystemPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductSystemPrice> productSystemPriceList = productSystemPriceRepository.findAll();
        assertThat(productSystemPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductSystemPrice.class);
        ProductSystemPrice productSystemPrice1 = new ProductSystemPrice();
        productSystemPrice1.setId(1L);
        ProductSystemPrice productSystemPrice2 = new ProductSystemPrice();
        productSystemPrice2.setId(productSystemPrice1.getId());
        assertThat(productSystemPrice1).isEqualTo(productSystemPrice2);
        productSystemPrice2.setId(2L);
        assertThat(productSystemPrice1).isNotEqualTo(productSystemPrice2);
        productSystemPrice1.setId(null);
        assertThat(productSystemPrice1).isNotEqualTo(productSystemPrice2);
    }
}
*/