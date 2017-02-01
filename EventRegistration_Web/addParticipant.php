<?php
require_once 'controller/Controller.php';
session_save_path("temp"); //Save to local temp folder
session_start();
$c = new Controller();

try {
    $c->createParticipant($_POST['participant_name']);
    $_SESSION["errorParticipantName"] = "";
} catch (Exception $e) {
    $_SESSION["errorParticipantName"] = $e->getMessage();
}
?>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="refresh" content="0; url=/EventRegistration_Web/"/>
</head>
</html>