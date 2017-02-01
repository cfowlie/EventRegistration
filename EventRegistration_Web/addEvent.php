<?php
require_once 'controller/Controller.php';
session_save_path("temp"); //Save to local temp folder
session_start();
$c = new Controller();

try {
    $c->createEvent($_POST["event_name"], $_POST["event_date"], $_POST["start_time"], $_POST["end_time"]);
    $_SESSION["errorRegisterEvent"] = "";
} catch (Exception $e) {
    $_SESSION["errorRegisterEvent"] = $e->getMessage();
}
?>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="refresh" content="0; url=/EventRegistration_Web/"/>
</head>
</html>