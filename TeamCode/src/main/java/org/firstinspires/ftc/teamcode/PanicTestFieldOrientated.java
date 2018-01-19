package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by thebiteffect on 9/23/17.
 */


@TeleOp(name = "Panic: Field Orientated", group = "PanicDEBUG")
public class PanicTestFieldOrientated extends OpMode {

    final long ARM_MOVE_TIME_MS = 1000;

    final double SERVO_DEGREES = 180;
    DcMotor motorLeftA,
            motorLeftB,
            motorRightA,
            motorRightB,

    liftMotor,
    relicArm;
    double temp, gyro,
            x,
            y;
    float powerRightA,
            powerRightB,
            powerLeftA,
            powerLeftB,

    velocityDrive,
            strafeDrive,
            rotationDrive;
    BNO055IMU imu;
    boolean lastAInput = false, thisAInput = false;
    boolean lastYInput = false, thisYInput = false;
    boolean gyroModeXP = false;
    Servo servo1, servo2, jewelSensorArm;
    CRServo relicGrabber;
    Orientation angles;

    boolean fieldOrient = true;

    @Override
    public void init() {

        motorRightA = hardwareMap.dcMotor.get("motorRightA");
        motorRightB = hardwareMap.dcMotor.get("motorRightB");
        motorLeftA = hardwareMap.dcMotor.get("motorLeftA");
        motorLeftB = hardwareMap.dcMotor.get("motorLeftB");

        liftMotor = hardwareMap.dcMotor.get("liftMotor");

        motorRightA.setDirection(DcMotor.Direction.REVERSE);
        motorRightB.setDirection(DcMotor.Direction.REVERSE);
        motorLeftA.setDirection(DcMotor.Direction.FORWARD);
        motorLeftB.setDirection(DcMotor.Direction.FORWARD);

        motorRightA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRightB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLeftA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLeftB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        powerRightA = 0;
        powerRightB = 0;
        powerLeftA = 0;
        powerLeftB = 0;

        servo1 = hardwareMap.servo.get("Servo1");
        servo2 = hardwareMap.servo.get("Servo2");
        servo2.setDirection(Servo.Direction.REVERSE);

        jewelSensorArm = hardwareMap.servo.get("Jewel Arm Servo");

        relicArm = hardwareMap.dcMotor.get("Relic Arm");
        relicArm.setDirection(DcMotor.Direction.REVERSE);
        relicGrabber = hardwareMap.crservo.get("Relic Grabber");
        relicGrabber.setDirection(CRServo.Direction.REVERSE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        //gyro.calibrate();


    }

    @Override
    public void loop() {
        thisAInput = gamepad2.a;
        thisYInput = gamepad2.y;


        liftMotor.setPower(-gamepad2.left_stick_y * 0.75);

        if (thisAInput && !lastAInput) {
            servo1.setPosition(180 / SERVO_DEGREES);
            servo2.setPosition(180 / SERVO_DEGREES);
        } else if (thisAInput ^ lastAInput) { // ^ is an XOR (or eXclusive-OR) for booleans. Very useful!
            servo1.setPosition(0 / SERVO_DEGREES);
            servo2.setPosition(0 / SERVO_DEGREES);
        }

        if (gamepad2.right_bumper) {
            relicArm.setPower((gamepad2.left_bumper ? -gamepad2.right_trigger : gamepad2.right_trigger) * 0.25); // Basically this is an integrated
        }

        if (gamepad2.dpad_up) {
            relicGrabber.setPower(1);

        } else if (gamepad2.dpad_down) {
            relicGrabber.setPower(-1);
        }

        if (thisYInput && !lastYInput) {
            if (jewelSensorArm.getPosition() == 0) {
                jewelSensorArm.setPosition(0.8);
            } else {
                jewelSensorArm.setPosition(0);
            }
        }
        lastAInput = thisAInput;
        lastYInput = thisYInput;

        velocityDrive = gamepad1.left_stick_y * 0.75f;
        strafeDrive = -gamepad1.left_stick_x * 0.75f;
        rotationDrive = -gamepad1.right_stick_x * 0.75f;


        x = strafeDrive;
        y = velocityDrive;
        gyro = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;

        telemetry.addData("x", x);
        telemetry.addData("y", y);
        telemetry.addData("gyro", gyro);
        telemetry.addData("velocityDrive", velocityDrive);
        telemetry.addData("strafeDrive", strafeDrive);
        telemetry.addData("rotationDrive", rotationDrive);

        if (fieldOrient) {
            temp = y * Math.toDegrees(Math.cos(Math.toRadians(gyro)) + x * Math.toDegrees(Math.sin(Math.toRadians(gyro))));
            x = -y * Math.toDegrees(Math.sin(Math.toRadians(gyro)) + x * Math.toDegrees(Math.cos(Math.toRadians(gyro))));
            y = temp;
        }


        strafeDrive = (float) x;
        velocityDrive = (float) y;

        powerRightA = velocityDrive + rotationDrive + strafeDrive;
        powerRightB = velocityDrive + rotationDrive - strafeDrive;
        powerLeftA = velocityDrive - rotationDrive - strafeDrive;
        powerLeftB = velocityDrive - rotationDrive + strafeDrive;

        powerRightA = Range.clip(powerRightA, -1f, 1f);
        powerRightB = Range.clip(powerRightB, -1f, 1f);
        powerLeftA = Range.clip(powerLeftA, -1f, 1f);
        powerLeftB = Range.clip(powerLeftB, -1f, 1f);

        motorRightA.setPower(powerRightA * (1 - ((double) gamepad1.left_trigger * 0.5)));
        motorRightB.setPower(powerRightB * (1 - ((double) gamepad1.left_trigger * 0.5)));
        motorLeftA.setPower(powerLeftA * (1 - ((double) gamepad1.left_trigger * 0.5)));
        motorLeftB.setPower(powerLeftB * (1 - ((double) gamepad1.left_trigger * 0.5)));
        telemetry.addData("left_trigger", gamepad1.left_trigger);
    }

    @Override
    public void stop() {
        System.gc();
        super.stop();
    }
}
