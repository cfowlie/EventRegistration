package ca.mcgill.ecse321.eventregistration.view;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;
import ca.mcgill.ecse321.eventregistration.controller.InvalidInputException;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;

import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.JButton;

public class ParticipantPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6398301441623263485L;
	private JTextField participantNameTextField;
	private JLabel participantNameLabel;
	private JButton addParticipantButton;
	private RegistrationManager rm;
	private String error = null;
	private JLabel errorMessage;

	/** Creates new form ParticipantPage */
	public ParticipantPage(RegistrationManager rm) {
		this.rm = rm;
		initComponents();
	}
	
	
	private void initComponents() {
	    // elements for participant
	    participantNameTextField = new JTextField();
	    participantNameLabel = new JLabel();
	    addParticipantButton = new JButton();
	    
	    // elements for error message
	    errorMessage = new JLabel();
	    errorMessage.setForeground(Color.RED);
	    
	    // global settings and listeners
	    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    setTitle("Event Registration");

	    participantNameLabel.setText("Name:");
	    addParticipantButton.setText("Add Participant");
	    // layout
	    GroupLayout layout = new GroupLayout(getContentPane());
	    getContentPane().setLayout(layout);
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    
	 // THIS CODE NEEDS TO REPLACE PREVIOUS SOLUTION
	    layout.setHorizontalGroup(
	        // error message is added here
	        layout.createParallelGroup()
	        .addComponent(errorMessage)
	        .addGroup(layout.createSequentialGroup()
	        .addComponent(participantNameLabel)
	        .addGroup(layout.createParallelGroup()
	            .addComponent(participantNameTextField, 200, 200, 400)
	            .addComponent(addParticipantButton))
	        ));

	    layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {addParticipantButton, participantNameTextField});

	 // THIS CODE NEEDS TO REPLACE PREVIOUS SOLUTION
	    layout.setVerticalGroup(
	        layout.createSequentialGroup()
	        // error message is adde here
	        .addComponent(errorMessage)
	        .addGroup(layout.createParallelGroup()
	            .addComponent(participantNameLabel)
	            .addComponent(participantNameTextField))
	        .addComponent(addParticipantButton)
	        );

	    pack();
	    
	    addParticipantButton.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            addParticipantButtonActionPerformed();
	        }
	    });
	
	}
	
	private void refreshData() {
	    // error
	    errorMessage.setText(error);
	    if (error == null || error.length() == 0) {
	        // participant
	        participantNameTextField.setText("");
	    }
	    // this is needed because the size of the window changes depending on whether an error message is shown or not
	    pack();
	}
	
	private void addParticipantButtonActionPerformed() {
	    // call the controller
	    EventRegistrationController erc = new EventRegistrationController(rm);
	    error = null;
	    try {
	        erc.createParticipant(participantNameTextField.getText());
	    } catch (InvalidInputException e) {
	        error = e.getMessage();
	    }
	    // update visuals
	    refreshData();
	}
}
