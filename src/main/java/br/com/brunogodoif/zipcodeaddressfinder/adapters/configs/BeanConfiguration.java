package br.com.brunogodoif.zipcodeaddressfinder.adapters.configs;

import br.com.brunogodoif.zipcodeaddressfinder.ZipCodeAddressFinderApplication;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.AddressEntity;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.AddressDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound.AddressServicePort;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound.PersistNewAddressFromApiSourceServicePort;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound.SearchAddressFromSourceServicePort;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.AddressAdapterPort;
import br.com.brunogodoif.zipcodeaddressfinder.core.services.AddressServicePortImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ZipCodeAddressFinderApplication.class)
public class BeanConfiguration {

    @Bean
    AddressServicePort AddressServicePortImpl(
            AddressAdapterPort addressAdapterPort,
            SearchAddressFromSourceServicePort searchAddressFromSourceServicePort,
            PersistNewAddressFromApiSourceServicePort persistNewAddressFromApiSourceServicePort
    ) {
        return new AddressServicePortImpl(addressAdapterPort, searchAddressFromSourceServicePort, persistNewAddressFromApiSourceServicePort);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(AddressEntity.class, AddressDomain.class)
                .addMapping(AddressEntity::getDistrict, AddressDomain::setDistrictDomain)
                .addMapping(AddressEntity::getCity, AddressDomain::setCityDomain)
                .addMapping(AddressEntity::getState, AddressDomain::setStateDomain)
                .addMapping(AddressEntity::getRegion, AddressDomain::setRegionDomain);

        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }
}
