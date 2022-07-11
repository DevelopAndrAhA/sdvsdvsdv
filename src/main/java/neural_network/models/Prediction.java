package neural_network.models;


/**
 * Created by Altynbek on 19.06.2022.
 */
public class Prediction implements Comparable<Prediction> {
    public float percentage;
    private boolean identified;
    private String username;
    private String photoName;
    private String inpDate;
    private double lat;
    private double lng;
    private int identifier;


    public Prediction(float percentage, boolean identified, String username, int identifier,String photoName) {
        this.percentage = percentage;
        this.identified = identified;
        this.username = username;
        this.photoName = photoName;
        this.identifier = identifier;
    }

    public String getInpDate() {
        return inpDate;
    }

    public void setInpDate(String inpDate) {
        this.inpDate = inpDate;
    }

    public float getPercentage() {
        return percentage;
    }


    public boolean isIdentified() {
        return identified;
    }


    public String getUsername() {
        return username;
    }


    public int getIdentifier() {
        return identifier;
    }

    public String getPhotoName() {
        return photoName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "percentage=" + percentage +
                ", identified=" + identified +
                ", username='" + username + '\'' +
                ", photoName='" + photoName + '\'' +
                ", inpDate='" + inpDate + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", identifier=" + identifier +
                '}';
    }

    @Override
    public int compareTo(Prediction p) {
        return Float.compare(p.percentage,this.percentage);
    }
}