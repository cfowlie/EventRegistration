package ca.mcgill.ecse321.eventregistration.view;

import javax.swing.JFrame;

import java.awt.Color;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.text.DateFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;
import ca.mcgill.ecse321.eventregistration.controller.InvalidInputException;
import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Registration;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;

public class EventRegistrationPage extends JFrame {
	// UI elements
	private JLabel errorMessage;
	private JComboBox<String> participantList;
	private JLabel participantLabel;
	private JComboBox<String> eventList;
	private JLabel eventLabel;
	private JButton registerButton;
	private JTextField participantNameTextField;
	private JLabel participantNameLabel;
	private JButton addParticipantButton;
	private JTextField eventNameTextField;
	private JLabel eventNameLabel;
	private JDatePickerImpl eventDatePicker;
	private JLabel eventDateLabel;
	private JSpinner startTimeSpinner;
	private JLabel startTimeLabel;
	private JSpinner endTimeSpinner;
	private JLabel endTimeLabel;
	private JButton addEventButton;
	private RegistrationManager rm;

	// data elements
	private String error = null;
	private Integer selectedParticipant = -1;
	private Integer selectedEvent = -1;
	
	public EventRegistrationPage(RegistrationManager aRegMan) {
	    rm = aRegMan;
	    initComponents();
	    refreshData();
	}
	private void initComponents() {
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		// elements for registration
		participantList = new JComboBox<String>(new String[0]);
		participantLabel = new JLabel();
		eventList = new JComboBox<String>(new String[0]);
		eventLabel = new JLabel();
		registerButton = new JButton();

		// elements for participant
		participantNameTextField = new JTextField();
		participantNameLabel = new JLabel();
		addParticipantButton = new JButton();

		// elements for event
		eventNameTextField = new JTextField();
		eventNameLabel = new JLabel();

		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		eventDatePicker = new JDatePickerImpl(datePanel, new DateFormatter());

		eventDateLabel = new JLabel();
		startTimeSpinner = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
		startTimeSpinner.setEditor(startTimeEditor); // will only show the current time
		startTimeLabel = new JLabel();
		endTimeSpinner = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
		endTimeSpinner.setEditor(endTimeEditor); // will only show the current time
		endTimeLabel = new JLabel();
		addEventButton = new JButton();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Event Registration");

		participantLabel.setText("Select Participant:");
		eventLabel.setText("Select Event:");
		registerButton.setText("Register");

		participantNameLabel.setText("Name");
		addParticipantButton.setText("Add Participant");

		eventNameLabel.setText("Name:");
		eventDateLabel.setText("Date:");
		startTimeLabel.setText("Start Time:");
		endTimeLabel.setText("End time:");
		addEventButton.setText("Add Event");

		// INSERT THIS CODE TO THE EXISTING METHOD
	    participantList.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            JComboBox<String> cb = (JComboBox<String>) evt.getSource();
	            selectedParticipant = cb.getSelectedIndex();
	        }
	    });
	    eventList.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            JComboBox<String> cb = (JComboBox<String>) evt.getSource();
	            selectedEvent = cb.getSelectedIndex();
	        }
	    });
	    registerButton.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            registerButtonActionPerformed();
	        }
	    });
	    addParticipantButton.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            addParticipantButtonActionPerformed();
	        }

	    });
	    addEventButton.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            addEventButtonActionPerformed();
	        }
	    });
		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(

				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(participantLabel)
								.addComponent(registerButton)
								.addComponent(participantNameLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(participantList)
								.addComponent(participantNameTextField, 200, 200, 400)
								.addComponent(addParticipantButton))
						.addGroup(layout.createParallelGroup()
								.addComponent(eventLabel)
								.addComponent(eventNameLabel)
								.addComponent(eventDateLabel)
								.addComponent(startTimeLabel)
								.addComponent(endTimeLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(eventList)
								.addComponent(eventNameTextField, 200, 200, 400)
								.addComponent(eventDatePicker)
								.addComponent(startTimeSpinner)
								.addComponent(endTimeSpinner)
								.addComponent(addEventButton)))
				);

		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {registerButton, participantLabel});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {addParticipantButton, participantNameTextField});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {addEventButton, eventNameTextField});

		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
						.addComponent(participantLabel)
						.addComponent(participantList)
						.addComponent(eventLabel)
						.addComponent(eventList))
				.addComponent(registerButton)
				.addGroup(layout.createParallelGroup()
						.addComponent(participantNameLabel)
						.addComponent(participantNameTextField)
						.addComponent(eventNameLabel)
						.addComponent(eventNameTextField))
				.addGroup(layout.createParallelGroup()
						.addComponent(eventDateLabel)
						.addComponent(eventDatePicker))
				.addGroup(layout.createParallelGroup()
						.addComponent(startTimeLabel)
						.addComponent(startTimeSpinner))
				.addGroup(layout.createParallelGroup()
						.addComponent(endTimeLabel)
						.addComponent(endTimeSpinner))
				.addGroup(layout.createParallelGroup()
						.addComponent(addParticipantButton)
						.addComponent(addEventButton))
				);

		pack();
	}
	private void refreshData() {
	    // error
	    errorMessage.setText(error);
	    if (error == null || error.length() == 0) {
	      // participant list
				participantList.removeAllItems();
				for (Participant p : rm.getParticipants()) {
					participantList.addItem(p.getName());
				}
				selectedParticipant = -1;
				participantList.setSelectedIndex(selectedParticipant);
				// event list
				eventList.removeAllItems();
				for (Event e : rm.getEvents()) {
					eventList.addItem(e.getName());
				}
				selectedEvent = -1;
				eventList.setSelectedIndex(selectedEvent);
				// participant
				participantNameTextField.setText("");
				// event
				eventNameTextField.setText("");
				eventDatePicker.getModel().setValue(null);
				startTimeSpinner.setValue(new Date());
				endTimeSpinner.setValue(new Date());
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

	private void addEventButtonActionPerformed() {
	    // call the controller
	    EventRegistrationController erc = new EventRegistrationController(rm);
	    // JSpinner actually returns a date and time
	    // force the same date for start and end time to ensure that only the times differ
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime((Date) startTimeSpinner.getValue());
	    calendar.set(2000, 1, 1);
	    Time startTime = new Time(calendar.getTime().getTime());
	    calendar.setTime((Date) endTimeSpinner.getValue());
	    calendar.set(2000, 1, 1);
	    Time endTime = new Time(calendar.getTime().getTime());
	    error = null;
	    try {
	        erc.createEvent(eventNameTextField.getText(), (java.sql.Date) eventDatePicker.getModel().getValue(), startTime, endTime);
	    } catch (InvalidInputException e) {
	        error = e.getMessage();
	    }
	    // update visuals
	    refreshData();
	}

	private void registerButtonActionPerformed() {
	    error = "";
	    if (selectedParticipant < 0)
	        error = error + "Participant needs to be selected for registration! ";
	    if (selectedEvent < 0)
	        error = error + "Event needs to be selected for registration!";
	    error = error.trim();
	    if (error.length() == 0) {
	        // call the controller
	        EventRegistrationController erc = new EventRegistrationController(rm);
	        try {
	            erc.register(rm.getParticipant(selectedParticipant), rm.getEvent(selectedEvent));
	        } catch (InvalidInputException e) {
	            error = e.getMessage();
	        }
	    }
	    // update visuals
	    refreshData();
	}
}
