package com.sbm.loyalty.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RiskEpurationPoints.
 */
@Entity
@Table(name = "risk_epuration_points")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RiskEpurationPoints implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "points")
    private Integer points;

    @Column(name = "valid_until")
    private Integer validUntil;

    @JsonIgnoreProperties(value = { "riskEpurationPoints" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "riskEpurationPoints")
    private LoyaltyPoints loyaltyPoints;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RiskEpurationPoints id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoints() {
        return this.points;
    }

    public RiskEpurationPoints points(Integer points) {
        this.setPoints(points);
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getValidUntil() {
        return this.validUntil;
    }

    public RiskEpurationPoints validUntil(Integer validUntil) {
        this.setValidUntil(validUntil);
        return this;
    }

    public void setValidUntil(Integer validUntil) {
        this.validUntil = validUntil;
    }

    public LoyaltyPoints getLoyaltyPoints() {
        return this.loyaltyPoints;
    }

    public void setLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
        if (this.loyaltyPoints != null) {
            this.loyaltyPoints.setRiskEpurationPoints(null);
        }
        if (loyaltyPoints != null) {
            loyaltyPoints.setRiskEpurationPoints(this);
        }
        this.loyaltyPoints = loyaltyPoints;
    }

    public RiskEpurationPoints loyaltyPoints(LoyaltyPoints loyaltyPoints) {
        this.setLoyaltyPoints(loyaltyPoints);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiskEpurationPoints)) {
            return false;
        }
        return getId() != null && getId().equals(((RiskEpurationPoints) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiskEpurationPoints{" +
            "id=" + getId() +
            ", points=" + getPoints() +
            ", validUntil=" + getValidUntil() +
            "}";
    }
}
