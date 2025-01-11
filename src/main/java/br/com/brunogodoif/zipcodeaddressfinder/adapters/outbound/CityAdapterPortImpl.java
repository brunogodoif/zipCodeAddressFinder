package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.CityEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository.CityRepository;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.CityDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.CityAdapterPort;
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
public class CityAdapterPortImpl implements CityAdapterPort {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    @PersistenceContext private EntityManager entityManager;

    public CityAdapterPortImpl(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<CityDomain> getByCityName(String cityName) {
        return cityRepository.findByCity(cityName).flatMap(cityEntity -> Optional.of(CityDomain.toDomain(cityEntity)));
    }

    @Override
    public CityDomain persist(CityDomain cityDomain) {
        CityEntity save = this.cityRepository.save(modelMapper.map(cityDomain, CityEntity.class));
        return CityDomain.toDomain(save);
    }

    @Override @Transactional(propagation = Propagation.REQUIRED)
    public void persistBatch(List<CityDomain> cityDomains) {
        int batchSize = 1000; // Tamanho do sub-lote para flush
        int count = 0;

        for (CityDomain cityDomain : cityDomains) {
            CityEntity cityEntity = modelMapper.map(cityDomain, CityEntity.class);
            entityManager.persist(cityEntity);
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


    @Transactional(readOnly = true)  @Override public List<CityDomain> findAll() {
        List<CityEntity> cityEntities = cityRepository.findAll();
        return cityEntities.stream().map(CityDomain::toDomain).collect(Collectors.toList());
    }
}