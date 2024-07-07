package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.FlightBuilder;
import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightService {

    List<Flight> flight = FlightBuilder.createFlights();

    public static List<Flight> excludeFlightUpToCurrentTime(List<Flight> flights) {
        List<Flight> newFlights = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (Flight flight : flights) {
            List<Segment> segments = flight.getSegments();
            boolean hasPastDeparture = false;
            for (Segment segment : segments) {
                if (segment.getDepartureDate().isBefore(currentDateTime)) {
                    hasPastDeparture = true;
                    break;
                }
            }
            if (!hasPastDeparture) {
                newFlights.add(flight);
            }
        }
        return newFlights;
    }

    public static List<Flight> excludeArrivalDateIsEarlierDepartureDate(List<Flight> flights) {
        List<Flight> newFlights = new ArrayList<>();

        for (Flight flight : flights) {
            List<Segment> segments = flight.getSegments();
            boolean hasInvalidSegment = false;
            for (Segment segment : segments) {
                if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                    hasInvalidSegment = true;
                    break;
                }
            }
            if (!hasInvalidSegment) {
                newFlights.add(flight);
            }
        }
        return newFlights;
    }

    public static List<Flight> excludeIntervalBetweenSegmentsMoreThanTwoHours(List<Flight> flights) {
        List<Flight> newFlights = new ArrayList<>();

        for (Flight flight : flights) {
            List<Segment> segments = flight.getSegments();
            Duration totalInterval = Duration.ZERO;

            for (int i = 0; i < segments.size() - 1; i++) {
                Duration interval = Duration.between(segments.get(i).getArrivalDate(), segments.get(i + 1).getDepartureDate());
                totalInterval = totalInterval.plus(interval);
            }
            if (totalInterval.toHours() <= 2) {
                newFlights.add(flight);
            }
        }
        return newFlights;
    }
}
