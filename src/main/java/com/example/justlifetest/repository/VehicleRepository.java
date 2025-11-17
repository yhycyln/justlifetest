package com.example.justlifetest.repository;

import com.example.justlifetest.model.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends RepositoryBase<Vehicle> {
    @Query("SELECT v FROM Vehicle AS v " +
            "WHERE v.deleted = false " )
    List<Vehicle> findAllVehicles();
}
