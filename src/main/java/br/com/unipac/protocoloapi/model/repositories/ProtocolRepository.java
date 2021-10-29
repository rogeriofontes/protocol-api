package br.com.unipac.protocoloapi.model.repositories;

import br.com.unipac.protocoloapi.model.domain.Protocol;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtocolRepository extends JpaRepository<Protocol, Long> {
    List<Protocol> findAllByName(String name, Pageable pageable);
}
