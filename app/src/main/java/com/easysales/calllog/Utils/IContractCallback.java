package com.easysales.calllog.Utils;

import com.easysales.calllog.Entities.POJO.ContractBase;

/**
 * Created by drmiller on 13.07.2016.
 */
public interface IContractCallback<T extends ContractBase> {
    void Response(T contract);
    void Failure(Throwable t);
}
