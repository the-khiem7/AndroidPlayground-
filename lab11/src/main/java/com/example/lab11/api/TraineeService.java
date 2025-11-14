package com.example.lab11.api;

import com.example.lab11.model.Trainee;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TraineeService {
    @GET("trainees")
    Call<List<Trainee>> getAll();

    @POST("trainees")
    Call<Trainee> create(@Body Trainee trainee);

    @PUT("trainees/{id}")
    Call<Trainee> update(@Path("id") String id, @Body Trainee trainee);

    @DELETE("trainees/{id}")
    Call<Void> delete(@Path("id") String id);
}

