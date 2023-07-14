package br.com.brunogodoif.zipcodeaddressfinder.core.domain;

import lombok.Data;

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
    }
}
