package org.jackson.views.web.rest;

import org.jackson.views.JacksonviewsApp;

import org.jackson.views.domain.Language;
import org.jackson.views.repository.LanguageRepository;
import org.jackson.views.service.LanguageService;
import org.jackson.views.service.dto.LanguageDTO;
import org.jackson.views.service.mapper.LanguageMapper;
import org.jackson.views.web.rest.errors.ExceptionTranslator;
import org.jackson.views.service.dto.LanguageCriteria;
import org.jackson.views.service.LanguageQueryService;

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


import static org.jackson.views.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LanguageResource REST controller.
 *
 * @see LanguageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JacksonviewsApp.class)
public class LanguageResourceIntTest {

    private static final String DEFAULT_ALPHA_3_B = "AAA";
    private static final String UPDATED_ALPHA_3_B = "BBB";

    private static final String DEFAULT_ALPHA_2 = "AA";
    private static final String UPDATED_ALPHA_2 = "BB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FLAG_32 = "AAAAAAAAAA";
    private static final String UPDATED_FLAG_32 = "BBBBBBBBBB";

    private static final String DEFAULT_FLAG_128 = "AAAAAAAAAA";
    private static final String UPDATED_FLAG_128 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private LanguageMapper languageMapper;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private LanguageQueryService languageQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLanguageMockMvc;

