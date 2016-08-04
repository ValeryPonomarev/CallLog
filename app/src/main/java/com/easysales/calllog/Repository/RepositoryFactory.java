package com.easysales.calllog.Repository;

import android.content.Context;

import com.easysales.calllog.Repository.Call.CallRESTRepository;
import com.easysales.calllog.Repository.Call.CallRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by drmiller on 05.07.2016.
 */
public final class RepositoryFactory {

    public static class RepositoryNames
    {
        public static final String CALL_REPOSITORY = "CallRepository";
        public static final String CALL_REST_REPOSITORY = "CallRESTRepository";
    }

    private static Map<String, Object> repositories = new HashMap<String, Object>();

    public static <TRepository extends IRepository> TRepository GetRepository(Context context, String name) {
        Object repository = null;
        if (repositories.containsKey(name))
        {
            repository = repositories.get(name);
        }
        else
        {
            switch (name)
            {
                case RepositoryNames.CALL_REPOSITORY:
                    repository = new CallRepository(context);
                    break;
                case RepositoryNames.CALL_REST_REPOSITORY:
                    repository = new CallRESTRepository(context);
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Repository %1$s not found!", name));
            }
        }

        return (TRepository)repository;
    }
}
