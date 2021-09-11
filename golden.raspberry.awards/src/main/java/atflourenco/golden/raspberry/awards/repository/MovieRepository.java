package atflourenco.golden.raspberry.awards.repository;

import atflourenco.golden.raspberry.awards.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByWinnerTrueOrderByProducerAscYearAsc();
}
