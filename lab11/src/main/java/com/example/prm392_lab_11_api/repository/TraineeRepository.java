package com.example.prm392_lab_11_api.repository;


import com.example.prm392_lab_11_api.api.RetrofitClient;
import com.example.prm392_lab_11_api.api.TraineeService;
import com.example.prm392_lab_11_api.model.Trainee;

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

