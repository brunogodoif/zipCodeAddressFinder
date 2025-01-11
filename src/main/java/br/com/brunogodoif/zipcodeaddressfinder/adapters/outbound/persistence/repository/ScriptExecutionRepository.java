package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.ScriptExecutionControl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScriptExecutionRepository extends JpaRepository<ScriptExecutionControl, String> {

    Optional<ScriptExecutionControl> findByScriptName(String scriptName);
}