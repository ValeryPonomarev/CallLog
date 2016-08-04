package com.easysales.calllog.Entities.POJO;

import com.easysales.calllog.Entities.Call;
import com.easysales.calllog.Entities.CallType;
import com.easysales.calllog.Entities.IEntity;

import java.util.Date;

/**
 * Created by drmiller on 11.07.2016.
 */
public final class ContractConverter {
    public static Object ToContract(IEntity entity)
    {
        String className = entity.getClass().getSimpleName();
        switch (className)
        {
            case "Call":
                return ToCallContract((Call)entity);
        }
        return null;
    }

    public static IEntity ToEntity(ContractBase contract){
        String className = contract.getClass().getSimpleName().replace("Contract", "");
        switch (className)
        {
            case "Call":
                return ToCall((CallContract) contract);
        }

        return null;
    }

    public static CallContract ToCallContract(Call call)
    {
        CallContract callContract = null;
        if(call != null){
            callContract = new CallContract();
            callContract.setKey(call.getKey());
            callContract.setName(call.getName());
            callContract.setNumberFrom(call.getNumberFrom());
            callContract.setNumberTo(call.getNumberTo());
            callContract.setType(call.getType().getValue());
            callContract.setBeginDate(call.getBeginDate().getTime());
            callContract.setEndDate(call.getEndDate().getTime());
            callContract.setRecordPath(call.getRecordPath());
            callContract.setIsSynchronized((call.getIsSynchronized() ? 1 : 0 ));
        }

        return callContract;
    }

    public static Call ToCall(CallContract callContract)
    {
        return new Call(
                callContract.getKey(),
                callContract.getName(),
                callContract.getNumberFrom(),
                callContract.getNumberTo(),
                CallType.valueOf(callContract.getType()),
                new Date(callContract.getBeginDate()),
                new Date(callContract.getEndDate()),
                callContract.getRecordPath(),
                (callContract.getIsSynchronized() != 0));
    }
}
