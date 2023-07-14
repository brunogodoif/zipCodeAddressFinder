package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.DistrictEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.StateEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository.StateRepository;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.RegionDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.StateDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.StateAdapterPort;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class StateAdapterPortImpl implements StateAdapterPort {

    private final StateRepository stateRepository;
    private final ModelMapper modelMapper;

    public StateAdapterPortImpl(StateRepository stateRepository, ModelMapper modelMapper) {
        this.stateRepository = stateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<StateDomain> getByUf(String stateUf) {
        Optional<StateEntity> byUf = this.stateRepository.findByUf(stateUf);
        if (!byUf.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(this.convertStateEntityToDomain(byUf.get()));
    }

    @Override
    public StateDomain persist(StateDomain stateDomain) {
        StateEntity save = this.stateRepository.save(modelMapper.map(stateDomain, StateEntity.class));
        return convertStateEntityToDomain(save);
    }

    public StateDomain convertStateEntityToDomain(StateEntity stateEntity) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return new StateDomain(
                stateEntity.getId(),
                stateEntity.getState(),
                stateEntity.getCapital(),
                stateEntity.getUf(),
                stateEntity.getLatitude(),
                stateEntity.getLongitude(),
                new RegionDomain(
                        stateEntity.getRegion().getId(),
                        stateEntity.getRegion().getRegion(),
                        stateEntity.getRegion().getCreatedAt(),
                        stateEntity.getRegion().getUpdatedAt()
                ),
                stateEntity.getCreatedAt(),
                stateEntity.getUpdatedAt()
        );
    }


}
