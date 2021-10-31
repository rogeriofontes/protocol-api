package br.com.unipac.protocoloapi.model.service.impl;

import br.com.unipac.protocoloapi.model.domain.Protocol;
import br.com.unipac.protocoloapi.exception.BadResourceException;
import br.com.unipac.protocoloapi.exception.ResourceAlreadyExistsException;
import br.com.unipac.protocoloapi.exception.ResourceNotFoundException;
import br.com.unipac.protocoloapi.model.repositories.ProtocolRepository;
import br.com.unipac.protocoloapi.model.repositories.specification.ProtocolSpecification;
import br.com.unipac.protocoloapi.model.service.ProtocolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProtocolServiceImpl implements ProtocolService {

    private final ProtocolRepository protocolRepository;

    @Override
    public boolean existsById(Long id) {
        return protocolRepository.existsById(id);
    }

    @Override
    public Protocol findById(Long id) throws ResourceNotFoundException {
        Protocol protocol = protocolRepository.findById(id).orElse(null);

        if (Objects.isNull(protocol)) {
            throw new ResourceNotFoundException("Cannot find Protocol with id: " + id);
        }

        return protocol;
    }

    @Override
    public List<Protocol> findAll(int pageNumber, int rowPerPage) throws ResourceNotFoundException {
        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage, Sort.by("id").ascending());
        List<Protocol> protocolList = protocolRepository.findAll(sortedByIdAsc).getContent();

        if (protocolList.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find Protocols: " + protocolList.size());
        }

        return protocolList;
    }

    @Override
    public List<Protocol> findAllByName(String name, int pageNumber, int size) {
        Protocol filter = Protocol.builder().name(name).build();
        Specification<Protocol> spec = new ProtocolSpecification(filter);

        PageRequest pagination = PageRequest.of(pageNumber, size);
        return protocolRepository.findAll(spec, pagination).getContent();
    }

    @Override
    public Protocol save(Protocol protocol) throws ResourceAlreadyExistsException, BadResourceException {
        if (!StringUtils.isEmpty(protocol.getName())) {

            if (protocol.getId() != null && existsById(protocol.getId())) {
                throw new ResourceAlreadyExistsException("Contact with id: " + protocol.getId() +
                        " already exists");
            }

            return protocolRepository.save(protocol);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save protocol");
            exc.addErrorMessage("Protocol is null or empty");
            throw exc;
        }
    }

    @Override
    public Protocol update(Long id, Protocol protocol) throws ResourceNotFoundException, BadResourceException {
        boolean protocolExists = existsById(id);
        if (!StringUtils.isEmpty(protocol.getName())) {

            if (!protocolExists) {
                throw new ResourceNotFoundException("Cannot find Contact with id: " + protocol.getId());
            }

            protocol.update(id, protocol);
            return protocolRepository.save(protocol);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save protocol");
            exc.addErrorMessage("Protocol is null or empty");
            throw exc;
        }
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find contact with id: " + id);
        }

        protocolRepository.deleteById(id);
    }

    @Override
    public Long count() {
        return protocolRepository.count();
    }


}
