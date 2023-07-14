package br.com.brunogodoif.zipcodeaddressfinder.core.domain;

import lombok.Getter;

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
    }

    public StateDomain(Integer id, String state, String capital, String uf, BigDecimal latitude, BigDecimal longitude, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.state = state;
        this.capital = capital;
        this.uf = uf;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
