package br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.entities.AddressEntity;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.repository.AddressRepository;
import br.com.brunogodoif.zipcodeaddressfinder.adapters.outbound.persistence.specifications.SpecificationTemplate;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.AddressDomain;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.pagination.PaginationRequest;
import br.com.brunogodoif.zipcodeaddressfinder.core.domain.request.AddressSearch;
import br.com.brunogodoif.zipcodeaddressfinder.core.ports.outbound.AddressAdapterPort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AddressAdapterPortImpl implements AddressAdapterPort {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @PersistenceContext private EntityManager entityManager;

    public AddressAdapterPortImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<AddressDomain> findAll(AddressSearch addressSearch, PaginationRequest paginationRequest) {

        Pageable pageable = PageRequest.of(paginationRequest.getPageNumber() - 1, paginationRequest.getPageSize());

        Page<AddressEntity> all = this.addressRepository.findAll(
                SpecificationTemplate.convertToAddressSpec(addressSearch), pageable);

        if (all.isEmpty()) {
            return Page.empty();
        }

        List<AddressDomain> addressDomainList = all.stream().map(AddressDomain::toDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(addressDomainList, pageable, all.getTotalElements());

    }

    @Override
    public Optional<AddressDomain> getByZipCode(String zipCode) {

        return addressRepository.findByZipCode(zipCode)
                                .flatMap(addressEntity -> Optional.of(AddressDomain.toDomain(addressEntity)));
    }

    @Override @Transactional
    public AddressDomain persist(AddressDomain addressDomain) {
        AddressEntity save = this.addressRepository.save(modelMapper.map(addressDomain, AddressEntity.class));
        return AddressDomain.toDomain(save);
    }

    @Override @Transactional(propagation = Propagation.REQUIRED)
    public void persistBatch(List<AddressDomain> addressDomains) {
        int batchSize = 1000; // Tamanho do sub-lote para flush
        int count = 0;

        for (AddressDomain addressDomain : addressDomains) {
            AddressEntity addressEntity = modelMapper.map(addressDomain, AddressEntity.class);
            entityManager.persist(addressEntity);
            count++;

            // Faz flush e limpa o contexto de persistência a cada batchSize registros
            if (count % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        // Garante que os registros restantes sejam persistidos
        if (count % batchSize != 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }

}
