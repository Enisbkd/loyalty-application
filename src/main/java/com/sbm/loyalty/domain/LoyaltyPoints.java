package com.sbm.loyalty.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LoyaltyPoints.
 */
@Entity
@Table(name = "loyalty_points")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoyaltyPoints implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "status_points")
    private Integer statusPoints;

    @Column(name = "my_points")
    private Integer myPoints;

    @JsonIgnoreProperties(value = { "loyaltyPoints" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private RiskEpurationPoints riskEpurationPoints;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LoyaltyPoints id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatusPoints() {
        return this.statusPoints;
    }

    public LoyaltyPoints statusPoints(Integer statusPoints) {
        this.setStatusPoints(statusPoints);
        return this;
    }

    public void setStatusPoints(Integer statusPoints) {
        this.statusPoints = statusPoints;
    }

    public Integer getMyPoints() {
        return this.myPoints;
    }

    public LoyaltyPoints myPoints(Integer myPoints) {
        this.setMyPoints(myPoints);
        return this;
    }

    public void setMyPoints(Integer myPoints) {
        this.myPoints = myPoints;
    }

    public RiskEpurationPoints getRiskEpurationPoints() {
        return this.riskEpurationPoints;
    }

    public void setRiskEpurationPoints(RiskEpurationPoints riskEpurationPoints) {
        this.riskEpurationPoints = riskEpurationPoints;
    }

    public LoyaltyPoints riskEpurationPoints(RiskEpurationPoints riskEpurationPoints) {
        this.setRiskEpurationPoints(riskEpurationPoints);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoyaltyPoints)) {
            return false;
        }
        return getId() != null && getId().equals(((LoyaltyPoints) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoyaltyPoints{" +
            "id=" + getId() +
            ", statusPoints=" + getStatusPoints() +
            ", myPoints=" + getMyPoints() +
            "}";
    }
}
