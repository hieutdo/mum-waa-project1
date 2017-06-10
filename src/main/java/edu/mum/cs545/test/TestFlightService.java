package edu.mum.cs545.test;

import cs545.airline.model.Airline;
import cs545.airline.model.Airplane;
import cs545.airline.model.Airport;
import cs545.airline.model.Flight;
import cs545.airline.service.AirlineService;
import cs545.airline.service.AirplaneService;
import cs545.airline.service.AirportService;
import cs545.airline.service.FlightService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Named
@RequestScoped
public class TestFlightService {
    // Due to difficulties instantiating the context in JUnit I included this
    // pseudotest class

    StringBuilder messages = new StringBuilder();

    @Inject
    AirportService airportService;
    @Inject
    AirplaneService airplaneService;
    @Inject
    AirlineService airlineService;
    @Inject
    FlightService flightService;

    Flight testFlight1;

    List<Airport> savedAirport = new ArrayList<>();
    List<Airplane> savedAirplane = new ArrayList<>();
    List<Airline> savedAirline = new ArrayList<>();

    private void setUp() {
        Airport airport1 = new Airport("test1", "here", "state1", "USA");
        Airport airport2 = new Airport("test2", "there", "state2", "USA");

        Airline airline1 = new Airline("testair1");

        Airplane airplane1 = new Airplane("test56789", "A380", 519);

        testFlight1 = new Flight("test3", "06/25/2017", "7:10 am", "06/25/2017", "9:00 pm", airline1, airport2,
                airport1, airplane1);

        savedAirline.add(airline1);
        savedAirport.add(airport1);
        savedAirport.add(airport2);
        savedAirplane.add(airplane1);
        airlineService.create(airline1);
    }

    private void tearDown() {
        for (Airport saved : savedAirport) {
            airportService.delete(saved);
        }
        for (Airplane saved : savedAirplane) {
            airplaneService.delete(saved);
        }
        for (Airline saved : savedAirline) {
            airlineService.delete(saved);
        }
    }

    private void findTest() throws Exception {
        Flight retrieved = flightService.find(testFlight1);

        if (!testFlight1.getFlightnr().equals(retrieved.getFlightnr())) {
            throw new Exception("Airport name does not match");
        }

    }

    private void findAllTest() throws Exception {

        List<Flight> result = flightService.findAll();

        if (result.isEmpty()) {
            throw new Exception("No flights retrieved");
        }

        if (!result.contains(testFlight1)) {
            throw new Exception("Not all the flights were retrieved");
        }
    }

    private void findbyAirlineTest() throws Exception {

        List<Flight> result = flightService.findByAirline(testFlight1.getAirline());

        if (result.isEmpty()) {
            throw new Exception("No flights retrieved");
        }

        if (!result.contains(testFlight1)) {
            throw new Exception("Not all the flights were retrieved");
        }
    }

    private void findbyAirplaneTest() throws Exception {

        List<Flight> result = flightService.findByAirplane(testFlight1.getAirplane());

        if (result.isEmpty()) {
            throw new Exception("No flights retrieved");
        }

        if (!result.contains(testFlight1)) {
            throw new Exception("Not all the flights were retrieved");
        }
    }

    private void findbyArrivalTest() throws Exception {

        Date arraivalDate;

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        arraivalDate = df.parse("06/25/2017 21:00:00");

        List<Flight> result = flightService.findByArrival(arraivalDate);

        if (result.isEmpty()) {
            throw new Exception("No flights retrieved");
        }

        if (!result.contains(testFlight1)) {
            throw new Exception("Not all the flights were retrieved");
        }
    }

    private void findbyArrivalBetweenTest() throws Exception {

        Date dateFrom, dateTo;

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);

        dateFrom = df.parse("06/25/2017");
        dateTo = df.parse("06/26/2017");

        List<Flight> result = flightService.findByArrivalBetween(dateFrom, dateTo);

        if (result.isEmpty()) {
            throw new Exception("No flights retrieved");
        }

