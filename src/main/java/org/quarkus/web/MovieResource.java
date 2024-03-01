package org.quarkus.web;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.quarkus.entity.Movie;
import org.quarkus.repository.MovieRepository;

import java.util.List;

@Path("/api/movies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    MovieRepository movieRepository;

    @Inject
    Logger logger;

    @GET
    public List<Movie> getAllMovies() {
        logger.info("Return all movies");
        return movieRepository.listAll();
    }

    @GET
    @Path("{id}")
    public Movie getById(@PathParam("id") Long id)
    {
        logger.info("Returning a movie with id " + id );
        return movieRepository.findById(id);
    }

    @Transactional
    @POST
    public Response create(Movie movie) {
        logger.info("Returns the number of books");
        movieRepository.persist(movie);
        return Response.status(Response.Status.CREATED).build();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public Response deleteById(@PathParam("id") Long id) {
        logger.info("Deleting a movie");
        movieRepository.deleteById(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
