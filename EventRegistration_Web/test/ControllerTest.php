<?php
require_once __DIR__ . '/../controller/Controller.php';
require_once __DIR__ . '/../persistence/PersistenceEventRegistration.php';
require_once __DIR__ . '/../model/RegistrationManager.php';
require_once __DIR__ . '/../model/Participant.php';
require_once __DIR__ . '/../model/Event.php';

class ControllerTest extends PHPUnit_Framework_TestCase
{
    protected $c;
    protected $pm;
    protected $rm;

    protected function setUp()
    {
        $this->c = new Controller();
        $this->pm = new PersistenceEventRegistration();
        $this->rm = $this->pm->loadDataFromStore();
        $this->rm->delete();
        $this->pm->writeDataToStore($this->rm);
    }

    protected function tearDown()
    {
    }

    public function testCreateParticipant()
    {
        $this->assertEquals(0, count($this->rm->getParticipants()));

        $name = "Oscar";

        try {
            $this->c->createParticipant($name);
        } catch (Exception $e) {
            // check that no error occurred
            $this->fail();
        }

        // check file contents
        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(1, count($this->rm->getParticipants()));
        $this->assertEquals($name, $this->rm->getParticipant_index(0)->getName());
        $this->assertEquals(0, count($this->rm->getEvents()));
        $this->assertEquals(0, count($this->rm->getRegistrations()));
    }

    public function testCreateParticipantNull()
    {
        $this->assertEquals(0, count($this->rm->getParticipants()));

        $name = null;

        $error = "";
        try {
            $this->c->createParticipant($name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals("Participant name cannot be empty!", $error);
        // check file contents
        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->rm->getParticipants()));
        $this->assertEquals(0, count($this->rm->getEvents()));
        $this->assertEquals(0, count($this->rm->getRegistrations()));
    }

    public function testCreateParticipantEmpty()
    {
        $this->assertEquals(0, count($this->rm->getParticipants()));

        $name = "";

        $error = "";
        try {
            $this->c->createParticipant($name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals("Participant name cannot be empty!", $error);
        // check file contents
        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->rm->getParticipants()));
        $this->assertEquals(0, count($this->rm->getEvents()));
        $this->assertEquals(0, count($this->rm->getRegistrations()));
    }

