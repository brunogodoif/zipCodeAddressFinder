package br.com.brunogodoif.zipcodeaddressfinder.core.domain

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.*
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.exceptions.AddressDomainException
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class AdressDomainSpec extends Specification {


    def "Should creating a valid AddressDomain"() {
        setup:
        def zipCode = "12345-678"
        def addressComplete = "Praça da Sé"
        def typeAddress = "Praça"
        def address = "Sé"
        def latitude = BigDecimal.ONE
        def longitude = BigDecimal.ONE
        def active = true
        def createdAt = LocalDateTime.now()
        def updatedAt = LocalDateTime.now()

        when:
        AddressDomain addressDomain = new AddressDomain(zipCode, addressComplete, typeAddress, address, getDistrictDomain(), getCityDomain(), getStateDomain(), getRegionDomain(), latitude, longitude, active, createdAt, updatedAt)

        then:
        addressDomain.getZipCode() == addressDomain.getZipCode()
        addressDomain.getAddressComplete() == addressDomain.getAddressComplete()
        addressDomain.getTypeAddress() == addressDomain.getTypeAddress()
        addressDomain.getAddress() == addressDomain.getAddress()
        addressDomain.getLatitude() == addressDomain.getLatitude()
        addressDomain.getLongitude() == addressDomain.getLongitude()
        addressDomain.getActive() == addressDomain.getActive()
        addressDomain.getCreatedAt() == addressDomain.getCreatedAt()
        addressDomain.getUpdatedAt() == addressDomain.getUpdatedAt()
    }

    def "Should converting AddressEntity to AddressDomain"() {
        given:
        AddressEntity addressEntity = new AddressEntity(
                zipCode: "12345-678",
                addressComplete: "São Paulo",
                typeAddress: "Praça",
                address: "Praça da Sé",
                district: getDistrictEntity(),
                city: getCityEntity(),
                state: getStateEntity(),
                region: getRegionEntity(),
                latitude: BigDecimal.ONE,
                longitude: BigDecimal.ONE,
                active: true,
                createdAt: LocalDateTime.now(),
                updatedAt: LocalDateTime.now()
        )

        when:
        AddressDomain addressDomain = AddressDomain.toDomain(addressEntity)

        then:
        addressDomain.getZipCode() == addressEntity.getZipCode()
        addressDomain.getAddressComplete() == addressEntity.getAddressComplete()
        addressDomain.getTypeAddress() == addressEntity.getTypeAddress()
        addressDomain.getAddress() == addressEntity.getAddress()
        addressDomain.getDistrictDomain().getId() == addressEntity.getDistrict().getId()
        addressDomain.getDistrictDomain().getDistrict() == addressEntity.getDistrict().getDistrict()
        addressDomain.getCityDomain().getId() == addressEntity.getCity().getId()
        addressDomain.getCityDomain().getCity() == addressEntity.getCity().getCity()
        addressDomain.getCityDomain().getLatitude() == addressEntity.getCity().getLatitude()
        addressDomain.getCityDomain().getLongitude() == addressEntity.getCity().getLongitude()
        addressDomain.getCityDomain().getDddCode() == addressEntity.getCity().getDddCode()
        addressDomain.getStateDomain().getId() == addressEntity.getState().getId()
        addressDomain.getStateDomain().getState() == addressEntity.getState().getState()
        addressDomain.getStateDomain().getUf() == addressEntity.getState().getUf()
        addressDomain.getRegionDomain().getId() == addressEntity.getRegion().getId()
        addressDomain.getRegionDomain().getRegion() == addressEntity.getRegion().getRegion()
        addressDomain.getLatitude() == addressEntity.getLatitude()
        addressDomain.getLongitude() == addressEntity.getLongitude()
        addressDomain.getActive() == addressEntity.getActive()
        addressDomain.getCreatedAt() == addressEntity.getCreatedAt()
        addressDomain.getUpdatedAt() == addressEntity.getUpdatedAt()
    }

    @Unroll("#description")
    def "Should throw AddressDomainException when"() {
        when:
        create.call()

        then:
        def exception = thrown(AddressDomainException)
        exception.getMessage() == message

        where:
        description                | create                                                                                                                                                                                                                        | message
        "zipCode is empty"         | ({ new AddressDomain("", "Praça da Sé", "Praça", "Sé", getDistrictDomain(), getCityDomain(), getStateDomain(), getRegionDomain(), BigDecimal.ONE, BigDecimal.ONE, true, LocalDateTime.now(), LocalDateTime.now()) })          | "Param [zipCode] cannot be null or empty"
        "latitude is null"         | ({ new AddressDomain("12345-678", "Praça da Sé", "Praça", "Sé", getDistrictDomain(), getCityDomain(), getStateDomain(), getRegionDomain(), null, BigDecimal.ONE, true, LocalDateTime.now(), LocalDateTime.now()) })           | "Param [latitude] cannot be null"
        "longitude is null"        | ({ new AddressDomain("12345-678", "Praça da Sé", "Praça", "Sé", getDistrictDomain(), getCityDomain(), getStateDomain(), getRegionDomain(), BigDecimal.ONE, null, true, LocalDateTime.now(), LocalDateTime.now()) })           | "Param [longitude] cannot be null"
        "active is null"           | ({ new AddressDomain("12345-678", "Praça da Sé", "Praça", "Sé", getDistrictDomain(), getCityDomain(), getStateDomain(), getRegionDomain(), BigDecimal.ONE, BigDecimal.ONE, null, LocalDateTime.now(), LocalDateTime.now()) }) | "Param [active] cannot be null"
    }

    def getRegionDomain() {
        return new RegionDomain(1,
                "Sudeste",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    def getStateDomain() {
        new StateDomain(1,
                "São Paulo",
                "São Paulo",
                "SP",
                BigDecimal.ONE,
                BigDecimal.ONE,
                getRegionDomain(),
                LocalDateTime.now(),
                LocalDateTime.now())
    }

    def getCityDomain() {
        new CityDomain(1, "São Paulo", BigDecimal.ONE, BigDecimal.ONE, 11, getStateDomain(), LocalDateTime.now(), LocalDateTime.now())
    }

    def getDistrictDomain() {
        new DistrictDomain(1, "Sé", BigDecimal.ONE, BigDecimal.ONE, getCityDomain(), LocalDateTime.now(), LocalDateTime.now())
    }

    def getRegionEntity() {
        return new RegionEntity(id: 1, region: "Sudeste", createdAt: LocalDateTime.now(), updatedAt: LocalDateTime.now());
    }

    def getStateEntity() {
        return new StateEntity(
                id: 1,
                state: "São Paulo",
                capital: "São Paulo",
                uf: "SP",
                latitude: BigDecimal.ONE,
                longitude: BigDecimal.ONE,
                region: getRegionEntity(),
                createdAt: LocalDateTime.now(),
                updatedAt: LocalDateTime.now()
        )
    }

    def getCityEntity() {
        return new CityEntity(
                id: 1,
                city: "São Paulo",
                latitude: BigDecimal.ONE,
                longitude: BigDecimal.ONE,
                dddCode: 11,
                state: getStateEntity(),
                createdAt: LocalDateTime.now(),
                updatedAt: LocalDateTime.now()
        )
    }

    def getDistrictEntity() {
        new DistrictEntity(
                id: 1,
                district: "Centro",
                latitude: BigDecimal.ONE,
                longitude: BigDecimal.ONE,
                city: getCityEntity(),
                createdAt: LocalDateTime.now(),
                updatedAt: LocalDateTime.now()
        )
    }

}
