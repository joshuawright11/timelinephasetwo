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
    
    private int index;
    
    private String path;
        
    /*
    * Constructor. The parameters of an individual icon will not be changed.
    */
    public Icon(String name, Image icon, String path){
        this.name = name;
        this.icon = icon;
        this.path = path;
        this.setIndex(-1);
        this.setId(-1);
    }
    
    public String getName(){
        return name;
    }
    
    public Image getImage(){
        return icon;
    }

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
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
