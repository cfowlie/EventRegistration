package ca.mcgill.ecse321.eventregistration.controller;
import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import org.junit.*;

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;



public class TestEventRegistrationController {
	private RegistrationManager rm;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeModelManager("output"+File.separator+"data.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		rm = new RegistrationManager();
	}

	@After
	public void tearDown() throws Exception {
		rm.delete();
	}

	@Test
	public void testRegisterParticipantAndEventDoNotExist() {
	    assertEquals(0, rm.getRegistrations().size());

	    String nameP = "Oscar";
	    Participant participant = new Participant(nameP);
	    assertEquals(0, rm.getParticipants().size());

	    String nameE = "Soccer Game";
	    Calendar c = Calendar.getInstance();
	    c.set(2016, Calendar.OCTOBER, 16, 9, 00, 0);
	    Date eventDate = new Date(c.getTimeInMillis());
	    Time startTime = new Time(c.getTimeInMillis());
	    c.set(2016, Calendar.OCTOBER, 16, 10, 30, 0);
	    Time endTime = new Time(c.getTimeInMillis());
	    Event event = new Event(nameE, eventDate, startTime, endTime);
	    assertEquals(0, rm.getEvents().size());

	    String error = null;
	    EventRegistrationController erc = new EventRegistrationController(rm);
	    try {
	        erc.register(participant, event);
	    } catch (InvalidInputException e) {
	        error = e.getMessage();
	    }

	    // check error
	    assertEquals("Participant does not exist! Event does not exist!", error);

	    // check model in memory
	    assertEquals(0, rm.getRegistrations().size());
	    assertEquals(0, rm.getParticipants().size());
	    assertEquals(0, rm.getEvents().size());
	}
	
	@Test
	public void testRegisterNull() {
		  assertEquals(0, rm.getRegistrations().size());

		  Participant participant = null;
		  assertEquals(0, rm.getParticipants().size());

		  Event event = null;
		  assertEquals(0, rm.getEvents().size());

		  String error = null;
		  EventRegistrationController erc = new EventRegistrationController(rm);
		  try {
		      erc.register(participant, event);
		  } catch (InvalidInputException e) {
		      error = e.getMessage();
		  }

		  // check error
		  assertEquals("Participant needs to be selected for registration! Event needs to be selected for registration!",
		          error);

		  // check model in memory
		  assertEquals(0, rm.getRegistrations().size());
		  assertEquals(0, rm.getParticipants().size());
		  assertEquals(0, rm.getEvents().size());
		}
	
	@Test
	public void testCreateEvent() {
		  RegistrationManager rm = new RegistrationManager();
		  assertEquals(0, rm.getEvents().size());

		  String name = "Soccer Game";
		  Calendar c = Calendar.getInstance();
		  c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		  Date eventDate = new Date(c.getTimeInMillis());
		  Time startTime = new Time(c.getTimeInMillis());
		  c.set(2017, Calendar.MARCH, 16, 10, 30, 0);
		  Time endTime = new Time(c.getTimeInMillis());
		  // test model in memory
		  EventRegistrationController erc = new EventRegistrationController(rm);
		  try {
		    erc.createEvent(name, eventDate, startTime, endTime);
		  } catch (InvalidInputException e) {
		    fail();
		  }
		  checkResultEvent(name, eventDate, startTime, endTime, rm);
		  // test file
		  RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();
		  checkResultEvent(name, eventDate, startTime, endTime, rm2);
		  rm2.delete();
		}
	
	@Test
	private void checkResultEvent(String name, Date eventDate, Time startTime, Time endTime, RegistrationManager rm2)   {
		  assertEquals(0, rm2.getParticipants().size());
		  assertEquals(1, rm2.getEvents().size());
		  assertEquals(name, rm2.getEvent(0).getName());
		  assertEquals(eventDate.toString(), rm2.getEvent(0).getEventDate().toString());
		  assertEquals(startTime.toString(), rm2.getEvent(0).getStartTime().toString());
		  assertEquals(endTime.toString(), rm2.getEvent(0).getEndTime().toString());
		  assertEquals(0, rm2.getRegistrations().size());
		}
	
	@Test
	public void testCreateParticipantEmpty() {
		  assertEquals(0, rm.getParticipants().size());

		  String name = "";

		  String error = null;
		  EventRegistrationController erc = new EventRegistrationController(rm);
		  try {
		    erc.createParticipant(name);
		  } catch (InvalidInputException e) {
		    error = e.getMessage();
		  }

		  // check error
		  assertEquals("Participant name cannot be empty!", error);

		  // check no change in memory
		  assertEquals(0, rm.getParticipants().size());
		}
	
	@Test
	public void testCreateParticipantNull() {
		assertEquals(0,rm.getParticipants().size());
		String name = null;
		String error = null;
		EventRegistrationController erc = new EventRegistrationController(rm);
		  try {
		    erc.createParticipant(name);
		  } catch (InvalidInputException e) {
		    error = e.getMessage();
		  }

		  // check error
		  assertEquals("Participant name cannot be empty!", error);

		  // check no change in memory
		  assertEquals(0, rm.getParticipants().size());
	}
	
	@Test
	public void testCreateParticipant() {
	  RegistrationManager rm = new RegistrationManager();
	  assertEquals(0, rm.getParticipants().size()); // import Assert from the `org.junit` package

	  String name = "Oscar";

	  EventRegistrationController erc = new EventRegistrationController(rm);
	  try {
		erc.createParticipant(name);
	} catch (InvalidInputException e) {
		// Check that no error occured
		fail();
	}
	  
	  RegistrationManager rm1 = rm;
	  checkResultParticipant(name, rm1);

	  RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();

	  // check file contents
	  checkResultParticipant(name, rm2);
	  rm2.delete();
	}

