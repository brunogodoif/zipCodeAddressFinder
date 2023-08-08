package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.specifications;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.AddressEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.CityEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.DistrictEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.StateEntity;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.request.AddressSearch;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

public class SpecificationTemplate {
    @And({
            @Spec(path = "address", spec = LikeIgnoreCase.class),
            @Spec(path = "district.district", params = "district", spec = LikeIgnoreCase.class),
            @Spec(path = "city.city", params = "city", spec = LikeIgnoreCase.class),
            @Spec(path = "state.uf", params = "state", spec = EqualIgnoreCase.class)
    })
    public interface AddressSpec extends Specification<AddressEntity> {
    }

    public static AddressSpec convertToAddressSpec(AddressSearch addressSearch) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (addressSearch.getAddress() != null) {
                predicate = builder.and(predicate, builder.like(builder.lower(root.get("addressComplete")), "%" + addressSearch.getAddress().toLowerCase() + "%"));
            }
            if (addressSearch.getDistrict() != null) {
                Join<AddressEntity, DistrictEntity> districtJoin = root.join("district", JoinType.INNER);
                predicate = builder.and(predicate, builder.like(builder.lower(districtJoin.get("district")), "%" + addressSearch.getDistrict().toLowerCase() + "%"));
            }
            if (addressSearch.getCity() != null) {
                Join<AddressEntity, CityEntity> cityJoin = root.join("city", JoinType.INNER);
                predicate = builder.and(predicate, builder.like(builder.lower(cityJoin.get("city")), "%" + addressSearch.getCity().toLowerCase() + "%"));
            }
            if (addressSearch.getState() != null) {
                Join<AddressEntity, StateEntity> stateJoin = root.join("state", JoinType.INNER);
                predicate = builder.and(predicate, builder.equal(builder.lower(stateJoin.get("uf")), addressSearch.getState().toUpperCase()));
            }

            return predicate;
        };
    }

}
