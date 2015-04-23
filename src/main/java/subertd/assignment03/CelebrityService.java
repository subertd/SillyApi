package subertd.assignment03;

import java.util.Date;
import java.util.List;

/**
 * Created by Donald on 4/22/2015.
 */
public interface CelebrityService {

    Celebrity getCelebrityByImdbId(String imdbId);

    List<Celebrity> getCelebritiesByName(String name);

    void addSighting(String imdbId, Sighting stighting);
}
