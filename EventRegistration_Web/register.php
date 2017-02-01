<?php
require_once 'controller/Controller.php';
session_save_path("temp"); //Save to local temp folder
session_start();
$_SESSION["errorRegisterParticipant"] = "";
$_SESSION["errorRegisterEvent"] = "";
$c = new Controller();
try {
    $participant = NULL;
    if (isset($_POST['participantSpinner'])) {
        $participant = $_POST['participantSpinner'];
    }
    $event = NULL;
    if (isset($_POST['eventSpinner'])) {
        $event = $_POST['eventSpinner'];
    }
    $c->register($participant, $event);
} catch (Exception $e) {
    $errors = explode("@", $e->getMessage());
    foreach ($errors as $error) {
        if (substr($error, 0, 1) == "1") {
            $_SESSION["errorRegisterParticipant"] = substr($error, 1);
        }
        if (substr($error, 0, 1) == "2") {
            $_SESSION["errorRegisterEvent"] = substr($error, 1);
        }
    }
}
?>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="refresh" content="0; url=/EventRegistration_Web/"/>
</head>
</html>