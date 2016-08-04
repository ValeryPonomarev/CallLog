package com.easysales.calllog.Synchronization;

import android.content.Context;
import android.util.Log;

import com.easysales.calllog.Entities.Call;
import com.easysales.calllog.Entities.POJO.CallContract;
import com.easysales.calllog.Entities.POJO.ContractConverter;
import com.easysales.calllog.Repository.Call.CallContractCallback;
import com.easysales.calllog.Repository.Call.CallRESTRepository;
import com.easysales.calllog.Repository.Call.CallRepository;
import com.easysales.calllog.Repository.RepositoryFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by drmiller on 14.07.2016.
 */
public final class Synchronizer {

    private static String Log_key = "Synchronizer";
    private static String MetadataPreferencesName = "Metadata";
    private static String LastSynchronizedPreferencesName = "LastSynchronized";

    private static Date GetLastSynchronized(Context context)
    {
        return new Date(context.getSharedPreferences(MetadataPreferencesName, Context.MODE_PRIVATE).getLong(LastSynchronizedPreferencesName, new Date().getTime()));
    }

    private static void SetLastSynchronized(Context context, Date lastSynchronized)
    {
        context.getSharedPreferences(MetadataPreferencesName, Context.MODE_PRIVATE).edit().putLong(LastSynchronizedPreferencesName, lastSynchronized.getTime());
    }

    private static List<Call> GetPendingCalls(Context context)
    {
        final CallRepository dbRepository = RepositoryFactory.GetRepository(context, RepositoryFactory.RepositoryNames.CALL_REPOSITORY);
        return dbRepository.FindBySyncState(false);
    }

    public static void Synchronize(Context context)
    {
        Log.d(Log_key, "Start synchronize");
        for (Call call:
            GetPendingCalls(context)) {
            CallRESTRepository repository = RepositoryFactory.GetRepository(context, RepositoryFactory.RepositoryNames.CALL_REST_REPOSITORY);
            final CallRepository dbRepository = RepositoryFactory.GetRepository(context, RepositoryFactory.RepositoryNames.CALL_REPOSITORY);
            repository.AddAsync(call, new CallContractCallback(){

                @Override
                public void Response(CallContract contract) {
                    Log.d(Log_key, String.format("the call(%1$s) is successfully synchronized", contract.getKey().toString()));
                    Call entity = (Call)ContractConverter.ToEntity(contract);
                    entity.setIsSynchronized(true);
                    dbRepository.Set(entity.getKey(), entity);
                }

                @Override
                public void Failure(Throwable t) {
                    Log.d(Log_key,"the call synchronized is failed: " + t.getMessage());
                }
            });
        }
    }

}
