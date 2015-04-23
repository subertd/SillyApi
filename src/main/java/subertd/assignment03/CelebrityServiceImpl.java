package subertd.assignment03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CelebrityServiceImpl implements CelebrityService {

    private static final Logger logger =
            Logger.getLogger(CelebrityServiceImpl.class.getName());


    private CelebrityRepository celebrityRepository;

    @Autowired
    public void setCelebrityRepository(
            final CelebrityRepository celebrityRepository) {
        logger.entering(CelebrityServiceImpl.class.getName(),
                "setCelebrityRepository("
                        + String.valueOf(celebrityRepository)
                        + ")");
        this.celebrityRepository = celebrityRepository;
    }

    @Override
    public Celebrity getCelebrityByImdbId(final String imdbId) {
        logger.entering(CelebrityRepository.class.getName(),
                "getCelebrityByImdbId("
                        + String.valueOf(imdbId)
                        + ")");
        return celebrityRepository.findOneByImdbId(imdbId);
    }

    @Override
    public List<Celebrity> getCelebritiesByName(String name) {
        return celebrityRepository.findByName(name);
    }

    @Override
    public void addSighting(final String imdbId, final Sighting sighting) {
        final Celebrity celebrity = celebrityRepository.findOneByImdbId(
                imdbId);

        if (celebrity != null
                && sighting.getLatitude() != null
                && sighting.getLongitude() != null
                && sighting.getDatetime() != null)
        celebrity.getSightings().add(sighting);
        celebrityRepository.save(celebrity);
    }
}