	@Test
	private void checkResultParticipant(String name, RegistrationManager rm2) {
		assertEquals(1, rm2.getParticipants().size());
		  assertEquals(name, rm2.getParticipant(0).getName());
		  assertEquals(0, rm2.getEvents().size());
		  assertEquals(0, rm2.getRegistrations().size());
	}
	
	@Test
	public void testRegister() {
		  RegistrationManager rm = new RegistrationManager();
		  assertEquals(0, rm.getRegistrations().size());

		  String nameP = "Oscar";
		  Participant participant = new Participant(nameP);
		  rm.addParticipant(participant);
		  assertEquals(1, rm.getParticipants().size());

		  String nameE = "Soccer Game";
		  Calendar c = Calendar.getInstance();
		  c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
		  Date eventDate = new Date(c.getTimeInMillis());
		  Time startTime = new Time(c.getTimeInMillis());
		  c.set(2017, Calendar.MARCH, 16, 10, 30, 0);
		  Time endTime = new Time(c.getTimeInMillis());
		  Event event = new Event(nameE, eventDate, startTime, endTime);
		  rm.addEvent(event);
		  assertEquals(1, rm.getEvents().size());

		  EventRegistrationController erc = new EventRegistrationController(rm);
		  try {
		    erc.register(participant, event);
		  } catch (InvalidInputException e) {
		    fail();
		  }
		  checkResultRegister(nameP, nameE, eventDate, startTime, endTime, rm);

		  RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();
		  // check file contents
		  checkResultRegister(nameP, nameE, eventDate, startTime, endTime, rm2);
		  rm2.delete();
	}
	
	@Test
	private void checkResultRegister(String nameP, String nameE, Date eventDate, Time startTime, Time endTime,
	        RegistrationManager rm2)
	{
	    assertEquals(1, rm2.getParticipants().size());
	    assertEquals(nameP, rm2.getParticipant(0).getName());
	    assertEquals(1, rm2.getEvents().size());
	    assertEquals(nameE, rm2.getEvent(0).getName());
	    assertEquals(eventDate.toString(), rm2.getEvent(0).getEventDate().toString());
	    assertEquals(startTime.toString(), rm2.getEvent(0).getStartTime().toString());
	    assertEquals(endTime.toString(), rm2.getEvent(0).getEndTime().toString());
	    assertEquals(1, rm2.getRegistrations().size());
	    assertEquals(rm2.getEvent(0), rm2.getRegistration(0).getEvent());
	    assertEquals(rm2.getParticipant(0), rm2.getRegistration(0).getParticipant());
	}
	
	@Test
	public void testCreateEventEmpty() {
		  assertEquals(0, rm.getEvents().size());

		  String name = "";
		  Calendar c = Calendar.getInstance();
		  c.set(2017, Calendar.FEBRUARY, 16, 10, 00, 0);
		  Date eventDate = new Date(c.getTimeInMillis());
		  Time startTime = new Time(c.getTimeInMillis());
		  c.set(2017, Calendar.FEBRUARY, 16, 11, 30, 0);
		  Time endTime = new Time(c.getTimeInMillis());

		  String error = null;
		  EventRegistrationController erc = new EventRegistrationController(rm);
		  try {
		      erc.createEvent(name, eventDate, startTime, endTime);
		  } catch (InvalidInputException e) {
		      error = e.getMessage();
		  }

		  // check error
		  assertEquals("Event name cannot be empty!", error);
		  // check model in memory
		  assertEquals(0, rm.getEvents().size());
	}
	
	@Test
	public void testCreateEventSpaces() {
		  assertEquals(0, rm.getEvents().size());

		  String name = " ";
		  Calendar c = Calendar.getInstance();
		  c.set(2016, Calendar.OCTOBER, 16, 9, 00, 0);
		  Date eventDate = new Date(c.getTimeInMillis());
		  Time startTime = new Time(c.getTimeInMillis());
		  c.set(2016, Calendar.OCTOBER, 16, 10, 30, 0);
		  Time endTime = new Time(c.getTimeInMillis());

		  String error = null;
		  EventRegistrationController erc = new EventRegistrationController(rm);
		  try {
		      erc.createEvent(name, eventDate, startTime, endTime);
		  } catch (InvalidInputException e) {
		      error = e.getMessage();
		  }
		  // check error
		  assertEquals("Event name cannot be empty!", error);
		  // check model in memory
		  assertEquals(0, rm.getEvents().size());
	}
	
	@Test
	public void testCreateEventEndTimeBeforeStartTime() {
		assertEquals(0, rm.getEvents().size());

		  String name = "Soccer Game";
		  Calendar c = Calendar.getInstance();
		  c.set(2016, Calendar.OCTOBER, 16, 9, 00, 0);
		  Date eventDate = new Date(c.getTimeInMillis());
		  Time startTime = new Time(c.getTimeInMillis());
		  c.set(2016, Calendar.OCTOBER, 16, 8, 59, 59);
		  Time endTime = new Time(c.getTimeInMillis());

		  String error = null;
		  EventRegistrationController erc = new EventRegistrationController(rm);
		  try {
		      erc.createEvent(name, eventDate, startTime, endTime);
		  } catch (InvalidInputException e) {
		      error = e.getMessage();
		  }

		  // check error
		  assertEquals("Event end time cannot be before event start time!", error);

		  // check model in memory
		  assertEquals(0, rm.getEvents().size());
	}
	
	


}
