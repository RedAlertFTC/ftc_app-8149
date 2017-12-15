package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.utils.MecanumDrive;
import org.firstinspires.ftc.teamcode.utils.VuMarkForia;

public class PanicAutonomousBase extends LinearOpMode {
    static int SERVO_DEGREES = 180;
    MecanumDrive drive = new MecanumDrive();
    VuMarkForia vuforia = new VuMarkForia(hardwareMap, "AdlfowT/////AAAAGUpRoaEvyUfNtuTDeKMo6qEf2Y8oPuvPan17xUGgdDWoYKTx+JNrzPv2tPPmKMQcyOw9MNnOeGDXCPFCDOOKjsUTjil2cGK9odRVmSWL0xsxdsxtbz9Y3ZW2q1fi9IJsBofvfxfTa/6t9JDldr1+6lcBi9izU2k0ZC9Md6S8DHkcvQ7Q7P9NRepmQZXU+ztVWxB9gNHJ1128u3zADXS+pIkW9qIUHfc6UybysSNpmeh65VxdFRu2Tnlwh3fqAB+NjG9eMmgP49FyW3C3wnkwMMCVqT4JBdhRPviRHyp7lXXtzVqr/BB30ww0hk0W7gyiANIbVvQq/04i18SFFuS5H9zX2v0g5J3ViTvfUybve/AA");
    Servo gemSensorArm;
    ColorSensor gemSensor;
    teamColor currentTeam, detectedBall;
    programType currentProgramType;
    RelicRecoveryVuMark left = RelicRecoveryVuMark.UNKNOWN;

    @Override
    public void runOpMode() throws InterruptedException {
        drive.InitMotors(hardwareMap); // Init Drive Train

        // Other Init
        gemSensorArm = hardwareMap.servo.get("Gem Arm Servo");
        gemSensor = hardwareMap.colorSensor.get("Gem Sensor");
        gemSensorArm.setPosition(20 / SERVO_DEGREES);

//        drive.motorRightA = hardwareMap.dcMotor.get("motorRightA");
//        drive.motorRightB = hardwareMap.dcMotor.get("motorRightB");
//        drive.motorLeftA = hardwareMap.dcMotor.get("motorLeftA");
//        drive.motorLeftB = hardwareMap.dcMotor.get("motorLeftB");

        // Show the viewforia camera!

        vuforia.init();

        waitForStart(); // Wait for the start

        // Step 1a. Lower the arm.
        gemSensorArm.setPosition(154 / SERVO_DEGREES);
        sleep(2000);

        // Step 1b. Figure out the color of the ball on the left side.
        if (gemSensor.red() >= 100) {
            detectedBall = teamColor.red;
        } else if (gemSensor.blue() >= 100) {
            detectedBall = teamColor.blue;
        }

        // Step 1c. Twist and stop!

        if (detectedBall == currentTeam) {
            drive.update(0, 0, -0.5);
            sleep(1000);
            drive.stop();
        } else if (detectedBall != currentTeam) {
            drive.update(0, 0, 0.5);
            sleep(1000);
            drive.stop();
        }

        // Step 2a. Find the VuMark
        do {
            drive.update(0, 0, 0.1); // TODO: Test
        } while (vuforia.getCurrentVuMark() == RelicRecoveryVuMark.UNKNOWN);
        drive.stop();

        // Step 2b. Record VuMark


    }

    enum teamColor {red, blue}

    enum programType {near, far}


}
