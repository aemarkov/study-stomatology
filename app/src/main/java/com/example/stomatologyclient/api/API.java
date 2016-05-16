package com.example.stomatologyclient.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Garrus on 15.05.2016.
 */
public interface API
{
    // Категории

    @GET("api/category")
    Call<List<Models.Category>> getCategories();

    @GET("api/category/{id}")
    Call<Models.Category> getCategory(@Path("id") int id);

    @PUT("api/category")
    Call<ResponseBody> putCategory(Models.Category category);

    @POST("api/category")
    Call<ResponseBody> postCategory(Models.Category category);

    @DELETE("api/category/{id}")
    Call<ResponseBody> deleteCategory(@Path("id") int id);


    // Подкатегории

    @GET("api/subcategory/{id}")
    Call<Models.Subcategory> getSubcategory(@Path("id") int id);

    @PUT("api/subcategory")
    Call<ResponseBody> putSubcategory(Models.Subcategory subcategory);

    @POST("api/subcategory")
    Call<ResponseBody> postSubcategory(Models.Subcategory subcategory);

    @DELETE("api/subcategory/{id}")
    Call<ResponseBody> deleteSubcategory(@Path("id") int id);

    //Процедура

    @GET("api/procedure")
    Call<List<Models.Procedure>> getProcedures();

    @GET("api/Procedure/{id}")
    Call<Models.Procedure> getProcedure(@Path("id") int id);

    @PUT("api/procedure")
    Call<ResponseBody> putProcedure(Models.Procedure procedure);

    @POST("api/procedure")
    Call<ResponseBody> postProcedure (Models.Procedure procedure);

    @DELETE("api/procedure/{id}")
    Call<ResponseBody> deleteProcedure(@Path("id") int id);


    //Врачи
    @GET("api/doctor")
    Call<List<Models.Doctor>> getDoctors();

    @GET("api/doctor/{id}")
    Call<Models.Doctor> getDoctor(@Path("id") int id);

    @POST("api/doctor")
    Call<ResponseBody> postDoctor(Models.Doctor doctor);

    @DELETE("api/doctor/{id}")
    Call<ResponseBody> deleteDoctor(@Path("id") int id);

    //Получение токена
    @FormUrlEncoded
    @POST("Token")
    Call<ResponseBody> getToken(@Field("grant_type") String grant_type,@Field("username") String username, @Field("password") String password );


    //Регистрация
    @POST("api/Account/RegisterPatient")
    Call<ResponseBody> registerPatient(@Body Models.PatientRegistrationViewModel model);

    @POST("api/Account/RegisterDoctor")
    Call<ResponseBody> registerDoctor(Models.DoctorRegistrationViewModel model);

    @POST("api/Account/RegisterDentalTechnican")
    Call<ResponseBody> registerTechnican(Models.TechnicanRegistrationViewModel model);
}
