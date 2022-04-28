package com.jewelry.api.commondata;

import com.jewelry.api.commondata.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface GenericService <T extends AbstractEntity, ID> {
    List<T> findAll();
    Optional<T> findById(ID id);
    T save(T entity);
    T update(T entity);
    void deleteById(ID id);
    <P> List<P> findAllBy(Class<P> type);
}
