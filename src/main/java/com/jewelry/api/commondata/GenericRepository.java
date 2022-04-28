package com.jewelry.api.commondata;

import com.jewelry.api.commondata.model.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T extends AbstractEntity, ID> extends JpaRepository<T, ID> {
    <P> List<P> findAllBy(Class<P> type);
}
