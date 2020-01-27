package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.MandatoryAddress;
import com.sygneto.repository.MandatoryAddressRepository;
import com.sygneto.service.MandatoryAddressService;
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
 * Integration tests for the {@link MandatoryAddressResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class MandatoryAddressResourceIT {

    @Autowired
    private MandatoryAddressRepository mandatoryAddressRepository;

    @Autowired
    private MandatoryAddressService mandatoryAddressService;

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

    private MockMvc restMandatoryAddressMockMvc;

    private MandatoryAddress mandatoryAddress;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MandatoryAddressResource mandatoryAddressResource = new MandatoryAddressResource(mandatoryAddressService);
        this.restMandatoryAddressMockMvc = MockMvcBuilders.standaloneSetup(mandatoryAddressResource)
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
    public static MandatoryAddress createEntity(EntityManager em) {
        MandatoryAddress mandatoryAddress = new MandatoryAddress();
        return mandatoryAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MandatoryAddress createUpdatedEntity(EntityManager em) {
        MandatoryAddress mandatoryAddress = new MandatoryAddress();
        return mandatoryAddress;
    }

    @BeforeEach
    public void initTest() {
        mandatoryAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createMandatoryAddress() throws Exception {
        int databaseSizeBeforeCreate = mandatoryAddressRepository.findAll().size();

        // Create the MandatoryAddress
        restMandatoryAddressMockMvc.perform(post("/api/mandatory-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mandatoryAddress)))
            .andExpect(status().isCreated());

        // Validate the MandatoryAddress in the database
        List<MandatoryAddress> mandatoryAddressList = mandatoryAddressRepository.findAll();
        assertThat(mandatoryAddressList).hasSize(databaseSizeBeforeCreate + 1);
        MandatoryAddress testMandatoryAddress = mandatoryAddressList.get(mandatoryAddressList.size() - 1);
    }

    @Test
    @Transactional
    public void createMandatoryAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mandatoryAddressRepository.findAll().size();

        // Create the MandatoryAddress with an existing ID
        mandatoryAddress.setMandatoryAddressId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMandatoryAddressMockMvc.perform(post("/api/mandatory-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mandatoryAddress)))
            .andExpect(status().isBadRequest());

        // Validate the MandatoryAddress in the database
        List<MandatoryAddress> mandatoryAddressList = mandatoryAddressRepository.findAll();
        assertThat(mandatoryAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMandatoryAddresses() throws Exception {
        // Initialize the database
        mandatoryAddressRepository.saveAndFlush(mandatoryAddress);

        // Get all the mandatoryAddressList
        restMandatoryAddressMockMvc.perform(get("/api/mandatory-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mandatoryAddress.getMandatoryAddressId().intValue())));
    }
    
    @Test
    @Transactional
    public void getMandatoryAddress() throws Exception {
        // Initialize the database
        mandatoryAddressRepository.saveAndFlush(mandatoryAddress);

        // Get the mandatoryAddress
        restMandatoryAddressMockMvc.perform(get("/api/mandatory-addresses/{id}", mandatoryAddress.getMandatoryAddressId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mandatoryAddress.getMandatoryAddressId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMandatoryAddress() throws Exception {
        // Get the mandatoryAddress
        restMandatoryAddressMockMvc.perform(get("/api/mandatory-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMandatoryAddress() throws Exception {
        // Initialize the database
        mandatoryAddressService.save(mandatoryAddress);

        int databaseSizeBeforeUpdate = mandatoryAddressRepository.findAll().size();

        // Update the mandatoryAddress
        MandatoryAddress updatedMandatoryAddress = mandatoryAddressRepository.findById(mandatoryAddress.getMandatoryAddressId()).get();
        // Disconnect from session so that the updates on updatedMandatoryAddress are not directly saved in db
        em.detach(updatedMandatoryAddress);

        restMandatoryAddressMockMvc.perform(put("/api/mandatory-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMandatoryAddress)))
            .andExpect(status().isOk());

        // Validate the MandatoryAddress in the database
        List<MandatoryAddress> mandatoryAddressList = mandatoryAddressRepository.findAll();
        assertThat(mandatoryAddressList).hasSize(databaseSizeBeforeUpdate);
        MandatoryAddress testMandatoryAddress = mandatoryAddressList.get(mandatoryAddressList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingMandatoryAddress() throws Exception {
        int databaseSizeBeforeUpdate = mandatoryAddressRepository.findAll().size();

        // Create the MandatoryAddress

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMandatoryAddressMockMvc.perform(put("/api/mandatory-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mandatoryAddress)))
            .andExpect(status().isBadRequest());

        // Validate the MandatoryAddress in the database
        List<MandatoryAddress> mandatoryAddressList = mandatoryAddressRepository.findAll();
        assertThat(mandatoryAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMandatoryAddress() throws Exception {
        // Initialize the database
        mandatoryAddressService.save(mandatoryAddress);

        int databaseSizeBeforeDelete = mandatoryAddressRepository.findAll().size();

        // Delete the mandatoryAddress
        restMandatoryAddressMockMvc.perform(delete("/api/mandatory-addresses/{id}", mandatoryAddress.getMandatoryAddressId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MandatoryAddress> mandatoryAddressList = mandatoryAddressRepository.findAll();
        assertThat(mandatoryAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MandatoryAddress.class);
        MandatoryAddress mandatoryAddress1 = new MandatoryAddress();
        mandatoryAddress1.setMandatoryAddressId(1L);
        MandatoryAddress mandatoryAddress2 = new MandatoryAddress();
        mandatoryAddress2.setMandatoryAddressId(mandatoryAddress1.getMandatoryAddressId());
        assertThat(mandatoryAddress1).isEqualTo(mandatoryAddress2);
        mandatoryAddress2.setMandatoryAddressId(2L);
        assertThat(mandatoryAddress1).isNotEqualTo(mandatoryAddress2);
        mandatoryAddress1.setMandatoryAddressId(null);
        assertThat(mandatoryAddress1).isNotEqualTo(mandatoryAddress2);
    }
}
