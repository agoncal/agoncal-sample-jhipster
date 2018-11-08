package org.jackson.views.service.dto;

import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Language entity.
 */
public class LanguageDTO implements Serializable {

    @JsonView({View.Minimal.class, View.Code.class})
    private Long id;

    @NotNull
    @Size(max = 3)
    @JsonView(View.Code.class)
    private String alpha3b;

    @NotNull
    @Size(max = 2)
    @JsonView(View.Code.class)
    private String alpha2;

    @JsonView(View.Minimal.class)
    @NotNull
    private String name;

    private String flag32;

    private String flag128;

    private Boolean activated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlpha3b() {
        return alpha3b;
    }

    public void setAlpha3b(String alpha3b) {
        this.alpha3b = alpha3b;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag32() {
        return flag32;
    }

    public void setFlag32(String flag32) {
        this.flag32 = flag32;
    }

    public String getFlag128() {
        return flag128;
    }

    public void setFlag128(String flag128) {
        this.flag128 = flag128;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LanguageDTO languageDTO = (LanguageDTO) o;
        if (languageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), languageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LanguageDTO{" +
            "id=" + getId() +
            ", alpha3b='" + getAlpha3b() + "'" +
            ", alpha2='" + getAlpha2() + "'" +
            ", name='" + getName() + "'" +
            ", flag32='" + getFlag32() + "'" +
            ", flag128='" + getFlag128() + "'" +
            ", activated='" + isActivated() + "'" +
            "}";
    }
}
