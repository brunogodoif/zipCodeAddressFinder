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
@Table(name = "cities")
public class CityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Column(nullable = false, length = 150)
    private String city;

    @Column(nullable = false, length = 150)
    private BigDecimal latitude;

    @Column(nullable = false, length = 150)
    private BigDecimal longitude;

    @Column(nullable = false)
    private Integer dddCode;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<AddressEntity> addresses;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<DistrictEntity> districts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private StateEntity state;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
