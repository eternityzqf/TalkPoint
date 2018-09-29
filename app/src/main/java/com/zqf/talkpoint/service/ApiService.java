package com.zqf.talkpoint.service;

import com.zqf.talkpoint.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Author：zqf
 * Time：2018/9/12 11:50
 * desc：服务器接口
 */
public interface ApiService {

    /**
     * 注册用户
     *
     * @param user 注册的实体类
     * @return User
     */
    @POST("users/regist")
    Call<User> createUser(@Body User user);


}
