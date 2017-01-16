package ca.mcgill.ecse321.eventregistration.controller;

import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;

public class EventRegistrationController {

	private RegistrationManager rm;

	public EventRegistrationController(RegistrationManager rm) {
		this.rm = rm;
	}

	public void createParticipant(String name) throws InvalidInputException {
		/*
		 * ****************OLD CODE********************
		Participant p = new Participant(name);
		// RegistrationManager rm = new RegistrationManager(); //Didn't work
		// because overriding link
		// from constructor
		rm.addParticipant(p);
		PersistenceXStream.saveToXMLwithXStream(rm);
	*/
		/* ROUND 2 CODE
		if (name == null) {
			throw new InvalidInputException("Participant name cannot be empty!");
		}
		Participant p = new Participant(name);
		rm.addParticipant(p);
		PersistenceXStream.saveToXMLwithXStream(rm);	
	*/
	
		
		
	if (name == null || name.trim().length() == 0) {
	    throw new InvalidInputException("Participant name cannot be empty!");		  
	}
		Participant p = new Participant(name);
		rm.addParticipant(p);
		PersistenceXStream.saveToXMLwithXStream(rm);
	}
}
