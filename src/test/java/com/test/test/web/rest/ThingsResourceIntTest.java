package com.test.test.web.rest;

import com.test.test.BattleApp;

import com.test.test.domain.Things;
import com.test.test.repository.ThingsRepository;
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
 * Test class for the ThingsResource REST controller.
 *
 * @see ThingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BattleApp.class)
public class ThingsResourceIntTest {

    private static final Integer DEFAULT_PID = 1;
    private static final Integer UPDATED_PID = 2;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Integer DEFAULT_BELONG = 1;
    private static final Integer UPDATED_BELONG = 2;

    @Autowired
    private ThingsRepository thingsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restThingsMockMvc;

    private Things things;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThingsResource thingsResource = new ThingsResource(thingsRepository);
        this.restThingsMockMvc = MockMvcBuilders.standaloneSetup(thingsResource)
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
    public static Things createEntity(EntityManager em) {
        Things things = new Things()
            .pid(DEFAULT_PID)
            .type(DEFAULT_TYPE)
            .belong(DEFAULT_BELONG);
        return things;
    }

    @Before
    public void initTest() {
        things = createEntity(em);
    }

    @Test
    @Transactional
    public void createThings() throws Exception {
        int databaseSizeBeforeCreate = thingsRepository.findAll().size();

        // Create the Things
        restThingsMockMvc.perform(post("/api/things")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(things)))
            .andExpect(status().isCreated());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeCreate + 1);
        Things testThings = thingsList.get(thingsList.size() - 1);
        assertThat(testThings.getPid()).isEqualTo(DEFAULT_PID);
        assertThat(testThings.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testThings.getBelong()).isEqualTo(DEFAULT_BELONG);
    }

    @Test
    @Transactional
    public void createThingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thingsRepository.findAll().size();

        // Create the Things with an existing ID
        things.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThingsMockMvc.perform(post("/api/things")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(things)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllThings() throws Exception {
        // Initialize the database
        thingsRepository.saveAndFlush(things);

        // Get all the thingsList
        restThingsMockMvc.perform(get("/api/things?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(things.getId().intValue())))
            .andExpect(jsonPath("$.[*].pid").value(hasItem(DEFAULT_PID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].belong").value(hasItem(DEFAULT_BELONG)));
    }

    @Test
    @Transactional
    public void getThings() throws Exception {
        // Initialize the database
        thingsRepository.saveAndFlush(things);

        // Get the things
        restThingsMockMvc.perform(get("/api/things/{id}", things.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(things.getId().intValue()))
            .andExpect(jsonPath("$.pid").value(DEFAULT_PID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.belong").value(DEFAULT_BELONG));
    }

    @Test
    @Transactional
    public void getNonExistingThings() throws Exception {
        // Get the things
        restThingsMockMvc.perform(get("/api/things/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThings() throws Exception {
        // Initialize the database
        thingsRepository.saveAndFlush(things);
        int databaseSizeBeforeUpdate = thingsRepository.findAll().size();

        // Update the things
        Things updatedThings = thingsRepository.findOne(things.getId());
        updatedThings
            .pid(UPDATED_PID)
            .type(UPDATED_TYPE)
            .belong(UPDATED_BELONG);

        restThingsMockMvc.perform(put("/api/things")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedThings)))
            .andExpect(status().isOk());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeUpdate);
        Things testThings = thingsList.get(thingsList.size() - 1);
        assertThat(testThings.getPid()).isEqualTo(UPDATED_PID);
        assertThat(testThings.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testThings.getBelong()).isEqualTo(UPDATED_BELONG);
    }

    @Test
    @Transactional
    public void updateNonExistingThings() throws Exception {
        int databaseSizeBeforeUpdate = thingsRepository.findAll().size();

        // Create the Things

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThingsMockMvc.perform(put("/api/things")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(things)))
            .andExpect(status().isCreated());

        // Validate the Things in the database
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteThings() throws Exception {
        // Initialize the database
        thingsRepository.saveAndFlush(things);
        int databaseSizeBeforeDelete = thingsRepository.findAll().size();

        // Get the things
        restThingsMockMvc.perform(delete("/api/things/{id}", things.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Things> thingsList = thingsRepository.findAll();
        assertThat(thingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Things.class);
    }
}
