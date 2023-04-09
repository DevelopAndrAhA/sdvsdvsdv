package neural_network.models;



import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;

@Entity
public class FaceFeatures {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long faceFeatures_id;

    public static final int LEFT_FACE = 0;
    public static final int CENTER_FACE = 1;
    public static final int RIGHT_FACE = 2;

    private double lng;
    private double lat;

    //128 features to characterize each face
    private float[] features = new float[192];
    private int faceType = -1;

    @CreationTimestamp
    private Date inp_date;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fullFaceFeatures_id")
    private FullFaceFeatures fullFaceFeatures;


    private FaceFeatures() { }


    public FaceFeatures(float[] features, int faceType) {
        this.features = features;
        this.faceType = faceType;
    }


    public void setFeatures(float[] features) {
        this.features = features;
    }


    public float[] getFeatures() {
        return features;
    }


    public void setFaceType(int faceType) {
        this.faceType = faceType;
    }


    public int getFaceType() {
        if (faceType <= -1) {
            throw new IllegalStateException("face type is not expected");
        }

        return faceType;
    }

    @Override
    public String toString() {
        return "FaceFeatures{" +
                "faceFeatures_id=" + faceFeatures_id +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", features=" + Arrays.toString(features) +
                ", faceType=" + faceType +
                ", inp_date=" + inp_date +
                ", fullFaceFeatures=" + fullFaceFeatures +
                '}';
    }

    public static int getLeftFace() {
        return LEFT_FACE;
    }

    public static int getCenterFace() {
        return CENTER_FACE;
    }

    public static int getRightFace() {
        return RIGHT_FACE;
    }

    public FullFaceFeatures getFullFaceFeatures() {
        return fullFaceFeatures;
    }

    public void setFullFaceFeatures(FullFaceFeatures fullFaceFeatures) {
        this.fullFaceFeatures = fullFaceFeatures;
    }

    public long getFaceFeatures_id() {
        return faceFeatures_id;
    }

    public void setFaceFeatures_id(long faceFeatures_id) {
        this.faceFeatures_id = faceFeatures_id;
    }

    public Date getInp_date() {
        return inp_date;
    }

    public void setInp_date(Date inp_date) {
        this.inp_date = inp_date;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
