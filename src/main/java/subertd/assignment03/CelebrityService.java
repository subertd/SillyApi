package subertd.assignment03;

import java.util.Date;
import java.util.List;

/**
 * Created by Donald on 4/22/2015.
 */
public interface CelebrityService {

    Celebrity addCelebrity(Celebrity celebrity);

    Celebrity getCelebrityByImdbId(String imdbId);

    List<Celebrity> getCelebritiesByName(String name);

    Celebrity addSighting(String imdbId, Sighting sighting);

    List<Celebrity> getCelebritiesByLocation(
            double latitude, double longitude);

    List<CelebritySighting> getCelebritySightingsByLocation(
            double latitude, double longitude);
}
