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
@Table(name = "districts")
public class DistrictEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Column(nullable = false, length = 150)
    private String district;

    @Column(nullable = false, length = 150)
    private BigDecimal latitude;

    @Column(nullable = false, length = 150)
    private BigDecimal longitude;

    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
    private Set<AddressEntity> addresses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private CityEntity city;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
