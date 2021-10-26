package br.com.unipac.protocoloapi.model.repositories;

import br.com.unipac.protocoloapi.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   // select * from tb_user where username =: ?
    //@Query("select * from User u where u.username =: ?")
    //@Query(value = "select * from tb_user u where u.username =: ?", nativeQuery = true)

    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
