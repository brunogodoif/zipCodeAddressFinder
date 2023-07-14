package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.CityEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository.CityRepository;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.CityDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.CityAdapterPort;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class CityAdapterPortImpl implements CityAdapterPort {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public CityAdapterPortImpl(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<CityDomain> getByCityName(String cityName) {
        Optional<CityEntity> byCityName = this.cityRepository.findByCity(cityName);
        if (!byCityName.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(this.convertCityEntityToDomain(byCityName.get()));
    }

    @Override
    public CityDomain persist(CityDomain cityDomain) {
        CityEntity save = this.cityRepository.save(modelMapper.map(cityDomain, CityEntity.class));
        return convertCityEntityToDomain(save);
    }

    public CityDomain convertCityEntityToDomain(CityEntity cityEntity) {

        return new CityDomain(
                cityEntity.getId(),
                cityEntity.getCity(),
                cityEntity.getLatitude(),
                cityEntity.getLongitude(),
                cityEntity.getDddCode(),
                cityEntity.getCreatedAt(),
                cityEntity.getUpdatedAt()
        );
    }

}