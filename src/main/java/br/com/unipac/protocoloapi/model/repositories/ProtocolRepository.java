package br.com.unipac.protocoloapi.model.repositories;

import br.com.unipac.protocoloapi.model.domain.Protocol;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtocolRepository extends PagingAndSortingRepository<Protocol, Long>, JpaSpecificationExecutor<Protocol> {
    List<Protocol> findAllByName(String name, Pageable pageable);
}
