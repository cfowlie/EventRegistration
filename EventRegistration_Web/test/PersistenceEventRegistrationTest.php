<?php

require_once __DIR__ . '/../persistence/PersistenceEventRegistration.php';
require_once __DIR__ . '/../model/RegistrationManager.php';
require_once __DIR__ . '/../model/Participant.php';

class PersistenceEventRegistrationTest extends PHPUnit_Framework_TestCase
{

    protected $pm;

    protected function setUp()
    {
        $this->pm = new PersistenceEventRegistration();
    }

    protected function tearDown()
    {

    }

    public function testPersistence()
    {
        // Create test data
        $rm = new RegistrationManager();
        $participant = new Participant("Carl");
        $rm->addParticipant($participant);

        // Write data to store
        $this->pm->writeDataToStore($rm);

        // Clear the data from memory
        $rm->delete();
        $this->assertEquals(0, count($rm->getParticipants()));

        // Load data back in
        $rm = $this->pm->loadDataFromStore();

        // Assert that data was returned
        $this->assertEquals(1, count($rm->getParticipants()));
        $myParticipant = $rm->getParticipant_index(0);
        $this->assertEquals("Carl", $myParticipant->getName());
    }
}