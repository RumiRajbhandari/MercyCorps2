package com.example.user.mercycorpsfinal.Api;

import com.example.user.mercycorpsfinal.model.ListItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by user on 9/3/2017.
 */

public interface MercyCorpInterface {
    @GET("sheet1.json")
    Call<List<ListItem>> getList1();
    @GET("sheet2.json")
    Call<List<ListItem>> getList2();

    @GET("sheet3.json")
    Call<List<ListItem>> getList3();

    @GET("sheet4.json")
    Call<List<ListItem>> getList4();

    @GET("sheet5.json")
    Call<List<ListItem>> getList5();
    @GET("sheet6.json")
    Call<List<ListItem>> getList6();
    @GET("sheet7.json")
    Call<List<ListItem>> getList7();
    @GET("sheet8.json")
    Call<List<ListItem>> getList8();

    @GET("sheet9.json")
    Call<List<ListItem>> getList9();

    @GET("sheet10.json")
    Call<List<ListItem>> getList10();


    @GET("sheet11.json")
    Call<List<ListItem>> getList11();

    @GET("sheet12.json")
    Call<List<ListItem>> getList12();
    @GET("sheet13.json")
    Call<List<ListItem>> getList13();
    @GET("sheet14.json")
    Call<List<ListItem>> getList14();

    @GET("sheet15.json")
    Call<List<ListItem>> getList15();
    @GET("sheet16.json")
    Call<List<ListItem>> getList16();
    @GET("sheet17.json")
    Call<List<ListItem>> getList17();
    @GET("sheet18.json")
    Call<List<ListItem>> getList18();
    @GET("sheet19.json")
    Call<List<ListItem>> getList19();

    @GET("sheet20.json")
    Call<List<ListItem>> getList20();
    @GET("sheet21.json")
    Call<List<ListItem>> getList21();
    @GET("sheet26.json")
    Call<List<ListItem>> getList26();
    @GET("sheet27.json")
    Call<List<ListItem>> getList27();
    @GET("sheet25.json")
    Call<List<ListItem>> getList25();



}
