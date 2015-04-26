package subertd.assignment03;

import java.util.List;

public interface CelebrityService {

    Celebrity addCelebrity(Celebrity celebrity);

    Celebrity getCelebrityById(String imdbId);

    List<Celebrity> queryCelebrities(
            Long start, Long end, Double latitude, Double longitude);

    void deleteCelebrity(String imdbId);

    //List<Celebrity> getCelebritiesByName(String name);

    Celebrity addSighting(String imdbId, Sighting sighting);

    List<CelebritySighting> queryCelebritySightings(
            Long start, Long end, Double latitude, Double longitude);
}
