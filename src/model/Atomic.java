/**
 * 
 */
package model;
import java.sql.Date;
import javax.swing.ImageIcon;
/**
 * Extension of class TLEvent to represent atomic (single date) events
 * 
 * @author Josh Wright
 * Created: Jan 29, 2014
 * Package: backend
 *
 */
public class Atomic extends TLEvent {
	
        private AtomicLabel label;
        String imageURL;
        
	public Atomic(String name, Category category, Date startDate){
            super(name, startDate, category);
	}
        
        public void setLabel(AtomicLabel label){
            this.label = label;
        }
        
        public ImageIcon generateIcon(String url){
            imageURL = url;
            return createImageIcon(imageURL,
                                 this.name);  
        //refreshButton.setIcon(icon);
        }
        
        /** 
         * Returns an ImageIcon, or null if the path was invalid. 
         * Code by http://docs.oracle.com/javase/tutorial/uiswing/components/icon.html.
         */
        protected ImageIcon createImageIcon(String path,String description) {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                return new ImageIcon(imgURL, description);
            } else {
                System.err.println("Couldn't find file: " + path);
                return null;
            }
        }

        
       /**
         * Saves the event to the database.
         * TODO: insert the functionality for saving to the database.
         */
        public void save(){
           //
        }
        
        
}
