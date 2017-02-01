<?php

class PersistenceEventRegistration
{
    private $filename;

    function __construct($filename = 'data.txt')
    {
        $this->filename = $filename;
    }

    function loadDataFromStore()
    {
        if (file_exists($this->filename)) {
            $str = file_get_contents($this->filename);
            $rm = unserialize($str);
        } else {
            $rm = new RegistrationManager();
        }
        return $rm;
    }

    function writeDataToStore($rm)
    {
        $str = serialize($rm);
        file_put_contents($this->filename, $str);
    }
}

?>