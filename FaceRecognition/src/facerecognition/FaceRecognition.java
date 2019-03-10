/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facerecognition;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author shail
 */
public class FaceRecognition {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        // TODO code application logic here
        TrainModel model=new TrainModel();
        model.setDataSetPath("./datastet");
        model.trainModel();
        RecognizeFace recobj=new RecognizeFace();
        String user=recobj.Predict("./test/qry.jpg");
        
    }
    
}
