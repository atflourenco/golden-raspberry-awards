package atflourenco.golden.raspberry.awards.init;

import atflourenco.golden.raspberry.awards.domain.Movie;
import atflourenco.golden.raspberry.awards.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DBWriter implements ItemWriter<Movie> {

    private final MovieRepository repository;

    @Override
    public void write(List<? extends Movie> movies) {
        List<Movie> moviesProcessed = processMovies(",", movies);
        moviesProcessed = processMovies(" and ", moviesProcessed);
        repository.saveAll(moviesProcessed);
    }

    public List<Movie> processMovies(String splitRegex, List<? extends Movie> movies) {
        List<Movie> moviesProcessed = new ArrayList<>();
        movies.forEach(movie -> {
            List<Movie> newMovies = splitProducersAndCreateNewMovies(splitRegex, movie);
            if (!newMovies.isEmpty()) {
                moviesProcessed.addAll(newMovies);
            } else {
                moviesProcessed.add(movie);
            }
        });
        return moviesProcessed;
    }

    public List<Movie> splitProducersAndCreateNewMovies(String splitRegex, Movie movie) {
        String[] producersComma = movie.getProducer().split(splitRegex);
        List<Movie> newMovies = new ArrayList<>();
        if (producersComma.length > 1) {
            addMovies(movie, producersComma, newMovies);
        }
        return newMovies;
    }

    private void addMovies(Movie movie, String[] producersComma, List<Movie> newMovies) {
        Arrays.stream(producersComma).map(s -> buildMovie(movie, s)).forEach(newMovies::add);
    }

    private Movie buildMovie(Movie m, String producer) {
        return Movie.builder()
                .id(m.getId())
                .year(m.getYear())
                .title(m.getTitle())
                .studio(m.getStudio())
                .producer(producer.trim())
                .winner(m.getWinner())
                .build();
    }
}
