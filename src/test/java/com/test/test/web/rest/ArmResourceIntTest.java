package com.test.test.web.rest;

import com.test.test.BattleApp;

import com.test.test.domain.Arm;
import com.test.test.repository.ArmRepository;
import com.test.test.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArmResource REST controller.
 *
 * @see ArmResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BattleApp.class)
public class ArmResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final Integer DEFAULT_ATTACK = 1;
    private static final Integer UPDATED_ATTACK = 2;

    private static final Integer DEFAULT_DEFENCE = 1;
    private static final Integer UPDATED_DEFENCE = 2;

    @Autowired
    private ArmRepository armRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArmMockMvc;

    private Arm arm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ArmResource armResource = new ArmResource(armRepository);
        this.restArmMockMvc = MockMvcBuilders.standaloneSetup(armResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arm createEntity(EntityManager em) {
        Arm arm = new Arm()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .attack(DEFAULT_ATTACK)
            .defence(DEFAULT_DEFENCE);
        return arm;
    }

    @Before
    public void initTest() {
        arm = createEntity(em);
    }

    @Test
    @Transactional
    public void createArm() throws Exception {
        int databaseSizeBeforeCreate = armRepository.findAll().size();

        // Create the Arm
        restArmMockMvc.perform(post("/api/arms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arm)))
            .andExpect(status().isCreated());

        // Validate the Arm in the database
        List<Arm> armList = armRepository.findAll();
        assertThat(armList).hasSize(databaseSizeBeforeCreate + 1);
        Arm testArm = armList.get(armList.size() - 1);
        assertThat(testArm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArm.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testArm.getAttack()).isEqualTo(DEFAULT_ATTACK);
        assertThat(testArm.getDefence()).isEqualTo(DEFAULT_DEFENCE);
    }

    @Test
    @Transactional
    public void createArmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = armRepository.findAll().size();

        // Create the Arm with an existing ID
        arm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArmMockMvc.perform(post("/api/arms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arm)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Arm> armList = armRepository.findAll();
        assertThat(armList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArms() throws Exception {
        // Initialize the database
        armRepository.saveAndFlush(arm);

        // Get all the armList
        restArmMockMvc.perform(get("/api/arms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arm.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].attack").value(hasItem(DEFAULT_ATTACK)))
            .andExpect(jsonPath("$.[*].defence").value(hasItem(DEFAULT_DEFENCE)));
    }

    @Test
    @Transactional
    public void getArm() throws Exception {
        // Initialize the database
        armRepository.saveAndFlush(arm);

        // Get the arm
        restArmMockMvc.perform(get("/api/arms/{id}", arm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(arm.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.attack").value(DEFAULT_ATTACK))
            .andExpect(jsonPath("$.defence").value(DEFAULT_DEFENCE));
    }

    @Test
    @Transactional
    public void getNonExistingArm() throws Exception {
        // Get the arm
        restArmMockMvc.perform(get("/api/arms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArm() throws Exception {
        // Initialize the database
        armRepository.saveAndFlush(arm);
        int databaseSizeBeforeUpdate = armRepository.findAll().size();

        // Update the arm
        Arm updatedArm = armRepository.findOne(arm.getId());
        updatedArm
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .attack(UPDATED_ATTACK)
            .defence(UPDATED_DEFENCE);

        restArmMockMvc.perform(put("/api/arms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArm)))
            .andExpect(status().isOk());

        // Validate the Arm in the database
        List<Arm> armList = armRepository.findAll();
        assertThat(armList).hasSize(databaseSizeBeforeUpdate);
        Arm testArm = armList.get(armList.size() - 1);
        assertThat(testArm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArm.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testArm.getAttack()).isEqualTo(UPDATED_ATTACK);
        assertThat(testArm.getDefence()).isEqualTo(UPDATED_DEFENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingArm() throws Exception {
        int databaseSizeBeforeUpdate = armRepository.findAll().size();

        // Create the Arm

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArmMockMvc.perform(put("/api/arms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arm)))
            .andExpect(status().isCreated());

        // Validate the Arm in the database
        List<Arm> armList = armRepository.findAll();
        assertThat(armList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArm() throws Exception {
        // Initialize the database
        armRepository.saveAndFlush(arm);
        int databaseSizeBeforeDelete = armRepository.findAll().size();

        // Get the arm
        restArmMockMvc.perform(delete("/api/arms/{id}", arm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Arm> armList = armRepository.findAll();
        assertThat(armList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Arm.class);
    }
}
