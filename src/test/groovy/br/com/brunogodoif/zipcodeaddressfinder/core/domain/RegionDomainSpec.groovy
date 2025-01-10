package br.com.brunogodoif.zipcodeaddressfinder.core.domain

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.RegionEntity
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.exceptions.RegionDomainException
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class RegionDomainSpec extends Specification {

    def "Should creating a valid RegionDomain"() {
        setup:
        def id = 1
        def region = "Sudeste"
        def createdAt = LocalDateTime.now()
        def updatedAt = LocalDateTime.now()

        when:
        RegionDomain regionDomain = new RegionDomain(id, region, createdAt, updatedAt)

        then:
        regionDomain.getId() == id
        regionDomain.getRegion() == region
        regionDomain.getCreatedAt() == createdAt
        regionDomain.getUpdatedAt() == updatedAt
    }

    def "Should converting RegionEntity to RegionDomain"() {
        setup:
        RegionEntity regionEntity = new RegionEntity(id: 1, region: "Sudeste", createdAt: LocalDateTime.now(), updatedAt: LocalDateTime.now())

        when:
        RegionDomain regionDomain = RegionDomain.toDomain(regionEntity)

        then:
        regionDomain.getId() == regionEntity.getId()
        regionDomain.getRegion() == regionEntity.getRegion()
        regionDomain.getCreatedAt() == regionEntity.getCreatedAt()
        regionDomain.getUpdatedAt() == regionEntity.getUpdatedAt()
    }

    @Unroll("#description")
    def "Should throw RegionDomainException when"() {
        when:
        create.call()

        then:
        def exception = thrown(RegionDomainException)
        exception.getMessage() == message

        where:
        description       | create                                                                          | message
        "id is null"      | ({ new RegionDomain(null, "Sudeste", LocalDateTime.now(), LocalDateTime.now()) }) | "Param [id] cannot be null"
        "id is negative"  | ({ new RegionDomain(-1, "Sudeste", LocalDateTime.now(), LocalDateTime.now()) })   | "Param [id] must be a positive integer"
        "region is empty" | ({ new RegionDomain(1, "", LocalDateTime.now(), LocalDateTime.now()) })         | "Param [region] cannot be null or empty"
    }


}
