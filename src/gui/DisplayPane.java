package gui;


import render.TimelineGraphics;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;

/**
 * DisplayPane.java
 * 
 * This class stores the JFXPanel updated by the graphics.
 * 
 * @author Andrew Thompson and Josh Wright
 * Wheaton College, CS 335, Spring 2014
 * Project Phase 1
 * Feb 15, 2014
 */
public class DisplayPane extends JScrollPane {
	
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The JFXPanel for graphics.
	 */
	private JFXPanel fxPanel;
	
	/**
	 * Constructor.
	 * Set up the TimelineGraphics object in the JFXPanel.
	 * @param graphics the TimelineGraphics object
	 */
	public DisplayPane(TimelineGraphics graphics) {       
		fxPanel = new JFXPanel();
		setViewportView(fxPanel);
        graphics.setPanel(fxPanel);
	}
}
