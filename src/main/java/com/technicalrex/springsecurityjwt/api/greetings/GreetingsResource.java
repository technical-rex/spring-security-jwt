package com.technicalrex.springsecurityjwt.api.greetings;

import com.google.common.collect.ImmutableList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("greetings")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingsResource {
    private static final ImmutableList<Greeting> GREETINGS = ImmutableList.of(
            new Greeting("Hello!"),
            new Greeting("Yo!"),
            new Greeting("What's up?"));

    @GET
    public Response getJars() {
        return Response.ok(GREETINGS).build();
    }
}
