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
    String name;
    Image icon;
    
    public Icon(String name, Image icon){
        this.name = name;
        this.icon = icon;
    }
    
    public Icon changeName(String name){
        return new Icon(name, icon);
    }
    
    public Icon changeIcon(Image icon){
        return new Icon(name, icon);
    }
    
    public String getName(){
        return name;
    }
    
    public Image getImage(){
        return icon;
    }
}
