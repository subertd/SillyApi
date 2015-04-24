package subertd.assignment03;

import java.util.Date;

public class CelebritySighting {

    private String imdbId;

    private String name;

    private Double latitude;

    private Double longitude;

    private Date datetime;

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return new StringBuffer()
            .append("imdbId: ")
            .append(imdbId)
            .append("; name")
            .append(name)
            .append("; latitude: ")
            .append(latitude)
            .append("; longitude: ")
            .append(longitude)
            .append("; datetime: ")
            .append(datetime)
            .toString();
    }
}
