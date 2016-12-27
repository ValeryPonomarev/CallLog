package com.easysales.calllog;

import android.app.Application;
import android.content.Context;

import com.easysales.calllog.Entities.Call;
import com.easysales.calllog.Repository.Call.CallRepository;
import com.easysales.calllog.Repository.RepositoryFactory;
import com.easysales.calllog.Utils.IOHelper;

/**
 * Created by drmiller on 23.07.2016.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getContext() {
        return MyApplication.context;
    }

    public static void ClearCallHistory()
    {
        IOHelper.RemoveCallsDirectory();
        CallRepository callRepository = RepositoryFactory.GetRepository(getContext(), RepositoryFactory.RepositoryNames.CALL_REPOSITORY);

        for (Call call:callRepository.FindAllList() ) {
            callRepository.Remove(call);
        }
    }
}
