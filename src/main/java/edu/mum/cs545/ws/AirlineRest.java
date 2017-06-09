package edu.mum.cs545.ws;

import cs545.airline.model.Airline;
import cs545.airline.service.AirlineService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
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

    @POST
    public Response createAirline(Airline airline) {
        try {
            airlineService.findByName(airline.getName());
            throw new BadRequestException("The airline with name " + airline.getName() + " already exists.");
        } catch (NoResultException e) {
            // only create new entity when it does not exist
            airlineService.create(airline);
            return Response.created(URI.create("airlines/" + airline.getId())).entity(airline).build();
        }
    }

    @PUT
    public Response updateAirline(Airline airline) {
        if (airlineService.findById(airline.getId()) == null) {
            throw new BadRequestException("The airline with id " + airline.getId() + " does not exist.");
        }
        airlineService.update(airline);
        return Response.ok().entity(airline).build();
    }
}
