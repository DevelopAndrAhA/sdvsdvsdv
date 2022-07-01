package neural_network;

import neural_network.models.FaceFeatures;
import neural_network.models.FullFaceFeatures;
import neural_network.models.Prediction;
import org.springframework.web.multipart.MultipartFile;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.Tensors;

import com.google.common.io.ByteStreams;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

/**
 * Created by Altynbek on 15.06.2022.
 */


public class FaceRecognizer {


    private Graph graph;
    private List<Prediction> prediction_list;



    public FaceRecognizer() {
        graph = new Graph();
        graph.importGraphDef(loadGraphDef());
    }
    public FaceFeatures calculateFeaturesForFace(MultipartFile photo) {


        BufferedImage bufferedImage = null;
        try {
            byte [] bytes = photo.getBytes();
            InputStream is = new ByteArrayInputStream(bytes);
            bufferedImage = ImageIO.read(is);
        }catch (Exception e){}

        //check if we have faces on image
        BufferedFace face = new BufferedFace(bufferedImage);
        FaceFeatures faceFeatures = startNeuro(resize(face.face,160,160),0);
        return faceFeatures;
    }
    private FaceFeatures startNeuro(BufferedImage image, int faceType) {
        FaceFeatures features;

        try (Session session = new Session(graph)) {
            Tensor<Float> feedImage = Tensors.create(imageToMultiDimensionalArray(image));

            long timeResponse = System.currentTimeMillis();

            Tensor<Float> response = session.runner()
                    .feed("input", feedImage)
                    .feed("phase_train", Tensor.create(false))
                    .fetch("embeddings")
                    .run().get(0)
                    .expect(Float.class);

            FileUtils.timeSpent(timeResponse, "RESPONSE");

            final long[] shape = response.shape();

            //first dimension should return 1 as for image with normal size
            //second dimension should give 128 characteristics of face

            if (shape[0] != 1 || shape[1] != 128) {
                throw new IllegalStateException("illegal output values:  " + shape[0] + " , " + shape[1]);
            }

            float[][] featuresHolder = new float[1][128];
            response.copyTo(featuresHolder);

            features = new FaceFeatures(featuresHolder[0], faceType);

            response.close();
        }

        return features;
    }


    private byte[] loadGraphDef() {
        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream("save_session/model_face_recognition.pb")) {
            return ByteStreams.toByteArray(is);
        } catch (IOException e) {
            throw new RuntimeException("couldn't load graph");
        }
    }


    private static float[][][][] imageToMultiDimensionalArray(BufferedImage bi) {
        if (bi == null) {
            throw new IllegalArgumentException("image for neural network is null");
        }

        int height = bi.getHeight(), width = bi.getWidth(), depth = 3;
        float image[][][][] = new float[1][width][height][depth];
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                int rgb = bi.getRGB(i, j);
                Color color = new Color(rgb);
                image[0][i][j][0] = color.getRed();
                image[0][i][j][1] = color.getGreen();
                image[0][i][j][2] = color.getBlue();
            }
        }
        return image;
    }




    private Prediction predictBestMatchFromPool(FaceFeatures userToFind, List<FullFaceFeatures> featureses,float percent,java.sql.Date inpDate) {
        //find best prediction using euclid distance
        Prediction[] predictions = new Prediction[featureses.size()];
        prediction_list = new ArrayList<Prediction>();

        for (int i = 0; i < featureses.size(); i++) {
            FullFaceFeatures fullFaceFeatures = featureses.get(i);
            predictions[i] = matchTwoFeatureArrays(userToFind,fullFaceFeatures.getFaceFeatures(1),fullFaceFeatures.getFaceLabel(),fullFaceFeatures.getIdentifier(),percent,fullFaceFeatures.getPhotoName(),inpDate);
        }

        try{
            return Arrays.stream(predictions). max((first, second) -> (Float.compare(first.percentage, second.percentage))).orElse(null);
        }catch (Exception e){
            return null;
        }
    }

    private Prediction matchTwoFeatureArrays(FaceFeatures first, FaceFeatures second,String username, int identifier,float percent,String photoName,java.sql.Date inpDate) {
        if(second.getInp_date().getTime()>=inpDate.getTime()){
            float distance = euclidDistance(first.getFeatures(), second.getFeatures());

            final float distanceThreshold = 0.6f;
            final float percentageThreshold = 70.0f;

            float percentage = Math.min(100, 100 * distanceThreshold / distance);
            Prediction prediction = new Prediction(percentage, percentage >= percentageThreshold, username, identifier,photoName);
            prediction.setInpDate(second.getInp_date().toString());
            prediction.setLat(second.getLat());
            prediction.setLng(second.getLng());
            if(percentage>=percent){
                prediction_list.add(prediction);
            }
            return prediction;
        }
        return null;

    }


    private float euclidDistance(float[] first, float[] second) {
        if (first.length != second.length) {
            throw new IllegalArgumentException("should be same size");
        }

        float sum = 0;
        for (int i = 0; i < first.length; i++) {
            sum += Math.abs(first[i] - second[i]);
        }

        return (float) Math.sqrt(sum);
    }




    public FaceFeatures addNew(MultipartFile photo) {

        //we still have to crop and normalize face from user image
        FaceFeatures features = calculateFeaturesForFace(photo);
        if (features == null) {
            return null;
        }
        return features;
    }


    /**
     * Поиск пользователя в пуле функций лица.
     * изображение пользователя для проверки
     * Ожидаемые функции  collectFeatures для всех зарегистрированных пользователей
     * Предсказание @return, которое содержит черты лица с соответствующим пользователем.
     */
    public List<Prediction> searchFromPool(MultipartFile user, List<FullFaceFeatures> fullFaceFeatures,float percent,java.sql.Date inpDate) {
        FaceFeatures userToFind = calculateFeaturesForFace(user);
        if (userToFind == null) {
            return null;
        }
            predictBestMatchFromPool(userToFind, fullFaceFeatures,percent,inpDate);
        if(prediction_list!=null && prediction_list.size()!=0){
            return prediction_list;
        }else {
            return null;
        }
    }




/*    *//**
     * Отметить всех зарегистрированных пользователей на фото
     * изображение со всеми отмеченными лицами - и, если возможно, идентифицированными
     *//*
    public Prediction searchphoto(BufferedImage face, FullFaceFeatures[] collectedFeatures) {
        Prediction predictions = null;
        //draw probabilities on image with highlighted faces
        FaceFeatures features = startNeuro(face,1);
        predictions = predictBestMatchFromPool(features, collectedFeatures);
        return predictions;
    }*/








    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }





}

