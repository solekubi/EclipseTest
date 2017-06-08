package org.max.dao;

import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface BaseUuidDao<T> extends BaseDao<T, String> {
}
