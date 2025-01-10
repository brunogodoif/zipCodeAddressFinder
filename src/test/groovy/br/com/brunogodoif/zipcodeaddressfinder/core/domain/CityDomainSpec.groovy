package br.com.brunogodoif.zipcodeaddressfinder.core.domain

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.CityEntity
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.RegionEntity
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.StateEntity
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.exceptions.CityDomainException
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class CityDomainSpec extends Specification {

    def "Should creating a valid CityDomain"() {
        setup:
        def id = 1
        def city = "São Paulo"
        def latitude = BigDecimal.ONE
        def longitude = BigDecimal.ONE
        def dddCode = 11
        def createdAt = LocalDateTime.now()
        def updatedAt = LocalDateTime.now()

        when:
        CityDomain cityDomain = new CityDomain(id, city, latitude, longitude, dddCode, getStateDomain(), createdAt, updatedAt)

        then:
        cityDomain.getId() == id
        cityDomain.getCity() == city
        cityDomain.getLatitude() == latitude
        cityDomain.getLongitude() == longitude
        cityDomain.getDddCode() == dddCode
        cityDomain.getCreatedAt() == createdAt
        cityDomain.getUpdatedAt() == updatedAt
    }

    def "Should creating a valid CityDomain without id attribute"() {
        setup:
        def city = "São Paulo"
        def latitude = BigDecimal.ONE
        def longitude = BigDecimal.ONE
        def dddCode = 11
        def createdAt = LocalDateTime.now()
        def updatedAt = LocalDateTime.now()

        when:
        CityDomain cityDomain = new CityDomain(city, latitude, longitude, dddCode, getStateDomain(), createdAt, updatedAt)

        then:
        cityDomain.getCity() == city
        cityDomain.getLatitude() == latitude
        cityDomain.getLongitude() == longitude
        cityDomain.getDddCode() == dddCode
        cityDomain.getCreatedAt() == createdAt
        cityDomain.getUpdatedAt() == updatedAt
    }

    def "Should converting CityEntity to CityDomain"() {
        setup:
        CityEntity cityEntity = new CityEntity(
                id: 1,
                city: "São Paulo",
                latitude: BigDecimal.ONE,
                longitude: BigDecimal.ONE,
                dddCode: 11,
                state: getStateEntity(),
                createdAt: LocalDateTime.now(),
                updatedAt: LocalDateTime.now()
        )

        when:
        CityDomain cityDomain = CityDomain.toDomain(cityEntity)

        then:
        cityDomain.getId() == cityEntity.getId()
        cityDomain.getCity() == cityEntity.getCity()
        cityDomain.getLatitude() == cityEntity.getLatitude()
        cityDomain.getLongitude() == cityEntity.getLongitude()
        cityDomain.getDddCode() == cityEntity.getDddCode()
        cityDomain.getStateDomain().getId() == cityEntity.getState().getId()
        cityDomain.getStateDomain().getState() == cityEntity.getState().getState()
        cityDomain.getCreatedAt() == cityEntity.getCreatedAt()
        cityDomain.getUpdatedAt() == cityEntity.getUpdatedAt()
    }

    @Unroll("#description")
    def "Should throw CityDomainException when"() {
        when:
        create.call()

        then:
        def exception = thrown(CityDomainException)
        exception.getMessage() == message

        where:
        description         | create                                                                                                                                  | message
        "id is null"        | ({ new CityDomain(null, "São Paulo", BigDecimal.ONE, BigDecimal.ONE, 11, getStateDomain(), LocalDateTime.now(), LocalDateTime.now()) }) | "Param [id] cannot be null"
        "id is negative"    | ({ new CityDomain(-1, "São Paulo", BigDecimal.ONE, BigDecimal.ONE, 11, getStateDomain(), LocalDateTime.now(), LocalDateTime.now()) })   | "Param [id] must be a positive integer"
        "city is empty"     | ({ new CityDomain(1, "", BigDecimal.ONE, BigDecimal.ONE, 11, getStateDomain(), LocalDateTime.now(), LocalDateTime.now()) })             | "Param [city] cannot be null or empty"
        "latitude is null"  | ({ new CityDomain(1, "São Paulo", null, BigDecimal.ONE, 11, getStateDomain(), LocalDateTime.now(), LocalDateTime.now()) })              | "Param [latitude] cannot be null"
        "longitude is null" | ({ new CityDomain(1, "São Paulo", BigDecimal.ONE, null, 11, getStateDomain(), LocalDateTime.now(), LocalDateTime.now()) })              | "Param [longitude] cannot be null"
        "dddCode is null"   | ({ new CityDomain(1, "São Paulo", BigDecimal.ONE, BigDecimal.ONE, null, getStateDomain(), LocalDateTime.now(), LocalDateTime.now()) })  | "Param [dddCode] cannot be null"
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


}

