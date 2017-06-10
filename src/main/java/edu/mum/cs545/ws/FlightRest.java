package edu.mum.cs545.ws;

import cs545.airline.model.Flight;
import cs545.airline.service.FlightService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Named
@Path("flights")
@Produces({MediaType.APPLICATION_JSON})
public class FlightRest {
    @Inject
    private FlightService flightService;

    @GET
    public List<Flight> getFlights() {
        return flightService.findAll();
    }

    @GET
    @Path("{flightId}")
    public Response getFlightById(@PathParam("flightId") long flightId) {
        Flight flight = flightService.findById(flightId);
        if (flight == null) {
            throw new NotFoundException(String.format("The flight you requested with id %s was not found in the database.", flightId));
        }
        return Response.ok().entity(flight).build();
    }

    @DELETE
    @Path("{flightId}")
    public Response deleteFlightById(@PathParam("flightId") long flightId) {
        Flight flight = flightService.findById(flightId);
        if (flight == null) {
            throw new NotFoundException(String.format("The flight you requested with id %s was not found in the database.", flightId));
        }
        flightService.delete(flight);
        return Response.ok().entity(flight).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createFlight(Flight flight) {
        flightService.create(flight);
        return Response.created(URI.create("flights/" + flight.getId())).entity(flight).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateFlight(Flight flight) {
        if (flightService.findById(flight.getId()) == null) {
            throw new BadRequestException("The flight with id " + flight.getId() + " does not exist.");
        }
        flightService.update(flight);
        return Response.ok().entity(flight).build();
    }
}
