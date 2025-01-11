package br.com.brunogodoif.zipcodeaddressfinder.adapters.configs;

import br.com.brunogodoif.zipcodeaddressfinder.ZipCodeAddressFinderApplication;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.AddressEntity;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.AddressDomain;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ZipCodeAddressFinderApplication.class)
public class ModelMapperConfig {
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

}
