package com.sygneto.web.rest;

import com.sygneto.SygnetoApiApp;

import com.sygneto.domain.NodeLabel;
import com.sygneto.repository.NodeLabelRepository;
import com.sygneto.service.NodeLabelService;
import com.sygneto.web.rest.errors.ExceptionTranslator;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
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
 * Test class for the NodeLabelResource REST controller.
 *
 * @see NodeLabelResource
 */
@SpringBootTest(classes = SygnetoApiApp.class)
public class NodeLabelResourceIntTest {/*

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    @Autowired
    private NodeLabelRepository nodeLabelRepository;

    @Autowired
    private NodeLabelService nodeLabelService;

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

    private MockMvc restNodeLabelMockMvc;

    private NodeLabel nodeLabel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NodeLabelResource nodeLabelResource = new NodeLabelResource(nodeLabelService);
        this.restNodeLabelMockMvc = MockMvcBuilders.standaloneSetup(nodeLabelResource)
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
    public static NodeLabel createEntity(EntityManager em) {
        NodeLabel nodeLabel = new NodeLabel()
            .label(DEFAULT_LABEL)
            .action(DEFAULT_ACTION)
            .role(DEFAULT_ROLE);
        return nodeLabel;
    }

    @Before
    public void initTest() {
        nodeLabel = createEntity(em);
    }

    @Test
    @Transactional
    public void createNodeLabel() throws Exception {
        int databaseSizeBeforeCreate = nodeLabelRepository.findAll().size();

        // Create the NodeLabel
        restNodeLabelMockMvc.perform(post("/api/node-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeLabel)))
            .andExpect(status().isCreated());

        // Validate the NodeLabel in the database
        List<NodeLabel> nodeLabelList = nodeLabelRepository.findAll();
        assertThat(nodeLabelList).hasSize(databaseSizeBeforeCreate + 1);
        NodeLabel testNodeLabel = nodeLabelList.get(nodeLabelList.size() - 1);
        assertThat(testNodeLabel.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testNodeLabel.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testNodeLabel.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    public void createNodeLabelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nodeLabelRepository.findAll().size();

        // Create the NodeLabel with an existing ID
        nodeLabel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNodeLabelMockMvc.perform(post("/api/node-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeLabel)))
            .andExpect(status().isBadRequest());

        // Validate the NodeLabel in the database
        List<NodeLabel> nodeLabelList = nodeLabelRepository.findAll();
        assertThat(nodeLabelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNodeLabels() throws Exception {
        // Initialize the database
        nodeLabelRepository.saveAndFlush(nodeLabel);

        // Get all the nodeLabelList
        restNodeLabelMockMvc.perform(get("/api/node-labels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nodeLabel.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }
    
    @Test
    @Transactional
    public void getNodeLabel() throws Exception {
        // Initialize the database
        nodeLabelRepository.saveAndFlush(nodeLabel);

        // Get the nodeLabel
        restNodeLabelMockMvc.perform(get("/api/node-labels/{id}", nodeLabel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nodeLabel.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNodeLabel() throws Exception {
        // Get the nodeLabel
        restNodeLabelMockMvc.perform(get("/api/node-labels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNodeLabel() throws Exception {
        // Initialize the database
        nodeLabelService.save(nodeLabel);

        int databaseSizeBeforeUpdate = nodeLabelRepository.findAll().size();

        // Update the nodeLabel
        NodeLabel updatedNodeLabel = nodeLabelRepository.findById(nodeLabel.getId()).get();
        // Disconnect from session so that the updates on updatedNodeLabel are not directly saved in db
        em.detach(updatedNodeLabel);
        updatedNodeLabel
            .label(UPDATED_LABEL)
            .action(UPDATED_ACTION)
            .role(UPDATED_ROLE);

        restNodeLabelMockMvc.perform(put("/api/node-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNodeLabel)))
            .andExpect(status().isOk());

        // Validate the NodeLabel in the database
        List<NodeLabel> nodeLabelList = nodeLabelRepository.findAll();
        assertThat(nodeLabelList).hasSize(databaseSizeBeforeUpdate);
        NodeLabel testNodeLabel = nodeLabelList.get(nodeLabelList.size() - 1);
        assertThat(testNodeLabel.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testNodeLabel.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testNodeLabel.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void updateNonExistingNodeLabel() throws Exception {
        int databaseSizeBeforeUpdate = nodeLabelRepository.findAll().size();

        // Create the NodeLabel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNodeLabelMockMvc.perform(put("/api/node-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nodeLabel)))
            .andExpect(status().isBadRequest());

        // Validate the NodeLabel in the database
        List<NodeLabel> nodeLabelList = nodeLabelRepository.findAll();
        assertThat(nodeLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNodeLabel() throws Exception {
        // Initialize the database
        nodeLabelService.save(nodeLabel);

        int databaseSizeBeforeDelete = nodeLabelRepository.findAll().size();

        // Delete the nodeLabel
        restNodeLabelMockMvc.perform(delete("/api/node-labels/{id}", nodeLabel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NodeLabel> nodeLabelList = nodeLabelRepository.findAll();
        assertThat(nodeLabelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NodeLabel.class);
        NodeLabel nodeLabel1 = new NodeLabel();
        nodeLabel1.setId(1L);
        NodeLabel nodeLabel2 = new NodeLabel();
        nodeLabel2.setId(nodeLabel1.getId());
        assertThat(nodeLabel1).isEqualTo(nodeLabel2);
        nodeLabel2.setId(2L);
        assertThat(nodeLabel1).isNotEqualTo(nodeLabel2);
        nodeLabel1.setId(null);
        assertThat(nodeLabel1).isNotEqualTo(nodeLabel2);
    }
*/}
