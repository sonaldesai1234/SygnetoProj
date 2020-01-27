package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.ItemInward;
import com.sygneto.repository.ItemInwardRepository;
import com.sygneto.service.ItemInwardService;
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
 * Integration tests for the {@link ItemInwardResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class ItemInwardResourceIT {

    @Autowired
    private ItemInwardRepository itemInwardRepository;

    @Autowired
    private ItemInwardService itemInwardService;

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

    private MockMvc restItemInwardMockMvc;

    private ItemInward itemInward;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemInwardResource itemInwardResource = new ItemInwardResource(itemInwardService);
        this.restItemInwardMockMvc = MockMvcBuilders.standaloneSetup(itemInwardResource)
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
    public static ItemInward createEntity(EntityManager em) {
        ItemInward itemInward = new ItemInward();
        return itemInward;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemInward createUpdatedEntity(EntityManager em) {
        ItemInward itemInward = new ItemInward();
        return itemInward;
    }

    @BeforeEach
    public void initTest() {
        itemInward = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemInward() throws Exception {
        int databaseSizeBeforeCreate = itemInwardRepository.findAll().size();

        // Create the ItemInward
        restItemInwardMockMvc.perform(post("/api/item-inwards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemInward)))
            .andExpect(status().isCreated());

        // Validate the ItemInward in the database
        List<ItemInward> itemInwardList = itemInwardRepository.findAll();
        assertThat(itemInwardList).hasSize(databaseSizeBeforeCreate + 1);
        ItemInward testItemInward = itemInwardList.get(itemInwardList.size() - 1);
    }

    @Test
    @Transactional
    public void createItemInwardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemInwardRepository.findAll().size();

        // Create the ItemInward with an existing ID
        itemInward.setItemInwardId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemInwardMockMvc.perform(post("/api/item-inwards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemInward)))
            .andExpect(status().isBadRequest());

        // Validate the ItemInward in the database
        List<ItemInward> itemInwardList = itemInwardRepository.findAll();
        assertThat(itemInwardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllItemInwards() throws Exception {
        // Initialize the database
        itemInwardRepository.saveAndFlush(itemInward);

        // Get all the itemInwardList
        restItemInwardMockMvc.perform(get("/api/item-inwards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemInward.getItemInwardId().intValue())));
    }
    
    @Test
    @Transactional
    public void getItemInward() throws Exception {
        // Initialize the database
        itemInwardRepository.saveAndFlush(itemInward);

        // Get the itemInward
        restItemInwardMockMvc.perform(get("/api/item-inwards/{id}", itemInward.getItemInwardId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemInward.getItemInwardId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemInward() throws Exception {
        // Get the itemInward
        restItemInwardMockMvc.perform(get("/api/item-inwards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemInward() throws Exception {
        // Initialize the database
        itemInwardService.save(itemInward);

        int databaseSizeBeforeUpdate = itemInwardRepository.findAll().size();

        // Update the itemInward
        ItemInward updatedItemInward = itemInwardRepository.findById(itemInward.getItemInwardId()).get();
        // Disconnect from session so that the updates on updatedItemInward are not directly saved in db
        em.detach(updatedItemInward);

        restItemInwardMockMvc.perform(put("/api/item-inwards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemInward)))
            .andExpect(status().isOk());

        // Validate the ItemInward in the database
        List<ItemInward> itemInwardList = itemInwardRepository.findAll();
        assertThat(itemInwardList).hasSize(databaseSizeBeforeUpdate);
        ItemInward testItemInward = itemInwardList.get(itemInwardList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingItemInward() throws Exception {
        int databaseSizeBeforeUpdate = itemInwardRepository.findAll().size();

        // Create the ItemInward

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemInwardMockMvc.perform(put("/api/item-inwards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemInward)))
            .andExpect(status().isBadRequest());

        // Validate the ItemInward in the database
        List<ItemInward> itemInwardList = itemInwardRepository.findAll();
        assertThat(itemInwardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemInward() throws Exception {
        // Initialize the database
        itemInwardService.save(itemInward);

        int databaseSizeBeforeDelete = itemInwardRepository.findAll().size();

        // Delete the itemInward
        restItemInwardMockMvc.perform(delete("/api/item-inwards/{id}", itemInward.getItemInwardId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemInward> itemInwardList = itemInwardRepository.findAll();
        assertThat(itemInwardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemInward.class);
        ItemInward itemInward1 = new ItemInward();
        itemInward1.setItemInwardId(1L);
        ItemInward itemInward2 = new ItemInward();
        itemInward2.setItemInwardId(itemInward1.getItemInwardId());
        assertThat(itemInward1).isEqualTo(itemInward2);
        itemInward2.setItemInwardId(2L);
        assertThat(itemInward1).isNotEqualTo(itemInward2);
        itemInward1.setItemInwardId(null);
        assertThat(itemInward1).isNotEqualTo(itemInward2);
    }
}
