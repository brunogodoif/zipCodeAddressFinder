package br.com.brunogodoif.zipcodeaddressfinder.core.domain;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.CityEntity;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.exceptions.CityDomainException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CityDomain {

    private Integer id;
    private String city;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer dddCode;
    private StateDomain stateDomain;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CityDomain(Integer id, String city, BigDecimal latitude, BigDecimal longitude, Integer dddCode, StateDomain stateDomain, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dddCode = dddCode;
        this.stateDomain = stateDomain;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }

    public CityDomain(String city, BigDecimal latitude, BigDecimal longitude, Integer dddCode, StateDomain stateDomain, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dddCode = dddCode;
        this.stateDomain = stateDomain;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validateCity();
        validateLatitude();
        validateLongitude();
        validateDddCode();
    }

    public static CityDomain toDomain(CityEntity cityEntity) {
        return new CityDomain(
                cityEntity.getId(),
                cityEntity.getCity(),
                cityEntity.getLatitude(),
                cityEntity.getLongitude(),
                cityEntity.getDddCode(),
                StateDomain.toDomain(cityEntity.getState()),
                cityEntity.getCreatedAt(),
                cityEntity.getUpdatedAt()
        );
    }

    public void validate() {
        validateId();
        validateCity();
        validateLatitude();
        validateLongitude();
        validateDddCode();
    }

    public void validateId() {
        if (id == null) {
            throw new CityDomainException("Param [id] cannot be null");
        }

        if (id <= 0) {
            throw new CityDomainException("Param [id] must be a positive integer");
        }
    }

    public void validateCity() {
        if (StringUtils.isEmpty(city)) {
            throw new CityDomainException("Param [city] cannot be null or empty");
        }
    }

    public void validateLatitude() {
        if (latitude == null) {
            throw new CityDomainException("Param [latitude] cannot be null");
        }
    }

    public void validateLongitude() {
        if (longitude == null) {
            throw new CityDomainException("Param [longitude] cannot be null");
        }
    }

    public void validateDddCode() {
        if (dddCode == null) {
            throw new CityDomainException("Param [dddCode] cannot be null");
        }
    }
}
