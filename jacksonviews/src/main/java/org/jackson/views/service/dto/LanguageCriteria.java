package org.jackson.views.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Language entity. This class is used in LanguageResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /languages?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LanguageCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter alpha3b;

    private StringFilter alpha2;

    private StringFilter name;

    private StringFilter flag32;

    private StringFilter flag128;

    private BooleanFilter activated;

    public LanguageCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAlpha3b() {
        return alpha3b;
    }

    public void setAlpha3b(StringFilter alpha3b) {
        this.alpha3b = alpha3b;
    }

    public StringFilter getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(StringFilter alpha2) {
        this.alpha2 = alpha2;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getFlag32() {
        return flag32;
    }

    public void setFlag32(StringFilter flag32) {
        this.flag32 = flag32;
    }

    public StringFilter getFlag128() {
        return flag128;
    }

    public void setFlag128(StringFilter flag128) {
        this.flag128 = flag128;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
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
        final LanguageCriteria that = (LanguageCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(alpha3b, that.alpha3b) &&
            Objects.equals(alpha2, that.alpha2) &&
            Objects.equals(name, that.name) &&
            Objects.equals(flag32, that.flag32) &&
            Objects.equals(flag128, that.flag128) &&
            Objects.equals(activated, that.activated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        alpha3b,
        alpha2,
        name,
        flag32,
        flag128,
        activated
        );
    }

    @Override
    public String toString() {
        return "LanguageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (alpha3b != null ? "alpha3b=" + alpha3b + ", " : "") +
                (alpha2 != null ? "alpha2=" + alpha2 + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (flag32 != null ? "flag32=" + flag32 + ", " : "") +
                (flag128 != null ? "flag128=" + flag128 + ", " : "") +
                (activated != null ? "activated=" + activated + ", " : "") +
            "}";
    }

}
