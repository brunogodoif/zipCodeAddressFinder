package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<DistrictEntity, Integer> {

    Optional<DistrictEntity> findByDistrict(String districtName);
}
