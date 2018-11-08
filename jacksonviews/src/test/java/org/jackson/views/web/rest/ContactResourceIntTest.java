package org.jackson.views.web.rest;

import org.jackson.views.JacksonviewsApp;

import org.jackson.views.domain.Contact;
import org.jackson.views.domain.Language;
import org.jackson.views.repository.ContactRepository;
import org.jackson.views.service.ContactService;
import org.jackson.views.service.dto.ContactDTO;
import org.jackson.views.service.mapper.ContactMapper;
import org.jackson.views.web.rest.errors.ExceptionTranslator;
import org.jackson.views.service.dto.ContactCriteria;
import org.jackson.views.service.ContactQueryService;

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
 * Test class for the ContactResource REST controller.
 *
 * @see ContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JacksonviewsApp.class)
public class ContactResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactQueryService contactQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContactMockMvc;

    private Contact contact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactResource contactResource = new ContactResource(contactService, contactQueryService);
        this.restContactMockMvc = MockMvcBuilders.standaloneSetup(contactResource)
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
    public static Contact createEntity(EntityManager em) {
        Contact contact = new Contact()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL);
        // Add required entity
        Language language = LanguageResourceIntTest.createEntity(em);
        em.persist(language);
        em.flush();
        contact.setLanguage(language);
        return contact;
    }

    @Before
    public void initTest() {
        contact = createEntity(em);
    }

    @Test
    @Transactional
    public void createContact() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);
        restContactMockMvc.perform(post("/api/contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isCreated());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate + 1);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContact.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testContact.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        // Create the Contact with an existing ID
        contact.setId(1L);
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMockMvc.perform(post("/api/contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setFirstName(null);

        // Create the Contact, which fails.
        ContactDTO contactDTO = contactMapper.toDto(contact);

        restContactMockMvc.perform(post("/api/contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setLastName(null);

        // Create the Contact, which fails.
        ContactDTO contactDTO = contactMapper.toDto(contact);

        restContactMockMvc.perform(post("/api/contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContacts() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList
        restContactMockMvc.perform(get("/api/contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get the contact
        restContactMockMvc.perform(get("/api/contacts/{id}", contact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contact.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getAllContactsByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where firstName equals to DEFAULT_FIRST_NAME
        defaultContactShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the contactList where firstName equals to UPDATED_FIRST_NAME
        defaultContactShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllContactsByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultContactShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the contactList where firstName equals to UPDATED_FIRST_NAME
        defaultContactShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllContactsByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where firstName is not null
        defaultContactShouldBeFound("firstName.specified=true");

        // Get all the contactList where firstName is null
        defaultContactShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    public void getAllContactsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where lastName equals to DEFAULT_LAST_NAME
        defaultContactShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the contactList where lastName equals to UPDATED_LAST_NAME
        defaultContactShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllContactsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultContactShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the contactList where lastName equals to UPDATED_LAST_NAME
        defaultContactShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllContactsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where lastName is not null
        defaultContactShouldBeFound("lastName.specified=true");

        // Get all the contactList where lastName is null
        defaultContactShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    public void getAllContactsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where email equals to DEFAULT_EMAIL
        defaultContactShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the contactList where email equals to UPDATED_EMAIL
        defaultContactShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContactsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultContactShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the contactList where email equals to UPDATED_EMAIL
        defaultContactShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContactsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where email is not null
        defaultContactShouldBeFound("email.specified=true");

        // Get all the contactList where email is null
        defaultContactShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllContactsByLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        Language language = LanguageResourceIntTest.createEntity(em);
        em.persist(language);
        em.flush();
        contact.setLanguage(language);
        contactRepository.saveAndFlush(contact);
        Long languageId = language.getId();

        // Get all the contactList where language equals to languageId
        defaultContactShouldBeFound("languageId.equals=" + languageId);

        // Get all the contactList where language equals to languageId + 1
        defaultContactShouldNotBeFound("languageId.equals=" + (languageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultContactShouldBeFound(String filter) throws Exception {
        restContactMockMvc.perform(get("/api/contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));

        // Check, that the count call also returns 1
        restContactMockMvc.perform(get("/api/contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultContactShouldNotBeFound(String filter) throws Exception {
        restContactMockMvc.perform(get("/api/contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContactMockMvc.perform(get("/api/contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingContact() throws Exception {
        // Get the contact
        restContactMockMvc.perform(get("/api/contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact
        Contact updatedContact = contactRepository.findById(contact.getId()).get();
        // Disconnect from session so that the updates on updatedContact are not directly saved in db
        em.detach(updatedContact);
        updatedContact
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL);
        ContactDTO contactDTO = contactMapper.toDto(updatedContact);

        restContactMockMvc.perform(put("/api/contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContact.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testContact.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMockMvc.perform(put("/api/contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeDelete = contactRepository.findAll().size();

        // Get the contact
        restContactMockMvc.perform(delete("/api/contacts/{id}", contact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contact.class);
        Contact contact1 = new Contact();
        contact1.setId(1L);
        Contact contact2 = new Contact();
        contact2.setId(contact1.getId());
        assertThat(contact1).isEqualTo(contact2);
        contact2.setId(2L);
        assertThat(contact1).isNotEqualTo(contact2);
        contact1.setId(null);
        assertThat(contact1).isNotEqualTo(contact2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactDTO.class);
        ContactDTO contactDTO1 = new ContactDTO();
        contactDTO1.setId(1L);
        ContactDTO contactDTO2 = new ContactDTO();
        assertThat(contactDTO1).isNotEqualTo(contactDTO2);
        contactDTO2.setId(contactDTO1.getId());
        assertThat(contactDTO1).isEqualTo(contactDTO2);
        contactDTO2.setId(2L);
        assertThat(contactDTO1).isNotEqualTo(contactDTO2);
        contactDTO1.setId(null);
        assertThat(contactDTO1).isNotEqualTo(contactDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contactMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contactMapper.fromId(null)).isNull();
    }
}
