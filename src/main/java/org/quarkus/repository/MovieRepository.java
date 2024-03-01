package org.quarkus.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.quarkus.entity.Movie;

@ApplicationScoped
public class MovieRepository implements PanacheRepository<Movie> {
}
