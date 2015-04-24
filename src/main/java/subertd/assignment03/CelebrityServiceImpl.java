package subertd.assignment03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Celebrity addCelebrity(final Celebrity celebrity) {
        logger.entering(CelebrityServiceImpl.class.getName(),
                "addCelebrity(" + String.valueOf(celebrity) + ")");

        if (celebrity != null
                && celebrity.getImdbId() != null
                && celebrity.getName() != null) {
            final Celebrity existingCelebrity =
                    celebrityRepository.findOneByImdbId(celebrity.getImdbId());
            if (existingCelebrity == null) {
                return celebrityRepository.save(celebrity);
            }
        }
        return null;
    }

    @Override
    public Celebrity getCelebrityByImdbId(final String imdbId) {
        logger.entering(CelebrityServiceImpl.class.getName(),
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
    public Celebrity addSighting(final String imdbId, final Sighting sighting) {
        final Celebrity celebrity = celebrityRepository.findOneByImdbId(
                imdbId);

        if (celebrity != null
                && sighting.getLatitude() != null
                && sighting.getLongitude() != null
                && sighting.getDatetime() != null) {
            celebrity.getSightings().add(sighting);
            celebrityRepository.save(celebrity);
            return celebrity;
        }

        return null;
    }

    @Override
    public List<Celebrity> getCelebritiesByLocation(
            final double latitude, final double longitude)
    {
        return celebrityRepository.findCelebritiesByLocation(
                latitude, longitude);
    }

    @Override
    public List<CelebritySighting> getCelebritySightingsByLocation(
            final double latitude, final double longitude)
    {
        final List<Celebrity> celebrities =
                celebrityRepository.findCelebritiesByLocation(
                latitude, longitude);

        final List<CelebritySighting> sightings =
                new ArrayList<CelebritySighting>();

        for (Celebrity celebrity : celebrities) {
            for (Sighting sighting : celebrity.getSightings()) {
                final CelebritySighting celebritySighting =
                        CelebritySightingFactory.getInstance(
                                celebrity.getImdbId(), celebrity.getName(), sighting);

                if (celebritySighting.getLatitude() == latitude
                        && celebritySighting.getLongitude() == longitude)
                {
                    sightings.add(celebritySighting);
                }
            }
        }

        return sightings;
    }

    @Override
    public void deleteCelebrity(DeleteCelebrityRequest request) {
        final Celebrity celebrity =
                celebrityRepository.findOneByImdbId(request.getImdbId());
        celebrityRepository.delete(celebrity);
    }
}
