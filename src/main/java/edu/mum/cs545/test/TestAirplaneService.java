package edu.mum.cs545.test;

import cs545.airline.model.Airline;
import cs545.airline.model.Airplane;
import cs545.airline.model.Airport;
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
public class TestAirplaneService {
    // Due to difficulties instantiating the context in JUnit I included this
    // pseudotest class

    StringBuilder messages = new StringBuilder();

    @Inject
    AirportService airportService;
    @Inject
    AirplaneService airplaneService;
    @Inject
    AirlineService airlineService;

    Airplane testAiplane1;

    List<Airport> savedAirport = new ArrayList<>();
    List<Airplane> savedAirplane = new ArrayList<>();
    List<Airline> savedAirline = new ArrayList<>();


    private void setUp() {
        testAiplane1 = new Airplane("testserialnr", "testmodel", 100);
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
        savedAirplane.add(testAiplane1);

        //Create
        airplaneService.create(testAiplane1);

        //Find
        Airplane retrieved = airplaneService.findById(testAiplane1.getId());

        if (!testAiplane1.getSerialnr().equals(retrieved.getSerialnr())) {
            throw new Exception("Airplane name does not match");
        }

        // Delete
        airplaneService.delete(retrieved);
        savedAirplane.remove(testAiplane1);

    }

    private void findAllTest() throws Exception {
        savedAirplane.add(testAiplane1);

        // At least one
        airplaneService.create(testAiplane1);

        List<Airplane> result = airplaneService.findAll();

        if (result.isEmpty()) {
            throw new Exception("No airplanes retrieved");
        }

        if (!result.contains(testAiplane1)) {
            throw new Exception("Not all the airplanes were retrieved");
        }
    }

    private void findbyFlightTest() throws Exception {
//		Airport airport1 = new Airport("test1", "here", "state1", "USA");
//
//		Airplane airplane1 = new Airplane("test56789", "A380", 519);
//		Airline airline1 = new Airline("test1");
//
//		Flight flight1 = new Flight("test3", "08/06/2009", "7:10 pm",
//				"06/25/2015", "9:00 am", airline1, airport1, testAiplane1, airplane1);
//
//		testAiplane1.addArrival(flight1);
//
//		savedAirplane.add(testAiplane1);
//		savedAirplane.add(airport1);
//		savedAirline.add(airline1);
//		savedAirplane.add(airplane1);
//
//		// At least one
//		airplaneService.create(testAiplane1);
//
//		List<Airport> result = airplaneService.findByArrival(flight1);
//
//		if (result.isEmpty()) {
//			throw new Exception("No airplanes retrieved");
//		}
//
//		if (!result.contains(testAiplane1)){
//			throw new Exception("Not all the airplanes were retrieved");
//		}
    }

    private void findByModelTest() throws Exception {
        savedAirplane.add(testAiplane1);

        // At least one
        airplaneService.create(testAiplane1);

        List<Airplane> result = airplaneService.findByModel(testAiplane1.getModel());

        if (result.isEmpty()) {
            throw new Exception("No airplanes retrieved");
        }

        if (!result.contains(testAiplane1)) {
            throw new Exception("Not all the airplanes were retrieved");
        }
    }

    private void findBySrlnrTest() throws Exception {
        savedAirplane.add(testAiplane1);

        //Create
        airplaneService.create(testAiplane1);

        Airplane retrieved = airplaneService.findBySrlnr(testAiplane1.getSerialnr());

        if (!testAiplane1.getSerialnr().equals(retrieved.getSerialnr())) {
            throw new Exception("Airplane name does not match");
        }

    }

    private void updateTest() throws Exception {

        savedAirplane.add(testAiplane1);

        // Save the values
        airplaneService.create(testAiplane1);

        Airplane saved = airplaneService.findById(testAiplane1.getId());

        // modify the values
        saved.setModel("CHANGED");

        //Airport merged =
        airplaneService.update(saved);

        Airplane modified = airplaneService.findById(testAiplane1.getId());

        if (modified == null) {
            throw new Exception("No airplanes retrieved");
        }

        if (!"CHANGED".equals(modified.getModel())) {
            throw new Exception("Airplane didn't change the code");
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
                        testName = "TestAirplaneService.createFindDeleteTest";
                        createFindDeleteTest();
                        break;
                    case 2:
                        testName = "TestAirplaneService.findAllTest";
                        findAllTest();
                        break;
                    case 3:
                        testName = "TestAirplaneService.findbyFlightTest";
                        findbyFlightTest();
                        break;
                    case 4:
                        testName = "TestAirplaneService.findByModelTest";
                        findByModelTest();
                        break;
                    case 5:
                        testName = "TestAirplaneService.findBySrlnrTest";
                        findBySrlnrTest();
                        break;
                    case 6:
                        testName = "TestAirplaneService.updateTest";
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