    public function testCreateParticipantSpaces()
    {
        $this->assertEquals(0, count($this->rm->getParticipants()));

        $name = " ";

        $error = "";
        try {
            $this->c->createParticipant($name);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals("Participant name cannot be empty!", $error);
        // check file contents
        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->rm->getParticipants()));
        $this->assertEquals(0, count($this->rm->getEvents()));
        $this->assertEquals(0, count($this->rm->getRegistrations()));
    }

    public function testCreateEvent()
    {
        $this->assertEquals(0, count($this->rm->getEvents()));

        $name = "Soccer Game";
        $date = "2016-10-16";
        $starttime = "09:00";
        $endtime = "10:30";

        try {
            $this->c->createEvent($name, $date, $starttime, $endtime);
        } catch (Exception $e) {
            // check that no error occurred
            $this->fail();
        }

        // check file contents
        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->rm->getParticipants()));
        $this->assertEquals(1, count($this->rm->getEvents()));
        $this->assertEquals($name, $this->rm->getEvent_index(0)->getName());
        $this->assertEquals($date, $this->rm->getEvent_index(0)->getEventDate());
        $this->assertEquals($starttime, $this->rm->getEvent_index(0)->getStartTime());
        $this->assertEquals($endtime, $this->rm->getEvent_index(0)->getEndTime());
        $this->assertEquals(0, count($this->rm->getRegistrations()));
    }

    public function testCreateEventNull()
    {
        $this->assertEquals(0, count($this->rm->getEvents()));

        $name = null;
        $date = null;
        $starttime = null;
        $endtime = null;

        $error = "";
        try {
            $this->c->createEvent($name, $date, $starttime, $endtime);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals("@1Event name cannot be empty! @2Event date must be specified correctly (YYYY-MM-DD)! @3Event start time must be specified correctly (HH:MM)! @4Event end time must be specified correctly (HH:MM)!", $error);
        // check file contents
        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->rm->getParticipants()));
        $this->assertEquals(0, count($this->rm->getEvents()));
        $this->assertEquals(0, count($this->rm->getRegistrations()));
    }

    public function testCreateEventEmpty()
    {
        $this->assertEquals(0, count($this->rm->getEvents()));

        $name = "";
        $date = "";
        $starttime = "";
        $endtime = "";

        $error = "";
        try {
            $this->c->createEvent($name, $date, $starttime, $endtime);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals("@1Event name cannot be empty! @2Event date must be specified correctly (YYYY-MM-DD)! @3Event start time must be specified correctly (HH:MM)! @4Event end time must be specified correctly (HH:MM)!", $error);
        // check file contents
        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->rm->getParticipants()));
        $this->assertEquals(0, count($this->rm->getEvents()));
        $this->assertEquals(0, count($this->rm->getRegistrations()));
    }

    public function testCreateEventSpaces()
    {
        $this->assertEquals(0, count($this->rm->getEvents()));

        $name = " ";
        $date = " ";
        $starttime = " ";
        $endtime = " ";

        $error = "";
        try {
            $this->c->createEvent($name, $date, $starttime, $endtime);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals("@1Event name cannot be empty! @2Event date must be specified correctly (YYYY-MM-DD)! @3Event start time must be specified correctly (HH:MM)! @4Event end time must be specified correctly (HH:MM)!", $error);
        // check file contents
        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->rm->getParticipants()));
        $this->assertEquals(0, count($this->rm->getEvents()));
        $this->assertEquals(0, count($this->rm->getRegistrations()));
    }

    public function testCreateEventEndTimeBeforeStartTime()
    {
        $this->assertEquals(0, count($this->rm->getEvents()));

        $name = "Soccer Game";
        $date = "2016-10-16";
        $starttime = "09:00";
        $endtime = "08:59";

        $error = "";
        try {
            $this->c->createEvent($name, $date, $starttime, $endtime);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals("@5Event end time cannot be before event start time!", $error);
        // check file contents
        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->rm->getParticipants()));
        $this->assertEquals(0, count($this->rm->getEvents()));
        $this->assertEquals(0, count($this->rm->getRegistrations()));
    }

    public function testRegister()
    {
        $this->assertEquals(0, count($this->rm->getParticipants()));
        $this->assertEquals(0, count($this->rm->getEvents()));
        $this->assertEquals(0, count($this->rm->getRegistrations()));

        $nameP = "Oscar";
        try {
            $this->c->createParticipant($nameP);
        } catch (Exception $e) {
            $this->fail();
        }

        $nameE = "Soccer Game";
        $date = "2016-10-16";
        $starttime = "09:00";
        $endtime = "10:30";
        try {
            $this->c->createEvent($nameE, $date, $starttime, $endtime);
        } catch (Exception $e) {
            $this->fail();
        }

        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(1, count($this->rm->getParticipants()));
        $this->assertEquals(1, count($this->rm->getEvents()));

        try {
            $this->c->register($nameP, $nameE);
        } catch (Exception $e) {
            $this->fail();
        }

        // check file contents
        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(1, count($this->rm->getParticipants()));
        $this->assertEquals($nameP, $this->rm->getParticipant_index(0)->getName());
        $this->assertEquals(1, count($this->rm->getEvents()));
        $this->assertEquals($nameE, $this->rm->getEvent_index(0)->getName());
        $this->assertEquals($date, $this->rm->getEvent_index(0)->getEventDate());
        $this->assertEquals($starttime, $this->rm->getEvent_index(0)->getStartTime());
        $this->assertEquals($endtime, $this->rm->getEvent_index(0)->getEndTime());
        $this->assertEquals(1, count($this->rm->getRegistrations()));
        $this->assertEquals($this->rm->getParticipant_index(0), $this->rm->getRegistration_index(0)->getParticipant());
        $this->assertEquals($this->rm->getEvent_index(0), $this->rm->getRegistration_index(0)->getEvent());
    }

    public function testRegisterNull()
    {
        $this->assertEquals(0, count($this->rm->getParticipants()));
        $this->assertEquals(0, count($this->rm->getEvents()));
        $this->assertEquals(0, count($this->rm->getRegistrations()));

        $nameP = null;
        $nameE = null;

        $error = "";
        try {
            $this->c->register($nameP, $nameE);
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals("@1Participant not found! @2Event not found!", $error);
        // check file contents
        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->rm->getParticipants()));
        $this->assertEquals(0, count($this->rm->getEvents()));
        $this->assertEquals(0, count($this->rm->getRegistrations()));
    }

    public function testRegisterParticipantAndEventDoNotExist()
    {
        $this->assertEquals(0, count($this->rm->getParticipants()));
        $this->assertEquals(0, count($this->rm->getEvents()));
        $this->assertEquals(0, count($this->rm->getRegistrations()));

        $nameP = "Oscar";
        try {
            $this->c->createParticipant($nameP);
        } catch (Exception $e) {
            $this->fail();
        }

        $nameE = "Soccer Game";
        $date = "2016-10-16";
        $starttime = "09:00";
        $endtime = "10:30";
        try {
            $this->c->createEvent($nameE, $date, $starttime, $endtime);
        } catch (Exception $e) {
            $this->fail();
        }

        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(1, count($this->rm->getParticipants()));
        $this->assertEquals(1, count($this->rm->getEvents()));
        $participant = $this->rm->getParticipant_index(0);
        $event = $this->rm->getEvent_index(0);
        $this->rm->delete();
        $this->pm->writeDataToStore($this->rm);

        try {
            $this->c->register($participant->getName(), $event->getName());
        } catch (Exception $e) {
            $error = $e->getMessage();
        }

        // check error
        $this->assertEquals("@1Participant Oscar not found! @2Event Soccer Game not found!", $error);
        // check file contents
        $this->rm = $this->pm->loadDataFromStore();
        $this->assertEquals(0, count($this->rm->getParticipants()));
        $this->assertEquals(0, count($this->rm->getEvents()));
        $this->assertEquals(0, count($this->rm->getRegistrations()));
    }

}

?>
