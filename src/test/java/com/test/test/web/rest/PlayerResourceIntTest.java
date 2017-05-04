package com.test.test.web.rest;

import com.test.test.BattleApp;

import com.test.test.domain.Player;
import com.test.test.repository.PlayerRepository;
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
 * Test class for the PlayerResource REST controller.
 *
 * @see PlayerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BattleApp.class)
public class PlayerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_HP = 1;
    private static final Integer UPDATED_HP = 2;

    private static final Integer DEFAULT_MONEY = 1;
    private static final Integer UPDATED_MONEY = 2;

    private static final Integer DEFAULT_STRENGTH = 1;
    private static final Integer UPDATED_STRENGTH = 2;

    private static final Integer DEFAULT_AGILITY = 1;
    private static final Integer UPDATED_AGILITY = 2;

    private static final Integer DEFAULT_ATTACK = 1;
    private static final Integer UPDATED_ATTACK = 2;

    private static final Integer DEFAULT_DEFENCE = 1;
    private static final Integer UPDATED_DEFENCE = 2;

    private static final Integer DEFAULT_FATIGUE = 1;
    private static final Integer UPDATED_FATIGUE = 2;

    private static final Long DEFAULT_BELONG = 1L;
    private static final Long UPDATED_BELONG = 2L;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlayerMockMvc;

    private Player player;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlayerResource playerResource = new PlayerResource(playerRepository);
        this.restPlayerMockMvc = MockMvcBuilders.standaloneSetup(playerResource)
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
    public static Player createEntity(EntityManager em) {
        Player player = new Player()
            .name(DEFAULT_NAME)
            .mail(DEFAULT_MAIL)
            .password(DEFAULT_PASSWORD)
            .image(DEFAULT_IMAGE)
            .hp(DEFAULT_HP)
            .money(DEFAULT_MONEY)
            .strength(DEFAULT_STRENGTH)
            .agility(DEFAULT_AGILITY)
            .attack(DEFAULT_ATTACK)
            .defence(DEFAULT_DEFENCE)
            .fatigue(DEFAULT_FATIGUE)
            .belong(DEFAULT_BELONG);
        return player;
    }

    @Before
    public void initTest() {
        player = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlayer() throws Exception {
        int databaseSizeBeforeCreate = playerRepository.findAll().size();

        // Create the Player
        restPlayerMockMvc.perform(post("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isCreated());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate + 1);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlayer.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testPlayer.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testPlayer.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPlayer.getHp()).isEqualTo(DEFAULT_HP);
        assertThat(testPlayer.getMoney()).isEqualTo(DEFAULT_MONEY);
        assertThat(testPlayer.getStrength()).isEqualTo(DEFAULT_STRENGTH);
        assertThat(testPlayer.getAgility()).isEqualTo(DEFAULT_AGILITY);
        assertThat(testPlayer.getAttack()).isEqualTo(DEFAULT_ATTACK);
        assertThat(testPlayer.getDefence()).isEqualTo(DEFAULT_DEFENCE);
        assertThat(testPlayer.getFatigue()).isEqualTo(DEFAULT_FATIGUE);
        assertThat(testPlayer.getBelong()).isEqualTo(DEFAULT_BELONG);
    }

    @Test
    @Transactional
    public void createPlayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playerRepository.findAll().size();

        // Create the Player with an existing ID
        player.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerMockMvc.perform(post("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlayers() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList
        restPlayerMockMvc.perform(get("/api/players?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(player.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].hp").value(hasItem(DEFAULT_HP)))
            .andExpect(jsonPath("$.[*].money").value(hasItem(DEFAULT_MONEY)))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH)))
            .andExpect(jsonPath("$.[*].agility").value(hasItem(DEFAULT_AGILITY)))
            .andExpect(jsonPath("$.[*].attack").value(hasItem(DEFAULT_ATTACK)))
            .andExpect(jsonPath("$.[*].defence").value(hasItem(DEFAULT_DEFENCE)))
            .andExpect(jsonPath("$.[*].fatigue").value(hasItem(DEFAULT_FATIGUE)))
            .andExpect(jsonPath("$.[*].belong").value(hasItem(DEFAULT_BELONG.intValue())));
    }

    @Test
    @Transactional
    public void getPlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get the player
        restPlayerMockMvc.perform(get("/api/players/{id}", player.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(player.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.hp").value(DEFAULT_HP))
            .andExpect(jsonPath("$.money").value(DEFAULT_MONEY))
            .andExpect(jsonPath("$.strength").value(DEFAULT_STRENGTH))
            .andExpect(jsonPath("$.agility").value(DEFAULT_AGILITY))
            .andExpect(jsonPath("$.attack").value(DEFAULT_ATTACK))
            .andExpect(jsonPath("$.defence").value(DEFAULT_DEFENCE))
            .andExpect(jsonPath("$.fatigue").value(DEFAULT_FATIGUE))
            .andExpect(jsonPath("$.belong").value(DEFAULT_BELONG.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlayer() throws Exception {
        // Get the player
        restPlayerMockMvc.perform(get("/api/players/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player
        Player updatedPlayer = playerRepository.findOne(player.getId());
        updatedPlayer
            .name(UPDATED_NAME)
            .mail(UPDATED_MAIL)
            .password(UPDATED_PASSWORD)
            .image(UPDATED_IMAGE)
            .hp(UPDATED_HP)
            .money(UPDATED_MONEY)
            .strength(UPDATED_STRENGTH)
            .agility(UPDATED_AGILITY)
            .attack(UPDATED_ATTACK)
            .defence(UPDATED_DEFENCE)
            .fatigue(UPDATED_FATIGUE)
            .belong(UPDATED_BELONG);

        restPlayerMockMvc.perform(put("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlayer)))
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlayer.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testPlayer.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testPlayer.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPlayer.getHp()).isEqualTo(UPDATED_HP);
        assertThat(testPlayer.getMoney()).isEqualTo(UPDATED_MONEY);
        assertThat(testPlayer.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testPlayer.getAgility()).isEqualTo(UPDATED_AGILITY);
        assertThat(testPlayer.getAttack()).isEqualTo(UPDATED_ATTACK);
        assertThat(testPlayer.getDefence()).isEqualTo(UPDATED_DEFENCE);
        assertThat(testPlayer.getFatigue()).isEqualTo(UPDATED_FATIGUE);
        assertThat(testPlayer.getBelong()).isEqualTo(UPDATED_BELONG);
    }

    @Test
    @Transactional
    public void updateNonExistingPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Create the Player

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlayerMockMvc.perform(put("/api/players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isCreated());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);
        int databaseSizeBeforeDelete = playerRepository.findAll().size();

        // Get the player
        restPlayerMockMvc.perform(delete("/api/players/{id}", player.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Player.class);
    }
}
