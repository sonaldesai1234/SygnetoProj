package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.AddressType;
import com.sygneto.repository.AddressTypeRepository;
import com.sygneto.service.AddressTypeService;
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
 * Integration tests for the {@link AddressTypeResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class AddressTypeResourceIT {

    @Autowired
    private AddressTypeRepository addressTypeRepository;

    @Autowired
    private AddressTypeService addressTypeService;

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

    private MockMvc restAddressTypeMockMvc;

    private AddressType addressType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddressTypeResource addressTypeResource = new AddressTypeResource(addressTypeService);
        this.restAddressTypeMockMvc = MockMvcBuilders.standaloneSetup(addressTypeResource)
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
    public static AddressType createEntity(EntityManager em) {
        AddressType addressType = new AddressType();
        return addressType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressType createUpdatedEntity(EntityManager em) {
        AddressType addressType = new AddressType();
        return addressType;
    }

    @BeforeEach
    public void initTest() {
        addressType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddressType() throws Exception {
        int databaseSizeBeforeCreate = addressTypeRepository.findAll().size();

        // Create the AddressType
        restAddressTypeMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressType)))
            .andExpect(status().isCreated());

        // Validate the AddressType in the database
        List<AddressType> addressTypeList = addressTypeRepository.findAll();
        assertThat(addressTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AddressType testAddressType = addressTypeList.get(addressTypeList.size() - 1);
    }

    @Test
    @Transactional
    public void createAddressTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressTypeRepository.findAll().size();

        // Create the AddressType with an existing ID
        addressType.setAddressType("1L");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressTypeMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressType)))
            .andExpect(status().isBadRequest());

        // Validate the AddressType in the database
        List<AddressType> addressTypeList = addressTypeRepository.findAll();
        assertThat(addressTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAddressTypes() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get all the addressTypeList
        restAddressTypeMockMvc.perform(get("/api/address-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressType.getAddressType())));
    }
    
    @Test
    @Transactional
    public void getAddressType() throws Exception {
        // Initialize the database
        addressTypeRepository.saveAndFlush(addressType);

        // Get the addressType
        restAddressTypeMockMvc.perform(get("/api/address-types/{id}", addressType.getAddressType()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(addressType.getAddressType()));
    }

    @Test
    @Transactional
    public void getNonExistingAddressType() throws Exception {
        // Get the addressType
        restAddressTypeMockMvc.perform(get("/api/address-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    //@Test
    //@Transactional
    /*public void updateAddressType() throws Exception {
        // Initialize the database
        addressTypeService.save(addressType);

        int databaseSizeBeforeUpdate = addressTypeRepository.findAll().size();

        // Update the addressType
        AddressType updatedAddressType = addressTypeRepository.findById(addressType.getAddressType()).get();
        // Disconnect from session so that the updates on updatedAddressType are not directly saved in db
        em.detach(updatedAddressType);

        restAddressTypeMockMvc.perform(put("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAddressType)))
            .andExpect(status().isOk());

        // Validate the AddressType in the database
        List<AddressType> addressTypeList = addressTypeRepository.findAll();
        assertThat(addressTypeList).hasSize(databaseSizeBeforeUpdate);
        AddressType testAddressType = addressTypeList.get(addressTypeList.size() - 1);
    }*/

    @Test
    @Transactional
    public void updateNonExistingAddressType() throws Exception {
        int databaseSizeBeforeUpdate = addressTypeRepository.findAll().size();

        // Create the AddressType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressTypeMockMvc.perform(put("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressType)))
            .andExpect(status().isBadRequest());

        // Validate the AddressType in the database
        List<AddressType> addressTypeList = addressTypeRepository.findAll();
        assertThat(addressTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAddressType() throws Exception {
        // Initialize the database
        addressTypeService.save(addressType);

        int databaseSizeBeforeDelete = addressTypeRepository.findAll().size();

        // Delete the addressType
        restAddressTypeMockMvc.perform(delete("/api/address-types/{id}", addressType.getAddressType())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AddressType> addressTypeList = addressTypeRepository.findAll();
        assertThat(addressTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
