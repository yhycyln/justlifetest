package com.example.justlifetest.repository;


import com.example.justlifetest.model.Cleaner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CleanerRepository extends RepositoryBase<Cleaner> {

    @Query("SELECT c FROM Cleaner AS c " +
            "WHERE (c.deleted = false) " )
    List<Cleaner> findAllCleaner();
    List<Cleaner> findAllByVehicleId(@Param("vehicleId") Long vehicleId);
}
