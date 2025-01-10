package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "states")
public class StateEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Column(nullable = false, length = 150)
    private String state;

    @Column(nullable = false, length = 150)
    private String capital;

    @Column(nullable = false, length = 3)
    private String uf;

    @Column(nullable = false, length = 50)
    private BigDecimal latitude;

    @Column(nullable = false, length = 50)
    private BigDecimal longitude;

    @OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
    private Set<AddressEntity> addresses;

    @OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
    private Set<CityEntity> cities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private RegionEntity region;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
