package br.com.brunogodoif.zipcodeaddressfinder.core.domain;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.DistrictEntity;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.exceptions.DistrictDomainException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class DistrictDomain {
    private Integer id;
    private String district;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private CityDomain cityDomain;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public DistrictDomain(Integer id, String district, BigDecimal latitude, BigDecimal longitude, CityDomain cityDomain, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityDomain = cityDomain;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }

    public DistrictDomain(String district, BigDecimal latitude, BigDecimal longitude, CityDomain cityDomain, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityDomain = cityDomain;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validateDistrict();
        validateLatitude();
        validateLongitude();
    }

    public static DistrictDomain toDomain(DistrictEntity districtEntity) {
        return new DistrictDomain(districtEntity.getId(), districtEntity.getDistrict(), districtEntity.getLatitude(),
                                  districtEntity.getLongitude(), CityDomain.toDomain(districtEntity.getCity()),
                                  districtEntity.getCreatedAt(), districtEntity.getUpdatedAt());
    }

    public void validate() {
        validateId();
        validateDistrict();
        validateLatitude();
        validateLongitude();
    }

    public void validateId() {
        if (id == null) {
            throw new DistrictDomainException("Param [id] cannot be null");
        }

        if (id <= 0) {
            throw new DistrictDomainException("Param [id] must be a positive integer");
        }
    }

    public void validateDistrict() {
        if (StringUtils.isEmpty(district)) {
            throw new DistrictDomainException("Param [district] cannot be null or empty");
        }
    }

    public void validateLatitude() {
        if (latitude == null) {
            throw new DistrictDomainException("Param [latitude] cannot be null");
        }
    }

    public void validateLongitude() {
        if (longitude == null) {
            throw new DistrictDomainException("Param [longitude] cannot be null");
        }
    }

}
