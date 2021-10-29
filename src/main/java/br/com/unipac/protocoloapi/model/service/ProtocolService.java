package br.com.unipac.protocoloapi.model.service;

import br.com.unipac.protocoloapi.model.domain.Protocol;

import java.util.List;

public interface ProtocolService extends CrudService<Protocol, Long> {
    List<Protocol> findAllByName(String name, int pageNumber, int size);
}
