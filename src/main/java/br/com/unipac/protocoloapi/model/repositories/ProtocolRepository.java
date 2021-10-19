package br.com.unipac.protocoloapi.model.repositories;

import br.com.unipac.protocoloapi.model.domain.Protocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtocolRepository extends JpaRepository<Protocol, Long> {
}
