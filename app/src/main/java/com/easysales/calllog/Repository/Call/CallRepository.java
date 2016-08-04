package com.easysales.calllog.Repository.Call;

import android.content.ContentValues;
import android.content.Context;

import com.easysales.calllog.Entities.Call;
import com.easysales.calllog.Entities.CallFactory;
import com.easysales.calllog.Repository.RepositoryDB;

import java.util.List;

/**
 * Created by drmiller on 05.07.2016.
 */
public class CallRepository extends RepositoryDB<Call> {
    public CallRepository(Context context) {
        super(context);
    }

    @Override
    protected String GetTableName() {
        return "Call";
    }

    @Override
    protected String GetTableCreateQuery() {
        return String.format("CREATE TABLE `" + GetTableName() + "` (\n" +
                "\t`" + CallFactory.FieldNames.Key + "`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                "\t`" + CallFactory.FieldNames.ContactName + "`\tTEXT,\n" +
                "\t`" + CallFactory.FieldNames.NumberFrom + "`\tTEXT,\n" +
                "\t`" + CallFactory.FieldNames.NumberTo + "`\tTEXT,\n" +
                "\t`" + CallFactory.FieldNames.Type + "`\tINTEGER,\n" +
                "\t`" + CallFactory.FieldNames.BeginDate + "`\tREAL,\n" +
                "\t`" + CallFactory.FieldNames.EndDate + "`\tREAL,\n" +
                "\t`" + CallFactory.FieldNames.RecordPath + "`\tTEXT,\n" +
                "\t`" + CallFactory.FieldNames.IsSynchronized + "`\tINTEGER\n" +
                ");");
    }

    @Override
    protected String GetTableUpdateQuery() {
        return "DROP TABLE IF EXISTS " + GetTableName();
    }

    @Override
    protected String GetBaseQuery() {
        return "SELECT * FROM Call";
    }

    @Override
    protected String GetBaseWhereClause() {
        return " WHERE _id = %1$s";
    }

    @Override
    protected void PersistNewItem(Call entity) {
        ContentValues values = new ContentValues();
        values.put(CallFactory.FieldNames.ContactName, entity.getName());
        values.put(CallFactory.FieldNames.NumberFrom, entity.getNumberFrom());
        values.put(CallFactory.FieldNames.NumberTo, entity.getNumberTo());
        values.put(CallFactory.FieldNames.Type, entity.getType().getValue());
        values.put(CallFactory.FieldNames.BeginDate, entity.getBeginDate().getTime());
        values.put(CallFactory.FieldNames.EndDate, entity.getEndDate().getTime());
        values.put(CallFactory.FieldNames.RecordPath, entity.getRecordPath());

        GetDatabase().insert(GetTableName(), null, values);
    }

    @Override
    protected void PersistUpdatedItem(Call entity) {
        ContentValues values = new ContentValues();
        values.put(CallFactory.FieldNames.ContactName, entity.getName());
        values.put(CallFactory.FieldNames.NumberFrom, entity.getNumberFrom());
        values.put(CallFactory.FieldNames.NumberTo, entity.getNumberTo());
        values.put(CallFactory.FieldNames.Type, entity.getType().getValue());
        values.put(CallFactory.FieldNames.BeginDate, entity.getBeginDate().getTime());
        values.put(CallFactory.FieldNames.EndDate, entity.getEndDate().getTime());
        values.put(CallFactory.FieldNames.RecordPath, entity.getRecordPath());
        values.put(CallFactory.FieldNames.IsSynchronized, entity.getIsSynchronized());

        GetDatabase().update(GetTableName(), values, "_id = ?", new String[]{entity.getKey().toString()});
    }

    @Override
    protected int GetTableVersion() {
        return 6;
    }

    public List<Call> FindBySyncState(boolean syncState)
    {
        String sql = String.format("%1$s WHERE IsSynchronized is NULL or IsSynchronized = %2$d", GetBaseQuery(), (syncState ? 1 : 0));
        return BuildEntitiesFromSql(sql);
    }
}
