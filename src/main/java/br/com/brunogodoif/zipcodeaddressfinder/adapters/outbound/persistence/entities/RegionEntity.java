package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "regions")
public class RegionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Column(nullable = false, length = 150)
    private String region;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private Set<AddressEntity> addresses;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private Set<StateEntity> states;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
