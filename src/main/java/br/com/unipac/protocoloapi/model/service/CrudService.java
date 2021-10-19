package br.com.unipac.protocoloapi.model.service;

import java.io.Serializable;
import java.util.List;

public interface CrudService<T, ID extends Serializable> {
    boolean existsById(ID id);
    T findById(ID id);
    List<T> findAll(int pageNumber, int rowPerPage);
    T save(T t);
    T update(ID id, T t);
    void deleteById(ID id);
    ID count();
}
