package br.com.unipac.protocoloapi.model.service;

import br.com.unipac.protocoloapi.model.domain.User;

import java.util.Optional;

public interface UserService {
    void save(User user);
    Optional<User> findByUsername(String username);
}
