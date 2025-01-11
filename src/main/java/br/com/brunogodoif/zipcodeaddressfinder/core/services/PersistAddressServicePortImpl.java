package br.com.brunogodoif.zipcodeaddressfinder.core.services;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.http.viaCep.ViaCepAddresResponse;
import br.com.brunogodoif.zipcodeaddressfinder.commons.ZipCodeUtil;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.AddressDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.CityDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.DistrictDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.StateDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.inbound.PersistAddressServicePort;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.AddressAdapterPort;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.CityAdapterPort;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.DistrictAdapterPort;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.StateAdapterPort;
import br.com.brunogodoif.zipcodeaddressfinder.core.services.exception.FailtPersistNewAddressException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Component
public class PersistAddressServicePortImpl implements PersistAddressServicePort {

    private final AddressAdapterPort addressAdapterPort;
    private final StateAdapterPort stateAdapterPort;
    private final CityAdapterPort cityAdapterPort;
    private final DistrictAdapterPort districtAdapterPort;
    private static final String UTC_ZONE_ID = "UTC";

    public PersistAddressServicePortImpl(AddressAdapterPort addressAdapterPort,
                                         StateAdapterPort stateAdapterPort,
                                         CityAdapterPort cityAdapterPort,
                                         DistrictAdapterPort districtAdapterPort) {
        this.addressAdapterPort = addressAdapterPort;
        this.stateAdapterPort = stateAdapterPort;
        this.cityAdapterPort = cityAdapterPort;
        this.districtAdapterPort = districtAdapterPort;
    }

    @Override
    public AddressDomain persist(ViaCepAddresResponse viaCepAddresResponse) {

        Optional<StateDomain> state = this.stateAdapterPort.getByUf(viaCepAddresResponse.getUf());

        if (state.isEmpty()) {
            throw new FailtPersistNewAddressException("Fail to persist new address, State [" + viaCepAddresResponse.getUf() + "] not found in database");
        }

        Optional<CityDomain> city = cityAdapterPort.getByCityName(viaCepAddresResponse.getLocalidade());

        if (city.isEmpty()) {
            CityDomain cityPersisted = persistNewCity(viaCepAddresResponse.getLocalidade(), viaCepAddresResponse.getDdd(), state.get());
            city = Optional.of(cityPersisted);
        }

        Optional<DistrictDomain> district = districtAdapterPort.getByDistrictName(viaCepAddresResponse.getBairro());

        if (district.isEmpty()) {
            DistrictDomain districtPersisted = persistNewDistrict(viaCepAddresResponse.getBairro(), city.get());
            district = Optional.of(districtPersisted);
        }

        return persistNewAddress(viaCepAddresResponse.getCep(), viaCepAddresResponse.getLogradouro(), state.get(), city.get(), district.get());

    }

    private CityDomain persistNewCity(String cityName, String ddd, StateDomain state) {
        return cityAdapterPort.persist(new CityDomain(
                cityName,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                Integer.parseInt(ddd),
                state,
                LocalDateTime.now(ZoneId.of(UTC_ZONE_ID)),
                LocalDateTime.now(ZoneId.of(UTC_ZONE_ID))
        ));
    }

    private DistrictDomain persistNewDistrict(String districtName, CityDomain city) {
        return districtAdapterPort.persist(new DistrictDomain(
                districtName,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                city,
                LocalDateTime.now(ZoneId.of(UTC_ZONE_ID)),
                LocalDateTime.now(ZoneId.of(UTC_ZONE_ID))
        ));
    }

    private AddressDomain persistNewAddress(String zipCode, String address, StateDomain state, CityDomain city, DistrictDomain district) {
        return addressAdapterPort.persist(
                new AddressDomain(
                        ZipCodeUtil.removeMask(zipCode),
                        address,
                        "",
                        "",
                        district,
                        city,
                        state,
                        state.getRegionDomain(),
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        true,
                        LocalDateTime.now(ZoneId.of(UTC_ZONE_ID)),
                        LocalDateTime.now(ZoneId.of(UTC_ZONE_ID))
                )
        );
    }
}
