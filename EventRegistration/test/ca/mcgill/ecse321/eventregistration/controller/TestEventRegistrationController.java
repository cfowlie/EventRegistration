package ca.mcgill.ecse321.eventregistration.controller;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.*;

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

	private void checkResultParticipant(String name, RegistrationManager rm2) {
		assertEquals(1, rm2.getParticipants().size());
		  assertEquals(name, rm2.getParticipant(0).getName());
		  assertEquals(0, rm2.getEvents().size());
		  assertEquals(0, rm2.getRegistrations().size());
	}

}
