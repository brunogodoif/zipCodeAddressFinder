package br.com.brunogodoif.zipcodeaddressfinder.core.domain;

import lombok.Getter;

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
    }

    public CityDomain(Integer id, String city, BigDecimal latitude, BigDecimal longitude, Integer dddCode, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dddCode = dddCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CityDomain(String city, BigDecimal latitude, BigDecimal longitude, Integer dddCode, StateDomain stateDomain, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dddCode = dddCode;
        this.stateDomain = stateDomain;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
