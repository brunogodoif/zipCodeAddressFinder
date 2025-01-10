package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.StateEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository.StateRepository;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.StateDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.StateAdapterPort;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StateAdapterPortImpl implements StateAdapterPort {

    private final StateRepository stateRepository;
    private final ModelMapper modelMapper;

    @PersistenceContext private EntityManager entityManager;

    public StateAdapterPortImpl(StateRepository stateRepository, ModelMapper modelMapper) {
        this.stateRepository = stateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<StateDomain> getByUf(String stateUf) {

        return stateRepository.findByUf(stateUf).flatMap(stateEntity -> Optional.of(StateDomain.toDomain(stateEntity)));
    }

    @Override
    public StateDomain persist(StateDomain stateDomain) {
        StateEntity save = this.stateRepository.save(modelMapper.map(stateDomain, StateEntity.class));
        return StateDomain.toDomain(save);
    }

    @Override
    public List<StateDomain> findAll() {
        List<StateEntity> stateEntities = stateRepository.findAll();
        return stateEntities.stream().map(StateDomain::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void persistBatch(List<StateDomain> stateDomains) {
        int batchSize = 1000; // Tamanho do sub-lote
        int count = 0;

        for (StateDomain stateDomain : stateDomains) {
            StateEntity stateEntity = modelMapper.map(stateDomain, StateEntity.class);
            entityManager.persist(stateEntity);
            count++;

            if (count % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        if (count % batchSize != 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }

}
