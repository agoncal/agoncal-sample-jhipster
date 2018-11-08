package org.jackson.views.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Language.
 */
@Entity
@Table(name = "jac_language")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "alpha_3_b", length = 3, nullable = false)
    private String alpha3b;

    @NotNull
    @Size(max = 2)
    @Column(name = "alpha_2", length = 2, nullable = false)
    private String alpha2;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "flag_32")
    private String flag32;

    @Column(name = "flag_128")
    private String flag128;

    @Column(name = "activated")
    private Boolean activated;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlpha3b() {
        return alpha3b;
    }

    public Language alpha3b(String alpha3b) {
        this.alpha3b = alpha3b;
        return this;
    }

    public void setAlpha3b(String alpha3b) {
        this.alpha3b = alpha3b;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public Language alpha2(String alpha2) {
        this.alpha2 = alpha2;
        return this;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }

    public String getName() {
        return name;
    }

    public Language name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag32() {
        return flag32;
    }

    public Language flag32(String flag32) {
        this.flag32 = flag32;
        return this;
    }

    public void setFlag32(String flag32) {
        this.flag32 = flag32;
    }

    public String getFlag128() {
        return flag128;
    }

    public Language flag128(String flag128) {
        this.flag128 = flag128;
        return this;
    }

    public void setFlag128(String flag128) {
        this.flag128 = flag128;
    }

    public Boolean isActivated() {
        return activated;
    }

    public Language activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Language language = (Language) o;
        if (language.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), language.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Language{" +
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
