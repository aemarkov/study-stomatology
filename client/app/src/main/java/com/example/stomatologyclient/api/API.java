package com.example.stomatologyclient.api;

import android.graphics.AvoidXfermode;
import android.graphics.Paint;

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
import retrofit2.http.Query;

/**
 * Created by Garrus on 15.05.2016.
 */
public interface API
{
    //Инфа о клинике
    @GET("api/clinicinfo")
    Call<Models.ClinicInfo> getClinicInfo();

    // Категории

    @GET("api/category")
    Call<List<Models.Category>> getCategories();

    @GET("api/category/{id}")
    Call<Models.Category> getCategory(@Path("id") int id);

    //TODO: PutResponse
    @PUT("api/category")
    Call<ResponseBody> putCategory(Models.Category category);

    @POST("api/category")
    Call<ResponseBody> postCategory(Models.Category category);

    @DELETE("api/category/{id}")
    Call<ResponseBody> deleteCategory(@Path("id") int id);


    // Подкатегории

    @GET("api/subcategory/{id}")
    Call<Models.Subcategory> getSubcategory(@Path("id") int id);

    //TODO: PutResponse
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

    //TODO: PutResponse
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

    //Пациенты
    @GET("api/patient")
    Call<List<Models.Patient>> getPatients();

    @GET("api/patient/{id}")
    Call<Models.Patient> getPatient(@Path("id") int id);

    @PUT("api/patient")
    Call<ResponseBody> putPatient(@Body Models.Patient patient);

    @POST("api/patient")
    Call<ResponseBody> postPatient(@Body Models.Patient patient);

    @DELETE("api/patient/{id}")
    Call<ResponseBody> deletePatient(@Path("id") int id);

    @GET("api/patient/GetMe")
    Call<Models.Patient> getCurrentPatient();

    //Посещения
    @GET("api/PatientVisit/{id}")
    Call<Models.Visit> getVisit(@Path("id") int id);

    @PUT("api/PatientVisit")
    //TODO: PutResponse
    Call<ResponseBody> putVisit(@Body Models.Visit visit);

    @POST("api/PatientVisit")
    Call<ResponseBody> postVisit(@Body Models.Visit visit);

    @DELETE("api/PatientVisit/{id}")
    Call<ResponseBody> deleteVisit(@Path("id") int id);

    @POST("api/PatientVisit/AddProcedure")
    Call<ResponseBody> addVisitProcedure(@Query("visitId") int visitId, @Query("procedureId") int procedureId);

    @DELETE("api/PatientVisit/RemoveProcedure")
    Call<ResponseBody> removeVisitProcedure(@Query("visitId") int visitId, @Query("procedureId") int procedureId);

    @GET("api/PatientVisit/Close/{visitId}")
    Call<ResponseBody> closeVisit(@Path("visitId")int visitId);

    //Наряд-заказы
    @GET("/api/order")
    Call<List<Models.Order>> getOrders();

    @GET("/api/order/{id}")
    Call<Models.Order> getOrder(@Path("id") int id);

    @PUT("/api/order")
    Call<Models.PutResponse> putOrder(@Body Models.Order order);

    @POST("/api/order")
    Call<ResponseBody> postOrder(@Body Models.Order order);

    @DELETE("/api/order/{id}")
    Call<ResponseBody> deleteOrder(@Path("id") int id);

    @POST("/api/order/AddTooth")
    Call<ResponseBody> addToith(@Query("orderId") int orderId, @Query("toothNo") int toothNo, @Query("procedureId") int procedureId);

    @DELETE("/api/order/RemoveTooth/{id}")
    Call<ResponseBody> removeTooth(@Path("id") int id);

    @GET("/api/order/Finish/{id}")
    Call<ResponseBody> finishOrder(@Path("id") int id);

    @GET("/api/order/Close/{id}")
    Call<ResponseBody> closeOrder(@Path("id") int id);


    //Процедуры техника (изделия?)
    @GET("/api/TechnicanProcedure")
    Call<List<Models.TechnicanProcedure>> getTechnicanProcedures();

    @PUT("/api/TechnicanProcedure")
    Call<ResponseBody> putTechnicanProcedure(@Body Models.TechnicanProcedure procedure);

    @POST("/api/TechnicanProcedure")
    Call<ResponseBody> poistTechnicanProcedure(@Body Models.TechnicanProcedure procedure);

    @DELETE("/api/TechnicanProcedure/{id}")
    Call<ResponseBody> deleteTechnicanProcedure(@Path("id") int id);

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
