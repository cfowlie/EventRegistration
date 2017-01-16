package ca.mcgill.ecse321.eventregistration.view;

import javax.swing.JFrame;
import javax.swing.JTextField;

import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;
import ca.mcgill.ecse321.eventregistration.controller.InvalidInputException;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class EventRegistrationPage extends JFrame {
	private static final long serialVersionUID = -3813819647258555349L;
	private JTextField participantNameTextField;
	private JLabel participantNameLabel;
	private JButton addParticipantButton;
	private RegistrationManager rm;
	private String error = null;
	private JLabel errorMessage;
	
	/** Creates new form EventRegistrationPage */
	public EventRegistrationPage(RegistrationManager rm) {
	    this.rm = rm;
	    initComponents();
	}
	private void initComponents() {
	   
		 // elements for error message
	    errorMessage = new JLabel();
	    errorMessage.setForeground(Color.RED);
	    
	 
		
	    // elements for participant
	    participantNameTextField = new JTextField();
	    participantNameLabel = new JLabel();
	    addParticipantButton = new JButton();
	    

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
	    });//Why does it end in a ')'  ?
	}
	
	private void refreshData() {
	    participantNameTextField.setText("");
	    pack();
	}
	
	private void addParticipantButtonActionPerformed() {
		// create and call the controller
		EventRegistrationController erc = new EventRegistrationController(rm);
		try {
		    erc.createParticipant(participantNameTextField.getText());
		} catch (InvalidInputException e) {
		    // At that point, we ignore the exception
		}
		refreshData();
		}

}
