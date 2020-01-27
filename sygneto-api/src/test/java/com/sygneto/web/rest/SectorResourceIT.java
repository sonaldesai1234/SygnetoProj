package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;
import com.sygneto.domain.Sector;
import com.sygneto.repository.SectorRepository;
import com.sygneto.service.SectorService;
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
 * Integration tests for the {@link SectorResource} REST controller.
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class SectorResourceIT {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private SectorService sectorService;

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

    private MockMvc restSectorMockMvc;

    private Sector sector;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SectorResource sectorResource = new SectorResource(sectorService);
        this.restSectorMockMvc = MockMvcBuilders.standaloneSetup(sectorResource)
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
    public static Sector createEntity(EntityManager em) {
        Sector sector = new Sector();
        return sector;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sector createUpdatedEntity(EntityManager em) {
        Sector sector = new Sector();
        return sector;
    }

    @BeforeEach
    public void initTest() {
        sector = createEntity(em);
    }

    @Test
    @Transactional
    public void createSector() throws Exception {
        int databaseSizeBeforeCreate = sectorRepository.findAll().size();

        // Create the Sector
        restSectorMockMvc.perform(post("/api/sectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sector)))
            .andExpect(status().isCreated());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeCreate + 1);
        Sector testSector = sectorList.get(sectorList.size() - 1);
    }

    @Test
    @Transactional
    public void createSectorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sectorRepository.findAll().size();

        // Create the Sector with an existing ID
        sector.setSectorName("1L");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectorMockMvc.perform(post("/api/sectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sector)))
            .andExpect(status().isBadRequest());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSectors() throws Exception {
        // Initialize the database
        sectorRepository.saveAndFlush(sector);

        // Get all the sectorList
        restSectorMockMvc.perform(get("/api/sectors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sector.getSectorName().toString())));
    }
    
    @Test
    @Transactional
    public void getSector() throws Exception {
        // Initialize the database
        sectorRepository.saveAndFlush(sector);

        // Get the sector
        restSectorMockMvc.perform(get("/api/sectors/{id}", sector.getSectorName()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sector.getSectorName().toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSector() throws Exception {
        // Get the sector
        restSectorMockMvc.perform(get("/api/sectors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSector() throws Exception {
        // Initialize the database
        sectorService.save(sector);

        int databaseSizeBeforeUpdate = sectorRepository.findAll().size();

        // Update the sector
        Object updatedSector = sectorRepository.findBySectorName(sector.getSectorName());
        // Disconnect from session so that the updates on updatedSector are not directly saved in db
        em.detach(updatedSector);

        restSectorMockMvc.perform(put("/api/sectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSector)))
            .andExpect(status().isOk());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeUpdate);
        Sector testSector = sectorList.get(sectorList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSector() throws Exception {
        int databaseSizeBeforeUpdate = sectorRepository.findAll().size();

        // Create the Sector

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSectorMockMvc.perform(put("/api/sectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sector)))
            .andExpect(status().isBadRequest());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSector() throws Exception {
        // Initialize the database
        sectorService.save(sector);

        int databaseSizeBeforeDelete = sectorRepository.findAll().size();

        // Delete the sector
        restSectorMockMvc.perform(delete("/api/sectors/{id}", sector.getSectorName())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sector.class);
        Sector sector1 = new Sector();
        sector1.setSectorName("1L");
        Sector sector2 = new Sector();
        sector2.setSectorName(sector1.getSectorName());
        assertThat(sector1).isEqualTo(sector2);
        sector2.setSectorName("2L");
        assertThat(sector1).isNotEqualTo(sector2);
        sector1.setSectorName(null);
        assertThat(sector1).isNotEqualTo(sector2);
    }
}
