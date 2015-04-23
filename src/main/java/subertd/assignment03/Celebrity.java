package subertd.assignment03;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Celebrity {

    @Id
    private String imdbId;

    private String name;

    private List<Sighting> sightings = new ArrayList<Sighting>();

    public Celebrity(final String imdbId, final String name) {
        this.imdbId = imdbId;
        this.name = name;
    }

    public String getImdbId() {
        return this.imdbId;
    }

    public void setImdbId(final String imdbId) {
        this.imdbId = imdbId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Sighting> getSightings() {
        return sightings;
    }

    public void setSightings(List<Sighting> sightings) {
        this.sightings = sightings;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("IMDb_ID: ")
                .append(imdbId)
                .append("; Name: ")
                .append(name)
                .toString();
    }
}
