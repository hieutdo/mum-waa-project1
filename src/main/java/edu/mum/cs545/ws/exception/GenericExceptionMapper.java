package edu.mum.cs545.ws.exception;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        int status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();

        if (ex instanceof WebApplicationException) {
            status = ((WebApplicationException) ex).getResponse().getStatus();
        }

        jsonBuilder.add("status", status);
        jsonBuilder.add("message", ex.getMessage());

        return Response.status(status)
                .entity(jsonBuilder.build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}