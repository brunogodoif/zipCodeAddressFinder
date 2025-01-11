package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {

    Optional<RegionEntity> findByRegion(String region);
}
