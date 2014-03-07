/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import javafx.scene.image.Image;

/**
 *
 * @author Kayley Lane
 */
public class Icon {
    /*
    * The name of the image, seperate from its entire path.
    */
    String name;
    /*
    * The image associated with this icon.
    */
    Image icon;
    
    /*
    * Constructor. The parameters of an individual icon will not be changed.
    */
    public Icon(String name, Image icon){
        this.name = name;
        this.icon = icon;
    }
    
    public String getName(){
        return name;
    }
    
    public Image getImage(){
        return icon;
    }
    
}
