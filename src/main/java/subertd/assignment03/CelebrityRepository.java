package subertd.assignment03;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CelebrityRepository extends CrudRepository<Celebrity, String> {

    Celebrity findOneByImdbId(String imdbId);

    List<Celebrity> findByName(String name);

    /*
    @Query(
        "{ $unwind: $sightings }"
        + "{ $match: { 'sightings.latitude': 'latitude' }"
                + "{ 'sightings.longitude': 'longitude' }"
    )
    List<CelebritySighting> findCelebritySightingsByLocation(
            double latitude, double longitude);
*/

    @Query(value = "{ 'name':?0 }")
    List<Celebrity> findCelebritySightingsByLocation(String name);
}