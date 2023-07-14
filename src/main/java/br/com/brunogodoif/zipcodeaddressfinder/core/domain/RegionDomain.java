package br.com.brunogodoif.zipcodeaddressfinder.core.domain;

import lombok.Getter;

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
    }
}
