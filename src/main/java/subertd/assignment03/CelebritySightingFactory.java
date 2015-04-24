package subertd.assignment03;

public class CelebritySightingFactory {

    public static CelebritySighting getInstance(
            final String imdbId,
            final String name,
            final Sighting sighting
    ) {
        final CelebritySighting celebritySighting = new CelebritySighting();

        celebritySighting.setImdbId(imdbId);
        celebritySighting.setName(name);
        celebritySighting.setLatitude(sighting.getLatitude());
        celebritySighting.setLongitude(sighting.getLongitude());
        celebritySighting.setDatetime((sighting.getDatetime()));

        return celebritySighting;
    }
}
