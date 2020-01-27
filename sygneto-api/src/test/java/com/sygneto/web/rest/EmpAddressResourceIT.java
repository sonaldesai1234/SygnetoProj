/*package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.MandatoryAddress;
import com.sygneto.repository.EmpAddressRepository;
import com.sygneto.service.EmpAddressService;
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
 * Integration tests for the {@link EmpAddressResource} REST controller.
 *//*
@SpringBootTest(classes = SygnetoApiApp.class)
public class EmpAddressResourceIT {

    @Autowired
    private EmpAddressRepository empAddressRepository;

    @Autowired
    private EmpAddressService empAddressService;

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

    private MockMvc restEmpAddressMockMvc;

    private MandatoryAddress mandatoryAddress;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmpAddressResource empAddressResource = new EmpAddressResource(empAddressService);
        this.restEmpAddressMockMvc = MockMvcBuilders.standaloneSetup(empAddressResource)
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
    public static MandatoryAddress createEntity(EntityManager em) {
        MandatoryAddress mandatoryAddress = new MandatoryAddress();
        return mandatoryAddress;
    }
    *//**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
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
    public void createEmpAddress() throws Exception {
        int databaseSizeBeforeCreate = empAddressRepository.findAll().size();

        // Create the MandatoryAddress
        restEmpAddressMockMvc.perform(post("/api/emp-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mandatoryAddress)))
            .andExpect(status().isCreated());

        // Validate the MandatoryAddress in the database
        List<MandatoryAddress> empAddressList = empAddressRepository.findAll();
        assertThat(empAddressList).hasSize(databaseSizeBeforeCreate + 1);
        MandatoryAddress testEmpAddress = empAddressList.get(empAddressList.size() - 1);
    }

    @Test
    @Transactional
    public void createEmpAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empAddressRepository.findAll().size();

        // Create the MandatoryAddress with an existing ID
        mandatoryAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpAddressMockMvc.perform(post("/api/emp-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mandatoryAddress)))
            .andExpect(status().isBadRequest());

        // Validate the MandatoryAddress in the database
        List<MandatoryAddress> empAddressList = empAddressRepository.findAll();
        assertThat(empAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmpAddresses() throws Exception {
        // Initialize the database
        empAddressRepository.saveAndFlush(mandatoryAddress);

        // Get all the empAddressList
        restEmpAddressMockMvc.perform(get("/api/emp-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mandatoryAddress.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getEmpAddress() throws Exception {
        // Initialize the database
        empAddressRepository.saveAndFlush(mandatoryAddress);

        // Get the mandatoryAddress
        restEmpAddressMockMvc.perform(get("/api/emp-addresses/{id}", mandatoryAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mandatoryAddress.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpAddress() throws Exception {
        // Get the mandatoryAddress
        restEmpAddressMockMvc.perform(get("/api/emp-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpAddress() throws Exception {
        // Initialize the database
        empAddressService.save(mandatoryAddress);

        int databaseSizeBeforeUpdate = empAddressRepository.findAll().size();

        // Update the mandatoryAddress
        MandatoryAddress updatedEmpAddress = empAddressRepository.findById(mandatoryAddress.getId()).get();
        // Disconnect from session so that the updates on updatedEmpAddress are not directly saved in db
        em.detach(updatedEmpAddress);

        restEmpAddressMockMvc.perform(put("/api/emp-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmpAddress)))
            .andExpect(status().isOk());

        // Validate the MandatoryAddress in the database
        List<MandatoryAddress> empAddressList = empAddressRepository.findAll();
        assertThat(empAddressList).hasSize(databaseSizeBeforeUpdate);
        MandatoryAddress testEmpAddress = empAddressList.get(empAddressList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpAddress() throws Exception {
        int databaseSizeBeforeUpdate = empAddressRepository.findAll().size();

        // Create the MandatoryAddress

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpAddressMockMvc.perform(put("/api/emp-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mandatoryAddress)))
            .andExpect(status().isBadRequest());

        // Validate the MandatoryAddress in the database
        List<MandatoryAddress> empAddressList = empAddressRepository.findAll();
        assertThat(empAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmpAddress() throws Exception {
        // Initialize the database
        empAddressService.save(mandatoryAddress);

        int databaseSizeBeforeDelete = empAddressRepository.findAll().size();

        // Delete the mandatoryAddress
        restEmpAddressMockMvc.perform(delete("/api/emp-addresses/{id}", mandatoryAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MandatoryAddress> empAddressList = empAddressRepository.findAll();
        assertThat(empAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MandatoryAddress.class);
        MandatoryAddress empAddress1 = new MandatoryAddress();
        empAddress1.setId(1L);
        MandatoryAddress empAddress2 = new MandatoryAddress();
        empAddress2.setId(empAddress1.getId());
        assertThat(empAddress1).isEqualTo(empAddress2);
        empAddress2.setId(2L);
        assertThat(empAddress1).isNotEqualTo(empAddress2);
        empAddress1.setId(null);
        assertThat(empAddress1).isNotEqualTo(empAddress2);
    }
}
*/