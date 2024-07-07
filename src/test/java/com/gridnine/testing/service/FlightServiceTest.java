package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlightServiceTest {

    private List<Flight> flights;

    private final FlightService out = new FlightService();

    @BeforeEach
    public void setUp() {
        flights = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        Segment segment1 = new Segment(LocalDateTime.parse("2024-06-27T15:00", formatter), LocalDateTime.parse("2024-06-27T20:00", formatter));
        Segment segment2 = new Segment(LocalDateTime.parse("2024-06-27T20:00", formatter), LocalDateTime.parse("2024-06-27T15:00", formatter));
        Segment segment3 = new Segment(LocalDateTime.parse("2024-06-27T18:00", formatter), LocalDateTime.parse("2024-06-27T20:00", formatter));


        List<Segment> segments1 = new ArrayList<>() {
            {
                add(segment1);
            }
        };

        List<Segment> segments2 = new ArrayList<>() {
            {
                add(segment2);
                add(segment3);
            }
        };

        List<Segment> segments3 = new ArrayList<>() {
            {
                add(segment3);
                add(segment1);
            }
        };

        Flight flight1 = new Flight(segments1);
        Flight flight2 = new Flight(segments2);
        Flight flight3 = new Flight(segments3);

        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);

    }

    @Test
    public void shouldExcludeFlightUpToCurrentTime() {

        List<Flight> actualFlights = out.excludeFlightUpToCurrentTime(flights);
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (Flight flight : actualFlights) {
            for (Segment segment : flight.getSegments()) {
                assertTrue(segment.getDepartureDate().isAfter(currentDateTime));
            }
        }
    }

    @Test
    public void shouldExcludeArrivalDateIsEarlierDepartureDate() {
        List<Flight> actualFlights = out.excludeArrivalDateIsEarlierDepartureDate(flights);
        assertEquals(2, actualFlights.size());
        assertTrue(actualFlights.contains(flights.get(0)));
        assertFalse(actualFlights.contains(flights.get(1)));
        assertTrue(actualFlights.contains(flights.get(2)));
    }

    @Test
    public void shouldExcludeIntervalBetweenSegmentsMoreThanTwoHours() {

        List<Flight> actualFlights = out.excludeIntervalBetweenSegmentsMoreThanTwoHours(flights);
        assertEquals(2, actualFlights.size());
        assertTrue(actualFlights.contains(flights.get(0)));
        assertFalse(actualFlights.contains(flights.get(1)));
        assertTrue(actualFlights.contains(flights.get(2)));
    }
}

