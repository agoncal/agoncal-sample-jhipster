package org.combo.autocompleteentity.service;

import org.combo.autocompleteentity.domain.Language;
import org.combo.autocompleteentity.repository.LanguageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Language.
 */
@Service
@Transactional
public class LanguageService {

    private final Logger log = LoggerFactory.getLogger(LanguageService.class);

    private LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    /**
     * Save a language.
     *
     * @param language the entity to save
     * @return the persisted entity
     */
    public Language save(Language language) {
        log.debug("Request to save Language : {}", language);
        return languageRepository.save(language);
    }

    /**
     * Get all the languages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Language> findAll(Pageable pageable) {
        log.debug("Request to get all Languages");
        return languageRepository.findAll(pageable);
    }


    /**
     * Get one language by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Language> findOne(Long id) {
        log.debug("Request to get Language : {}", id);
        return languageRepository.findById(id);
    }

    /**
     * Delete the language by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Language : {}", id);
        languageRepository.deleteById(id);
    }
}
