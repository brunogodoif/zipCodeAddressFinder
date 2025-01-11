package br.com.brunogodoif.zipcodeaddressfinder.core.domain

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.RegionEntity
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.StateEntity
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.exceptions.StateDomainException
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class StateDomainSpec extends Specification {

    def "Should creating a valid RegionDomain"() {
        setup:
        def id = 1
        def state = "São Paulo"
        def capital = "São Paulo"
        def uf = "SP"
        def latitude = BigDecimal.ONE
        def longitude = BigDecimal.ONE
        def createdAt = LocalDateTime.now()
        def updatedAt = LocalDateTime.now()

        when:
        StateDomain stateDomain = new StateDomain(id, state, capital, uf, latitude, longitude, getRegionDomain(), createdAt, updatedAt)

        then:
        stateDomain.getId() == id
        stateDomain.getState() == state
        stateDomain.getCapital() == capital
        stateDomain.getUf() == uf
        stateDomain.getLatitude() == latitude
        stateDomain.getLongitude() == longitude
        stateDomain.getCreatedAt() == createdAt
        stateDomain.getUpdatedAt() == updatedAt
    }

    def "Should converting StateEntity to StateDomain"() {
        setup:
        StateEntity stateEntity = new StateEntity(
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

        when:
        StateDomain stateDomain = StateDomain.toDomain(stateEntity)

        then:
        stateDomain.getId() == stateEntity.getId()
        stateDomain.getState() == stateEntity.getState()
        stateDomain.getCapital() == stateEntity.getCapital()
        stateDomain.getUf() == stateEntity.getUf()
        stateDomain.getLatitude() == stateEntity.getLatitude()
        stateDomain.getLongitude() == stateEntity.getLongitude()
        stateDomain.getRegionDomain().getId() == stateEntity.getRegion().getId()
        stateDomain.getRegionDomain().getRegion() == stateEntity.getRegion().getRegion()
        stateDomain.getCreatedAt() == stateEntity.getCreatedAt()
        stateDomain.getUpdatedAt() == stateEntity.getUpdatedAt()
    }

    @Unroll("#description")
    def "Should throw StateDomainException when"() {
        when:
        create.call()

        then:
        def exception = thrown(StateDomainException)
        exception.getMessage() == message

        where:
        description         | create                                                                                                                                                   | message
        "id is null"        | ({ new StateDomain(null, "São Paulo", "São Paulo", "SP", BigDecimal.ONE, BigDecimal.ONE, getRegionDomain(), LocalDateTime.now(), LocalDateTime.now()) }) | "Param [id] cannot be null"
        "id is negative"    | ({ new StateDomain(-1, "São Paulo", "São Paulo", "SP", BigDecimal.ONE, BigDecimal.ONE, getRegionDomain(), LocalDateTime.now(), LocalDateTime.now()) })   | "Param [id] must be a positive integer"
        "state is empty"    | ({ new StateDomain(1, "", "São Paulo", "SP", BigDecimal.ONE, BigDecimal.ONE, getRegionDomain(), LocalDateTime.now(), LocalDateTime.now()) })             | "Param [state] cannot be null or empty"
        "capital is empty"  | ({ new StateDomain(1, "São Paulo", "", "SP", BigDecimal.ONE, BigDecimal.ONE, getRegionDomain(), LocalDateTime.now(), LocalDateTime.now()) })             | "Param [capital] cannot be null or empty"
        "uf is empty"       | ({ new StateDomain(1, "São Paulo", "São Paulo", "", BigDecimal.ONE, BigDecimal.ONE, getRegionDomain(), LocalDateTime.now(), LocalDateTime.now()) })      | "Param [uf] cannot be null or empty"
        "latitude is null"  | ({ new StateDomain(1, "São Paulo", "São Paulo", "SP", null, BigDecimal.ONE, getRegionDomain(), LocalDateTime.now(), LocalDateTime.now()) })              | "Param [latitude] cannot be null"
        "longitude is null" | ({ new StateDomain(1, "São Paulo", "São Paulo", "SP", BigDecimal.ONE, null, getRegionDomain(), LocalDateTime.now(), LocalDateTime.now()) })              | "Param [longitude] cannot be null"
    }

    def getRegionDomain() {
        return new RegionDomain(1,
                "Sudeste",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    def getRegionEntity() {
        return new RegionEntity(id: 1, region: "Sudeste", createdAt: LocalDateTime.now(), updatedAt: LocalDateTime.now());
    }

}
