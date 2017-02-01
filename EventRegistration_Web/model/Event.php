<?php
/*PLEASE DO NOT EDIT THIS CODE*/

/*This code was generated using the UMPLE 1.25.0-7a2d34d modeling language!*/

class Event
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Event Attributes
    private $name;
    private $eventDate;
    private $startTime;
    private $endTime;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public function __construct($aName, $aEventDate, $aStartTime, $aEndTime)
    {
        $this->name = $aName;
        $this->eventDate = $aEventDate;
        $this->startTime = $aStartTime;
        $this->endTime = $aEndTime;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public function setName($aName)
    {
        $wasSet = false;
        $this->name = $aName;
        $wasSet = true;
        return $wasSet;
    }

    public function setEventDate($aEventDate)
    {
        $wasSet = false;
        $this->eventDate = $aEventDate;
        $wasSet = true;
        return $wasSet;
    }

    public function setStartTime($aStartTime)
    {
        $wasSet = false;
        $this->startTime = $aStartTime;
        $wasSet = true;
        return $wasSet;
    }

    public function setEndTime($aEndTime)
    {
        $wasSet = false;
        $this->endTime = $aEndTime;
        $wasSet = true;
        return $wasSet;
    }

    public function getName()
    {
        return $this->name;
    }

    public function getEventDate()
    {
        return $this->eventDate;
    }

    public function getStartTime()
    {
        return $this->startTime;
    }

    public function getEndTime()
    {
        return $this->endTime;
    }

    public function equals($compareTo)
    {
        return $this == $compareTo;
    }

    public function delete()
    {
    }

}

?>