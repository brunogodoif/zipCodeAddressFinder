package br.com.brunogodoif.zipcodeaddressfinder.commons;

import br.com.brunogodoif.zipcodeaddressfinder.commons.exceptions.ZipCodeException;
import br.com.brunogodoif.zipcodeaddressfinder.commons.exceptions.ZipCodeFormatException;
import org.apache.commons.lang3.StringUtils;

public class ZipCodeUtil {
    public static void validate(String zipCode) {

        if (StringUtils.isEmpty(zipCode))
            throw new ZipCodeException("The zip code entered cannot be null or empty");

        zipCode = zipCode.replace("-", "");

        if (zipCode.length() > 8)
            throw new ZipCodeFormatException("Postal code with many numbers, It was informed [" + zipCode + "]");

        if (zipCode.length() < 8)
            throw new ZipCodeException("Zip code missing numbers, Has been informed [" + zipCode + "]");
    }

    public static String removeMask(String cep) {
        validate(cep);
        return cep.replace("-", "");
    }

    public static String applyMask(String cep) {
        validate(cep);
        return cep.substring(0, 5) + "-" + cep.substring(5);
    }
}