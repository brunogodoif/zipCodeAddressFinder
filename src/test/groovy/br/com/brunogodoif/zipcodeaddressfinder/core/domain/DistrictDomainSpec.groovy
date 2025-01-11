package br.com.brunogodoif.zipcodeaddressfinder.core.domain

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.CityEntity
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.DistrictEntity
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.RegionEntity
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.StateEntity
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.exceptions.DistrictDomainException
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class DistrictDomainSpec extends Specification {


    def "Should creating a valid DistrictDomain"() {
        setup:
        def id = 1
        def district = "Centro"
        def latitude = BigDecimal.ONE
        def longitude = BigDecimal.ONE
        def createdAt = LocalDateTime.now()
        def updatedAt = LocalDateTime.now()

        when:
        DistrictDomain districtDomain = new DistrictDomain(id, district, latitude, longitude, getCityDomain(), createdAt, updatedAt)

        then:
        districtDomain.getId() == id
        districtDomain.getDistrict() == district
        districtDomain.getLatitude() == latitude
        districtDomain.getLongitude() == longitude
        districtDomain.getCreatedAt() == createdAt
        districtDomain.getUpdatedAt() == updatedAt
    }

    def "Should creating a valid DistrictDomain  without id attribute"() {
        setup:
        def district = "Centro"
        def latitude = BigDecimal.ONE
        def longitude = BigDecimal.ONE
        def createdAt = LocalDateTime.now()
        def updatedAt = LocalDateTime.now()

        when:
        DistrictDomain districtDomain = new DistrictDomain(district, latitude, longitude, getCityDomain(), createdAt, updatedAt)

        then:
        districtDomain.getDistrict() == district
        districtDomain.getLatitude() == latitude
        districtDomain.getLongitude() == longitude
        districtDomain.getCreatedAt() == createdAt
        districtDomain.getUpdatedAt() == updatedAt
    }

    def "Should converting DistrictEntity to DistrictDomain"() {
        given:
        DistrictEntity districtEntity = new DistrictEntity(
                id: 1,
                district: "Centro",
                latitude: BigDecimal.ONE,
                longitude: BigDecimal.ONE,
                city: getCityEntity(),
                createdAt: LocalDateTime.now(),
                updatedAt: LocalDateTime.now()
        )

        when:
        DistrictDomain districtDomain = DistrictDomain.toDomain(districtEntity)

        then:
        districtDomain.getId() == districtEntity.getId()
        districtDomain.getDistrict() == districtEntity.getDistrict()
        districtDomain.getLatitude() == districtEntity.getLatitude()
        districtDomain.getLongitude() == districtEntity.getLongitude()
        districtDomain.getCityDomain().getId() == districtEntity.getCity().getId()
        districtDomain.getCityDomain().getCity() == districtEntity.getCity().getCity()
        districtDomain.getCityDomain().getLatitude() == districtEntity.getCity().getLatitude()
        districtDomain.getCityDomain().getLongitude() == districtEntity.getCity().getLongitude()
        districtDomain.getCityDomain().getDddCode() == districtEntity.getCity().getDddCode()
        districtDomain.getCreatedAt() == districtEntity.getCreatedAt()
        districtDomain.getUpdatedAt() == districtEntity.getUpdatedAt()
    }

    @Unroll("#description")
    def "Should throw DistrictDomainException when"() {
        when:
        create.call()

        then:
        def exception = thrown(DistrictDomainException)
        exception.getMessage() == message

        where:
        description         | create                                                                                                                          | message
        "id is null"        | ({ new DistrictDomain(null, "Centro", BigDecimal.ONE, BigDecimal.ONE, getCityDomain(), LocalDateTime.now(), LocalDateTime.now()) }) | "Param [id] cannot be null"
        "id is negative"    | ({ new DistrictDomain(-1, "Centro", BigDecimal.ONE, BigDecimal.ONE, getCityDomain(), LocalDateTime.now(), LocalDateTime.now()) })   | "Param [id] must be a positive integer"
        "district is empty" | ({ new DistrictDomain(1, "", BigDecimal.ONE, BigDecimal.ONE, getCityDomain(), LocalDateTime.now(), LocalDateTime.now()) })      | "Param [district] cannot be null or empty"
        "latitude is null"  | ({ new DistrictDomain(1, "Centro", null, BigDecimal.ONE, getCityDomain(), LocalDateTime.now(), LocalDateTime.now()) })              | "Param [latitude] cannot be null"
        "longitude is null" | ({ new DistrictDomain(1, "Centro", BigDecimal.ONE, null, getCityDomain(), LocalDateTime.now(), LocalDateTime.now()) })              | "Param [longitude] cannot be null"
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


}
