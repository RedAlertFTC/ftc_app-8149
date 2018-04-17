package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.utils.MecanumDrive;

import static org.firstinspires.ftc.teamcode.PanicAutonomousBase.programType.far;
import static org.firstinspires.ftc.teamcode.PanicAutonomousBase.programType.near;
import static org.firstinspires.ftc.teamcode.PanicAutonomousBase.teamColor.blue;
import static org.firstinspires.ftc.teamcode.PanicAutonomousBase.teamColor.red;

public class PanicAutonomousBase extends LinearOpMode {
    static double SERVO_DEGREES = 180;
    MecanumDrive drive = new MecanumDrive();
    DcMotor liftMotor;
    LED leds;
    VuforiaLocalizer vuforia;
    Servo servo1, servo2, gemSensorArm;
    ColorSensor gemSensor;
    teamColor currentTeam, detectedBall;
    programType currentProgramType;

    @Override
    public void runOpMode() throws InterruptedException {
        drive.InitMotors(hardwareMap); // Init Drive Train

        // Other Init
        gemSensorArm = hardwareMap.servo.get("Jewel Arm Servo");
        gemSensor = hardwareMap.colorSensor.get("Jewel Sensor");
        gemSensorArm.setPosition(0 / SERVO_DEGREES);

        servo1 = hardwareMap.servo.get("Servo1");
        servo2 = hardwareMap.servo.get("Servo2");
        servo2.setDirection(Servo.Direction.REVERSE);

        liftMotor = hardwareMap.dcMotor.get("liftMotor");

        leds = hardwareMap.led.get("led");
        leds.enable(true);

//        drive.motorRightA = hardwareMap.dcMotor.get("motorRightA");
//        drive.motorRightB = hardwareMap.dcMotor.get("motorRightB");
//        drive.motorLeftA = hardwareMap.dcMotor.get("motorLeftA");
//        drive.motorLeftB = hardwareMap.dcMotor.get("motorLeftB");

        servo1.setPosition(0 / SERVO_DEGREES);
        servo2.setPosition(0 / SERVO_DEGREES);

        waitForStart(); // Wait for the start

        // Step 1a. Lower the arm.
        gemSensorArm.setPosition(165 / SERVO_DEGREES);
        sleep(1000);

        servo1.setPosition(180 / SERVO_DEGREES);
        servo2.setPosition(180 / SERVO_DEGREES);
        sleep(200);

        liftMotor.setPower(0.5);
        sleep(3000);
        liftMotor.setPower(0);
        sleep(500);
        // Step 1b. Figure out the color of the ball on the left side.
        if ((gemSensor.red() - 5) > gemSensor.blue()) {
            detectedBall = red;
        } else if ((gemSensor.blue() - 5) > gemSensor.red()) {
            detectedBall = blue;
        }
        telemetry.addData("red", gemSensor.red());
        telemetry.addData("blue", gemSensor.blue());

        telemetry.addData("detectedBall", detectedBall);
        telemetry.update();

        // Step 1c. Twist and stop!

        if (detectedBall != null) {
            if (detectedBall == currentTeam) {
                drive.update(0, 0, 0.25);
                sleep(300);
                drive.stop();
                sleep(100);
                drive.update(0, 0, -0.25);
                sleep(250);
                drive.stop();
            } else if (detectedBall != currentTeam) {
                drive.update(0, 0, -0.25);
                sleep(300);
                drive.stop();
                sleep(100);
                drive.update(0, 0, 0.25);
                sleep(250);
                drive.stop();
            }
        } else {
            // do nothing
        }
        sleep(250);
        gemSensorArm.setPosition(20 / SERVO_DEGREES);
        sleep(500);
        gemSensorArm.setPosition(0 / SERVO_DEGREES);

        if (currentTeam == red && currentProgramType == far) {
            drive.update(0, 0, 0);
        } else if (currentTeam == red && currentProgramType == near) {
            drive.update(-0.28, 0, 0);
        } else if (currentTeam == blue && currentProgramType == near) {
            drive.update(-0.23, 0, 0);
        }
        else if (currentTeam == red && currentProgramType == near) {
            drive.update(0.0, 0, 0);
        }
        sleep(1400);
        drive.stop();
        sleep(100);

        if (currentTeam == red && currentProgramType == far) {
            drive.update(0, 0.40, 0);
        } else if (currentTeam == red && currentProgramType == near) {
            drive.update(0, 0.32, 0);
        } else if (currentTeam == blue && currentProgramType == far) {
            drive.update(0, -0.38, 0);
        } else if (currentTeam == blue && currentProgramType == near) {
            drive.update(0, -0.365, 0);
        }

        sleep(2300);
        drive.stop();
        sleep(100);

        if (currentTeam == blue && currentProgramType == near) {
            drive.update(0.30, 0.0, 0.0);
            sleep(850);
            drive.stop();

        }
        else if (currentTeam == blue && currentProgramType == far) {
            drive.update(0.16, 0, 0);
            sleep(800);
            drive.stop();
        }
        else if (currentTeam == red && currentProgramType == near) {
            drive.update(0.30, 0, 0);
            sleep(800);
            drive.stop();
        }
        else if (currentTeam == red && currentProgramType == far) {
            drive.update(0.6, 0, 0);
            sleep(800);
            drive.stop();
        }
        //Move to the center column
        if (currentProgramType == near && currentTeam == red) {
            drive.update(0.0, 0, currentTeam == red ? -0.25 : 0.25);
            sleep(5250);
            drive.stop();
        }

        if (currentProgramType == near && currentTeam == blue) {
            drive.update(0.0, 0, currentTeam == red ? -0.25 : 0.25);
            sleep(5000);
            drive.stop();
        }

        if (currentProgramType == far) {
            drive.update(0.0, 0, currentTeam == red ? -0.25 : 0.25);
            sleep(4100);
            drive.stop();
        }

        liftMotor.setPower(-0.5);
        sleep(1800);
        liftMotor.setPower(0);

        // Drive forward
        drive.update(-0.25, 0, 0);
        sleep(2000);
        drive.stop();

        // Release the block
        servo1.setPosition(0 / SERVO_DEGREES);
        servo2.setPosition(0 / SERVO_DEGREES);
        sleep(750);

        // Go backward
        drive.update(0.20, 0, 0);
        sleep(1200);
        drive.stop();
        sleep(200);
//        // Close the arms
        servo1.setPosition(180 / SERVO_DEGREES);
        servo2.setPosition(180 / SERVO_DEGREES);
        sleep(250);

        // Go and ram
        drive.update(-0.25, 0, 0);
        sleep(1600);
        drive.stop();

        // Go back again
        drive.update(0.15, 0, 0);
        sleep(750);
        drive.stop();

    }

    enum teamColor {red, blue}

    enum programType {near, far}


}
