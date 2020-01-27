package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.ItemPriceHistory;
import com.sygneto.repository.ItemPriceHistoryRepository;
import com.sygneto.service.ItemPriceHistoryService;
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
 * Integration tests for the {@link ItemPriceHistoryResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class ItemPriceHistoryResourceIT {

    @Autowired
    private ItemPriceHistoryRepository itemPriceHistoryRepository;

    @Autowired
    private ItemPriceHistoryService itemPriceHistoryService;

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

    private MockMvc restItemPriceHistoryMockMvc;

    private ItemPriceHistory itemPriceHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemPriceHistoryResource itemPriceHistoryResource = new ItemPriceHistoryResource(itemPriceHistoryService);
        this.restItemPriceHistoryMockMvc = MockMvcBuilders.standaloneSetup(itemPriceHistoryResource)
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
    public static ItemPriceHistory createEntity(EntityManager em) {
        ItemPriceHistory itemPriceHistory = new ItemPriceHistory();
        return itemPriceHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemPriceHistory createUpdatedEntity(EntityManager em) {
        ItemPriceHistory itemPriceHistory = new ItemPriceHistory();
        return itemPriceHistory;
    }

    @BeforeEach
    public void initTest() {
        itemPriceHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPriceHistory() throws Exception {
        int databaseSizeBeforeCreate = itemPriceHistoryRepository.findAll().size();

        // Create the ItemPriceHistory
        restItemPriceHistoryMockMvc.perform(post("/api/item-price-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPriceHistory)))
            .andExpect(status().isCreated());

        // Validate the ItemPriceHistory in the database
        List<ItemPriceHistory> itemPriceHistoryList = itemPriceHistoryRepository.findAll();
        assertThat(itemPriceHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPriceHistory testItemPriceHistory = itemPriceHistoryList.get(itemPriceHistoryList.size() - 1);
    }

    @Test
    @Transactional
    public void createItemPriceHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPriceHistoryRepository.findAll().size();

        // Create the ItemPriceHistory with an existing ID
        itemPriceHistory.setItemId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPriceHistoryMockMvc.perform(post("/api/item-price-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPriceHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPriceHistory in the database
        List<ItemPriceHistory> itemPriceHistoryList = itemPriceHistoryRepository.findAll();
        assertThat(itemPriceHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllItemPriceHistories() throws Exception {
        // Initialize the database
        itemPriceHistoryRepository.saveAndFlush(itemPriceHistory);

        // Get all the itemPriceHistoryList
        restItemPriceHistoryMockMvc.perform(get("/api/item-price-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPriceHistory.getItemId().intValue())));
    }
    
    @Test
    @Transactional
    public void getItemPriceHistory() throws Exception {
        // Initialize the database
        itemPriceHistoryRepository.saveAndFlush(itemPriceHistory);

        // Get the itemPriceHistory
        restItemPriceHistoryMockMvc.perform(get("/api/item-price-histories/{id}", itemPriceHistory.getItemId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPriceHistory.getItemId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemPriceHistory() throws Exception {
        // Get the itemPriceHistory
        restItemPriceHistoryMockMvc.perform(get("/api/item-price-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPriceHistory() throws Exception {
        // Initialize the database
        itemPriceHistoryService.save(itemPriceHistory);

        int databaseSizeBeforeUpdate = itemPriceHistoryRepository.findAll().size();

        // Update the itemPriceHistory
        ItemPriceHistory updatedItemPriceHistory = itemPriceHistoryRepository.findById(itemPriceHistory.getItemId()).get();
        // Disconnect from session so that the updates on updatedItemPriceHistory are not directly saved in db
        em.detach(updatedItemPriceHistory);

        restItemPriceHistoryMockMvc.perform(put("/api/item-price-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemPriceHistory)))
            .andExpect(status().isOk());

        // Validate the ItemPriceHistory in the database
        List<ItemPriceHistory> itemPriceHistoryList = itemPriceHistoryRepository.findAll();
        assertThat(itemPriceHistoryList).hasSize(databaseSizeBeforeUpdate);
        ItemPriceHistory testItemPriceHistory = itemPriceHistoryList.get(itemPriceHistoryList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPriceHistory() throws Exception {
        int databaseSizeBeforeUpdate = itemPriceHistoryRepository.findAll().size();

        // Create the ItemPriceHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemPriceHistoryMockMvc.perform(put("/api/item-price-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPriceHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPriceHistory in the database
        List<ItemPriceHistory> itemPriceHistoryList = itemPriceHistoryRepository.findAll();
        assertThat(itemPriceHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemPriceHistory() throws Exception {
        // Initialize the database
        itemPriceHistoryService.save(itemPriceHistory);

        int databaseSizeBeforeDelete = itemPriceHistoryRepository.findAll().size();

        // Delete the itemPriceHistory
        restItemPriceHistoryMockMvc.perform(delete("/api/item-price-histories/{id}", itemPriceHistory.getItemId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemPriceHistory> itemPriceHistoryList = itemPriceHistoryRepository.findAll();
        assertThat(itemPriceHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
