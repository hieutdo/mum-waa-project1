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
public class TestAirportService {
    // Due to difficulties instantiating the context in JUnit I included this
    // pseudotest class

    StringBuilder messages = new StringBuilder();

    @Inject
    AirportService airportService;
    @Inject
    AirplaneService airplaneService;
    @Inject
    AirlineService airlineService;

    Airport testAiport1;

    List<Airport> savedAirport = new ArrayList<>();
    List<Airplane> savedAirplane = new ArrayList<>();
    List<Airline> savedAirline = new ArrayList<>();


    private void setUp() {
        testAiport1 = new Airport("testairport1", "airport1", "city1", "country1");
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

    private void createFindDeleteTest() throws Exception {
        savedAirport.add(testAiport1);

        //Create
        airportService.create(testAiport1);

        //Find
        Airport retrieved = airportService.findById(testAiport1.getId());

        if (!testAiport1.getName().equals(retrieved.getName())) {
            throw new Exception("Airport name does not match");
        }

        // Delete
        airportService.delete(retrieved);
        savedAirport.remove(testAiport1);

    }

    private void findAllTest() throws Exception {
        savedAirport.add(testAiport1);

        // At least one
        airportService.create(testAiport1);

        List<Airport> result = airportService.findAll();

        if (result.isEmpty()) {
            throw new Exception("No airports retrieved");
        }

        if (!result.contains(testAiport1)) {
            throw new Exception("Not all the airports were retrieved");
        }
    }

    private void findbyArrivalTest() throws Exception {
        Airport airport1 = new Airport("test1", "here", "state1", "USA");

        Airplane airplane1 = new Airplane("test56789", "A380", 519);
        Airline airline1 = new Airline("test1");

        Flight flight1 = new Flight("test3", "08/06/2009", "7:10 pm",
                "06/25/2015", "9:00 am", airline1, airport1, testAiport1, airplane1);

        testAiport1.addArrival(flight1);

        savedAirport.add(testAiport1);
        savedAirport.add(airport1);
        savedAirline.add(airline1);
        savedAirplane.add(airplane1);

        // At least one
        airportService.create(testAiport1);

        List<Airport> result = airportService.findByArrival(flight1);

        if (result.isEmpty()) {
            throw new Exception("No airports retrieved");
        }

        if (!result.contains(testAiport1)) {
            throw new Exception("Not all the airports were retrieved");
        }
    }

    private void findByNameTest() throws Exception {
        savedAirport.add(testAiport1);

        // At least one
        airportService.create(testAiport1);

        List<Airport> result = airportService.findByName(testAiport1.getName());

        if (result.isEmpty()) {
            throw new Exception("No airports retrieved");
        }

        if (!result.contains(testAiport1)) {
            throw new Exception("Not all the airports were retrieved");
        }
    }

    private void findByCityTest() throws Exception {
        savedAirport.add(testAiport1);

        // At least one
        airportService.create(testAiport1);

        List<Airport> result = airportService.findByCity(testAiport1.getCity());

        if (result.isEmpty()) {
            throw new Exception("No airports retrieved");
        }

        if (!result.contains(testAiport1)) {
            throw new Exception("Not all the airports were retrieved");
        }
    }

    private void findByCountryTest() throws Exception {
        savedAirport.add(testAiport1);

        // At least one
        airportService.create(testAiport1);

        List<Airport> result = airportService.findByCountry(testAiport1.getCountry());

        if (result.isEmpty()) {
            throw new Exception("No airports retrieved");
        }

        if (!result.contains(testAiport1)) {
            throw new Exception("Not all the airports were retrieved");
        }
    }

    private void findByDepartureTest() throws Exception {
        Airport airport1 = new Airport("test1", "here", "state1", "USA");

        Airplane airplane1 = new Airplane("test56789", "A380", 519);
        Airline airline1 = new Airline("test1");

        Flight flight1 = new Flight("test3", "08/06/2009", "7:10 pm",
                "06/25/2015", "9:00 am", airline1, testAiport1, airport1, airplane1);

        testAiport1.addDeparture(flight1);

        savedAirport.add(testAiport1);
        savedAirport.add(airport1);
        savedAirline.add(airline1);
        savedAirplane.add(airplane1);

        // At least one
        airportService.create(testAiport1);

        List<Airport> result = airportService.findByDeparture(flight1);

        if (result.isEmpty()) {
            throw new Exception("No airports retrieved");
        }

        if (!result.contains(testAiport1)) {
            throw new Exception("Not all the airports were retrieved");
        }
    }

    private void updateTest() throws Exception {

        savedAirport.add(testAiport1);

        // Save the values
        airportService.create(testAiport1);

        Airport saved = airportService.findById(testAiport1.getId());

        // modify the values
        saved.setAirportcode("CHANGE");

        //Airport merged =
        airportService.update(saved);

        Airport modified = airportService.findById(testAiport1.getId());

        if (modified == null) {
            throw new Exception("No airports retrieved");
        }

        if (!"CHANGE".equals(modified.getAirportcode())) {
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
                        testName = "TestAirportService.createFindDeleteTest";
                        createFindDeleteTest();
                        break;
                    case 2:
                        testName = "TestAirportService.findAllTest";
                        findAllTest();
                        break;
                    case 3:
                        testName = "TestAirportService.findbyArrivalTest";
                        findbyArrivalTest();
                        break;
                    case 4:
                        testName = "TestAirportService.findByCityTest";
                        findByCityTest();
                        break;
                    case 5:
                        testName = "TestAirportService.findByCountryTest";
                        findByCountryTest();
                        break;
                    case 6:
                        testName = "TestAirportService.findByDepartureTest";
                        findByDepartureTest();
                        break;
                    case 7:
                        testName = "TestAirportService.findByNameTest";
                        findByNameTest();
                        break;
                    case 8:
                        testName = "TestAirportService.updateTest";
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
