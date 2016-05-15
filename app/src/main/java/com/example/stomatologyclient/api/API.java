package com.example.stomatologyclient.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
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

    @GET("api/procedire/{id}")
    Call<Models.Procedure> getProcedures(@Path("id") int id);

    @PUT("api/procedure")
    Call<Response> putProcedure(Models.Procedure procedure);

    @POST("api/procedure")
    Call<Response> postProcedure (Models.Procedure procedure);

    @DELETE("api/procedure/{id}")
    Call<Response> deleteProcedure(@Path("id") int id);

}
