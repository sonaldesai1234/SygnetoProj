package com.sygneto.web.rest;

import org.springframework.boot.test.context.SpringBootTest;

import com.sygneto.SygnetoApiApp;

/**
 * Test class for the ChildrenResource REST controller.
 *
 * @see ChildrenResource
 */
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = SygnetoApiApp.class)
public class ChildrenResourceIntTest {/*

    @Autowired
    private ChildrenRepository childrenRepository;

    @Autowired
    private ChildrenService childrenService;

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

    private MockMvc restChildrenMockMvc;

    private Children children;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChildrenResource childrenResource = new ChildrenResource(childrenService);
        this.restChildrenMockMvc = MockMvcBuilders.standaloneSetup(childrenResource)
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
    public static Children createEntity(EntityManager em) {
        Children children = new Children();
        return children;
    }

    @Before
    public void initTest() {
        children = createEntity(em);
    }

    @Test
    @Transactional
    public void createChildren() throws Exception {
        int databaseSizeBeforeCreate = childrenRepository.findAll().size();

        // Create the Children
        restChildrenMockMvc.perform(post("/api/children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(children)))
            .andExpect(status().isCreated());

        // Validate the Children in the database
        List<Children> childrenList = childrenRepository.findAll();
        assertThat(childrenList).hasSize(databaseSizeBeforeCreate + 1);
        Children testChildren = childrenList.get(childrenList.size() - 1);
    }

    @Test
    @Transactional
    public void createChildrenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = childrenRepository.findAll().size();

        // Create the Children with an existing ID
        children.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChildrenMockMvc.perform(post("/api/children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(children)))
            .andExpect(status().isBadRequest());

        // Validate the Children in the database
        List<Children> childrenList = childrenRepository.findAll();
        assertThat(childrenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChildren() throws Exception {
        // Initialize the database
        childrenRepository.saveAndFlush(children);

        // Get all the childrenList
        restChildrenMockMvc.perform(get("/api/children?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(children.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getChildren() throws Exception {
        // Initialize the database
        childrenRepository.saveAndFlush(children);

        // Get the children
        restChildrenMockMvc.perform(get("/api/children/{id}", children.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(children.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingChildren() throws Exception {
        // Get the children
        restChildrenMockMvc.perform(get("/api/children/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChildren() throws Exception {
        // Initialize the database
        childrenService.save(children);

        int databaseSizeBeforeUpdate = childrenRepository.findAll().size();

        // Update the children
        Children updatedChildren = childrenRepository.findById(children.getId()).get();
        // Disconnect from session so that the updates on updatedChildren are not directly saved in db
        em.detach(updatedChildren);

        restChildrenMockMvc.perform(put("/api/children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChildren)))
            .andExpect(status().isOk());

        // Validate the Children in the database
        List<Children> childrenList = childrenRepository.findAll();
        assertThat(childrenList).hasSize(databaseSizeBeforeUpdate);
        Children testChildren = childrenList.get(childrenList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingChildren() throws Exception {
        int databaseSizeBeforeUpdate = childrenRepository.findAll().size();

        // Create the Children

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChildrenMockMvc.perform(put("/api/children")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(children)))
            .andExpect(status().isBadRequest());

        // Validate the Children in the database
        List<Children> childrenList = childrenRepository.findAll();
        assertThat(childrenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChildren() throws Exception {
        // Initialize the database
        childrenService.save(children);

        int databaseSizeBeforeDelete = childrenRepository.findAll().size();

        // Delete the children
        restChildrenMockMvc.perform(delete("/api/children/{id}", children.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Children> childrenList = childrenRepository.findAll();
        assertThat(childrenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Children.class);
        Children children1 = new Children();
        children1.setId(1L);
        Children children2 = new Children();
        children2.setId(children1.getId());
        assertThat(children1).isEqualTo(children2);
        children2.setId(2L);
        assertThat(children1).isNotEqualTo(children2);
        children1.setId(null);
        assertThat(children1).isNotEqualTo(children2);
    }
*/}
