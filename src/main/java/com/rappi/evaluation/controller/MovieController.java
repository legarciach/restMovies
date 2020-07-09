package com.rappi.evaluation.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rappi.evaluation.entity.Movie;
import com.rappi.evaluation.feing.ImdbClient;
import com.rappi.evaluation.repository.MovieRepository;

@RestController
public class MovieController {
	@Autowired
	private MovieRepository repository;
	
	@Autowired
    private ImdbClient imdbClient;
	
	private final Log LOG = LogFactory.getLog(MovieController.class);

    @GetMapping("/movie/{title}")
    public ResponseEntity<String> findByTitle(@PathVariable String title){
        LOG.info("Dentro de findByTitle");
        ResponseEntity<String> re = imdbClient.getMovieByTitle(title);
        
        JsonObject convertedObject = new Gson().fromJson(re.getBody(), JsonObject.class);
        if(!convertedObject.get("Response").toString().equals("\"False\"")) {
        	String imdbid = convertedObject.get("imdbID").toString();
        	
        	Movie movie = repository.findByImdbID(imdbid);
        	if(movie != null) {
        		convertedObject.addProperty("watched_date", movie.getWatched_date().toString());
        		convertedObject.addProperty("rating", movie.getRating());
        		convertedObject.addProperty("comments", movie.getComments());
        		
        	}
        	LOG.info("Response: "+convertedObject.toString());
        }
        return new ResponseEntity<String>(convertedObject.toString(), HttpStatus.OK);
    }
    
    @PostMapping("/movie/{title}")
    public ResponseEntity<String> reviewTitle(@PathVariable String title, @RequestBody Movie movie){
        LOG.info("Dentro de reviewTitle");
        ResponseEntity<String> re = imdbClient.getMovieByTitle(title);
        JsonObject convertedObject = new Gson().fromJson(re.getBody(), JsonObject.class);
        
        LOG.info(convertedObject.get("Response").toString());
        if(!convertedObject.get("Response").toString().equals("\"False\"")) {
        	movie.setImdbID(convertedObject.get("imdbID").toString());
        	LOG.info("imdbID: "+convertedObject.get("imdbID").toString());
        	repository.save(movie);
        	return new ResponseEntity<String>("Review Saved", HttpStatus.OK);
        }
        LOG.info("Response: "+re.toString());
        return new ResponseEntity<String>(convertedObject.toString(), HttpStatus.OK);
    }
    
    @PutMapping("/movie/{title}")
    public ResponseEntity<String> updateMovie(@PathVariable String title, @RequestBody Movie movie){
    	LOG.info("Dentro de updateMovie "+movie.toJson());
    	ResponseEntity<String> re = imdbClient.getMovieByTitle(title);
    	JsonObject convertedObject = new Gson().fromJson(re.getBody(), JsonObject.class);
        
        LOG.info(convertedObject.get("Response").toString());
        if(!convertedObject.get("Response").toString().equals("\"False\"")) {
        	String imdbid = convertedObject.get("imdbID").toString();
        	movie.setImdbID(imdbid);
        	Movie moviedb = repository.findByImdbID(imdbid);
        	if(movie.getComments() != null && !movie.getComments().trim().equals("")) {
        		moviedb.setComments(movie.getComments());
        	}
        	
        	if(movie.getWatched_date() != null) {
        		moviedb.setWatched_date(movie.getWatched_date());
        	}
        	
        	if(movie.getRating() != 0) {
        		moviedb.setRating(movie.getRating());
        	}
        	repository.save(moviedb);
        	return new ResponseEntity<String>("Review Update", HttpStatus.OK);
        }
        
        LOG.info("Response Update: "+re.toString());
    	return re;
    }
    
    @DeleteMapping("/movie/{title}")
    public ResponseEntity<String> deleteMovie(@PathVariable String title){
    	LOG.info("Dentro de deleteMovie ");
    	ResponseEntity<String> re = imdbClient.getMovieByTitle(title);
    	JsonObject convertedObject = new Gson().fromJson(re.getBody(), JsonObject.class);
    	if(!convertedObject.get("Response").toString().equals("\"False\"")) {
    		String imdbid = convertedObject.get("imdbID").toString();
        	Movie movie = repository.findByImdbID(imdbid);
        	if(movie != null && !movie.getImdbID().equals("")) {
        		repository.delete(movie);
        	}
    	}
    	return re;
    }

    
    
}

