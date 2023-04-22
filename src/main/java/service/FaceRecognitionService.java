package service;

import org.springframework.stereotype.Service;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.op.Ops;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;



import org.tensorflow.lite.Interpreter;
import org.tensorflow.SavedModel;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Service
public class FaceRecognitionService {

    public class ConvertTFLiteToTF {

        public static void main(String[] args) throws Exception {
            // Загрузка модели из файла TFLite
            Interpreter tflite = new Interpreter(new FileInputStream("mobile_face_net.tflite"));

            // Определение размерности входа и выхода
            int inputSize = tflite.getInputTensor(0).shape()[1];
            int outputSize = tflite.getOutputTensor(0).shape()[1];

            // Создание сессии TensorFlow и экспорт модели в формате TensorFlow
            try (Session sess = new SavedModel.Builder("export")
                    .withTag("serve")
                    .addInput("input", Tensor.<Float>create(new int[] {1, inputSize}, FloatBuffer.allocate(inputSize * 1).order(ByteOrder.nativeOrder())))
                    .addOutput("output", Tensor.<Float>create(new int[] {1, outputSize}, FloatBuffer.allocate(outputSize * 1).order(ByteOrder.nativeOrder())))
                    .build()) {

                // Получение входного и выходного тензоров для сессии TensorFlow
                Tensor<Float> input = sess.runner().fetch("input").feed("input", Tensor.<Float>create(new int[] {1, inputSize}, FloatBuffer.allocate(inputSize * 1).order(ByteOrder.nativeOrder()))).run().get(0).expect(Float.class);
                Tensor<Float> output = sess.runner().fetch("output").feed("input", input).run().get(0).expect(Float.class);

                // Экспорт модели в формате TensorFlow
                SavedModel.Builder builder = new SavedModel.Builder("export");
                builder.addMetaGraphAndDef()
                        .setMetaInfoDef(SavedModel.MetaGraphDef.MetaInfoDef.newBuilder().setTags(new String[]{"serve"}).build())
                        .setGraphDef(output.graph().toGraphDef())
                        .build();
                builder.build().writeTo(new FileOutputStream("mobile_face_net.pb"));
            }
        }
    }



}



