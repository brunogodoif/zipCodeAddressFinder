package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.DistrictEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository.DistrictRepository;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.DistrictDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.DistrictAdapterPort;
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
public class DistrictAdapterPortImpl implements DistrictAdapterPort {

    private final DistrictRepository districtRepository;
    private final ModelMapper modelMapper;

    @PersistenceContext private EntityManager entityManager;

    public DistrictAdapterPortImpl(DistrictRepository districtRepository, ModelMapper modelMapper) {
        this.districtRepository = districtRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<DistrictDomain> getByDistrictName(String districtName) {
        return districtRepository.findByDistrict(districtName)
                                 .flatMap(districtEntity -> Optional.of(DistrictDomain.toDomain(districtEntity)));
    }

    @Override
    public DistrictDomain persist(DistrictDomain districtDomain) {
        DistrictEntity save = this.districtRepository.save(modelMapper.map(districtDomain, DistrictEntity.class));

        return DistrictDomain.toDomain(save);
    }

    @Override @Transactional(propagation = Propagation.REQUIRED)
    public void persistBatch(List<DistrictDomain> districtDomains) {
        int batchSize = 1000; // Tamanho do sub-lote para flush
        int count = 0;

        for (DistrictDomain districtDomain : districtDomains) {
            DistrictEntity districtEntity = modelMapper.map(districtDomain, DistrictEntity.class);
            entityManager.persist(districtEntity);
            count++;

            // Faz flush e limpa o contexto de persistência a cada batchSize registros
            if (count % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        // Garante que os registros restantes sejam persistidos
        if (count % batchSize != 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }

    @Transactional(readOnly = true) @Override public List<DistrictDomain> findAll() {
        List<DistrictEntity> districtEntities = districtRepository.findAll();
        return districtEntities.stream().map(DistrictDomain::toDomain).collect(Collectors.toList());
    }

}
