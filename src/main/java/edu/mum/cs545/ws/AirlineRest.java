package edu.mum.cs545.ws;

import cs545.airline.model.Airline;
import cs545.airline.service.AirlineService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Named
@Path("airlines")
public class AirlineRest {
    @Inject
    private AirlineService airlineService;

    @GET
    public List<Airline> getAirlines() {
        return airlineService.findAll();
    }

    @GET
    @Path("{airlineId}")
    public Response getAirlineById(@PathParam("airlineId") long airlineId) {
        Airline airline = airlineService.findById(airlineId);
        if (airline == null) {
            throw new NotFoundException(String.format("The airline you requested with id %s was not found in the database.", airlineId));
        }
        return Response.ok().entity(airline).build();
    }

    @DELETE
    @Path("{airlineId}")
    public Response deleteAirlineById(@PathParam("airlineId") long airlineId) {
        Airline airline = airlineService.findById(airlineId);
        if (airline == null) {
            throw new NotFoundException(String.format("The airline you requested with id %s was not found in the database.", airlineId));
        }
        airlineService.delete(airline);
        return Response.ok().entity(airline).build();
    }
}
