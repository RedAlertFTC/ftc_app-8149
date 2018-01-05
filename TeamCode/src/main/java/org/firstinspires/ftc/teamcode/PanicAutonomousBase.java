package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.utils.MecanumDrive;

import static org.firstinspires.ftc.teamcode.PanicAutonomousBase.programType.far;
import static org.firstinspires.ftc.teamcode.PanicAutonomousBase.programType.near;
import static org.firstinspires.ftc.teamcode.PanicAutonomousBase.teamColor.blue;
import static org.firstinspires.ftc.teamcode.PanicAutonomousBase.teamColor.red;

public class PanicAutonomousBase extends LinearOpMode {
    static double SERVO_DEGREES = 180;
    MecanumDrive drive = new MecanumDrive();
    VuforiaLocalizer vuforia;
    Servo servo1, servo2, gemSensorArm;
    ColorSensor gemSensor;
    teamColor currentTeam, detectedBall;
    programType currentProgramType;
    RelicRecoveryVuMark detectedVuMark = RelicRecoveryVuMark.UNKNOWN;

    OpenGLMatrix whereWeAre = new OpenGLMatrix(), whereWeNeedToGo = new OpenGLMatrix();

    @Override
    public void runOpMode() throws InterruptedException {
        drive.InitMotors(hardwareMap); // Init Drive Train

        // Other Init
        gemSensorArm = hardwareMap.servo.get("Jewel Arm Servo");
        gemSensor = hardwareMap.colorSensor.get("Jewel Sensor");

        servo1 = hardwareMap.servo.get("Servo1");
        servo2 = hardwareMap.servo.get("Servo2");
        servo2.setDirection(Servo.Direction.REVERSE);
        gemSensorArm.setPosition(20 / SERVO_DEGREES);

//        drive.motorRightA = hardwareMap.dcMotor.get("motorRightA");
//        drive.motorRightB = hardwareMap.dcMotor.get("motorRightB");
//        drive.motorLeftA = hardwareMap.dcMotor.get("motorLeftA");
//        drive.motorLeftB = hardwareMap.dcMotor.get("motorLeftB");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "KeyHere";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters); // Create the Vuforia instance.

        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // could possibly help in debugging
        servo1.setPosition(0 / SERVO_DEGREES);
        servo2.setPosition(0 / SERVO_DEGREES);

        waitForStart(); // Wait for the start


        // Start viewforia!
        relicTrackables.activate();

        // Step 1a. Lower the arm.
        gemSensorArm.setPosition(160 / SERVO_DEGREES);
        sleep(5000);

        // Step 1b. Figure out the color of the ball on the left side.
        if ((gemSensor.red() - 5) > gemSensor.blue()) {
            detectedBall = red;
        } else if ((gemSensor.blue() - 5) > gemSensor.red()) {
            detectedBall = blue;
        }
        telemetry.addData("red", gemSensor.red());
        telemetry.addData("blue", gemSensor.blue());

        telemetry.update();

        // Step 1c. Twist and stop!

        if (detectedBall != null) {
            if (detectedBall == currentTeam) {
                drive.update(0, 0, 0.5);
                sleep(200);
                drive.stop();
            } else if (detectedBall != currentTeam) {
                drive.update(0, 0, -0.5);
                sleep(200);
                drive.stop();
            }
        } else {
            // do nothing
        }
        sleep(1000);
        gemSensorArm.setPosition(20 / SERVO_DEGREES);
        sleep(2000);
        if (false) {
            // Step 1d. Try to get on the triangle
            if (currentTeam == red) {
                if (currentProgramType == near) {
                    drive.update(-0.05, -0.25, 0);
                    sleep(4000);
                    drive.stop();
                } else if (currentProgramType == far) {
                    drive.update(0.05, -0.25, 0);
                    sleep(4000);
                    drive.stop();
                }
            } else if (currentTeam == blue) {
                if (currentProgramType == near) {
                    sleep(4000);
                    drive.stop();
                    drive.update(-0.05, 0.25, 0);
                    sleep(4000);
                    drive.stop();
                } else if (currentProgramType == far) {
                    drive.update(0.05, 0.25, 0);
                }
            }
        }

        if (currentTeam == red) {
            drive.update(0, 0.3, 0);
        } else if (currentTeam == blue) {
            drive.update(0, -0.3, 0);
        }
        sleep(5000);
        drive.stop();

    }

    enum teamColor {red, blue}

    enum programType {near, far}


}
