package com.easysales.calllog.Repository.RetrofitExample;

/**
 * Created by drmiller on 12.07.2016.
 */
public class Contributor {

    String login;
    String html_url;

    int contributions;

    @Override
    public String toString() {
        return login + " (" + contributions + ")";
    }
}
