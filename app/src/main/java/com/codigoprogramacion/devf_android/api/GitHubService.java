package com.codigoprogramacion.devf_android.api;

import com.codigoprogramacion.devf_android.modelos.GitHubUser;

import retrofit.http.GET;
import retrofit.http.Path;

/*
 * Created by xymind on 12/11/14.
 */
public interface GitHubService {

    @GET("/users/{user}")
    GitHubUser getUser(@Path("user") String user);
}
