package org.combo.autocompletedtooptim.service.mapper;

import org.combo.autocompletedtooptim.domain.Language;
import org.combo.autocompletedtooptim.service.dto.LanguageIdNameDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Language and its DTO LanguageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LanguageIdNameMapper extends EntityMapper<LanguageIdNameDTO, Language> {



    default Language fromId(Long id) {
        if (id == null) {
            return null;
        }
        Language language = new Language();
        language.setId(id);
        return language;
    }
}
