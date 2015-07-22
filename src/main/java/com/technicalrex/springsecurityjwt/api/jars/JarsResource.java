package com.technicalrex.springsecurityjwt.api.jars;

import com.google.common.collect.ImmutableList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("jars")
@Produces(MediaType.APPLICATION_JSON)
public class JarsResource {
    private static final ImmutableList<Jar> JARS = ImmutableList.of(
            new Jar("Restaurant Tax", "Don't eat out so much!"),
            new Jar("Swear Jar", "Stop dropping those F bombs."));

    @GET
    public Response getJars() {
        return Response.ok(JARS).build();
    }
}
