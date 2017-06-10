package edu.mum.cs545.ws;

import cs545.airline.model.Airport;
import cs545.airline.service.AirportService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Named
@Path("airports")
@Produces({MediaType.APPLICATION_JSON})
public class AirportRest {
    @Inject
    private AirportService airportService;

    @GET
    public List<Airport> getAirports() {
        return airportService.findAll();
    }

    @GET
    @Path("{airportId}")
    public Response getAirportById(@PathParam("airportId") long airportId) {
        Airport airport = airportService.findById(airportId);
        if (airport == null) {
            throw new NotFoundException(String.format("The airport you requested with id %s was not found in the database.", airportId));
        }
        return Response.ok().entity(airport).build();
    }

    @DELETE
    @Path("{airportId}")
    public Response deleteAirportById(@PathParam("airportId") long airportId) {
        Airport airport = airportService.findById(airportId);
        if (airport == null) {
            throw new NotFoundException(String.format("The airport you requested with id %s was not found in the database.", airportId));
        }
        airportService.delete(airport);
        return Response.ok().entity(airport).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createAirport(Airport airport) {
        try {
            airportService.findByCode(airport.getAirportcode());
            throw new BadRequestException("The airport with code " + airport.getAirportcode() + " already exists.");
        } catch (NoResultException e) {
            // only create new entity when it does not exist
            airportService.create(airport);
            return Response.created(URI.create("airports/" + airport.getId())).entity(airport).build();
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateAirport(Airport airport) {
        if (airportService.findById(airport.getId()) == null) {
            throw new BadRequestException("The airport with id " + airport.getId() + " does not exist.");
        }
        airportService.update(airport);
        return Response.ok().entity(airport).build();
    }
}
