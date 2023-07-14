package br.com.brunogodoif.zipcodeaddressfinder.common.zipcode;

import br.com.brunogodoif.zipcodeaddressfinder.common.zipcode.exceptions.ZipCodeException;
import br.com.brunogodoif.zipcodeaddressfinder.common.zipcode.exceptions.ZipCodeFormatException;
import org.apache.commons.lang3.StringUtils;

public class ZipCodeUtil {
    public static void validate(String zipCode) {
        zipCode = zipCode.replace("-", "");
        if (StringUtils.isEmpty(zipCode))
            throw new ZipCodeException("The zip code entered cannot be null or empty");
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