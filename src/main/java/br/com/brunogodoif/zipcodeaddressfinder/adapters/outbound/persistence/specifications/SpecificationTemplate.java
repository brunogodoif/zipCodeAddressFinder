package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.specifications;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.AddressEntity;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.request.AddressSearch;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {
    @And({
            @Spec(path = "address", spec = LikeIgnoreCase.class),
            @Spec(path = "city", spec = LikeIgnoreCase.class),
            @Spec(path = "idState", spec = Equal.class),
    })
    public interface AddressSpec extends Specification<AddressEntity> {
    }

    public static AddressSpec convertToAddressSpec(AddressSearch addressSearch) {
        return (root, query, builder) -> {
            javax.persistence.criteria.Predicate predicate = builder.conjunction();
            if (addressSearch.getAddress() != null) {
                predicate = builder.and(predicate, builder.like(builder.lower(root.get("address")), "%" + addressSearch.getAddress().toLowerCase() + "%"));
            }
//            if (addressSearch.getDistrict() != null) {
//                predicate = builder.and(predicate, builder.like(builder.lower(root.get("address")), "%" + addressSearch.getDistrict().toLowerCase() + "%"));
//            }
//            if (addressSearch.getCity() != null) {
//                predicate = builder.and(predicate, builder.like(builder.lower(root.get("address")), "%" + addressSearch.getCity().toLowerCase() + "%"));
//            }
//            if (addressSearch.getState() != null) {
//                predicate = builder.and(predicate, builder.like(builder.lower(root.get("address")), "%" + addressSearch.getState().toLowerCase() + "%"));
//            }
            return predicate;
        };
    }

}
