package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.utils.MatrixUtils;
import org.firstinspires.ftc.teamcode.utils.MecanumDrive;

import static org.firstinspires.ftc.teamcode.PanicAutonomousBase.programType.far;
import static org.firstinspires.ftc.teamcode.PanicAutonomousBase.programType.near;
import static org.firstinspires.ftc.teamcode.PanicAutonomousBase.teamColor.blue;
import static org.firstinspires.ftc.teamcode.PanicAutonomousBase.teamColor.red;

public class PanicAutonomousBase extends LinearOpMode {
    static double SERVO_DEGREES = 180;
    MecanumDrive drive = new MecanumDrive();
    DcMotor liftMotor;
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
        gemSensorArm.setPosition(0 / SERVO_DEGREES);

        servo1 = hardwareMap.servo.get("Servo1");
        servo2 = hardwareMap.servo.get("Servo2");
        servo2.setDirection(Servo.Direction.REVERSE);

        liftMotor = hardwareMap.dcMotor.get("liftMotor");

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
        servo1.setPosition(180 / SERVO_DEGREES);
        servo2.setPosition(180 / SERVO_DEGREES);

        waitForStart(); // Wait for the start


        // Start viewforia!
        relicTrackables.activate();

        // Step 1a. Lower the arm.
        gemSensorArm.setPosition(160 / SERVO_DEGREES);
        sleep(5000);

        liftMotor.setPower(1);
        sleep(500);
        liftMotor.setPower(0);

        servo1.setPosition(0 / SERVO_DEGREES);
        servo2.setPosition(0 / SERVO_DEGREES);

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

        // Step 2a. Find the VuMark
        if ((currentProgramType == near && currentTeam == red) || (currentProgramType == far && currentTeam == blue)) {
            drive.update(0, 0, -0.3);
            sleep(5500);
            drive.stop();
            sleep(1000);
            do {
                drive.update(0, 0, -0.3); // TODO: Test
                telemetry.addData("vuMark", RelicRecoveryVuMark.from(relicTemplate));
            }
            while (RelicRecoveryVuMark.from(relicTemplate) == RelicRecoveryVuMark.UNKNOWN && opModeIsActive());
        } else if ((currentProgramType == far && currentTeam == red) || (currentProgramType == near && currentTeam == blue)) {
            drive.update(0, 0, -0.3);
            sleep(5000);
            drive.stop();
            sleep(1000);
            do {
                drive.update(0, 0, 0.3); // TODO: Test
                telemetry.addData("vuMark", RelicRecoveryVuMark.from(relicTemplate));
            }
            while (RelicRecoveryVuMark.from(relicTemplate) == RelicRecoveryVuMark.UNKNOWN && opModeIsActive());
        }
        drive.stop();

        // Step 2b. Record VuMark

        detectedVuMark = RelicRecoveryVuMark.from(relicTemplate);
        telemetry.addLine();
        telemetry.addData("detectedVuMark", detectedBall);
        telemetry.update();


        // Step 2c. Figure out where to go

        if (currentTeam == red) {
            if (currentProgramType == near) {
                whereWeNeedToGo.translate(-108.93f, -596.84f, -822.89f);
                whereWeNeedToGo.rotate(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, -4, -13, 84);
            } else if (currentProgramType == far) {
                whereWeNeedToGo.translate(-42.54f, -351.17f, -787.79f);
                whereWeNeedToGo.rotate(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 4, -2, -95);
            }
        } else if (currentTeam == blue) {
            if (currentProgramType == near) {
                whereWeNeedToGo.translate(-56.24f, -705.93f, -952.24f);
                whereWeNeedToGo.rotate(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 40, -89, -132);
            } else if (currentProgramType == far) {
                whereWeNeedToGo.translate(18.92f, 295.35f, -1135.27f);
                whereWeNeedToGo.rotate(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 4, -31, -97);
            }
        }
        double velocity = 0, strafe = 0, rotation = 0;
        whereWeAre = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
        while (MatrixUtils.isNotClose(whereWeAre, whereWeNeedToGo)) {
            whereWeAre = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
            if (whereWeAre.getTranslation().get(1) - 0.5 > whereWeNeedToGo.getTranslation().get(1)) {
                strafe = -0.2;
            } else if (whereWeAre.getTranslation().get(1) < whereWeNeedToGo.getTranslation().get(1) - 0.5) {
                strafe = 0.2;
            }
            if (whereWeAre.getTranslation().get(2) - 0.5 > whereWeNeedToGo.getTranslation().get(2)) {
                velocity = 0.2;
            } else if (whereWeAre.getTranslation().get(2) < whereWeNeedToGo.getTranslation().get(2) - 0.5) {
                velocity = -0.2;
            }
            if ((Orientation.getOrientation(whereWeAre, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).secondAngle) - 0.5 > Orientation.getOrientation(whereWeNeedToGo, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).secondAngle) {
                rotation = -0.2;
            } else if ((Orientation.getOrientation(whereWeAre, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).secondAngle) < Orientation.getOrientation(whereWeNeedToGo, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).secondAngle - 0.5) {
                rotation = 0.2;
            }
            drive.update(velocity, strafe, rotation);
        }
        drive.stop();

        /*
        if (detectedVuMark == RelicRecoveryVuMark.LEFT) {
            drive.update(0, -0.4, 0);
        } else if (detectedVuMark == RelicRecoveryVuMark.RIGHT) {
            drive.update(0, 0.4, 0);
        }
        sleep(500);
        drive.stop();

        // Drive forward
        drive.update(0.5, 0, 0);
        sleep(1000);
        drive.stop();

        // Release the block
        servo1.setPosition(180 / SERVO_DEGREES);
        servo2.setPosition(180 / SERVO_DEGREES);
        sleep(100);

        // Go backward
        drive.update(-0.5, 0, 0);
        sleep(500);
        drive.stop();

        // Close the arms
        servo1.setPosition(0 / SERVO_DEGREES);
        servo2.setPosition(0 / SERVO_DEGREES);

        // Go and ram
        drive.update(0.5, 0, 0);
        sleep(1000);

        // Go back again
        drive.update(-0.5, 0 , 0);
        sleep(500);
        drive.stop();
        */

    }

    enum teamColor {red, blue}

    enum programType {near, far}


}
