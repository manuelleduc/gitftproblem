package org.acme.quickstart;

import fr.mleduc.giftproblem.core.cs.Problem;
import fr.mleduc.giftproblem.core.domain.Family;
import fr.mleduc.giftproblem.core.domain.GiftSolution;
import fr.mleduc.giftproblem.core.domain.Person;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    public String hello() {
        return "hello";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/solution")
    public GiftSolution askForSolution(@RequestBody List<Family> families) {
        final List<Person> collect = families.stream()
                .flatMap(family -> family.getPeople().stream())
                .collect(Collectors.toList());
        final Problem problem = new Problem(collect);
        List<GiftSolution> solve = problem.solve();
        if (solve.size() > 0) {
            return solve.get(0);
        } else {
            return new GiftSolution();
        }
    }
}