package edu.mum.cs545.ws;

import cs545.airline.model.Airplane;
import cs545.airline.service.AirplaneService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Named
@Path("airplanes")
@Produces({MediaType.APPLICATION_JSON})
public class AirplaneRest {
    @Inject
    private AirplaneService airplaneService;

    @GET
    public List<Airplane> getAirplanes() {
        return airplaneService.findAll();
    }

    @GET
    @Path("{airplaneId}")
    public Response getAirplaneById(@PathParam("airplaneId") long airplaneId) {
        Airplane airplane = airplaneService.findById(airplaneId);
        if (airplane == null) {
            throw new NotFoundException(String.format("The airplane you requested with id %s was not found in the database.", airplaneId));
        }
        return Response.ok().entity(airplane).build();
    }

    @DELETE
    @Path("{airplaneId}")
    public Response deleteAirplaneById(@PathParam("airplaneId") long airplaneId) {
        Airplane airplane = airplaneService.findById(airplaneId);
        if (airplane == null) {
            throw new NotFoundException(String.format("The airplane you requested with id %s was not found in the database.", airplaneId));
        }
        airplaneService.delete(airplane);
        return Response.ok().entity(airplane).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createAirplane(Airplane airplane) {
        try {
            airplaneService.findBySrlnr(airplane.getSerialnr());
            throw new BadRequestException("The airplane with serial number " + airplane.getSerialnr() + " already exists.");
        } catch (NoResultException e) {
            // only create new entity when it does not exist
            airplaneService.create(airplane);
            return Response.created(URI.create("airplanes/" + airplane.getId())).entity(airplane).build();
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateAirplane(Airplane airplane) {
        if (airplaneService.findById(airplane.getId()) == null) {
            throw new BadRequestException("The airplane with id " + airplane.getId() + " does not exist.");
        }
        airplaneService.update(airplane);
        return Response.ok().entity(airplane).build();
    }
}
