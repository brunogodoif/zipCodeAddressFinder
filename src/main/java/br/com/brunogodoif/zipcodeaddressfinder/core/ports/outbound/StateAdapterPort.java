package br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.StateEntity;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.StateDomain;

import java.util.List;
import java.util.Optional;

public interface StateAdapterPort {

    Optional<StateDomain> getByUf(String stateUf);

    StateDomain persist(StateDomain stateDomain);

    List<StateDomain> findAll();

    void persistBatch(List<StateDomain> stateDomains);
}
