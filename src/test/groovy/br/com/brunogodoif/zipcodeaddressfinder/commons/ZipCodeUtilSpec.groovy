package br.com.brunogodoif.zipcodeaddressfinder.commons

import br.com.brunogodoif.zipcodeaddressfinder.commons.ZipCodeUtil
import br.com.brunogodoif.zipcodeaddressfinder.commons.exceptions.ZipCodeException
import br.com.brunogodoif.zipcodeaddressfinder.commons.exceptions.ZipCodeFormatException
import spock.lang.Specification
import spock.lang.Unroll

class ZipCodeUtilSpec extends Specification {


    @Unroll("#description")
    def "Should throw exception when"() {
        when:
        ZipCodeUtil.validate(zipCode)

        then:
        def exception = thrown(ZipCodeException)
        exception.getMessage() == message

        where:
        description      | zipCode  | message
        "null zip code"  | null     | "The zip code entered cannot be null or empty"
        "empty zip code" | ""       | "The zip code entered cannot be null or empty"
        "short zip code" | "123456" | "Zip code missing numbers, Has been informed [123456]"
    }

    def "Should throw ZipCodeFormatException for zip code with more than 8 characters"() {
        when:
        ZipCodeUtil.validate("123456789")

        then:
        thrown(ZipCodeFormatException)
    }

    def "Should not throw any exception for valid zip code"() {
        when:
        ZipCodeUtil.validate("12345678")

        then:
        notThrown(Exception)
    }

    def "Should remove dashes from zip code"() {
        when:
        def result = ZipCodeUtil.removeMask("12345-678")

        then:
        result == "12345678"
    }

    def "Should apply dashes to zip code"() {
        when:
        def result = ZipCodeUtil.applyMask("12345678")

        then:
        result == "12345-678"
    }

}
