package atflourenco.golden.raspberry.awards.service;

import atflourenco.golden.raspberry.awards.controller.response.AwardProducerResponse;
import atflourenco.golden.raspberry.awards.controller.response.WinnersResponse;
import atflourenco.golden.raspberry.awards.domain.Movie;
import atflourenco.golden.raspberry.awards.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public List fetchAllMovies() {
        return repository.findAll();
    }

    public WinnersResponse fetchWinners() {
        List<AwardProducerResponse> list = getWinners();

        Comparator<AwardProducerResponse> comparator = Comparator.comparing(AwardProducerResponse::getInterval);
        AwardProducerResponse min = list.stream().min(comparator).get();
        AwardProducerResponse max = list.stream().max(comparator).get();

        List<AwardProducerResponse> listMin = new ArrayList<>();
        List<AwardProducerResponse> listMax = new ArrayList<>();
        listMin.add(min);
        listMax.add(max);

        return buildWinnersResponse(listMin, listMax);

    }

    private WinnersResponse buildWinnersResponse(List<AwardProducerResponse> listMin,
                                                 List<AwardProducerResponse> listMax) {
        WinnersResponse interval = new WinnersResponse();
        interval.setMax(listMax);
        interval.setMin(listMin);
        return interval;
    }

    private List<AwardProducerResponse> getWinners() {
        List<Movie> movies = repository.findByWinnerTrueOrderByProducerAscYearAsc();
        Map<String, AwardProducerResponse> map = new HashMap<>();
        List<AwardProducerResponse> responseList = new ArrayList<>();
        processMovies(movies, map, responseList);
        return responseList;
    }

    private void processMovies(List<Movie> movies, Map<String, AwardProducerResponse> map,
                               List<AwardProducerResponse> responseList) {

        movies.forEach(movie -> {
            AwardProducerResponse response = map.get(movie.getProducer());
            if (response == null) {
                response = buildAwardProducerResponse(movie);
                map.put(movie.getProducer(), response);
            } else {
                AwardProducerResponse nextProducer = buildNextProducerResponse(movie, response);
                responseList.add(nextProducer);
                map.put(movie.getProducer(), nextProducer);
            }
        });
    }

    private AwardProducerResponse buildNextProducerResponse(Movie movie, AwardProducerResponse response) {
        return AwardProducerResponse
                .builder()
                .producer(movie.getProducer())
                .previousWin(response.getFollowingWin())
                .followingWin(movie.getYear())
                .interval(movie.getYear() - response.getFollowingWin())
                .build();
    }

    private AwardProducerResponse buildAwardProducerResponse(Movie movie) {
        return AwardProducerResponse.builder()
                .producer(movie.getProducer())
                .previousWin(movie.getYear())
                .followingWin(movie.getYear())
                .interval(1)
                .build();
    }

}
