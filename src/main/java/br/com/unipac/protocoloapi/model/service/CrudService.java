package br.com.unipac.protocoloapi.model.service;

import br.com.unipac.protocoloapi.exception.BadResourceException;
import br.com.unipac.protocoloapi.exception.ResourceAlreadyExistsException;
import br.com.unipac.protocoloapi.exception.ResourceNotFoundException;

import java.io.Serializable;
import java.util.List;

public interface CrudService<T, ID extends Serializable> {
    boolean existsById(ID id);
    T findById(ID id) throws ResourceNotFoundException;
    List<T> findAll(int pageNumber, int rowPerPage) throws ResourceNotFoundException;
    T save(T t) throws ResourceAlreadyExistsException, BadResourceException;
    T update(ID id, T t) throws ResourceNotFoundException, BadResourceException;
    void deleteById(ID id) throws ResourceNotFoundException;
    ID count();
}
