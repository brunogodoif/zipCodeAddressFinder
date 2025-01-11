package br.com.brunogodoif.zipcodeaddressfinder.adapters.configs;

import br.com.brunogodoif.zipcodeaddressfinder.ZipCodeAddressFinderApplication;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound.AddressServicePort;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound.PersistAddressServicePort;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound.SearchAddressFromSourceServicePort;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.AddressAdapterPort;
import br.com.brunogodoif.zipcodeaddressfinder.core.services.AddressServicePortImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ZipCodeAddressFinderApplication.class)
public class BusinessBeanConfig {

    @Bean
    AddressServicePort AddressServicePortImpl(
            AddressAdapterPort addressAdapterPort,
            SearchAddressFromSourceServicePort searchAddressFromSourceServicePort,
            PersistAddressServicePort persistAddressServicePort
    ) {
        return new AddressServicePortImpl(addressAdapterPort, searchAddressFromSourceServicePort,
                                          persistAddressServicePort);
    }

}
