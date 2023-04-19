package com.geopokrovskiy.repository;

import java.util.List;

public interface GenericRepository <T, ID>{
    boolean addNew(T value);
    T getById(ID id);
    List<T> getAll();
    boolean update(ID id, T value);
    boolean delete(ID id);

}
