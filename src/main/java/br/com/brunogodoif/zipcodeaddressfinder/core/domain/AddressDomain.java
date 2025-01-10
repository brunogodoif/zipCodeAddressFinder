package br.com.brunogodoif.zipcodeaddressfinder.core.domain;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.AddressEntity;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.exceptions.AddressDomainException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AddressDomain {

    private String zipCode;
    private String addressComplete;
    private String typeAddress;
    private String address;
    private DistrictDomain districtDomain;
    private CityDomain cityDomain;
    private StateDomain stateDomain;
    private RegionDomain regionDomain;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AddressDomain(String zipCode, String addressComplete, String typeAddress, String address, DistrictDomain districtDomain, CityDomain cityDomain, StateDomain stateDomain, RegionDomain regionDomain, BigDecimal latitude, BigDecimal longitude, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.zipCode = zipCode;
        this.addressComplete = addressComplete;
        this.typeAddress = typeAddress;
        this.address = address;
        this.districtDomain = districtDomain;
        this.cityDomain = cityDomain;
        this.stateDomain = stateDomain;
        this.regionDomain = regionDomain;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }

    public static AddressDomain toDomain(AddressEntity addressEntity) {
        return new AddressDomain(
                addressEntity.getZipCode(),
                addressEntity.getAddressComplete(),
                addressEntity.getTypeAddress(),
                addressEntity.getAddress(),
                DistrictDomain.toDomain(addressEntity.getDistrict()),
                CityDomain.toDomain(addressEntity.getCity()),
                StateDomain.toDomain(addressEntity.getState()),
                RegionDomain.toDomain(addressEntity.getRegion()),
                addressEntity.getLatitude(),
                addressEntity.getLongitude(),
                addressEntity.getActive(),
                addressEntity.getCreatedAt(),
                addressEntity.getUpdatedAt()
        );
    }

    public void validate() {
        validateZipCode();
        validateLatitude();
        validateLongitude();
        validateActive();
    }

    public void validateZipCode() {
        if (StringUtils.isEmpty(zipCode)) {
            throw new AddressDomainException("Param [zipCode] cannot be null or empty");
        }
    }

    public void validateAddressComplete() {
        if (StringUtils.isEmpty(addressComplete)) {
            throw new AddressDomainException("Param [addressComplete] cannot be null or empty");
        }
    }

    public void validateTypeAddress() {
        if (StringUtils.isEmpty(typeAddress)) {
            throw new AddressDomainException("Param [typeAddress] cannot be null or empty");
        }
    }

    public void validateAddress() {
        if (StringUtils.isEmpty(address)) {
            throw new AddressDomainException("Param [address] cannot be null or empty");
        }
    }

    public void validateLatitude() {
        if (latitude == null) {
            throw new AddressDomainException("Param [latitude] cannot be null");
        }
    }

    public void validateLongitude() {
        if (longitude == null) {
            throw new AddressDomainException("Param [longitude] cannot be null");
        }
    }

    public void validateActive() {
        if (active == null) {
            throw new AddressDomainException("Param [active] cannot be null");
        }
    }

}
