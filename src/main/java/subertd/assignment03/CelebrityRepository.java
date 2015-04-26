package subertd.assignment03;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CelebrityRepository extends CrudRepository<Celebrity, String> {

    Celebrity findOneByImdbId(String imdbId);

    //List<Celebrity> findByName(String name);

    @Query("{ }")
    List<Celebrity> findCelebrities();

    @Query("{ 'sightings.latitude' : ?0, 'sightings.longitude': ?1 }")
    List<Celebrity> findCelebritiesByLocation(double latitude, double longitude);
}