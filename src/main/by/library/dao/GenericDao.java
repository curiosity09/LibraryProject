package main.by.library.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {

    List<T> findAll(int limit, int offset);

    boolean addNew(T t);

    boolean update(T t);

    int getCount();

    boolean delete(T t);

    Optional<T> findById(int id);
}
