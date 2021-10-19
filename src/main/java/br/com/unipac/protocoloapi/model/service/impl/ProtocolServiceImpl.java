package br.com.unipac.protocoloapi.model.service.impl;

import br.com.unipac.protocoloapi.model.domain.Protocol;
import br.com.unipac.protocoloapi.model.repositories.ProtocolRepository;
import br.com.unipac.protocoloapi.model.service.ProtocolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
    public Protocol findById(Long id) {
        return protocolRepository.findById(id).orElse(null);
    }

    @Override
    public List<Protocol> findAll(int pageNumber, int rowPerPage) {
        List<Protocol> protocolList = new ArrayList<>();

        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage, Sort.by("id").ascending());
        protocolRepository.findAll(sortedByIdAsc).forEach(protocolList::add);

        /*Page<Protocol> all = protocolRepository.findAll(sortedByIdAsc);
        for (Protocol p: all) {
            protocolList.add(p);
        }*/
        return protocolList;
    }

    @Override
    public Protocol save(Protocol protocol) {
        if (!StringUtils.isEmpty(protocol.getName())) {
            if (protocol.getId() != null && existsById(protocol.getId())) {
                log.info("Esse id já existe na nossa base");
                return null;
            } else {
                return protocolRepository.save(protocol);
            }
        } else {
            return null;
        }
    }

    @Override
    public Protocol update(Long id, Protocol protocol) {
        boolean protocolExists = existsById(id);
        if (!StringUtils.isEmpty(protocol.getName())) {
            if (!protocolExists) {
                log.info("Esse id nao existe na nossa base");
                return null;
            }

            //early return
            return protocolRepository.save(protocol);
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        if (!existsById(id)) {
            log.info("Esse id nao existe na nossa base");
        }

        protocolRepository.deleteById(id);
    }

    @Override
    public Long count() {
        return protocolRepository.count();
    }
}
