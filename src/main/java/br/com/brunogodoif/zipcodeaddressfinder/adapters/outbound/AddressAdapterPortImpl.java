package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.AddressEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.CityEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.DistrictEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository.AddressRepository;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.specifications.SpecificationTemplate;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.*;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.pagination.PaginationRequest;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.request.AddressSearch;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.AddressAdapterPort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AddressAdapterPortImpl implements AddressAdapterPort {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressAdapterPortImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<AddressDomain> findAll(AddressSearch addressSearch, PaginationRequest paginationRequest) {

        Pageable pageable = PageRequest.of(paginationRequest.getPageNumber() - 1, paginationRequest.getPageSize());
        SpecificationTemplate.AddressSpec addressSpec = SpecificationTemplate.convertToAddressSpec(addressSearch);

        Page<AddressEntity> all = this.addressRepository.findAll(addressSpec, pageable);

        if (all.isEmpty()) {
            return Page.empty();
        }

        List<AddressDomain> addressDomainList = all
                .stream()
                .map(this::convertAddressEntityToDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(addressDomainList, pageable, all.getTotalElements());

    }

    @Override
    public Optional<AddressDomain> getByZipCode(String zipCode) {

        Optional<AddressEntity> byZipCodeOptional = this.addressRepository.findByZipCode(zipCode);

        if (!byZipCodeOptional.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(this.convertAddressEntityToDomain(byZipCodeOptional.get()));
    }

    @Override
    public AddressDomain persist(AddressDomain addressDomain) {
        AddressEntity save = this.addressRepository.save(modelMapper.map(addressDomain, AddressEntity.class));
        return this.convertAddressEntityToDomain(save);
    }

    public AddressDomain convertAddressEntityToDomain(AddressEntity addressEntity) {

        return new AddressDomain(
                addressEntity.getZipCode(),
                addressEntity.getAddressComplete(),
                addressEntity.getTypeAddress(),
                addressEntity.getAddress(),
                new DistrictDomain(
                        addressEntity.getDistrict().getId(),
                        addressEntity.getDistrict().getDistrict(),
                        addressEntity.getDistrict().getLatitude(),
                        addressEntity.getDistrict().getLongitude(),
                        addressEntity.getDistrict().getCreatedAt(),
                        addressEntity.getDistrict().getUpdatedAt()
                ),
                new CityDomain(
                        addressEntity.getCity().getId(),
                        addressEntity.getCity().getCity(),
                        addressEntity.getCity().getLatitude(),
                        addressEntity.getCity().getLongitude(),
                        addressEntity.getCity().getDddCode(),
                        addressEntity.getCity().getCreatedAt(),
                        addressEntity.getCity().getUpdatedAt()
                ),
                new StateDomain(
                        addressEntity.getState().getId(),
                        addressEntity.getState().getState(),
                        addressEntity.getState().getCapital(),
                        addressEntity.getState().getUf(),
                        addressEntity.getState().getLatitude(),
                        addressEntity.getState().getLongitude(),
                        addressEntity.getState().getCreatedAt(),
                        addressEntity.getState().getUpdatedAt()
                ),
                new RegionDomain(
                        addressEntity.getRegion().getId(),
                        addressEntity.getRegion().getRegion(),
                        addressEntity.getRegion().getCreatedAt(),
                        addressEntity.getRegion().getUpdatedAt()
                ),
                addressEntity.getLatitude(),
                addressEntity.getLongitude(),
                addressEntity.getActive(),
                addressEntity.getCreatedAt(),
                addressEntity.getUpdatedAt()
        );


    }
}
