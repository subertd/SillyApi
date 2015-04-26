package subertd.assignment03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    public Celebrity getCelebrityById(final String imdbId) {
        logger.entering(CelebrityServiceImpl.class.getName(),
                "getCelebrityById("
                        + String.valueOf(imdbId)
                        + ")");
        return celebrityRepository.findOneByImdbId(imdbId);
    }

    @Override
    public void deleteCelebrity(final String imdbId) {
        final Celebrity celebrity =
                celebrityRepository.findOneByImdbId(imdbId);
        celebrityRepository.delete(celebrity);
    }

    @Override
    public List<Celebrity> queryCelebrities(
            final Long start, final Long end, final Double latitude, final Double longitude)
    {
        List<Celebrity> celebrities;

        if (latitude != null && longitude != null) {
            celebrities = celebrityRepository.findCelebritiesByLocation(latitude, longitude);
        }
        else {
            celebrities =  celebrityRepository.findCelebrities();
        }

        celebrities = filterByDate(celebrities, start, end);

        return celebrities;
    }

    /*
    @Override
    public List<Celebrity> getCelebritiesByName(String name) {
        return celebrityRepository.findByName(name);
    }
    */

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
    public List<CelebritySighting> queryCelebritySightings(
            Long start, Long end, final Double latitude, final Double longitude)
    {
        final Date startDate = start == null ? new Date(0) : new Date(start);
        final Date endDate = end == null ? new Date() : new Date(end);

        List<Celebrity> celebrities;

        if (latitude != null && longitude != null) {
            celebrities = celebrityRepository.findCelebritiesByLocation(
                 latitude, longitude);
        }
        else {
            celebrities = celebrityRepository.findCelebrities();
        }

        final List<CelebritySighting> sightings =
                new ArrayList<CelebritySighting>();

        for (Celebrity celebrity : celebrities) {
            for (Sighting sighting : celebrity.getSightings()) {
                final CelebritySighting celebritySighting =
                    CelebritySightingFactory.getInstance(
                        celebrity.getImdbId(), celebrity.getName(), sighting);

                if (celebritySighting.getLatitude().equals(latitude)
                    && celebritySighting.getLongitude().equals(longitude)
                    && isBetweenDates(celebritySighting.getDatetime(), startDate, endDate))
                {
                    sightings.add(celebritySighting);
                }
            }
        }

        return sightings;
    }

    private List<Celebrity> filterByDate(final List<Celebrity> celebrities, final Long start, Long end) {

        if (start == null) {
            return celebrities;
        }

        Date startDate = new Date(start);
        Date endDate;

        if (end == null) {
            endDate = new Date();
        }
        else {
            endDate = new Date(end);
        }

        List<Celebrity> filteredCelebrities = new ArrayList<Celebrity>();

        for (Celebrity celebrity : celebrities) {
            for (Sighting sighting : celebrity.getSightings()) {
                final Date datetime = sighting.getDatetime();
                if (isBetweenDates(datetime, startDate, endDate)) {
                    filteredCelebrities.add(celebrity);
                    break;
                }
            }
        }

        return filteredCelebrities;
    }

    private boolean isBetweenDates(final Date d, final Date start, final Date end) {
        return ( ( d.after(start) || d.equals(start) )
                && ( d.before(end) || d.equals(end) ) );
    }

}
