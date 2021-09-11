package atflourenco.golden.raspberry.awards.init;

import atflourenco.golden.raspberry.awards.domain.Movie;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<Movie, Movie> {
    @Override
    public Movie process(Movie movie) throws Exception {
        return movie;
    }
}
