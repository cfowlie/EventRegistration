<?php
/*PLEASE DO NOT EDIT THIS CODE*/

/*This code was generated using the UMPLE 1.25.0-7a2d34d modeling language!*/

class RegistrationManager
{


    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //RegistrationManager Associations
    private $registrations;
    private $participants;
    private $events;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public function __construct()
    {
        $this->registrations = array();
        $this->participants = array();
        $this->events = array();
    }

    //------------------------
    // INTERFACE
    //------------------------

    public function getRegistration_index($index)
    {
        $aRegistration = $this->registrations[$index];
        return $aRegistration;
    }

    public function getRegistrations()
    {
        $newRegistrations = $this->registrations;
        return $newRegistrations;
    }

    public function numberOfRegistrations()
    {
        $number = count($this->registrations);
        return $number;
    }

    public function hasRegistrations()
    {
        $has = $this->numberOfRegistrations() > 0;
        return $has;
    }

    public function indexOfRegistration($aRegistration)
    {
        $wasFound = false;
        $index = 0;
        foreach ($this->registrations as $registration) {
            if ($registration->equals($aRegistration)) {
                $wasFound = true;
                break;
            }
            $index += 1;
        }
        $index = $wasFound ? $index : -1;
        return $index;
    }

    public function getParticipant_index($index)
    {
        $aParticipant = $this->participants[$index];
        return $aParticipant;
    }

    public function getParticipants()
    {
        $newParticipants = $this->participants;
        return $newParticipants;
    }

    public function numberOfParticipants()
    {
        $number = count($this->participants);
        return $number;
    }

    public function hasParticipants()
    {
        $has = $this->numberOfParticipants() > 0;
        return $has;
    }

    public function indexOfParticipant($aParticipant)
    {
        $wasFound = false;
        $index = 0;
        foreach ($this->participants as $participant) {
            if ($participant->equals($aParticipant)) {
                $wasFound = true;
                break;
            }
            $index += 1;
        }
        $index = $wasFound ? $index : -1;
        return $index;
    }

    public function getEvent_index($index)
    {
        $aEvent = $this->events[$index];
        return $aEvent;
    }

    public function getEvents()
    {
        $newEvents = $this->events;
        return $newEvents;
    }

    public function numberOfEvents()
    {
        $number = count($this->events);
        return $number;
    }

    public function hasEvents()
    {
        $has = $this->numberOfEvents() > 0;
        return $has;
    }

    public function indexOfEvent($aEvent)
    {
        $wasFound = false;
        $index = 0;
        foreach ($this->events as $event) {
            if ($event->equals($aEvent)) {
                $wasFound = true;
                break;
            }
            $index += 1;
        }
        $index = $wasFound ? $index : -1;
        return $index;
    }

    public static function minimumNumberOfRegistrations()
    {
        return 0;
    }

    public function addRegistration($aRegistration)
    {
        $wasAdded = false;
        if ($this->indexOfRegistration($aRegistration) !== -1) {
            return false;
        }
        $this->registrations[] = $aRegistration;
        $wasAdded = true;
        return $wasAdded;
    }

    public function removeRegistration($aRegistration)
    {
        $wasRemoved = false;
        if ($this->indexOfRegistration($aRegistration) != -1) {
            unset($this->registrations[$this->indexOfRegistration($aRegistration)]);
            $this->registrations = array_values($this->registrations);
            $wasRemoved = true;
        }
        return $wasRemoved;
    }

    public function addRegistrationAt($aRegistration, $index)
    {
        $wasAdded = false;
        if ($this->addRegistration($aRegistration)) {
            if ($index < 0) {
                $index = 0;
            }
            if ($index > $this->numberOfRegistrations()) {
                $index = $this->numberOfRegistrations() - 1;
            }
            array_splice($this->registrations, $this->indexOfRegistration($aRegistration), 1);
            array_splice($this->registrations, $index, 0, array($aRegistration));
            $wasAdded = true;
        }
        return $wasAdded;
    }

