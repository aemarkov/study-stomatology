package com.example.stomatologyclient.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
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
    Call<Response> putCategory(Models.Category category);

    @POST("api/category")
    Call<Response> postCategory(Models.Category category);

    @DELETE("api/category/{id}")
    Call<Response> deleteCategory(@Path("id") int id);


    // Подкатегории

    @GET("api/subcategory/{id}")
    Call<Models.Subcategory> getSubcategory(@Path("id") int id);

    @PUT("api/subcategory")
    Call<Response> putSubcategory(Models.Subcategory subcategory);

    @POST("api/subcategory")
    Call<Response> postSubcategory(Models.Subcategory subcategory);

    @DELETE("api/subcategory/{id}")
    Call<Response> deleteSubcategory(@Path("id") int id);

    //Процедура

    @GET("api/procedure")
    Call<List<Models.Procedure>> getProcedures();

    @GET("api/Procedure/{id}")
    Call<Models.Procedure> getProcedure(@Path("id") int id);

    @PUT("api/procedure")
    Call<Response> putProcedure(Models.Procedure procedure);

    @POST("api/procedure")
    Call<Response> postProcedure (Models.Procedure procedure);

    @DELETE("api/procedure/{id}")
    Call<Response> deleteProcedure(@Path("id") int id);


    //Врачи
    @GET("api/doctor")
    Call<List<Models.Doctor>> getDoctors();

    @GET("api/doctor/{id}")
    Call<Models.Doctor> getDoctor(@Path("id") int id);

    @POST("api/doctor")
    Call<Response> postDoctor(Models.Doctor doctor);

    @DELETE("api/doctor/{id}")
    Call<Response> deleteDoctor(@Path("id") int id);

    //Получение токена
    @FormUrlEncoded
    @POST("Token")
    Call<Response> getToken(@Field("grant_type") String grant_type,@Field("username") String username, @Field("password") String password );


    //Регистрация
    @POST("api/Account/RegisterPatient")
    Call<Response> registerPatient(Models.PatientRegistrationViewModel model);

    @POST("api/Account/RegisterDoctor")
    Call<Response> registerDoctor(Models.DoctorRegistrationViewModel model);

    @POST("api/Account/RegisterDentalTechnican")
    Call<Response> registerTechnican(Models.TechnicanRegistrationViewModel model);
}
