package gui;

import model.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Date;
import java.util.Iterator;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;

/**
 * TLEventPropertiesWindow.java
 * @author Andrew Thompson
 */
public class EventPropertiesWindow extends JFrame {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	// Window components.
	/**
	 * The event title field label.
	 */
	private JLabel titleLabel;
	/**
	 * The event title field.
	 */
	private JTextField title;

	/**
	 * The event type dropdown label.
	 */
	private JLabel typeLabel;
	/**
	 * The event type dropdown.
	 */
	private JComboBox<String> type;
        //Create a file chooser
        private JFileChooser fc;
        private Category selectedCategory;
        private JComboBox categoriesComboBox;
        
	/**
	 * The event date field label.
	 */
	private JLabel dateLabel;
	/**
	 * The event start date field.
	 */
	private JTextField startDate; // TODO Replace with JCalendar date-picker.
	/**
	 * The "to" label.
	 */
	private JLabel toLabel;
	/**
	 * The event end date field.
	 */
	private JTextField endDate; // TODO Replace with JCalendar date-picker.

	/**
	 * The event categoriesComboBox field label.
	 */
	private JLabel categoryLabel;

	/**
	 * The comments field label.
	 */
	private JLabel commentLabel;
	/**
	 * The comments scrollable pane.
	 */
	private JScrollPane comments;
	/**
	 * The comments text area.
	 */
	private JTextArea commentsArea;

	/**
	 * The ok button.
	 */
	private JButton okButton;
	/**
	 * The cancel button.
	 */
	private JButton cancelButton;
        private JButton openButton;
        private JLabel fileLabel;
        private String iconURL; 
        private final TimelineMaker model;

	/**
	 * Constructor.
	 * Constructor for adding a new event.
	 * @param model the TimelineMaker application model
	 */
	public EventPropertiesWindow(final TimelineMaker model) {
            iconURL = "";
            this.model = model;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Add Event");
		
		initComponents();
		
		// Define action for adding a new event.
		okButton.addActionListener(new ActionListener() {
			/**
			 * Get information from data fields and create new event. Add event to model.
			 */
			public void actionPerformed(ActionEvent e) {
				final String title = EventPropertiesWindow.this.title.getText();
				final String type = EventPropertiesWindow.this.type.getSelectedItem().toString();
				final String startDate = EventPropertiesWindow.this.startDate.getText();
				final String endDate = EventPropertiesWindow.this.endDate.getText();
				new Thread(new Runnable() {
					public void run() {
						if (type.equals("Atomic"))
							model.addEvent(new Atomic(title, selectedCategory, Date.valueOf(startDate)));
						else if (type.equals("Duration"))
							model.addEvent(new Duration(title, selectedCategory, Date.valueOf(startDate), Date.valueOf(endDate)));
					}
				}).start();
				dispose();
			}
		});
		
		initLayout();	
	}
	
	/**
	 * Constructor.
	 * Constructor for editing an existing event.
	 * @param model the TimelineMaker application model
	 * @param event the event to edit
	 */
	public EventPropertiesWindow(final TimelineMaker model, final TLEvent event) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Edit Event Properties");
		this.model = model;
		initComponents();
		
