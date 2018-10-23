package org.combo.noautocomplete.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.combo.noautocomplete.domain.Language;
import org.combo.noautocomplete.domain.*; // for static metamodels
import org.combo.noautocomplete.repository.LanguageRepository;
import org.combo.noautocomplete.service.dto.LanguageCriteria;

/**
 * Service for executing complex queries for Language entities in the database.
 * The main input is a {@link LanguageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Language} or a {@link Page} of {@link Language} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LanguageQueryService extends QueryService<Language> {

    private final Logger log = LoggerFactory.getLogger(LanguageQueryService.class);

    private LanguageRepository languageRepository;

    public LanguageQueryService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    /**
     * Return a {@link List} of {@link Language} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Language> findByCriteria(LanguageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Language> specification = createSpecification(criteria);
        return languageRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Language} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Language> findByCriteria(LanguageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Language> specification = createSpecification(criteria);
        return languageRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LanguageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Language> specification = createSpecification(criteria);
        return languageRepository.count(specification);
    }

    /**
     * Function to convert LanguageCriteria to a {@link Specification}
     */
    private Specification<Language> createSpecification(LanguageCriteria criteria) {
        Specification<Language> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Language_.id));
            }
            if (criteria.getAlpha3b() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlpha3b(), Language_.alpha3b));
            }
            if (criteria.getAlpha2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlpha2(), Language_.alpha2));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Language_.name));
            }
            if (criteria.getFlag32() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFlag32(), Language_.flag32));
            }
            if (criteria.getFlag128() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFlag128(), Language_.flag128));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), Language_.activated));
            }
        }
        return specification;
    }
}
