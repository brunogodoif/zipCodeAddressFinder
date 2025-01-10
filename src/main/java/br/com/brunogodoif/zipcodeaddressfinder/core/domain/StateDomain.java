package br.com.brunogodoif.zipcodeaddressfinder.core.domain;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.StateEntity;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.exceptions.StateDomainException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class StateDomain {

    private Integer id;
    private String state;
    private String capital;
    private String uf;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private RegionDomain regionDomain;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StateDomain(Integer id, String state, String capital, String uf, BigDecimal latitude, BigDecimal longitude, RegionDomain regionDomain, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.state = state;
        this.capital = capital;
        this.uf = uf;
        this.latitude = latitude;
        this.longitude = longitude;
        this.regionDomain = regionDomain;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }

    public static StateDomain toDomain(StateEntity stateEntity) {
        return new StateDomain(
                stateEntity.getId(),
                stateEntity.getState(),
                stateEntity.getCapital(),
                stateEntity.getUf(),
                stateEntity.getLatitude(),
                stateEntity.getLongitude(),
                RegionDomain.toDomain(stateEntity.getRegion()),
                stateEntity.getCreatedAt(),
                stateEntity.getUpdatedAt()
        );
    }

    public void validate() {
        validateId();
        validateState();
        validateCapital();
        validateUf();
        validateLatitude();
        validateLongitude();
    }

    public void validateId() {
        if (id == null) {
            throw new StateDomainException("Param [id] cannot be null");
        }

        if (id <= 0) {
            throw new StateDomainException("Param [id] must be a positive integer");
        }
    }

    public void validateState() {
        if (StringUtils.isEmpty(state)) {
            throw new StateDomainException("Param [state] cannot be null or empty");
        }
    }

    public void validateCapital() {
        if (StringUtils.isEmpty(capital)) {
            throw new StateDomainException("Param [capital] cannot be null or empty");
        }
    }

    public void validateUf() {
        if (StringUtils.isEmpty(uf)) {
            throw new StateDomainException("Param [uf] cannot be null or empty");
        }
    }

    public void validateLatitude() {
        if (latitude == null) {
            throw new StateDomainException("Param [latitude] cannot be null");
        }
    }

    public void validateLongitude() {
        if (longitude == null) {
            throw new StateDomainException("Param [longitude] cannot be null");
        }
    }


}
