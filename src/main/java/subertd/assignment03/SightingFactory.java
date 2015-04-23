package subertd.assignment03;

import java.util.Date;

/**
 * Created by Donald on 4/22/2015.
 */
public class SightingFactory {

    public static Sighting getInstance(final double latitude, final double longitude,
                              final Date datetime) {
        final Sighting sighting = new Sighting();
        sighting.setLatitude(latitude);
        sighting.setLongitude(longitude);
        sighting.setDatetime(datetime);
        return sighting;
    }
}
