package com.develhope.Queries_2.controllers;


import com.develhope.Queries_2.entities.Flight;
import com.develhope.Queries_2.entities.FlightStatus;
import com.develhope.Queries_2.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    @PostMapping("/provision")
    public void provisionFlights(@RequestParam(required = false, defaultValue = "100") int n) {
        List<Flight> flights = IntStream.range(0, n)
                .mapToObj(i -> new Flight(
                        generateRandomString(),
                        generateRandomString(),
                        generateRandomString(),
                        generateRandomStatus()
                )).collect(Collectors.toList());

        flightRepository.saveAll(flights);
    }

    @GetMapping
    public List<Flight> getAllFlights(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fromAirport").ascending());
        return flightRepository.findAll(pageable).getContent();
    }

    @GetMapping("/ontime")
    public List<Flight> getOnTimeFlights() {
        return flightRepository.findByStatus(FlightStatus.ONTIME);
    }

    @GetMapping("/status")
    public List<Flight> getFlightsByStatus(@RequestParam FlightStatus p1, @RequestParam FlightStatus p2) {
        return flightRepository.findByStatusIn(p1, p2);
    }

    private String generateRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private FlightStatus generateRandomStatus() {
        FlightStatus[] statuses = FlightStatus.values();
        return statuses[new Random().nextInt(statuses.length)];
    }
}

