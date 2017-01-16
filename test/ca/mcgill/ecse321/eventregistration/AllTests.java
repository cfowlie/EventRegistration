package ca.mcgill.ecse321.eventregistration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse321.eventregistration.controller.TestEventRegistrationController;
import ca.mcgill.ecse321.eventregistration.persistence.TestPersistence;

@RunWith(Suite.class)
@SuiteClasses({TestEventRegistrationController.class, TestPersistence.class})
public class AllTests {

}
