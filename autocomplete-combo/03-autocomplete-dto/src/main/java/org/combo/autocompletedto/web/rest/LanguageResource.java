package org.combo.autocompletedto.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.combo.autocompletedto.service.LanguageService;
import org.combo.autocompletedto.web.rest.errors.BadRequestAlertException;
import org.combo.autocompletedto.web.rest.util.HeaderUtil;
import org.combo.autocompletedto.web.rest.util.PaginationUtil;
import org.combo.autocompletedto.service.dto.LanguageDTO;
import org.combo.autocompletedto.service.dto.LanguageCriteria;
import org.combo.autocompletedto.service.LanguageQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Language.
 */
@RestController
@RequestMapping("/api")
public class LanguageResource {

    private final Logger log = LoggerFactory.getLogger(LanguageResource.class);

    private static final String ENTITY_NAME = "language";

    private LanguageService languageService;

    private LanguageQueryService languageQueryService;

    public LanguageResource(LanguageService languageService, LanguageQueryService languageQueryService) {
        this.languageService = languageService;
        this.languageQueryService = languageQueryService;
    }

    /**
     * POST  /languages : Create a new language.
     *
     * @param languageDTO the languageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new languageDTO, or with status 400 (Bad Request) if the language has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/languages")
    @Timed
    public ResponseEntity<LanguageDTO> createLanguage(@Valid @RequestBody LanguageDTO languageDTO) throws URISyntaxException {
        log.debug("REST request to save Language : {}", languageDTO);
        if (languageDTO.getId() != null) {
            throw new BadRequestAlertException("A new language cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LanguageDTO result = languageService.save(languageDTO);
        return ResponseEntity.created(new URI("/api/languages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /languages : Updates an existing language.
     *
     * @param languageDTO the languageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated languageDTO,
     * or with status 400 (Bad Request) if the languageDTO is not valid,
     * or with status 500 (Internal Server Error) if the languageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/languages")
    @Timed
    public ResponseEntity<LanguageDTO> updateLanguage(@Valid @RequestBody LanguageDTO languageDTO) throws URISyntaxException {
        log.debug("REST request to update Language : {}", languageDTO);
        if (languageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LanguageDTO result = languageService.save(languageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, languageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /languages : get all the languages.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of languages in body
     */
    @GetMapping("/languages")
    @Timed
    public ResponseEntity<List<LanguageDTO>> getAllLanguages(LanguageCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Languages by criteria: {}", criteria);
        Page<LanguageDTO> page = languageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/languages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * GET  /languages/count : count all the languages.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/languages/count")
    @Timed
    public ResponseEntity<Long> countLanguages(LanguageCriteria criteria) {
        log.debug("REST request to count Languages by criteria: {}", criteria);
        return ResponseEntity.ok().body(languageQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /languages/:id : get the "id" language.
     *
     * @param id the id of the languageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the languageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/languages/{id}")
    @Timed
    public ResponseEntity<LanguageDTO> getLanguage(@PathVariable Long id) {
        log.debug("REST request to get Language : {}", id);
        Optional<LanguageDTO> languageDTO = languageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(languageDTO);
    }

    /**
     * DELETE  /languages/:id : delete the "id" language.
     *
     * @param id the id of the languageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/languages/{id}")
    @Timed
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        log.debug("REST request to delete Language : {}", id);
        languageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
