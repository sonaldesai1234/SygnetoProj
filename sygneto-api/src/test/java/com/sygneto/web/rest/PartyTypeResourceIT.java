package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.PartyType;
import com.sygneto.repository.PartyTypeRepository;
import com.sygneto.service.PartyTypeService;
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
 * Integration tests for the {@link PartyTypeResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class PartyTypeResourceIT {

   
	@Autowired
	private PartyTypeRepository partyTypeRepository;

    @Autowired
    private PartyTypeService partyTypeService;

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

    private MockMvc restPartyTypeMockMvc;

    private PartyType partyType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartyTypeResource partyTypeResource = new PartyTypeResource(partyTypeService);
        this.restPartyTypeMockMvc = MockMvcBuilders.standaloneSetup(partyTypeResource)
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
    public static PartyType createEntity(EntityManager em) {
        PartyType partyType = new PartyType();
        return partyType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyType createUpdatedEntity(EntityManager em) {
        PartyType partyType = new PartyType();
        return partyType;
    }

    @BeforeEach
    public void initTest() {
        partyType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyType() throws Exception {
        int databaseSizeBeforeCreate = partyTypeRepository.findAll().size();

        // Create the PartyType
        restPartyTypeMockMvc.perform(post("/api/party-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyType)))
            .andExpect(status().isCreated());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PartyType testPartyType = partyTypeList.get(partyTypeList.size() - 1);
    }

    @Test
    @Transactional
    public void createPartyTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyTypeRepository.findAll().size();

        // Create the PartyType with an existing ID
        partyType.setPartyType("1L");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyTypeMockMvc.perform(post("/api/party-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyType)))
            .andExpect(status().isBadRequest());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyTypes() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        // Get all the partyTypeList
        restPartyTypeMockMvc.perform(get("/api/party-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyType.getPartyType().toString())));
    }
    
    @Test
    @Transactional
    public void getPartyType() throws Exception {
        // Initialize the database
        partyTypeRepository.saveAndFlush(partyType);

        // Get the partyType
        restPartyTypeMockMvc.perform(get("/api/party-types/{id}", partyType.getPartyType()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partyType.getPartyType().toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPartyType() throws Exception {
        // Get the partyType
        restPartyTypeMockMvc.perform(get("/api/party-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyType() throws Exception {
        // Initialize the database
        partyTypeService.save(partyType);

        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();

        // Update the partyType
        Object updatedPartyType = partyTypeRepository.findByPartyType(partyType.getPartyType());
        // Disconnect from session so that the updates on updatedPartyType are not directly saved in db
        em.detach(updatedPartyType);

        restPartyTypeMockMvc.perform(put("/api/party-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyType)))
            .andExpect(status().isOk());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
        PartyType testPartyType = partyTypeList.get(partyTypeList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyType() throws Exception {
        int databaseSizeBeforeUpdate = partyTypeRepository.findAll().size();

        // Create the PartyType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyTypeMockMvc.perform(put("/api/party-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partyType)))
            .andExpect(status().isBadRequest());

        // Validate the PartyType in the database
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyType() throws Exception {
        // Initialize the database
        partyTypeService.save(partyType);

        int databaseSizeBeforeDelete = partyTypeRepository.findAll().size();

        // Delete the partyType
        restPartyTypeMockMvc.perform(delete("/api/party-types/{id}", partyType.getPartyType())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyType> partyTypeList = partyTypeRepository.findAll();
        assertThat(partyTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartyType.class);
        PartyType partyType1 = new PartyType();
        partyType1.setPartyType("1L");
        PartyType partyType2 = new PartyType();
        partyType2.setPartyType(partyType1.getPartyType());
        assertThat(partyType1).isEqualTo(partyType2);
        partyType2.setPartyType("2L");
        assertThat(partyType1).isNotEqualTo(partyType2);
        partyType1.setPartyType(null);
        assertThat(partyType1).isNotEqualTo(partyType2);
    }
}
