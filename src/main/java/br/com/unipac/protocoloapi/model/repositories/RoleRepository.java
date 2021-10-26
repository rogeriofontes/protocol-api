package br.com.unipac.protocoloapi.model.repositories;

import br.com.unipac.protocoloapi.model.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
