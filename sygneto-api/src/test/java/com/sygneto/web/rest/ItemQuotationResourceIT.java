package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.ItemQuotation;
import com.sygneto.repository.ItemQuotationRepository;
import com.sygneto.service.ItemQuotationService;
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
 * Integration tests for the {@link ItemQuotationResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class ItemQuotationResourceIT {

    @Autowired
    private ItemQuotationRepository itemQuotationRepository;

    @Autowired
    private ItemQuotationService itemQuotationService;

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

    private MockMvc restItemQuotationMockMvc;

    private ItemQuotation itemQuotation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemQuotationResource itemQuotationResource = new ItemQuotationResource(itemQuotationService);
        this.restItemQuotationMockMvc = MockMvcBuilders.standaloneSetup(itemQuotationResource)
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
    public static ItemQuotation createEntity(EntityManager em) {
        ItemQuotation itemQuotation = new ItemQuotation();
        return itemQuotation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemQuotation createUpdatedEntity(EntityManager em) {
        ItemQuotation itemQuotation = new ItemQuotation();
        return itemQuotation;
    }

    @BeforeEach
    public void initTest() {
        itemQuotation = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemQuotation() throws Exception {
        int databaseSizeBeforeCreate = itemQuotationRepository.findAll().size();

        // Create the ItemQuotation
        restItemQuotationMockMvc.perform(post("/api/item-quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemQuotation)))
            .andExpect(status().isCreated());

        // Validate the ItemQuotation in the database
        List<ItemQuotation> itemQuotationList = itemQuotationRepository.findAll();
        assertThat(itemQuotationList).hasSize(databaseSizeBeforeCreate + 1);
        ItemQuotation testItemQuotation = itemQuotationList.get(itemQuotationList.size() - 1);
    }

    @Test
    @Transactional
    public void createItemQuotationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemQuotationRepository.findAll().size();

        // Create the ItemQuotation with an existing ID
        itemQuotation.setItemQuotationId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemQuotationMockMvc.perform(post("/api/item-quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemQuotation)))
            .andExpect(status().isBadRequest());

        // Validate the ItemQuotation in the database
        List<ItemQuotation> itemQuotationList = itemQuotationRepository.findAll();
        assertThat(itemQuotationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllItemQuotations() throws Exception {
        // Initialize the database
        itemQuotationRepository.saveAndFlush(itemQuotation);

        // Get all the itemQuotationList
        restItemQuotationMockMvc.perform(get("/api/item-quotations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemQuotation.getItemQuotationId().intValue())));
    }
    
    @Test
    @Transactional
    public void getItemQuotation() throws Exception {
        // Initialize the database
        itemQuotationRepository.saveAndFlush(itemQuotation);

        // Get the itemQuotation
        restItemQuotationMockMvc.perform(get("/api/item-quotations/{id}", itemQuotation.getItemQuotationId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemQuotation.getItemQuotationId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemQuotation() throws Exception {
        // Get the itemQuotation
        restItemQuotationMockMvc.perform(get("/api/item-quotations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemQuotation() throws Exception {
        // Initialize the database
        itemQuotationService.save(itemQuotation);

        int databaseSizeBeforeUpdate = itemQuotationRepository.findAll().size();

        // Update the itemQuotation
        ItemQuotation updatedItemQuotation = itemQuotationRepository.findById(itemQuotation.getItemQuotationId()).get();
        // Disconnect from session so that the updates on updatedItemQuotation are not directly saved in db
        em.detach(updatedItemQuotation);

        restItemQuotationMockMvc.perform(put("/api/item-quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemQuotation)))
            .andExpect(status().isOk());

        // Validate the ItemQuotation in the database
        List<ItemQuotation> itemQuotationList = itemQuotationRepository.findAll();
        assertThat(itemQuotationList).hasSize(databaseSizeBeforeUpdate);
        ItemQuotation testItemQuotation = itemQuotationList.get(itemQuotationList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingItemQuotation() throws Exception {
        int databaseSizeBeforeUpdate = itemQuotationRepository.findAll().size();

        // Create the ItemQuotation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemQuotationMockMvc.perform(put("/api/item-quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemQuotation)))
            .andExpect(status().isBadRequest());

        // Validate the ItemQuotation in the database
        List<ItemQuotation> itemQuotationList = itemQuotationRepository.findAll();
        assertThat(itemQuotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemQuotation() throws Exception {
        // Initialize the database
        itemQuotationService.save(itemQuotation);

        int databaseSizeBeforeDelete = itemQuotationRepository.findAll().size();

        // Delete the itemQuotation
        restItemQuotationMockMvc.perform(delete("/api/item-quotations/{id}", itemQuotation.getItemQuotationId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemQuotation> itemQuotationList = itemQuotationRepository.findAll();
        assertThat(itemQuotationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
