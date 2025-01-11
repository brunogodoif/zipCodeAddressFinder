package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.RegionEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository.RegionRepository;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.RegionDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.RegionAdapterPort;
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
public class RegionAdapterPortImpl implements RegionAdapterPort {

    private final RegionRepository stateRepository;
    private final ModelMapper modelMapper;

    @PersistenceContext private EntityManager entityManager;

    public RegionAdapterPortImpl(RegionRepository stateRepository, ModelMapper modelMapper) {
        this.stateRepository = stateRepository;
        this.modelMapper = modelMapper;
    }

    @Override public Optional<RegionDomain> getByUf(String region) {

        return stateRepository.findByRegion(region)
                              .flatMap(stateEntity -> Optional.of(RegionDomain.toDomain(stateEntity)));
    }

    @Override public RegionDomain persist(RegionDomain stateDomain) {
        RegionEntity save = this.stateRepository.save(modelMapper.map(stateDomain, RegionEntity.class));
        return RegionDomain.toDomain(save);
    }

    @Override public List<RegionDomain> findAll() {
        List<RegionEntity> stateEntities = stateRepository.findAll();
        return stateEntities.stream().map(RegionDomain::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void persistBatch(List<RegionDomain> regionDomains) {
        int batchSize = 1000; // Tamanho do sub-lote
        int count = 0;

        for (RegionDomain regionDomain : regionDomains) {
            RegionEntity regionEntity = modelMapper.map(regionDomain, RegionEntity.class);
            entityManager.persist(regionEntity);
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
