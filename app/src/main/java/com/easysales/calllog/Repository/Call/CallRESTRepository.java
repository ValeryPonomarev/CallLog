package com.easysales.calllog.Repository.Call;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.easysales.calllog.Entities.Call;
import com.easysales.calllog.Entities.POJO.CallContract;
import com.easysales.calllog.Entities.POJO.ContractConverter;
import com.easysales.calllog.Repository.RepositoryREST;
import com.easysales.calllog.Utils.RetrofitServiceGenerator;
import com.easysales.calllog.Utils.SettingsHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by drmiller on 11.07.2016.
 */
public class CallRESTRepository extends RepositoryREST<Call> {

    private Context context;
    private ICallRetrofit callRetrofit;
    private String serverName;
    private final String LOG_TAG = "CallRESTRepository";

    public CallRESTRepository(Context context)
    {
        serverName = SettingsHelper.GetServerName();

        this.context = context;
        callRetrofit = new Retrofit.Builder()
                .baseUrl(serverName)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ICallRetrofit.class);
    }

    public void FindByAsync(Object key, final CallContractCallback callCallback)
    {
        retrofit2.Call<CallContract> call = callRetrofit.getCall(key.toString());
        call.enqueue(new Callback<CallContract>() {
            @Override
            public void onResponse(retrofit2.Call<CallContract> call, Response<CallContract> response) {
                CallContract contract = response.body();
                Call callResult = (Call)ContractConverter.ToEntity(contract);
                callCallback.Response(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<CallContract> call, Throwable t) {
                callCallback.Failure(t);
            }
        });
    }

    public void AddAsync(Call entity, final CallContractCallback callCallback)
    {
        CallContract callContract = (CallContract)ContractConverter.ToContract(entity);
        retrofit2.Call<CallContract> call = callRetrofit.addCall(callContract);
        call.enqueue(new Callback<CallContract>() {
            @Override
            public void onResponse(retrofit2.Call<CallContract> call, Response<CallContract> response) {
                callCallback.Response(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<CallContract> call, Throwable t) {
                callCallback.Failure(t);
            }
        });
        uploadFile(entity);
    }

    private void uploadFile(Call call) {
        IFileUploadServer fileUploadServer = RetrofitServiceGenerator.createService(IFileUploadServer.class);
        File file = new File(call.getRecordPath());
        if(!file.exists()) {
            Log.d(LOG_TAG, String.format("Upload failure: file %1$s not found", call.getRecordPath()));
            return;
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/from-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        String descriptionString = call.getKey().toString();
        RequestBody description = RequestBody.create(MediaType.parse("multipart/from-data"), descriptionString);

        retrofit2.Call<ResponseBody> rfCall = fileUploadServer.upload(description, body);
        rfCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(LOG_TAG, "Upload succes");
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Log.d(LOG_TAG, "Upload failure:" + t.getMessage());
            }
        });
    }

    @Override
    public Call FindBy(Object key) {
        CallContract contract = null;
        try {
            contract = callRetrofit.getCall(key.toString()).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (Call)ContractConverter.ToEntity(contract);
    }

    @Override
    public List<Call> FindAllList() {
        List<CallContract> callContracts = new ArrayList<CallContract>();

        try {
            callContracts = callRetrofit.getAllCalls().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Call> calls = new ArrayList<>();
        for (CallContract callContract: callContracts) {
            calls.add((Call)ContractConverter.ToEntity(callContract));
        }
        return calls;
    }

    @Override
    public Cursor FindAll() {
        throw new UnsupportedOperationException("Операция не поддерживается");
    }

    @Override
    public void Add(Call entity) {
        callRetrofit.addCall((CallContract) ContractConverter.ToContract(entity));
    }

    @Override
    public Call Get(Object key) {
        return FindBy(key);
    }

    @Override
    public void Set(Object key, Call entity) {
        callRetrofit.addCall((CallContract)ContractConverter.ToContract(entity));
    }

    @Override
    public void Remove(Call entity) {
        throw new UnsupportedOperationException("Операция не поддерживается");
    }
}
