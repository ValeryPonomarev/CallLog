package com.easysales.calllog.Repository.Call;

import android.database.Cursor;

import com.easysales.calllog.Entities.Call;
import com.easysales.calllog.Repository.IRepository;

import java.util.List;

/**
 * Created by drmiller on 29.12.2016.
 */
public interface ICallRepository extends IRepository<Call> {
    Cursor FindAllOrderedByDateDesc();
}
