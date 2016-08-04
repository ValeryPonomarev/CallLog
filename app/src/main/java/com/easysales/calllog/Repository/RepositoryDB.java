package com.easysales.calllog.Repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.easysales.calllog.Entities.Entity;
import com.easysales.calllog.Entities.EntityFactoryBuilder;
import com.easysales.calllog.Entities.IEntityFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drmiller on 05.07.2016.
 */
public abstract class RepositoryDB<T extends Entity> implements IRepository<T> {

    private Context context;
    private SQLiteOpenHelper dbHelper;
    private IEntityFactory<T> entityFactory;
    private SQLiteDatabase database = null;

    public RepositoryDB(Context context)
    {
        this.context = context;
        dbHelper = DBHelper.getInstance(this.context, GetTableName(), null, GetTableVersion(), GetTableCreateQuery(), GetTableUpdateQuery());
        entityFactory = EntityFactoryBuilder.GetEntityFactory(GetTableName());
    }

    protected abstract String GetTableName();
    protected abstract String GetTableCreateQuery();
    protected abstract String GetTableUpdateQuery();
    protected abstract String GetBaseQuery();
    protected abstract String GetBaseWhereClause();
    protected abstract void PersistNewItem(T entity);
    protected abstract void PersistUpdatedItem(T entity);
    protected abstract int GetTableVersion();

    protected String BuildBaseWhereClause(Object key)
    {
        return String.format(GetBaseWhereClause(), key);
    }


    protected String GetKeyFieldName()
    {
        return "_id";
    }

    protected SQLiteDatabase GetDatabase()
    {
        if(database == null) {
            try {
                database = dbHelper.getWritableDatabase();
            } catch (SQLiteException exc) {
                database = dbHelper.getReadableDatabase();
            }
        }
        return database;
    }

    protected List<T> BuildEntitiesFromSql(String sql)
    {
        ArrayList<T> entities = new ArrayList<>();
        Cursor cursor = null;
        try {
            Log.d("Execute sql", sql);
            cursor = GetDatabase().rawQuery(sql, null);

            while (cursor.moveToNext())
            {
                entities.add(BuildEntityFromCursor(cursor));
            }
        } finally {
            if(cursor != null) cursor.close();
        }
        return entities;
    }

    protected T BuildEntitiyFromSql(String sql)
    {
        T entity = null;
        Cursor cursor = null;
        try {
            Log.d("Execute sql", sql);
            cursor = GetDatabase().rawQuery(sql, null);
            while (cursor.moveToNext())
            {
                entity = BuildEntityFromCursor(cursor);
            }
        } finally {
            if(cursor != null) cursor.close();
        }
        return entity;
    }

    protected T BuildEntityFromCursor(Cursor cursor)
    {
        T entity = this.entityFactory.BuildEntity(cursor);
        return entity;
    }

    //region IRepository implements
    public T FindBy(Object key)
    {
        String query = GetBaseQuery() + BuildBaseWhereClause(key);
        return BuildEntitiyFromSql(query);
    }

    public List<T> FindAllList()
    {
        String query = GetBaseQuery();
        return BuildEntitiesFromSql(query);
    }

    @Override
    public Cursor FindAll() {
        String sql = GetBaseQuery();
        Log.d("Execute sql", sql);
        return GetDatabase().rawQuery(sql, null);
    }


    public void Add(T entity)
    {
        PersistNewItem(entity);
    }

    public T Get(Object key)
    {
        return FindBy(key);
    }

    public void Set(Object key, T entity)
    {
        if(Get(key) == null)
        {
            Add(entity);
        }
        else
        {
            PersistUpdatedItem(entity);
        }
    }

    public void Remove(T entity)
    {
        String query = String.format("DELETE FROM %1$s %2$s", GetTableName(), BuildBaseWhereClause(entity.getKey()));
        Log.d("Execute sql", query);
        GetDatabase().execSQL(query);
    }
    //endregion


}