		new Thread(new Runnable() {
			/**
			 * Load information from the event to be edited into the window.
			 */
			public void run() {
				final String eventName = event.getName();
				if (event instanceof Atomic) {
					final String date = ((Atomic)event).getStartDate().toString();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							title.setText(eventName);
							type.setSelectedItem("Atomic");
							startDate.setText(date);
                                                        
						}
					});
				} else if (event instanceof Duration) {
					final String startDateString = ((Duration)event).getStartDate().toString();
					final String endDateString = ((Duration)event).getEndDate().toString();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							title.setText(eventName);
							type.setSelectedItem("Duration");
							startDate.setText(startDateString);
							endDate.setText(endDateString);
						}
					});
				}
			}
		}).start();

		// Define action for editing an existing event.
		okButton.addActionListener(new ActionListener() {
			/**
			 * Get information from data fields and create a new event. Replace the existing event
			 * with the new one.
			 */
			public void actionPerformed(ActionEvent e) {
				final String title = EventPropertiesWindow.this.title.getText();
				final String type = EventPropertiesWindow.this.type.getSelectedItem().toString();
				final String startDate = EventPropertiesWindow.this.startDate.getText();
				final String endDate = EventPropertiesWindow.this.endDate.getText();
                                final String URL = EventPropertiesWindow.this.iconURL;
				new Thread(new Runnable() {
					public void run() {
                                            if (type.equals("Atomic")){
                                                Atomic ev;
                                                ev = new Atomic(title, selectedCategory, Date.valueOf(startDate));
                                                ev.generateIcon(URL);
                                                model.editEvent(ev);
                                            }
                                            else if (type.equals("Duration")){
                                                Duration ev;
                                                ev = new Duration(title, selectedCategory, Date.valueOf(startDate), Date.valueOf(endDate));
                                                model.editEvent(ev);
                                            }
                                                
                                        }
				}).start();
				dispose();
			}
		});
		
		initLayout();
	}

	/**
	 * Initialize window components.
	 */
	private void initComponents() {
		titleLabel = new JLabel();
		title = new JTextField();

		typeLabel = new JLabel();
		type = new JComboBox<String>();

		dateLabel = new JLabel();
		startDate = new JTextField(10);
		toLabel = new JLabel();
                fileLabel = new JLabel();
		endDate = new JTextField(10);

		categoryLabel = new JLabel();

		commentLabel = new JLabel();
		comments = new JScrollPane();
		commentsArea = new JTextArea();

		okButton = new JButton();
		cancelButton = new JButton();
                openButton = new JButton();

		titleLabel.setText("Title");

		typeLabel.setText("Type");
                categoriesComboBox = new JComboBox();
                setComboBox();
                
		type.setModel(new DefaultComboBoxModel<String>(new String[] { "Duration", "Atomic" }));
		type.addActionListener(new ActionListener() {
			/**
			 * Toggle the event type.
			 */
			public void actionPerformed(ActionEvent e) {
				if (type.getSelectedItem().equals("Atomic")) {
					endDate.setVisible(false);
					toLabel.setVisible(false);
				} else {
					endDate.setVisible(true);
					toLabel.setVisible(true);
				}
			}
		});

		dateLabel.setText("Date");
		startDate.setText("yyyy-mm-dd");
		toLabel.setText("to");
		endDate.setText("yyyy-mm-dd");
                fileLabel.setText("Icon path:");

                fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                fc.addChoosableFileFilter(new ImageFilter());
                fc.setFileView(new FileView(){
                            public Icon getIcon(File f)
                            {
                                return FileSystemView.getFileSystemView().getSystemIcon(f);
                            }
                        });                
                openButton.setText("Choose Icon");
		categoryLabel.setText("Category");
		commentLabel.setText("Description:");
		commentsArea.setColumns(20);
		commentsArea.setRows(5);
		comments.setViewportView(commentsArea);

		okButton.setText("Ok");

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
                
                openButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //Handle open button action.
                        if (e.getSource() == openButton) {
                            int returnVal = fc.showOpenDialog(EventPropertiesWindow.this);

                            if (returnVal == JFileChooser.APPROVE_OPTION) {
                                File file = fc.getSelectedFile();
                                fileLabel.setText("Icon path: "+file.getName()+ ".");
                                iconURL = file.getAbsolutePath();
                                //This is where a real application would open the file.
                                //log.append("Opening: " + file.getName() + "." + newline);
                            } else {
                                //log.append("Open command cancelled by user." + newline);
                            }
                       }
                   }
                });
                
                categoriesComboBox.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent ae){
                        JComboBox thisBox = (JComboBox) ae.getSource();
                        Iterator<Category> categoriesComboBoxIterator =  model.getCategoryIterator();
                        Category c;
                        while(categoriesComboBoxIterator.hasNext()){
                            c = categoriesComboBoxIterator.next();
                            if(thisBox.getSelectedItem().equals(c.getName())){
                               selectedCategory = c;
                               break;
                            }            
                        }
                    }
                });
	}
        
                /**
         * Populates the ComboBox with categories.
         */
        public void setComboBox(){
            Iterator categoriesComboBoxIterator =  model.getCategoryIterator();
            String[] names = new String[model.catSize()];
            int i = 0;
            Category c = new Category("Base");
            while(categoriesComboBoxIterator.hasNext()){
                c = (Category)categoriesComboBoxIterator.next();
                names[i++] = c.getName();
            }
            categoriesComboBox.setModel(new javax.swing.DefaultComboBoxModel(names));
            selectedCategory = c;
        }


	/**
	 * Initialize the layout of the window.
	 * Note: Generated code.
	 */
	private void initLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addComponent(typeLabel)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(type, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup()
							.addComponent(dateLabel)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(startDate, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(toLabel)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(endDate, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
							.addComponent(comments)
							.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                            .addGap(0, 0, Short.MAX_VALUE)
                                                            .addComponent(okButton)
                                                            .addGap(18, 18, 18)
                                                            .addComponent(cancelButton))
                                                            .addGroup(layout.createSequentialGroup()
								.addComponent(categoryLabel)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(categoriesComboBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup()
									.addComponent(commentLabel)
									.addGap(0, 0, Short.MAX_VALUE))
									.addGroup(layout.createSequentialGroup()
										.addComponent(titleLabel)                                                                                                                               
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(title, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
										.addComponent(openButton)                                                                                                                                
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(fileLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                                                                )
										.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(titleLabel)
								.addComponent(title, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(type, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(typeLabel))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(endDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(dateLabel)
												.addComponent(toLabel)
												.addComponent(startDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                .addComponent(openButton)
                                                                                                .addComponent(fileLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                                                                )
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(commentLabel)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(comments, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        									.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
														.addComponent(categoryLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
														.addComponent(categoriesComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
																.addComponent(cancelButton)
																.addComponent(okButton))
																.addContainerGap())
				);
		pack();
	} 

}