        if (!result.contains(testFlight1)) {
            throw new Exception("Not all the flights were retrieved");
        }
    }

    private void findbyDepartureTest() throws Exception {

        Date departureDate;

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        departureDate = df.parse("06/25/2017 7:10 am");

        List<Flight> result = flightService.findByDeparture(departureDate);

        if (result.isEmpty()) {
            throw new Exception("No flights retrieved");
        }

        if (!result.contains(testFlight1)) {
            throw new Exception("Not all the flights were retrieved");
        }
    }

    private void findbyDepartureBetweenTest() throws Exception {

        Date dateFrom, dateTo;

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);

        dateFrom = df.parse("06/25/2017");
        dateTo = df.parse("06/26/2017");

        List<Flight> result = flightService.findByDepartureBetween(dateFrom, dateTo);

        if (result.isEmpty()) {
            throw new Exception("No flights retrieved");
        }

        if (!result.contains(testFlight1)) {
            throw new Exception("Not all the flights were retrieved");
        }
    }

    private void findByDestinationTest() throws Exception {

        List<Flight> result = flightService.findByDestination(testFlight1.getDestination());

        if (result.isEmpty()) {
            throw new Exception("No flights retrieved");
        }

        if (!result.contains(testFlight1)) {
            throw new Exception("Not all the flights were retrieved");
        }
    }

    private void findByNumberTest() throws Exception {

        List<Flight> result = flightService.findByNumber(testFlight1.getFlightnr());

        if (result.isEmpty()) {
            throw new Exception("No flights retrieved");
        }

        if (!result.contains(testFlight1)) {
            throw new Exception("Not all the flights were retrieved");
        }
    }

    private void findByOriginTest() throws Exception {

        List<Flight> result = flightService.findByOrigin(testFlight1.getOrigin());

        if (result.isEmpty()) {
            throw new Exception("No flights retrieved");
        }

        if (!result.contains(testFlight1)) {
            throw new Exception("Not all the flights were retrieved");
        }
    }

    private void updateTest() throws Exception {

        Flight saved = flightService.find(testFlight1);

        // modify the values
        saved.setArrivalTime("10:00 pm");

        // Airport merged =
        flightService.update(saved);

        Flight modified = flightService.find(testFlight1);

        if (modified == null) {
            throw new Exception("No flights retrieved");
        }

        if (!"10:00 pm".equals(modified.getArrivalTime())) {
            throw new Exception("Airport didn't change the code");
        }
    }

    public void runAll() {
        boolean complete = false;
        String testName = "";
        for (int test = 1; !complete; test++) {
            boolean failed = false;
            try {
                setUp();

                switch (test) {
                    case 1:
                        testName = "TestFlighttService.findTest";
                        findTest();
                        break;
                    case 2:
                        testName = "TestFlighttService.findAllTest";
                        findAllTest();
                        break;
                    case 3:
                        testName = "TestFlighttService.findbyAirlineTest";
                        findbyAirlineTest();
                        break;
                    case 4:
                        testName = "TestFlighttService.findbyAirplaneTest";
                        findbyAirplaneTest();
                        break;
                    case 5:
                        testName = "TestFlighttService.findbyArrivalTest";
                        findbyArrivalTest();
                        break;
                    case 6:
                        testName = "TestFlighttService.findbyArrivalBetweenTest";
                        findbyArrivalBetweenTest();
                        break;
                    case 7:
                        testName = "TestFlighttService.findbyDepartureTest";
                        findbyDepartureTest();
                        break;
                    case 8:
                        testName = "TestFlighttService.findbyDepartureBetweenTest";
                        findbyDepartureBetweenTest();
                        break;
                    case 9:
                        testName = "TestFlighttService.findByDestinationTest";
                        findByDestinationTest();
                        break;
                    case 10:
                        testName = "TestFlighttService.findByNumberTest";
                        findByNumberTest();
                        break;
                    case 11:
                        testName = "TestFlighttService.findByOriginTest";
                        findByOriginTest();
                        break;
                    case 12:
                        testName = "TestFlighttService.updateTest";
                        updateTest();
                        break;
                    default:
                        complete = true;
                }
            } catch (Exception ex) {
                failed = true;
                messages.append(testName + " FAILED!***!");
                messages.append(ex.getMessage());

                System.out.println(testName);
                ex.printStackTrace();
            }
            if (!failed && !complete) {
                messages.append(testName + " SUCCESS! ");
            }
            tearDown();
        }
    }

    public String getMessages() {
        return messages.toString();
    }

}
