package com.example.assignment_ph26746.Interface;

import com.example.assignment_ph26746.Model.Accountmodel;
import com.example.assignment_ph26746.Model.LoverModel;
import com.example.assignment_ph26746.Model.PlansModel;
import com.example.assignment_ph26746.Model.TypeModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //Lover
    @GET("api/listlover")
    Call<List<LoverModel>> getList(
            @Query("id_user") String id_user
    );
    @GET("api/detaillover/{idL}")
    Call<LoverModel> getDetail(
            @Path("idL") String id
    );

    @Multipart
    @POST("api/addlover")
    Call<Void> addObjImage(
            @Part("name") RequestBody name,
            @Part("phone") RequestBody  phone,
            @Part("date") RequestBody  date,
            @Part("typeId") RequestBody  id_type,
            @Part("userId") RequestBody  id_user,
            @Part("des") RequestBody  des,
            @Part MultipartBody.Part imageFile
    );
    @Multipart
    @PUT("api/updatelover/{idL}")
    Call<Void> updateImage(
            @Path("idL") String id,
            @Part("name") RequestBody name,
            @Part("phone") RequestBody  phone,
            @Part("date") RequestBody  date,
            @Part("typeId") RequestBody  id_type,

            @Part("des") RequestBody  des,
            @Part MultipartBody.Part imageFile
    );

    @DELETE("api/deletelover/{idL}")
    Call<String> deleteLover(
            @Path("idL") String id
    );

    ////Type
    @GET("api/listtype")
    Call<List<TypeModel>> getListType(
            @Query("id_user") String id_user
    );

    @GET("api/lovertype")
    Call<List<LoverModel>> getListLoverType( @Query("id_user") String id_user, @Query("id_type") String id
    );
    @FormUrlEncoded
    @POST("api/addtype")
    Call<Void> addType(
            @Field("name") String name,
            @Field("des") String des,
            @Field("id_user") String id_user
    );

    @DELETE("api/deletetype/{idT}")
    Call<String> deleteType(
            @Path("idT") String id
    );


    //Register_login
    @GET("api/persion/{idU}")
    Call<Accountmodel> getListAcc(
            @Path("idU") String id
    );


    @FormUrlEncoded
    @POST("api/register")
    Call<Void> Register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("fullname") String fullname
    );

    @FormUrlEncoded
    @POST("api/login")
    Call<String> Login(
            @Field("username") String username,
            @Field("password") String password
    );
    @Multipart
    @PUT("api/upadteuser/{idU}")
    Call<Void> updateavt(
            @Path("idU") String id,
            @Part MultipartBody.Part imageFile
    );

    @FormUrlEncoded
    @PUT("api/putPass/{idU}")
    Call<String> ChangePass(
            @Path("idU") String idU,
            @Field("passwd") String pass
    );

    @FormUrlEncoded
    @POST("api/checkpass")
    Call<String> CheckPass(
            @Field("username") String username,
            @Field("passwd") String pass
    );

    ///Plans
    @FormUrlEncoded
    @POST("api/addplans")
    Call<Void> addPlans(
            @Field("title") String title,
            @Field("userId") String idU,
            @Field("loverId") String idL,
            @Field("date") String date,
            @Field("location") String location,
            @Field("des") String des
    );

    @GET("api/listplans")
    Call<List<PlansModel>> getPlans(
            @Query("id_user") String id
    );

    @DELETE("api/deleteplans/{idP}")
    Call<String> deletePlans(
        @Path("idP") String id
    );

    @GET("api/detailplans/{idP}")
    Call<PlansModel> getdetailPlans(
            @Path("idP") String id
    );

}
