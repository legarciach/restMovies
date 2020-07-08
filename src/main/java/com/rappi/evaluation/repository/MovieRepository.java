package com.rappi.evaluation.repository;


import org.springframework.data.repository.CrudRepository;
import com.rappi.evaluation.entity.Movie;


public interface MovieRepository extends CrudRepository<Movie, Long> {
    public Movie findById(long id);
    //public List<Movie> findAll();
    public Movie findByImdbID(String imdbid);

}