    private Language language;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LanguageResource languageResource = new LanguageResource(languageService, languageQueryService);
        this.restLanguageMockMvc = MockMvcBuilders.standaloneSetup(languageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Language createEntity(EntityManager em) {
        Language language = new Language()
            .alpha3b(DEFAULT_ALPHA_3_B)
            .alpha2(DEFAULT_ALPHA_2)
            .name(DEFAULT_NAME)
            .flag32(DEFAULT_FLAG_32)
            .flag128(DEFAULT_FLAG_128)
            .activated(DEFAULT_ACTIVATED);
        return language;
    }

    @Before
    public void initTest() {
        language = createEntity(em);
    }

    @Test
    @Transactional
    public void createLanguage() throws Exception {
        int databaseSizeBeforeCreate = languageRepository.findAll().size();

        // Create the Language
        LanguageDTO languageDTO = languageMapper.toDto(language);
        restLanguageMockMvc.perform(post("/api/languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isCreated());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeCreate + 1);
        Language testLanguage = languageList.get(languageList.size() - 1);
        assertThat(testLanguage.getAlpha3b()).isEqualTo(DEFAULT_ALPHA_3_B);
        assertThat(testLanguage.getAlpha2()).isEqualTo(DEFAULT_ALPHA_2);
        assertThat(testLanguage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLanguage.getFlag32()).isEqualTo(DEFAULT_FLAG_32);
        assertThat(testLanguage.getFlag128()).isEqualTo(DEFAULT_FLAG_128);
        assertThat(testLanguage.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void createLanguageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = languageRepository.findAll().size();

        // Create the Language with an existing ID
        language.setId(1L);
        LanguageDTO languageDTO = languageMapper.toDto(language);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLanguageMockMvc.perform(post("/api/languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAlpha3bIsRequired() throws Exception {
        int databaseSizeBeforeTest = languageRepository.findAll().size();
        // set the field null
        language.setAlpha3b(null);

        // Create the Language, which fails.
        LanguageDTO languageDTO = languageMapper.toDto(language);

        restLanguageMockMvc.perform(post("/api/languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isBadRequest());

        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAlpha2IsRequired() throws Exception {
        int databaseSizeBeforeTest = languageRepository.findAll().size();
        // set the field null
        language.setAlpha2(null);

        // Create the Language, which fails.
        LanguageDTO languageDTO = languageMapper.toDto(language);

        restLanguageMockMvc.perform(post("/api/languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isBadRequest());

        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = languageRepository.findAll().size();
        // set the field null
        language.setName(null);

        // Create the Language, which fails.
        LanguageDTO languageDTO = languageMapper.toDto(language);

        restLanguageMockMvc.perform(post("/api/languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isBadRequest());

        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLanguages() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList
        restLanguageMockMvc.perform(get("/api/languages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(language.getId().intValue())))
            .andExpect(jsonPath("$.[*].alpha3b").value(hasItem(DEFAULT_ALPHA_3_B.toString())))
            .andExpect(jsonPath("$.[*].alpha2").value(hasItem(DEFAULT_ALPHA_2.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].flag32").value(hasItem(DEFAULT_FLAG_32.toString())))
            .andExpect(jsonPath("$.[*].flag128").value(hasItem(DEFAULT_FLAG_128.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLanguage() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get the language
        restLanguageMockMvc.perform(get("/api/languages/{id}", language.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(language.getId().intValue()))
            .andExpect(jsonPath("$.alpha3b").value(DEFAULT_ALPHA_3_B.toString()))
            .andExpect(jsonPath("$.alpha2").value(DEFAULT_ALPHA_2.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.flag32").value(DEFAULT_FLAG_32.toString()))
            .andExpect(jsonPath("$.flag128").value(DEFAULT_FLAG_128.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllLanguagesByAlpha3bIsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where alpha3b equals to DEFAULT_ALPHA_3_B
        defaultLanguageShouldBeFound("alpha3b.equals=" + DEFAULT_ALPHA_3_B);

        // Get all the languageList where alpha3b equals to UPDATED_ALPHA_3_B
        defaultLanguageShouldNotBeFound("alpha3b.equals=" + UPDATED_ALPHA_3_B);
    }

    @Test
    @Transactional
    public void getAllLanguagesByAlpha3bIsInShouldWork() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where alpha3b in DEFAULT_ALPHA_3_B or UPDATED_ALPHA_3_B
        defaultLanguageShouldBeFound("alpha3b.in=" + DEFAULT_ALPHA_3_B + "," + UPDATED_ALPHA_3_B);

        // Get all the languageList where alpha3b equals to UPDATED_ALPHA_3_B
        defaultLanguageShouldNotBeFound("alpha3b.in=" + UPDATED_ALPHA_3_B);
    }

    @Test
    @Transactional
    public void getAllLanguagesByAlpha3bIsNullOrNotNull() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where alpha3b is not null
        defaultLanguageShouldBeFound("alpha3b.specified=true");

        // Get all the languageList where alpha3b is null
        defaultLanguageShouldNotBeFound("alpha3b.specified=false");
    }

    @Test
    @Transactional
    public void getAllLanguagesByAlpha2IsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where alpha2 equals to DEFAULT_ALPHA_2
        defaultLanguageShouldBeFound("alpha2.equals=" + DEFAULT_ALPHA_2);

        // Get all the languageList where alpha2 equals to UPDATED_ALPHA_2
        defaultLanguageShouldNotBeFound("alpha2.equals=" + UPDATED_ALPHA_2);
    }

    @Test
    @Transactional
    public void getAllLanguagesByAlpha2IsInShouldWork() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where alpha2 in DEFAULT_ALPHA_2 or UPDATED_ALPHA_2
        defaultLanguageShouldBeFound("alpha2.in=" + DEFAULT_ALPHA_2 + "," + UPDATED_ALPHA_2);

        // Get all the languageList where alpha2 equals to UPDATED_ALPHA_2
        defaultLanguageShouldNotBeFound("alpha2.in=" + UPDATED_ALPHA_2);
    }

    @Test
    @Transactional
    public void getAllLanguagesByAlpha2IsNullOrNotNull() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where alpha2 is not null
        defaultLanguageShouldBeFound("alpha2.specified=true");

        // Get all the languageList where alpha2 is null
        defaultLanguageShouldNotBeFound("alpha2.specified=false");
    }

    @Test
    @Transactional
    public void getAllLanguagesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where name equals to DEFAULT_NAME
        defaultLanguageShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the languageList where name equals to UPDATED_NAME
        defaultLanguageShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLanguagesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLanguageShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the languageList where name equals to UPDATED_NAME
        defaultLanguageShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLanguagesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where name is not null
        defaultLanguageShouldBeFound("name.specified=true");

        // Get all the languageList where name is null
        defaultLanguageShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllLanguagesByFlag32IsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where flag32 equals to DEFAULT_FLAG_32
        defaultLanguageShouldBeFound("flag32.equals=" + DEFAULT_FLAG_32);

        // Get all the languageList where flag32 equals to UPDATED_FLAG_32
        defaultLanguageShouldNotBeFound("flag32.equals=" + UPDATED_FLAG_32);
    }

    @Test
    @Transactional
    public void getAllLanguagesByFlag32IsInShouldWork() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where flag32 in DEFAULT_FLAG_32 or UPDATED_FLAG_32
        defaultLanguageShouldBeFound("flag32.in=" + DEFAULT_FLAG_32 + "," + UPDATED_FLAG_32);

        // Get all the languageList where flag32 equals to UPDATED_FLAG_32
        defaultLanguageShouldNotBeFound("flag32.in=" + UPDATED_FLAG_32);
    }

    @Test
    @Transactional
    public void getAllLanguagesByFlag32IsNullOrNotNull() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where flag32 is not null
        defaultLanguageShouldBeFound("flag32.specified=true");

        // Get all the languageList where flag32 is null
        defaultLanguageShouldNotBeFound("flag32.specified=false");
    }

    @Test
    @Transactional
    public void getAllLanguagesByFlag128IsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where flag128 equals to DEFAULT_FLAG_128
        defaultLanguageShouldBeFound("flag128.equals=" + DEFAULT_FLAG_128);

        // Get all the languageList where flag128 equals to UPDATED_FLAG_128
        defaultLanguageShouldNotBeFound("flag128.equals=" + UPDATED_FLAG_128);
    }

    @Test
    @Transactional
    public void getAllLanguagesByFlag128IsInShouldWork() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where flag128 in DEFAULT_FLAG_128 or UPDATED_FLAG_128
        defaultLanguageShouldBeFound("flag128.in=" + DEFAULT_FLAG_128 + "," + UPDATED_FLAG_128);

        // Get all the languageList where flag128 equals to UPDATED_FLAG_128
        defaultLanguageShouldNotBeFound("flag128.in=" + UPDATED_FLAG_128);
    }

    @Test
    @Transactional
    public void getAllLanguagesByFlag128IsNullOrNotNull() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where flag128 is not null
        defaultLanguageShouldBeFound("flag128.specified=true");

        // Get all the languageList where flag128 is null
        defaultLanguageShouldNotBeFound("flag128.specified=false");
    }

    @Test
    @Transactional
    public void getAllLanguagesByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where activated equals to DEFAULT_ACTIVATED
        defaultLanguageShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the languageList where activated equals to UPDATED_ACTIVATED
        defaultLanguageShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllLanguagesByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultLanguageShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the languageList where activated equals to UPDATED_ACTIVATED
        defaultLanguageShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllLanguagesByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where activated is not null
        defaultLanguageShouldBeFound("activated.specified=true");

        // Get all the languageList where activated is null
        defaultLanguageShouldNotBeFound("activated.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLanguageShouldBeFound(String filter) throws Exception {
        restLanguageMockMvc.perform(get("/api/languages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(language.getId().intValue())))
            .andExpect(jsonPath("$.[*].alpha3b").value(hasItem(DEFAULT_ALPHA_3_B.toString())))
            .andExpect(jsonPath("$.[*].alpha2").value(hasItem(DEFAULT_ALPHA_2.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].flag32").value(hasItem(DEFAULT_FLAG_32.toString())))
            .andExpect(jsonPath("$.[*].flag128").value(hasItem(DEFAULT_FLAG_128.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));

        // Check, that the count call also returns 1
        restLanguageMockMvc.perform(get("/api/languages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLanguageShouldNotBeFound(String filter) throws Exception {
        restLanguageMockMvc.perform(get("/api/languages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLanguageMockMvc.perform(get("/api/languages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLanguage() throws Exception {
        // Get the language
        restLanguageMockMvc.perform(get("/api/languages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLanguage() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        int databaseSizeBeforeUpdate = languageRepository.findAll().size();

        // Update the language
        Language updatedLanguage = languageRepository.findById(language.getId()).get();
        // Disconnect from session so that the updates on updatedLanguage are not directly saved in db
        em.detach(updatedLanguage);
        updatedLanguage
            .alpha3b(UPDATED_ALPHA_3_B)
            .alpha2(UPDATED_ALPHA_2)
            .name(UPDATED_NAME)
            .flag32(UPDATED_FLAG_32)
            .flag128(UPDATED_FLAG_128)
            .activated(UPDATED_ACTIVATED);
        LanguageDTO languageDTO = languageMapper.toDto(updatedLanguage);

        restLanguageMockMvc.perform(put("/api/languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isOk());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
        Language testLanguage = languageList.get(languageList.size() - 1);
        assertThat(testLanguage.getAlpha3b()).isEqualTo(UPDATED_ALPHA_3_B);
        assertThat(testLanguage.getAlpha2()).isEqualTo(UPDATED_ALPHA_2);
        assertThat(testLanguage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLanguage.getFlag32()).isEqualTo(UPDATED_FLAG_32);
        assertThat(testLanguage.getFlag128()).isEqualTo(UPDATED_FLAG_128);
        assertThat(testLanguage.isActivated()).isEqualTo(UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void updateNonExistingLanguage() throws Exception {
        int databaseSizeBeforeUpdate = languageRepository.findAll().size();

        // Create the Language
        LanguageDTO languageDTO = languageMapper.toDto(language);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLanguageMockMvc.perform(put("/api/languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(languageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLanguage() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        int databaseSizeBeforeDelete = languageRepository.findAll().size();

        // Get the language
        restLanguageMockMvc.perform(delete("/api/languages/{id}", language.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Language.class);
        Language language1 = new Language();
        language1.setId(1L);
        Language language2 = new Language();
        language2.setId(language1.getId());
        assertThat(language1).isEqualTo(language2);
        language2.setId(2L);
        assertThat(language1).isNotEqualTo(language2);
        language1.setId(null);
        assertThat(language1).isNotEqualTo(language2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LanguageDTO.class);
        LanguageDTO languageDTO1 = new LanguageDTO();
        languageDTO1.setId(1L);
        LanguageDTO languageDTO2 = new LanguageDTO();
        assertThat(languageDTO1).isNotEqualTo(languageDTO2);
        languageDTO2.setId(languageDTO1.getId());
        assertThat(languageDTO1).isEqualTo(languageDTO2);
        languageDTO2.setId(2L);
        assertThat(languageDTO1).isNotEqualTo(languageDTO2);
        languageDTO1.setId(null);
        assertThat(languageDTO1).isNotEqualTo(languageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(languageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(languageMapper.fromId(null)).isNull();
    }
}
