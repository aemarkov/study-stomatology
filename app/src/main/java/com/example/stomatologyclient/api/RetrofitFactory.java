package com.example.stomatologyclient.api;

import android.media.session.MediaSession;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

    private static final OkHttpClient CLIENT = new OkHttpClient();

    static {
        /*CLIENT.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);*/
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
        //Сраная магия, чтобы добавить токен
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final HttpUrl modified_url = chain.request()
                        .url().newBuilder()
                        .addQueryParameter("Authorization", "bearer "+token)
                        .build();

                final Request request = chain.request().newBuilder().url(modified_url).build();
                final Response response = chain.proceed(request);
                return  response;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
