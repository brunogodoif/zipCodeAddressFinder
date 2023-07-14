package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateRepository extends JpaRepository<StateEntity, Integer> {

    Optional<StateEntity> findByUf(String stateUf);
}
