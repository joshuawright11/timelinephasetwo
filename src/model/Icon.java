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
    private String name;
    
    /*
    * The image associated with this icon.
    */
    private Image icon;
    
    private int id;
    
    private String path;
        
    /*
    * Constructor. The parameters of an individual icon will not be changed.
    */
    public Icon(String name, Image icon, String path){
        this.name = name;
        this.icon = icon;
        this.path = path;
        this.setId(-1);
    }
    
    public String getName(){
        return name;
    }
    
    public Image getImage(){
        return icon;
    }

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPath(){
		return path;
	}
       
    
}
