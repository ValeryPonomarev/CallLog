package com.easysales.calllog.Entities;

import android.os.Parcelable;

/**
 * Created by drmiller on 05.07.2016.
 */
public abstract class Entity implements Parcelable, IEntity {
    public Entity(Object key)
    {
        this.key = key;
    }

    private Object key;

    public Object getKey() {
        return key;
    }
}
