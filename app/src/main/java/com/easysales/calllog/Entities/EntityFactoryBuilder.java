package com.easysales.calllog.Entities;

/**
 * Created by drmiller on 06.07.2016.
 */
public class EntityFactoryBuilder {
    public static IEntityFactory GetEntityFactory(String entityName)
    {
        switch (entityName)
        {
            case "Call":
                return new CallFactory();
            default:
                return null;
        }
    }
}
