package com.develhope.Queries_2.repositories;

import com.develhope.Queries_2.entities.Flight;
import com.develhope.Queries_2.entities.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {
    List<Flight> findByStatus(FlightStatus status);

    @Query("SELECT f FROM Flight f WHERE f.status = :status1 OR f.status = :status2")
    List<Flight> findByStatusIn(FlightStatus status1, FlightStatus status2);
}

