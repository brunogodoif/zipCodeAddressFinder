package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.CityEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.DistrictEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository.DistrictRepository;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.DistrictDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.DistrictAdapterPort;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class DistrictAdapterPortImpl implements DistrictAdapterPort {

    private final DistrictRepository districtRepository;
    private final ModelMapper modelMapper;

    public DistrictAdapterPortImpl(DistrictRepository districtRepository, ModelMapper modelMapper) {
        this.districtRepository = districtRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<DistrictDomain> getByDistrictName(String districtName) {
        Optional<DistrictEntity> byDistricName = this.districtRepository.findByDistrict(districtName);
        if (!byDistricName.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(this.convertDistrictEntityToDomain(byDistricName.get()));
    }

    @Override
    public DistrictDomain persist(DistrictDomain districtDomain) {
        DistrictEntity save = this.districtRepository.save(modelMapper.map(districtDomain, DistrictEntity.class));

        return convertDistrictEntityToDomain(save);
    }

    public DistrictDomain convertDistrictEntityToDomain(DistrictEntity districtEntity) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return new DistrictDomain(
                districtEntity.getId(),
                districtEntity.getDistrict(),
                districtEntity.getLatitude(),
                districtEntity.getLongitude(),
                districtEntity.getCreatedAt(),
                districtEntity.getUpdatedAt()
        );
    }
}