    public function addOrMoveRegistrationAt($aRegistration, $index)
    {
        $wasAdded = false;
        if ($this->indexOfRegistration($aRegistration) !== -1) {
            if ($index < 0) {
                $index = 0;
            }
            if ($index > $this->numberOfRegistrations()) {
                $index = $this->numberOfRegistrations() - 1;
            }
            array_splice($this->registrations, $this->indexOfRegistration($aRegistration), 1);
            array_splice($this->registrations, $index, 0, array($aRegistration));
            $wasAdded = true;
        } else {
            $wasAdded = $this->addRegistrationAt($aRegistration, $index);
        }
        return $wasAdded;
    }

    public static function minimumNumberOfParticipants()
    {
        return 0;
    }

    public function addParticipant($aParticipant)
    {
        $wasAdded = false;
        if ($this->indexOfParticipant($aParticipant) !== -1) {
            return false;
        }
        $this->participants[] = $aParticipant;
        $wasAdded = true;
        return $wasAdded;
    }

    public function removeParticipant($aParticipant)
    {
        $wasRemoved = false;
        if ($this->indexOfParticipant($aParticipant) != -1) {
            unset($this->participants[$this->indexOfParticipant($aParticipant)]);
            $this->participants = array_values($this->participants);
            $wasRemoved = true;
        }
        return $wasRemoved;
    }

    public function addParticipantAt($aParticipant, $index)
    {
        $wasAdded = false;
        if ($this->addParticipant($aParticipant)) {
            if ($index < 0) {
                $index = 0;
            }
            if ($index > $this->numberOfParticipants()) {
                $index = $this->numberOfParticipants() - 1;
            }
            array_splice($this->participants, $this->indexOfParticipant($aParticipant), 1);
            array_splice($this->participants, $index, 0, array($aParticipant));
            $wasAdded = true;
        }
        return $wasAdded;
    }

    public function addOrMoveParticipantAt($aParticipant, $index)
    {
        $wasAdded = false;
        if ($this->indexOfParticipant($aParticipant) !== -1) {
            if ($index < 0) {
                $index = 0;
            }
            if ($index > $this->numberOfParticipants()) {
                $index = $this->numberOfParticipants() - 1;
            }
            array_splice($this->participants, $this->indexOfParticipant($aParticipant), 1);
            array_splice($this->participants, $index, 0, array($aParticipant));
            $wasAdded = true;
        } else {
            $wasAdded = $this->addParticipantAt($aParticipant, $index);
        }
        return $wasAdded;
    }

    public static function minimumNumberOfEvents()
    {
        return 0;
    }

    public function addEvent($aEvent)
    {
        $wasAdded = false;
        if ($this->indexOfEvent($aEvent) !== -1) {
            return false;
        }
        $this->events[] = $aEvent;
        $wasAdded = true;
        return $wasAdded;
    }

    public function removeEvent($aEvent)
    {
        $wasRemoved = false;
        if ($this->indexOfEvent($aEvent) != -1) {
            unset($this->events[$this->indexOfEvent($aEvent)]);
            $this->events = array_values($this->events);
            $wasRemoved = true;
        }
        return $wasRemoved;
    }

    public function addEventAt($aEvent, $index)
    {
        $wasAdded = false;
        if ($this->addEvent($aEvent)) {
            if ($index < 0) {
                $index = 0;
            }
            if ($index > $this->numberOfEvents()) {
                $index = $this->numberOfEvents() - 1;
            }
            array_splice($this->events, $this->indexOfEvent($aEvent), 1);
            array_splice($this->events, $index, 0, array($aEvent));
            $wasAdded = true;
        }
        return $wasAdded;
    }

    public function addOrMoveEventAt($aEvent, $index)
    {
        $wasAdded = false;
        if ($this->indexOfEvent($aEvent) !== -1) {
            if ($index < 0) {
                $index = 0;
            }
            if ($index > $this->numberOfEvents()) {
                $index = $this->numberOfEvents() - 1;
            }
            array_splice($this->events, $this->indexOfEvent($aEvent), 1);
            array_splice($this->events, $index, 0, array($aEvent));
            $wasAdded = true;
        } else {
            $wasAdded = $this->addEventAt($aEvent, $index);
        }
        return $wasAdded;
    }

    public function equals($compareTo)
    {
        return $this == $compareTo;
    }

    public function delete()
    {
        $this->registrations = array();
        $this->participants = array();
        $this->events = array();
    }

}

?>