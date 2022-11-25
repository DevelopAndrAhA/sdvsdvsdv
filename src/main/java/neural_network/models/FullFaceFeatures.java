package neural_network.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class FullFaceFeatures {

    public FullFaceFeatures() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FullFaceFeatures_id")
    private long fullFaceFeatures_id;



    @OneToOne(mappedBy = "fullFaceFeatures" ,cascade = CascadeType.ALL)
    @JoinColumn
    private FaceFeatures center;



    @CreationTimestamp
    private Date inp_date;


    private String faceLabel;
    private String photoName;
    private String deviceId;
    private int identifier = 0;
    private int city_id = 0;


    public FullFaceFeatures(String faceLabel) {
        this.faceLabel = faceLabel;
    }


    public String getFaceLabel() {
        return faceLabel;
    }


    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }


    public int getIdentifier() {
        return identifier;
    }


    public void setFaceFeatures(int faceType, FaceFeatures features) {
        switch (faceType) {
            case FaceFeatures.CENTER_FACE:
                center = features;
                break;
            default:
                throw new IllegalArgumentException("not expected facetype");
        }
    }


    public FaceFeatures getFaceFeatures(int faceType) {
        switch (faceType) {
            case FaceFeatures.CENTER_FACE:
                return center;
            default:
                return null;
        }
    }


    public long getFullFaceFeatures_id() {
        return fullFaceFeatures_id;
    }

    public void setFullFaceFeatures_id(long fullFaceFeatures_id) {
        this.fullFaceFeatures_id = fullFaceFeatures_id;
    }

    public FaceFeatures getCenter() {
        return center;
    }

    public void setCenter(FaceFeatures center) {
        this.center = center;
    }

    public void setFaceLabel(String faceLabel) {
        this.faceLabel = faceLabel;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public Date getInp_date() {
        return inp_date;
    }

    public void setInp_date(Date inp_date) {
        this.inp_date = inp_date;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
