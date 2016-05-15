package com.example.stomatologyclient.api;

import okhttp3.OkHttpClient;
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
}
