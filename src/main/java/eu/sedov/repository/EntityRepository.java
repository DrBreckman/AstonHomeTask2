package eu.sedov.repository;

import java.util.List;

public interface EntityRepository<T> {
    List<T> getAll();
    T get(Integer id);

    int delete(Integer id);
    int update(T entity);
    int insert(T entity);
}
