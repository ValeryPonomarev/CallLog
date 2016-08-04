package com.easysales.calllog.Data;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

/**
 * Created by drmiller on 23.07.2016.
 */
public class LongEditTextPreference extends EditTextPreference {
    public LongEditTextPreference(Context context) {
        super(context);
    }

    public LongEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LongEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean persistString(String value) {
        return persistLong(Long.valueOf(value));
    }

    @Override
    protected String getPersistedString(String defaultReturnValue) {
        return String.valueOf(getPersistedLong(-1));
    }
}
