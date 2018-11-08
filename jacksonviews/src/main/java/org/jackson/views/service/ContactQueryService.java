package org.jackson.views.service;

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

import org.jackson.views.domain.Contact;
import org.jackson.views.domain.*; // for static metamodels
import org.jackson.views.repository.ContactRepository;
import org.jackson.views.service.dto.ContactCriteria;
import org.jackson.views.service.dto.ContactDTO;
import org.jackson.views.service.mapper.ContactMapper;

/**
 * Service for executing complex queries for Contact entities in the database.
 * The main input is a {@link ContactCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContactDTO} or a {@link Page} of {@link ContactDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContactQueryService extends QueryService<Contact> {

    private final Logger log = LoggerFactory.getLogger(ContactQueryService.class);

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;

    public ContactQueryService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    /**
     * Return a {@link List} of {@link ContactDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContactDTO> findByCriteria(ContactCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Contact> specification = createSpecification(criteria);
        return contactMapper.toDto(contactRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContactDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactDTO> findByCriteria(ContactCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Contact> specification = createSpecification(criteria);
        return contactRepository.findAll(specification, page)
            .map(contactMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContactCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Contact> specification = createSpecification(criteria);
        return contactRepository.count(specification);
    }

    /**
     * Function to convert ContactCriteria to a {@link Specification}
     */
    private Specification<Contact> createSpecification(ContactCriteria criteria) {
        Specification<Contact> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Contact_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Contact_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Contact_.lastName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Contact_.email));
            }
            if (criteria.getLanguageId() != null) {
                specification = specification.and(buildSpecification(criteria.getLanguageId(),
                    root -> root.join(Contact_.language, JoinType.LEFT).get(Language_.id)));
            }
        }
        return specification;
    }
}
