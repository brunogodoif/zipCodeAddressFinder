package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, String>, JpaSpecificationExecutor<AddressEntity> {

    Optional<AddressEntity> findByZipCode(String zipCode);

}
