<?php
/*PLEASE DO NOT EDIT THIS CODE*/

/*This code was generated using the UMPLE 1.25.0-7a2d34d modeling language!*/

class Registration
{

    //------------------------
    // STATIC VARIABLES
    //------------------------

    private static $nextId = 1;

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Autounique Attributes
    private $id;

    //Registration Associations
    private $participant;
    private $event;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public function __construct($aParticipant, $aEvent)
    {
        $this->id = self::$nextId++;
        if (!$this->setParticipant($aParticipant)) {
            throw new Exception("Unable to create Registration due to aParticipant");
        }
        if (!$this->setEvent($aEvent)) {
            throw new Exception("Unable to create Registration due to aEvent");
        }
    }

    //------------------------
    // INTERFACE
    //------------------------

    public function getId()
    {
        return $this->id;
    }

    public function getParticipant()
    {
        return $this->participant;
    }

    public function getEvent()
    {
        return $this->event;
    }

    public function setParticipant($aNewParticipant)
    {
        $wasSet = false;
        if ($aNewParticipant != null) {
            $this->participant = $aNewParticipant;
            $wasSet = true;
        }
        return $wasSet;
    }

    public function setEvent($aNewEvent)
    {
        $wasSet = false;
        if ($aNewEvent != null) {
            $this->event = $aNewEvent;
            $wasSet = true;
        }
        return $wasSet;
    }

    public function equals($compareTo)
    {
        return $this == $compareTo;
    }

    public function delete()
    {
        $this->participant = null;
        $this->event = null;
    }

}

?>