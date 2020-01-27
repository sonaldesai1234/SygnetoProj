package com.sygneto.web.rest;

import org.springframework.boot.test.context.SpringBootTest;

import com.sygneto.SygnetoApiApp;

/**
 * Test class for the NodeResource REST controller.
 *
 * @see NodeResource
 */
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = SygnetoApiApp.class)
public class NodeResourceIntTest {/*

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private NodeService nodeService;

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

    private MockMvc restNodeMockMvc;

    private Node node;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NodeResource nodeResource = new NodeResource(nodeService);
        this.restNodeMockMvc = MockMvcBuilders.standaloneSetup(nodeResource)
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
    public static Node createEntity(EntityManager em) {
        Node node = new Node();
        return node;
    }

    @Before
    public void initTest() {
        node = createEntity(em);
    }

    @Test
    @Transactional
    public void createNode() throws Exception {
        int databaseSizeBeforeCreate = nodeRepository.findAll().size();

        // Create the Node
        restNodeMockMvc.perform(post("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(node)))
            .andExpect(status().isCreated());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeCreate + 1);
        Node testNode = nodeList.get(nodeList.size() - 1);
    }

    @Test
    @Transactional
    public void createNodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nodeRepository.findAll().size();

        // Create the Node with an existing ID
        node.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNodeMockMvc.perform(post("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(node)))
            .andExpect(status().isBadRequest());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNodes() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);

        // Get all the nodeList
        restNodeMockMvc.perform(get("/api/nodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(node.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getNode() throws Exception {
        // Initialize the database
        nodeRepository.saveAndFlush(node);

        // Get the node
        restNodeMockMvc.perform(get("/api/nodes/{id}", node.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(node.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNode() throws Exception {
        // Get the node
        restNodeMockMvc.perform(get("/api/nodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNode() throws Exception {
        // Initialize the database
        nodeService.save(node);

        int databaseSizeBeforeUpdate = nodeRepository.findAll().size();

        // Update the node
        Node updatedNode = nodeRepository.findById(node.getId()).get();
        // Disconnect from session so that the updates on updatedNode are not directly saved in db
        em.detach(updatedNode);

        restNodeMockMvc.perform(put("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNode)))
            .andExpect(status().isOk());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeUpdate);
        Node testNode = nodeList.get(nodeList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingNode() throws Exception {
        int databaseSizeBeforeUpdate = nodeRepository.findAll().size();

        // Create the Node

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNodeMockMvc.perform(put("/api/nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(node)))
            .andExpect(status().isBadRequest());

        // Validate the Node in the database
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNode() throws Exception {
        // Initialize the database
        nodeService.save(node);

        int databaseSizeBeforeDelete = nodeRepository.findAll().size();

        // Delete the node
        restNodeMockMvc.perform(delete("/api/nodes/{id}", node.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Node> nodeList = nodeRepository.findAll();
        assertThat(nodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Node.class);
        Node node1 = new Node();
        node1.setId(1L);
        Node node2 = new Node();
        node2.setId(node1.getId());
        assertThat(node1).isEqualTo(node2);
        node2.setId(2L);
        assertThat(node1).isNotEqualTo(node2);
        node1.setId(null);
        assertThat(node1).isNotEqualTo(node2);
    }
*/}
