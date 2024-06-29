package com.gridnine.testing;

import com.gridnine.testing.model.FlightBuilder;
import com.gridnine.testing.service.FlightService;

public class Main {
    public static void main(String[] args) {

        System.out.println("Исключены вылеты до текущего момента времени: " + FlightService.excludeFlightUpToCurrentTime(FlightBuilder.createFlights()));

        System.out.println("Исключены полеты с датой прилета раньше даты вылета: " + FlightService.excludeArrivalDateIsEarlierDepartureDate(FlightBuilder.createFlights()));

        System.out.println("Исключены перелеты, где общее время, проведенное на земле превышает 2 часа: " + FlightService.excludeIntervalBetweenSegmentsMoreThanTwoHours(FlightBuilder.createFlights()));


    }
}