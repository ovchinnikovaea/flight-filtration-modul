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
            boolean hasLongInterval = false;

            for (int i = 0; i < segments.size() - 1; i++) {
                Duration duration = Duration.between(segments.get(i).getArrivalDate(), segments.get(i + 1).getDepartureDate());
                if (duration.toHours() > 2) {
                    hasLongInterval = true;
                    break;
                }
            }
            if (!hasLongInterval) {
                newFlights.add(flight);
            }
        }
        return newFlights;
    }
}
