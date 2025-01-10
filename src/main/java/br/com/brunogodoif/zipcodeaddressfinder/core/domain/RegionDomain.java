package br.com.brunogodoif.zipcodeaddressfinder.core.domain;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.RegionEntity;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.exceptions.RegionDomainException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Getter
public class RegionDomain {

    private Integer id;
    private String region;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RegionDomain(Integer id, String region, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.region = region;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }

    public static RegionDomain toDomain(RegionEntity regionEntity) {
        return new RegionDomain(
                regionEntity.getId(),
                regionEntity.getRegion(),
                regionEntity.getCreatedAt(),
                regionEntity.getUpdatedAt()
        );
    }

    public void validate() {
        validateId();
        validateRegion();
    }

    public void validateId() {
        if (id == null) {
            throw new RegionDomainException("Param [id] cannot be null");
        }

        if (id <= 0) {
            throw new RegionDomainException("Param [id] must be a positive integer");
        }
    }

    public void validateRegion() {
        if (StringUtils.isEmpty(region)) {
            throw new RegionDomainException("Param [region] cannot be null or empty");
        }
    }

}
