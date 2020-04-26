package com.example.stomatologyclient.api;

import android.media.session.MediaSession;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Создает retrofit
 */
public class RetrofitFactory
{
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int TIMEOUT = 60;
    public final static String base_url = "http://stomatologyapi.azurewebsites.net/";

    private static  OkHttpClient CLIENT;

    static {
        /*CLIENT.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);*/

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        CLIENT = httpClient.build();
    }


    /**
     * Возвращает ретрофит
     * @return
     */
    public static Retrofit GetRetrofit()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(CLIENT)
                .build();

        return retrofit;
    }

    /**
     * Возвращает ретрофит c включенным в заголовки токеном
     * @return
     */
    public static Retrofit GetRetrofit(final String token)
    {
        if(token==null)return  GetRetrofit();

        //Лог
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //Токен
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", "bearer "+token)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        httpClient.addInterceptor(logging);

        OkHttpClient client = httpClient.build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
