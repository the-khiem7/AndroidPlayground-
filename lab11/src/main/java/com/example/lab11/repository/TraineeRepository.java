package com.example.lab11.repository;


import com.example.lab11.api.RetrofitClient;
import com.example.lab11.api.TraineeService;
import com.example.lab11.model.Trainee;

import java.util.List;
import retrofit2.Call;

public class TraineeRepository {
    private final TraineeService service;

    public TraineeRepository() {
        service = RetrofitClient.getTraineeService();
    }

    public Call<List<Trainee>> getAll() {
        return service.getAll();
    }

    public Call<Trainee> create(Trainee t) {
        return service.create(t);
    }

    public Call<Trainee> update(String id, Trainee t) {
        return service.update(id, t);
    }

    public Call<Void> delete(String id) {
        return service.delete(id);
    }
}

