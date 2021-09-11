package atflourenco.golden.raspberry.awards.controller;

import atflourenco.golden.raspberry.awards.controller.response.WinnersResponse;
import atflourenco.golden.raspberry.awards.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/movies")
@Slf4j
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @RequestMapping(value = "/min-max-interval-awards", method = RequestMethod.GET)
    public ResponseEntity<WinnersResponse> minMaxIntervalTwoAwards() {
        log.debug("Movie controller - minMaxIntervalTwoAwards");
        WinnersResponse dto = service.fetchWinners();
        return ResponseEntity.ok().body(dto);
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List> getAllMovies() {
        log.debug("Movie controller - allMovies");
        List movies = service.fetchAllMovies();
        return ResponseEntity.ok().body(movies);
    }
}
