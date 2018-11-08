package org.jackson.views.service.mapper;

import org.jackson.views.domain.*;
import org.jackson.views.service.dto.ContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Contact and its DTO ContactDTO.
 */
@Mapper(componentModel = "spring", uses = {LanguageMapper.class})
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {

    @Mapping(source = "language.id", target = "languageId")
    @Mapping(source = "language.name", target = "languageName")
    ContactDTO toDto(Contact contact);

    @Mapping(source = "languageId", target = "language")
    Contact toEntity(ContactDTO contactDTO);

    default Contact fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }
}
