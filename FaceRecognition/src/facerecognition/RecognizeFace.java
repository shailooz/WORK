/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facerecognition;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_face;
import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

/**
 *
 * @author shail
 */
public class RecognizeFace {

    ModelWrapper model=new ModelWrapper();
     HashMap<String,Integer> users;
    public RecognizeFace() throws FileNotFoundException, IOException, ClassNotFoundException {
        
        model.faceRecognizer=opencv_face.FisherFaceRecognizer.create();
        model.faceRecognizer.read("model.ser");
    
        FileInputStream fis = new FileInputStream("users.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        users = (HashMap<String, Integer>) ois.readObject();
        
        ois.close();
    }
    public String Predict(String path)
    {
        
        opencv_core.Mat testImage = imread(path, IMREAD_GRAYSCALE);
        IntPointer label = new IntPointer(1);
        DoublePointer confidence = new DoublePointer(1);
        model.faceRecognizer.predict(testImage, label, confidence);
        int predictedLabel = label.get(0);

        String user="";
        for (Map.Entry<String,Integer> entry : users.entrySet())  
        {
           if(entry.getValue()==predictedLabel)
           {
               user=entry.getKey();
               break;
           }       
        } 
        System.out.println("Predicted label: " + user);
        return user;
    }
    
    
    
}
