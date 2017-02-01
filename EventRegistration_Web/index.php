<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Event Registration</title>
    <style>
        .error {
            color: #FF0000;
        }
    </style>
</head>
<body>
<?php
require_once 'model/Participant.php';
require_once 'model/Event.php';
require_once 'model/RegistrationManager.php';
require_once 'persistence/PersistenceEventRegistration.php';

session_save_path("temp"); //Save session to local temp folder
session_start();

?>

<h1>Create Participant</h1>
<form action="addParticipant.php/" method="POST">
    <p>New Participant Name: <input type="text" name="participant_name" placeholder="Your Name"/>
        <span class="error">
			<?php
            if (isset($_SESSION['errorParticipantName']) && !empty($_SESSION['errorParticipantName'])) {
                echo " * " . $_SESSION["errorParticipantName"];
            }
            ?>
			</span></p>
    <p><input type="submit" value="Add Participant"/></p>
</form>

<h1>Create Event</h1>
<form action="addEvent.php" method="POST">
    <p>Event Name: <input type="text" name="event_name"/>
        <span class="error">
			<?php
            if (isset($_SESSION['errorAddEventName']) && !empty($_SESSION['errorAddEventName'])) {
                echo " * " . $_SESSION["errorAddEventName"];
            }
            ?>
			</span></p>
    <p>Event Date: <input type="date" name="event_date" value="<?php echo date('Y-m-d'); ?>"/>
        <span class="error">
			<?php
            if (isset($_SESSION['errorAddEventDate']) && !empty($_SESSION['errorAddEventDate'])) {
                echo " * " . $_SESSION["errorAddEventDate"];
            }
            ?>
			</span></p>
    <p>Event Start time: <input type="time" name="start_time" value="<?php echo date('H:i'); ?>"/></p>
    <p>Event End Time: <input type="time" name="end_time" value="<?php echo date('H:i'); ?>"/>
        <span class="error">
			<?php
            if (isset($_SESSION['errorAddEventTime']) && !empty($_SESSION['errorAddEventTime'])) {
                echo " * " . $_SESSION["errorAddEventTime"];
            }
            ?>
			</span></p>
    <p><input type="submit" value="Add Event"></p>
</form>

<h1>Register For Event</h1>
<form action="register.php/" method="POST">
    <p>Participant: <select name='participantSpinner'>
            <?php
            // Retrieve data
            $pm = new PersistenceEventRegistration();
            $rm = $pm->loadDataFromStore();

            foreach ($rm->getParticipants() as $participant) {
                echo "<option>" . $participant->getName() . "</option>";
            }
            ?>
        </select>
        <span class='error'>
            <?php
            if (isset($_SESSION['errorRegisterParticipant']) && !empty($_SESSION['errorRegisterParticipant'])) {
                echo " * " . $_SESSION["errorRegisterParticipant"];
            }
            ?>
        </span>
    </p>

    <p>Event: <select name='eventSpinner'>
            <?php

            // Retrieve data
            $pm = new PersistenceEventRegistration();
            $rm = $pm->loadDataFromStore();

            foreach ($rm->getEvents() as $event) {
                echo "<option>" . $event->getName() . "</option>";
            }
            ?>
        </select>
        <span class='error'>
            <?php
            if (isset($_SESSION['errorRegisterEvent']) && !empty($_SESSION['errorRegisterEvent'])) {
                echo " * " . $_SESSION["errorRegisterEvent"];
            }
            ?>
        </span>
    </p>
    <p><input type='submit' value='Register'>

</form>

</body>
</html>