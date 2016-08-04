package com.easysales.calllog.Repository.Call;

import com.easysales.calllog.Entities.POJO.CallContract;
import com.easysales.calllog.Utils.IContractCallback;

/**
 * Created by drmiller on 13.07.2016.
 */
public class CallContractCallback implements IContractCallback<CallContract> {

    @Override
    public void Response(CallContract contract) {

    }

    @Override
    public void Failure(Throwable t) {

    }
}
