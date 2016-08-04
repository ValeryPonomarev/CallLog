package com.easysales.calllog.Repository.Call;

import com.easysales.calllog.Entities.POJO.CallContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by drmiller on 11.07.2016.
 */
public interface ICallRetrofit {

    @GET("calls/{idcall}")
    Call<CallContract> getCall(
            @Path("idcall") String id);

    @GET("calls")
    Call<List<CallContract>> getAllCalls();

    @POST("calls")
    Call<CallContract> addCall(@Body CallContract callContract);

}
