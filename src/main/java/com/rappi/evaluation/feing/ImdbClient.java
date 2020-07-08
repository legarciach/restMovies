package com.rappi.evaluation.feing;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(url = "http://www.omdbapi.com", name = "example")
public interface ImdbClient {

    @RequestMapping(method = RequestMethod.GET, value ="?apikey=a597d131&t={title}")
    public ResponseEntity<String> getMovieByTitle(@PathVariable("title") String title);

}


