package com.aleksandr.aleksandrov.project.test.android.movies.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aleksandr.aleksandrov.project.test.android.movies.ApiEvent;
import com.aleksandr.aleksandrov.project.test.android.movies.R;
import com.aleksandr.aleksandrov.project.test.android.movies.models.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aleksandr.aleksandrov.project.test.android.movies.service.ServicesConstants.GET_MOVIES;

/**
 * Created by aleksandr on 9/29/17.
 */

public class DataLoaderService extends IntentService {

    public static final String TAG = "DataLoaderService";

    private static OkHttpClient httpClient = null;

    public DataLoaderService() {
        super(TAG);
    }

    public DataLoaderService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        switch (intent.getAction()) {
            case GET_MOVIES:
                getMovies();
                break;
            default:
                break;
        }
    }

    private void getMovies() {
        Log.d(TAG, "getMovies request");
        API api = getApi();
        Call<List<Movie>> call = api.getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, retrofit2.Response<List<Movie>> response) {
                Log.d(TAG, "response code: " + response.code());
                if (HttpURLConnection.HTTP_OK == response.code()) {
                    DBHelper.write(response.body());
                    DBHelper.setGenres(getApplicationContext(), response.body());
                    DBHelper.setYears(getApplicationContext(), response.body());
                    sendEvent(GET_MOVIES, true, null);
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.d(TAG, "response code: " + t.getMessage());
            }
        });
    }

    void sendEvent(String eventType, boolean success, String errorMessage) {
        Log.d(
                TAG,
                "sendEvent event type " + eventType +
                        " success " + success +
                        " error " + errorMessage
        );
        ApiEvent apiEvent = new ApiEvent(eventType, success, errorMessage);
        EventBus.getDefault().postSticky(apiEvent);
    }

    API getApi() {

        OkHttpClient client = getHttpClient();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();


        int connectionsCount = client.connectionPool().connectionCount();

        Log.d(TAG, "connections count " + connectionsCount);

        return retrofit.create(API.class);
    }

    private OkHttpClient getHttpClient() {
        if (httpClient != null)
            return httpClient;
        return getClient();
    }

    private OkHttpClient getClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Log.d(TAG, "request tag " + chain.request().tag());
                Request request;
                request = original;

                okhttp3.Response response = chain.proceed(request);

                return response;
            }
        });


        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        httpClient = httpClientBuilder.build();
        return httpClient;
    }
}