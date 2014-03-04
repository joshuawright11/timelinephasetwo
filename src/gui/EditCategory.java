package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import model.*;

/**
 * A JFrame by which to edit Categories.
 * 
 * @author Daniel
 * @author Kayley
 */
public class EditCategory extends javax.swing.JFrame {

    /**
     * Creates new form EditCategory
     */
    private Category category;
    private final TimelineMaker model;
    /**
     * Constructor
     * 
     * @param category The category to edit.
     * @param model The model by which to save.
     * @param superManage The parent window.
     */
    public EditCategory(Category category, final TimelineMaker model) {
        this.category = category;
        this.model = model;
        initComponents();
    }
    
    private JButton previewButton;
    private JButton finishedButton;
    private JLabel titleLabel;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JTextField nameTextField;
    private JTextField redTextField;
    private JTextField greenTextField;
    private JTextField blueTextField;
    private JTextField jTextField5;
    private Integer r, g, b;

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {
        setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);    
        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int y = (int) ((d.getHeight() - getHeight()) / 2);        
        int x = (int) ((d.getWidth() - getWidth()) / 2);
        setLocation(x, y);
        
        titleLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        redTextField = new javax.swing.JTextField();
        greenTextField = new javax.swing.JTextField();
        blueTextField = new javax.swing.JTextField();
        previewButton = new javax.swing.JButton();
        finishedButton = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();

        titleLabel.setText("Edit Category");
        
        try{
            titleLabel.setFont(new Font("Vijaya", 0, 22));
        }catch(Exception e){
            titleLabel.setFont(new Font("Times new Roman", 0, 16));
        }
        
        nameTextField.setDocument(new JTextFieldLimit(20));
        redTextField.setDocument(new JTextFieldLimit(3));
        greenTextField.setDocument(new JTextFieldLimit(3));
        blueTextField.setDocument(new JTextFieldLimit(3));

        fillTextFields();
        makeColor();
        
        jLabel2.setText("Red:");
        jLabel3.setText("Green:");
        jLabel4.setText("Blue:");

        previewButton.setText("Preview");
        finishedButton.setText("Finished");
        
        previewButton.addActionListener(new ECListener());
        finishedButton.addActionListener(new ECListener());
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(finishedButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addComponent(blueTextField))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addComponent(greenTextField))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addComponent(redTextField))
                            .addComponent(nameTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(titleLabel))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(previewButton)
                        .addComponent(jTextField5))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(redTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(greenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(blueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(previewButton)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(finishedButton))
        );

        pack();
    }
    /**
     * Fills the text fields with either default values or, if
     * applicable, the category's saved values.
     */
    private void fillTextFields(){
        if(category.getName()!=null)
            nameTextField.setText(category.getName());
        else
            nameTextField.setText("<Name>");        
        redTextField.setText(""+category.getColor().getRed());
        greenTextField.setText(""+category.getColor().getGreen());
        blueTextField.setText(""+category.getColor().getBlue());
    }
    /**
     * Submits the text fields for saving.
     * 
     * @return returns true if successful, else false.
     */
    private boolean submitTextFields(){
        String name = nameTextField.getText().trim();
        if(name.equals("<Name>") || name.length() < 1){ 
            JOptionPane.showMessageDialog(
            null, "The category needs a name!", 
                "FATAL_ERROR", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        category.setName(name);        
        if(!makeColor()) return false;
        Color color = new Color(r, g, b);
        category.setColor(color);
        model.addCategory(category);
        return true;
    }
    /**
     * Returns the category being edited.
     * 
     * @return The category being edited.
     */
    public Category getCategory(){
        return category;
    }
    /**
     * Previews the color selected.
     * 
     * @return true if successful, else false.
     */
    private boolean makeColor(){
        String red = redTextField.getText();
        String green = greenTextField.getText();
        String blue = blueTextField.getText();
        
        try{
            r = Integer.parseInt(red);
        }catch(NumberFormatException e){
           JOptionPane.showMessageDialog(
            null, "Please remember to use integers for rgb values!", 
                "FATAL_ERROR", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try{
            g = Integer.parseInt(green);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(
            null, "Please remember to use integers for rgb values!", 
                "FATAL_ERROR", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }        
        try{
            b = Integer.parseInt(blue);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(
            null, "Please remember to use integers for rgb values!", 
                "FATAL_ERROR", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(r > 255 || b > 255 || g > 255){
            JOptionPane.showMessageDialog(
            null, "All rgb values must be <= 255!", 
                "FATAL_ERROR", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        jTextField5.setBackground(new java.awt.Color(r, g, b));
        return true;
    }
    
    /**
     * The action listener by which to finish.
     */
    private class ECListener implements ActionListener{
        /**
         * Constructor
         */
        public ECListener(){
        }
        /**
         * Implements the actionPerformed method to complete the edit.
         * 
         * @param ae 
         */
        public void actionPerformed(ActionEvent ae){
            JButton thisButton = (JButton) ae.getSource();
            if(thisButton == finishedButton){
                if(submitTextFields()){
                    setVisible(false);
                    dispose();
                }
            }else if(thisButton == previewButton){
                if(makeColor())
                    jTextField5.setBackground(new java.awt.Color(r, g, b));
            }
        }
    }
}    
    
    
  