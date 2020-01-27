package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.GstSlabs;
import com.sygneto.repository.GstSlabsRepository;
import com.sygneto.service.GstSlabsService;
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
 * Integration tests for the {@link GstSlabsResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class GstSlabsResourceIT {

    @Autowired
    private GstSlabsRepository gstSlabsRepository;

    @Autowired
    private GstSlabsService gstSlabsService;

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

    private MockMvc restGstSlabsMockMvc;

    private GstSlabs gstSlabs;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GstSlabsResource gstSlabsResource = new GstSlabsResource(gstSlabsService);
        this.restGstSlabsMockMvc = MockMvcBuilders.standaloneSetup(gstSlabsResource)
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
    public static GstSlabs createEntity(EntityManager em) {
        GstSlabs gstSlabs = new GstSlabs();
        return gstSlabs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GstSlabs createUpdatedEntity(EntityManager em) {
        GstSlabs gstSlabs = new GstSlabs();
        return gstSlabs;
    }

    @BeforeEach
    public void initTest() {
        gstSlabs = createEntity(em);
    }

    @Test
    @Transactional
    public void createGstSlabs() throws Exception {
        int databaseSizeBeforeCreate = gstSlabsRepository.findAll().size();

        // Create the GstSlabs
        restGstSlabsMockMvc.perform(post("/api/gst-slabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gstSlabs)))
            .andExpect(status().isCreated());

        // Validate the GstSlabs in the database
        List<GstSlabs> gstSlabsList = gstSlabsRepository.findAll();
        assertThat(gstSlabsList).hasSize(databaseSizeBeforeCreate + 1);
        GstSlabs testGstSlabs = gstSlabsList.get(gstSlabsList.size() - 1);
    }

    @Test
    @Transactional
    public void createGstSlabsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gstSlabsRepository.findAll().size();

        // Create the GstSlabs with an existing ID
        gstSlabs.setGstSlabs(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGstSlabsMockMvc.perform(post("/api/gst-slabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gstSlabs)))
            .andExpect(status().isBadRequest());

        // Validate the GstSlabs in the database
        List<GstSlabs> gstSlabsList = gstSlabsRepository.findAll();
        assertThat(gstSlabsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGstSlabs() throws Exception {
        // Initialize the database
        gstSlabsRepository.saveAndFlush(gstSlabs);

        // Get all the gstSlabsList
        restGstSlabsMockMvc.perform(get("/api/gst-slabs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gstSlabs.getGstSlabs())));
    }
    
    @Test
    @Transactional
    public void getGstSlabs() throws Exception {
        // Initialize the database
        gstSlabsRepository.saveAndFlush(gstSlabs);

        // Get the gstSlabs
        restGstSlabsMockMvc.perform(get("/api/gst-slabs/{id}", gstSlabs.getGstSlabs()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gstSlabs.getGstSlabs()));
    }

    @Test
    @Transactional
    public void getNonExistingGstSlabs() throws Exception {
        // Get the gstSlabs
        restGstSlabsMockMvc.perform(get("/api/gst-slabs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGstSlabs() throws Exception {
        // Initialize the database
        gstSlabsService.save(gstSlabs);

        int databaseSizeBeforeUpdate = gstSlabsRepository.findAll().size();

        // Update the gstSlabs
        GstSlabs updatedGstSlabs = gstSlabsRepository.findById((long) gstSlabs.getGstSlabs()).get();
        // Disconnect from session so that the updates on updatedGstSlabs are not directly saved in db
        em.detach(updatedGstSlabs);

        restGstSlabsMockMvc.perform(put("/api/gst-slabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGstSlabs)))
            .andExpect(status().isOk());

        // Validate the GstSlabs in the database
        List<GstSlabs> gstSlabsList = gstSlabsRepository.findAll();
        assertThat(gstSlabsList).hasSize(databaseSizeBeforeUpdate);
        GstSlabs testGstSlabs = gstSlabsList.get(gstSlabsList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingGstSlabs() throws Exception {
        int databaseSizeBeforeUpdate = gstSlabsRepository.findAll().size();

        // Create the GstSlabs

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGstSlabsMockMvc.perform(put("/api/gst-slabs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gstSlabs)))
            .andExpect(status().isBadRequest());

        // Validate the GstSlabs in the database
        List<GstSlabs> gstSlabsList = gstSlabsRepository.findAll();
        assertThat(gstSlabsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGstSlabs() throws Exception {
        // Initialize the database
        gstSlabsService.save(gstSlabs);

        int databaseSizeBeforeDelete = gstSlabsRepository.findAll().size();

        // Delete the gstSlabs
        restGstSlabsMockMvc.perform(delete("/api/gst-slabs/{id}", gstSlabs.getGstSlabs())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GstSlabs> gstSlabsList = gstSlabsRepository.findAll();
        assertThat(gstSlabsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
