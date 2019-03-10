/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facerecognition;

import java.io.Serializable;
import org.bytedeco.javacpp.opencv_face;

/**
 *
 * @author shail
 */
public class ModelWrapper implements Serializable{

     opencv_face.FaceRecognizer faceRecognizer; 
}
