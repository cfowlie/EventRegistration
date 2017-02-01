<?php

require_once __DIR__ . '/../controller/InputValidator.php';
require_once __DIR__ . '/../persistence/PersistenceEventRegistration.php';
require_once __DIR__ . '/../model/RegistrationManager.php';
require_once __DIR__ . '/../model/Participant.php';
require_once __DIR__ . '/../model/Event.php';
require_once __DIR__ . '/../model/Registration.php';

class Controller
{
    public function __construct()
    {

    }

    public function createParticipant($participant_name)
    {
        // Validate input
        $name = InputValidator::validate_input($participant_name);
        if ($name == NULL || strlen($name) == 0) {
            throw new Exception("Participant name cannot be empty!");
        } else {
            // Load data from store
            $pm = new PersistenceEventRegistration();
            $rm = $pm->loadDataFromStore();

            // Add new participant
            $participant = new Participant($name);
            $rm->addParticipant($participant);

            //Write data to store
            $pm->writeDataToStore($rm);
        }
    }

    public function createEvent($event_name, $event_date, $start_time, $end_time)
    {
        // Validate inputs
        $name = InputValidator::validate_input($event_name);
        $date = InputValidator::validate_input($event_date);
        $start = InputValidator::validate_input($start_time);
        $end = InputValidator::validate_input($end_time);

        $error = "";

        if ($name == NULL || strlen($name) == 0) {
            $error .= "@1Event name cannot be empty! ";
        }
        if ($date == NULL || strlen($date) == 0) { // TODO: validate format with regex
            $error .= "@2Event date must be specified correctly (YYYY-MM-DD)! ";
        }
        if ($start == NULL || strlen($start) == 0) { // TODO: validate format with regex
            $error .= "@3Event start time must be specified correctly (HH:MM)! ";
        }
        if ($end == NULL || strlen($end) == 0) { // TODO: validate format with regex
            $error .= "@4Event end time must be specified correctly (HH:MM)! ";
        }
        if (strtotime($start) > strtotime($end)) {
            $error .= "@5Event end time cannot be before event start time!";
        }

        if (strcmp($error, "") == 0) {
            // Load data from store
            $pm = new PersistenceEventRegistration();
            $rm = $pm->loadDataFromStore();

            // Add new participant
            $event = new Event($name, $date, $start, $end);
            $rm->addEvent($event);

            //Write data to store
            $pm->writeDataToStore($rm);

        } else {
            throw new Exception(trim($error));
        }

    }

    public function register($aParticipant, $aEvent)
    {
        // Load data from store
        $pm = new PersistenceEventRegistration();
        $rm = $pm->loadDataFromStore();

        // Find the participant
        $myParticipant = NULL;
        foreach ($rm->getParticipants() as $participant) {
            if (strcmp($participant->getName(), $aParticipant) == 0) {
                $myParticipant = $participant;
                break;
            }
        }

        // Find the event
        $myEvent = NULL;
        foreach ($rm->getEvents() as $event) {
            if (strcmp($event->getName(), $aEvent) == 0) {
                $myEvent = $event;
                break;
            }
        }

        // Register for the event
        $error = "";
        if ($myParticipant != NULL && $myEvent != NULL) {
            $myRegistration = new Registration($myParticipant, $myEvent);
            $rm->addRegistration($myRegistration);
            $pm->writeDataToStore($rm);
        } else {
            if ($myParticipant == NULL) {
                $error .= "@1Participant";
                if ($aParticipant != NULL) {
                    $error .= " ";
                    $error .= $aParticipant;
                }
                $error .= " not found! ";
            }
            if ($myEvent == NULL) {
                $error .= "@2Event";
                if ($aEvent != NULL) {
                    $error .= " ";
                    $error .= $aEvent;
                }
                $error .= " not found! ";
            }
            throw new Exception(trim($error));
        }

    }
}

?>