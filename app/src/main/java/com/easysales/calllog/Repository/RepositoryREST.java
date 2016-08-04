package com.easysales.calllog.Repository;

import android.database.Cursor;

import com.easysales.calllog.Entities.Entity;

import java.util.List;

/**
 * Created by drmiller on 11.07.2016.
 */
public abstract class RepositoryREST<T extends Entity> implements IRepository<T> {

    //region IRepository implementation
    @Override
    public T FindBy(Object key) {
        return null;
    }

    @Override
    public List<T> FindAllList() {
        return null;
    }

    @Override
    public Cursor FindAll() {
        return null;
    }

    @Override
    public void Add(T entity) {

    }

    @Override
    public T Get(Object key) {
        return null;
    }

    @Override
    public void Set(Object key, T entity) {

    }

    @Override
    public void Remove(T entity) {

    }
    //endregion
}
