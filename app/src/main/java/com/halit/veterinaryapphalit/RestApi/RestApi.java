package com.halit.veterinaryapphalit.RestApi;

import com.halit.veterinaryapphalit.Models.LoginModel;
import com.halit.veterinaryapphalit.Models.PetModel;
import com.halit.veterinaryapphalit.Models.RegisterPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RestApi {

    @FormUrlEncoded
//    @POST("/VeterinerServices_muratkoc/kayitol.php")
    @POST("/veteriner/kayitol.php")
//    @POST("/veteriner/kayitolDeneme.php")
    Call<RegisterPojo> registerUser(@Field("mailAdres") String kayitol, @Field("kadi") String kadi, @Field("pass") String pass );


    @FormUrlEncoded
    @POST("/veteriner/girisyap.php")
    Call<LoginModel> loginUser(@Field("mailadres") String mailAdres, @Field("sifre") String pass );


    @FormUrlEncoded
    @POST("/veteriner/petlerim.php")
    Call<List<PetModel>> getPets(@Field("musid") String mus_id );



}
