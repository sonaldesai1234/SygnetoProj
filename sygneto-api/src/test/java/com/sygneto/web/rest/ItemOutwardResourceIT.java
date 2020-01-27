package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.ItemOutward;
import com.sygneto.repository.ItemOutwardRepository;
import com.sygneto.service.ItemOutwardService;
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
 * Integration tests for the {@link ItemOutwardResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class ItemOutwardResourceIT {

    @Autowired
    private ItemOutwardRepository itemOutwardRepository;

    @Autowired
    private ItemOutwardService itemOutwardService;

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

    private MockMvc restItemOutwardMockMvc;

    private ItemOutward itemOutward;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemOutwardResource itemOutwardResource = new ItemOutwardResource(itemOutwardService);
        this.restItemOutwardMockMvc = MockMvcBuilders.standaloneSetup(itemOutwardResource)
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
    public static ItemOutward createEntity(EntityManager em) {
        ItemOutward itemOutward = new ItemOutward();
        return itemOutward;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemOutward createUpdatedEntity(EntityManager em) {
        ItemOutward itemOutward = new ItemOutward();
        return itemOutward;
    }

    @BeforeEach
    public void initTest() {
        itemOutward = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemOutward() throws Exception {
        int databaseSizeBeforeCreate = itemOutwardRepository.findAll().size();

        // Create the ItemOutward
        restItemOutwardMockMvc.perform(post("/api/item-outwards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemOutward)))
            .andExpect(status().isCreated());

        // Validate the ItemOutward in the database
        List<ItemOutward> itemOutwardList = itemOutwardRepository.findAll();
        assertThat(itemOutwardList).hasSize(databaseSizeBeforeCreate + 1);
        ItemOutward testItemOutward = itemOutwardList.get(itemOutwardList.size() - 1);
    }

    @Test
    @Transactional
    public void createItemOutwardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemOutwardRepository.findAll().size();

        // Create the ItemOutward with an existing ID
        itemOutward.setItemOutwardId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemOutwardMockMvc.perform(post("/api/item-outwards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemOutward)))
            .andExpect(status().isBadRequest());

        // Validate the ItemOutward in the database
        List<ItemOutward> itemOutwardList = itemOutwardRepository.findAll();
        assertThat(itemOutwardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllItemOutwards() throws Exception {
        // Initialize the database
        itemOutwardRepository.saveAndFlush(itemOutward);

        // Get all the itemOutwardList
        restItemOutwardMockMvc.perform(get("/api/item-outwards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemOutward.getItemOutwardId().intValue())));
    }
    
    @Test
    @Transactional
    public void getItemOutward() throws Exception {
        // Initialize the database
        itemOutwardRepository.saveAndFlush(itemOutward);

        // Get the itemOutward
        restItemOutwardMockMvc.perform(get("/api/item-outwards/{id}", itemOutward.getItemOutwardId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemOutward.getItemOutwardId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemOutward() throws Exception {
        // Get the itemOutward
        restItemOutwardMockMvc.perform(get("/api/item-outwards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemOutward() throws Exception {
        // Initialize the database
        itemOutwardService.save(itemOutward);

        int databaseSizeBeforeUpdate = itemOutwardRepository.findAll().size();

        // Update the itemOutward
        ItemOutward updatedItemOutward = itemOutwardRepository.findById(itemOutward.getItemOutwardId()).get();
        // Disconnect from session so that the updates on updatedItemOutward are not directly saved in db
        em.detach(updatedItemOutward);

        restItemOutwardMockMvc.perform(put("/api/item-outwards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemOutward)))
            .andExpect(status().isOk());

        // Validate the ItemOutward in the database
        List<ItemOutward> itemOutwardList = itemOutwardRepository.findAll();
        assertThat(itemOutwardList).hasSize(databaseSizeBeforeUpdate);
        ItemOutward testItemOutward = itemOutwardList.get(itemOutwardList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingItemOutward() throws Exception {
        int databaseSizeBeforeUpdate = itemOutwardRepository.findAll().size();

        // Create the ItemOutward

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemOutwardMockMvc.perform(put("/api/item-outwards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemOutward)))
            .andExpect(status().isBadRequest());

        // Validate the ItemOutward in the database
        List<ItemOutward> itemOutwardList = itemOutwardRepository.findAll();
        assertThat(itemOutwardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemOutward() throws Exception {
        // Initialize the database
        itemOutwardService.save(itemOutward);

        int databaseSizeBeforeDelete = itemOutwardRepository.findAll().size();

        // Delete the itemOutward
        restItemOutwardMockMvc.perform(delete("/api/item-outwards/{id}", itemOutward.getItemOutwardId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemOutward> itemOutwardList = itemOutwardRepository.findAll();
        assertThat(itemOutwardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemOutward.class);
        ItemOutward itemOutward1 = new ItemOutward();
        itemOutward1.setItemOutwardId(1L);
        ItemOutward itemOutward2 = new ItemOutward();
        itemOutward2.setItemOutwardId(itemOutward1.getItemOutwardId());
        assertThat(itemOutward1).isEqualTo(itemOutward2);
        itemOutward2.setItemOutwardId(2L);
        assertThat(itemOutward1).isNotEqualTo(itemOutward2);
        itemOutward1.setItemOutwardId(null);
        assertThat(itemOutward1).isNotEqualTo(itemOutward2);
    }
}
