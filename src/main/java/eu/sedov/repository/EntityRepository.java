package eu.sedov.repository;

import java.util.List;

public interface EntityRepository<T> {
    List<T> getAll();
    T get(int id);

    int delete(int id);
    int update(T entity);
    int insert(T entity);
}
