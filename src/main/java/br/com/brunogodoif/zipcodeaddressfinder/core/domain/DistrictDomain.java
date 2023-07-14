package br.com.brunogodoif.zipcodeaddressfinder.core.domain;

import lombok.Getter;

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
    }
    public DistrictDomain(Integer id, String district, BigDecimal latitude, BigDecimal longitude, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public DistrictDomain(String district, BigDecimal latitude, BigDecimal longitude, CityDomain cityDomain, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityDomain = cityDomain;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
