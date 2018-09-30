package com.zqf.talkpoint.service;

import com.zqf.talkpoint.model.CategoryBean;
import com.zqf.talkpoint.model.DetailBean;
import com.zqf.talkpoint.model.UserBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author：zqf
 * Time：2018/9/12 11:50
 * desc：服务器接口(梨视频API)
 */
public interface ApiService {

    /**
     * 注册用户
     *
     * @param user 注册的实体类
     * @return UserBean
     */
    @POST("users/regist")
    Call<UserBean> createUser(@Body UserBean user);

    /**
     * 获取标签
     *
     * @return 标签Bean
     */
    @GET("getCategorys.jsp")
    Observable<CategoryBean> getCategorys();

    /**
     * 获取详情
     *
     * @param lastLikeIds 参数对应标签ID
     */
    @GET("home.jsp")
    Observable<DetailBean> getDetail(@Query("lastLikeIds") String lastLikeIds);

}
