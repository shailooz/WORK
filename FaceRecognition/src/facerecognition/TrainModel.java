/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facerecognition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.IntBuffer;
import java.util.HashMap;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import org.bytedeco.javacpp.opencv_face;
import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

/**
 *
 * @author shail
 */
public class TrainModel {
    
    
    String path="";
    ModelWrapper model;

    public TrainModel() {
        model=new ModelWrapper();
    }
    void setDataSetPath(String path)
    {
        this.path=path;
    }
    void trainModel() throws FileNotFoundException, IOException
    {
        String trainingDir = path;
        

        File root = new File(trainingDir);

        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };

        File[] imageFiles = root.listFiles(imgFilter);

        opencv_core.MatVector images = new opencv_core.MatVector(imageFiles.length);

        opencv_core.Mat labels = new opencv_core.Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelsBuf = labels.createBuffer();

        int counter = 0;

        HashMap<String,Integer> users=new HashMap<String, Integer>();
        for (File image : imageFiles) {
            System.out.println(""+image.getName());
            opencv_core.Mat img = imread(image.getAbsolutePath(), IMREAD_GRAYSCALE);
            int label=-1;
            String slabel = image.getName().split("\\-")[0];
            if(!users.containsKey(slabel))
            {
                users.put(slabel, users.size()+1);
            }
            label=users.get(slabel);
            images.put(counter, img);

            labelsBuf.put(counter, label);

            counter++;
        }

         model.faceRecognizer= opencv_face.FisherFaceRecognizer.create();
        // FaceRecognizer faceRecognizer = EigenFaceRecognizer.create();
        // FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();

        model.faceRecognizer.train(images, labels);
        model.faceRecognizer.write("model.ser");
        FileOutputStream fos;
        ObjectOutputStream oos;
        fos = new FileOutputStream("users.ser");
        oos = new ObjectOutputStream(fos);
        oos.writeObject(users);
        oos.close();

    }
    
}
