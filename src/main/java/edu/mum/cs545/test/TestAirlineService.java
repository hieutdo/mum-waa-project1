package edu.mum.cs545.test;

import cs545.airline.model.Airline;
import cs545.airline.model.Airplane;
import cs545.airline.model.Airport;
import cs545.airline.model.Flight;
import cs545.airline.service.AirlineService;
import cs545.airline.service.AirplaneService;
import cs545.airline.service.AirportService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class TestAirlineService {
    // Due to difficulties instantiating the context in JUnit I included this
    // pseudotest class

    StringBuilder messages = new StringBuilder();

    @Inject
    AirlineService airlineService;
    @Inject
    AirportService airportService;
    @Inject
    AirplaneService airplaneService;

    Airline testAirline1;

    List<Airline> savedAirline = new ArrayList<>();
    List<Airport> savedAirport = new ArrayList<>();
    List<Airplane> savedAirplane = new ArrayList<>();


    private void setUp() {
        testAirline1 = new Airline("testAirline");
    }

    private void tearDown() {
        for (Airline saved : savedAirline) {
            airlineService.delete(saved);
        }
        for (Airport saved : savedAirport) {
            airportService.delete(saved);
        }
        for (Airplane saved : savedAirplane) {
            airplaneService.delete(saved);
        }
    }

    private void createFindDeleteTest() throws Exception {
        savedAirline.add(testAirline1);

        //Create
        airlineService.create(testAirline1);

        //Find
        Airline retrieved = airlineService.find(testAirline1);

        if (!testAirline1.getName().equals(retrieved.getName())) {
            throw new Exception("Airline name does not match");
        }

        // Delete
        airlineService.delete(retrieved);
        savedAirline.remove(testAirline1);

    }

    private void findAllTest() throws Exception {
        savedAirline.add(testAirline1);

        // At least one
        airlineService.create(testAirline1);

        List<Airline> result = airlineService.findAll();

        if (result.isEmpty()) {
            throw new Exception("No airlines retrieved");
        }

        if (!result.contains(testAirline1)) {
            throw new Exception("Not all the airlines were retrieved");
        }
    }

    private void findbyFlightTest() throws Exception {

        Airport airport1 = new Airport("test1", "here", "state1", "USA");
        Airport airport2 = new Airport("test2", "there", "state2", "USA");

        Airplane airplane1 = new Airplane("test56789", "A380", 519);

        Flight flight1 = new Flight("test3", "08/06/2009", "7:10 pm",
                "06/25/2015", "9:00 am", testAirline1, airport2, airport1, airplane1);

        testAirline1.addFlight(flight1);

        savedAirline.add(testAirline1);
        savedAirport.add(airport1);
        savedAirport.add(airport2);
        savedAirplane.add(airplane1);

        // At least one
        airlineService.create(testAirline1);

        List<Airline> result = airlineService.findByFlight(flight1);

        if (result.isEmpty()) {
            throw new Exception("No airlines retrieved");
        }

        if (!result.contains(testAirline1)) {
            throw new Exception("Not all the airlines were retrieved");
        }
    }

    private void findByNameTest() throws Exception {
        savedAirline.add(testAirline1);

        // At least one
        airlineService.create(testAirline1);

        Airline saved = airlineService.findByName(testAirline1.getName());

        if (saved == null) {
            throw new Exception("No airlines retrieved");
        }

        if (!testAirline1.getName().equals(saved.getName())) {
            throw new Exception("Airline name does not match");
        }
    }

    private void updateTest() throws Exception {
        Airport airport1 = new Airport("test1", "here", "state1", "USA");
        Airport airport2 = new Airport("test2", "there", "state2", "USA");

        Airplane airplane1 = new Airplane("test56789", "A380", 519);

        Flight flight1 = new Flight("test3", "08/06/2009", "7:10 pm",
                "06/25/2015", "9:00 am", testAirline1, airport2, airport1, airplane1);

        testAirline1.addFlight(flight1);

        savedAirline.add(testAirline1);
        savedAirport.add(airport1);
        savedAirport.add(airport2);
        savedAirplane.add(airplane1);

        // Save the values
        airlineService.create(testAirline1);

        Airline saved = airlineService.findByName(testAirline1.getName());

        // modify the values
        saved.removeFlight(flight1);

        //Airline merged =
        airlineService.update(saved);

        Airline modified = airlineService.findByName(testAirline1.getName());

        if (modified == null) {
            throw new Exception("No airlines retrieved");
        }

        System.out.println("************************************************* number of flights" + modified.getFlights().size());
        if (modified.getFlights().contains(flight1)) {
            throw new Exception("Airline didn't remove the flight");
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
                        testName = "TestAirlineService.createFindDeleteTest";
                        createFindDeleteTest();
                        break;
                    case 2:
                        testName = "TestAirlineService.findAllTest";
                        findAllTest();
                        break;
                    case 3:
                        testName = "TestAirlineService.findbyFlightTest";
                        findbyFlightTest();
                        break;
                    case 4:
                        testName = "TestAirlineService.findByNameTest";
                        findByNameTest();
                        break;
                    case 5:
                        testName = "TestAirlineService.updateTest";
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
