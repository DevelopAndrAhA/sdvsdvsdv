package neural_network.models;


import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Immutable
public class ResponseModelImg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int fullFaceFeatures_id;
    private String faceLabel;
    private int identifier;
    private String inp_date;
    private String photoName;
    private int facefeatures_id;
    private double lat;
    private double lng;

    public int getFullFaceFeatures_id() {
        return fullFaceFeatures_id;
    }

    public void setFullFaceFeatures_id(int fullFaceFeatures_id) {
        this.fullFaceFeatures_id = fullFaceFeatures_id;
    }

    public String getFaceLabel() {
        return faceLabel;
    }

    public void setFaceLabel(String faceLabel) {
        this.faceLabel = faceLabel;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getInp_date() {
        return inp_date;
    }

    public void setInp_date(String inp_date) {
        this.inp_date = inp_date;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public int getFacefeatures_id() {
        return facefeatures_id;
    }

    public void setFacefeatures_id(int facefeatures_id) {
        this.facefeatures_id = facefeatures_id;
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

}
