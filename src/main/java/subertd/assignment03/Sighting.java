package subertd.assignment03;

import java.util.Date;

public class Sighting {

    private Double latitude;

    private Double longitude;

    private Date datetime;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
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
                .append("latitude: ")
                .append(latitude)
                .append("; longitude: ")
                .append(longitude)
                .append("; datetime: ")
                .append(datetime)
                .toString();
    }
}
