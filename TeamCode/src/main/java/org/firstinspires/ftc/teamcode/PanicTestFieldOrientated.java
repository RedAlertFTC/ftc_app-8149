package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static android.os.SystemClock.sleep;

/**
 * Created by thebiteffect on 9/23/17.
 */

@TeleOp(name = "Panic: Field Orientated", group = "PanicDEBUG")
public class PanicTestFieldOrientated extends OpMode {

    DcMotor motorLeftA,
            motorLeftB,
            motorRightA,
            motorRightB;

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

    boolean lastYInput = false;
    boolean thisYInput = false;
    boolean gyroModeXP = false;

    boolean lastDpadLeftInput = false;
    boolean thisDpadLeftInput = false;

    boolean lastDpadRightInput = false;
    boolean thisDpadRightInput = false;

    CRServo servo1, servo2;

    final long ARM_MOVE_TIME_MS = 100;

    Orientation angles;

    boolean fieldOrient = true;

    @Override
    public void init() {

        motorRightA = hardwareMap.dcMotor.get("motorRightA");
        motorRightB = hardwareMap.dcMotor.get("motorRightB");
        motorLeftA = hardwareMap.dcMotor.get("motorLeftA");
        motorLeftB = hardwareMap.dcMotor.get("motorLeftB");

        motorRightA.setDirection(DcMotor.Direction.REVERSE);
        motorRightB.setDirection(DcMotor.Direction.REVERSE);
        motorLeftA.setDirection(DcMotor.Direction.FORWARD);
        motorLeftB.setDirection(DcMotor.Direction.FORWARD);

        motorRightA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRightB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLeftA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLeftB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        powerRightA = 0;
        powerRightB = 0;
        powerLeftA = 0;
        powerLeftB = 0;

        servo1 = hardwareMap.crservo.get("Servo1");
        servo2 = hardwareMap.crservo.get("Servo2");
        servo2.setDirection(DcMotor.Direction.REVERSE);


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        //gyro.calibrate();

    }

    @Override
    public void loop() {

        if (thisDpadLeftInput && lastDpadLeftInput) {
            servo1.setPower(1);
            servo2.setPower(1);
            sleep(ARM_MOVE_TIME_MS);
            servo1.setPower(0);
            servo2.setPower(0);
        }
        if (thisDpadRightInput /*&& lastDpadRightInput*/) {
            servo1.setPower(-1);
            servo2.setPower(-1);
            sleep(ARM_MOVE_TIME_MS);
            // servo.setPower(0);
        } else { // FIXME: 10/5/17 remove stuff
            servo1.setPower(0);
            servo2.setPower(0);
        }
        lastDpadLeftInput = !thisDpadLeftInput;
        lastDpadRightInput = !thisDpadRightInput;

        velocityDrive = gamepad1.left_stick_y;
        strafeDrive = -gamepad1.left_stick_x;
        rotationDrive = -gamepad1.right_stick_x;

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
            temp = y * Math.toDegrees(Math.cos(Math.toRadians(gyro)) + x * Math.sin(Math.toRadians(gyro)));
            x = -y * Math.toDegrees(Math.sin(Math.toRadians(gyro)) + x * Math.cos(Math.toRadians(gyro)));
            y = temp;
        }

        strafeDrive = (float) x;
        velocityDrive = (float) y;
        
        powerRightA = velocityDrive + rotationDrive + strafeDrive;
        powerRightB = velocityDrive + rotationDrive - strafeDrive;
        powerLeftA = velocityDrive - rotationDrive - strafeDrive;
        powerLeftB = velocityDrive - rotationDrive + strafeDrive;

        motorRightA.setPower(powerRightA);
        motorRightB.setPower(powerRightB);
        motorLeftA.setPower(powerLeftA);
        motorLeftB.setPower(powerLeftB);

        if (gamepad1.left_stick_x == 0 || gamepad1.left_stick_y == 0 || gamepad1.right_stick_x == 0) {
            powerRightA = Range.clip(powerRightA, -1, 1);
            powerRightB = Range.clip(powerRightB, -1, 1);
            powerLeftA = Range.clip(powerLeftA, -1, 1);
            powerLeftB = Range.clip(powerLeftB, -1, 1);
        } else {
            powerRightA = Range.clip(powerRightA, -0.5f, 0.5f);
            powerRightB = Range.clip(powerRightB, -0.5f, 0.5f);
            powerLeftA = Range.clip(powerLeftA, -0.5f, 0.5f);
            powerLeftB = Range.clip(powerLeftB, -0.5f, 0.5f);
        }

    }

    @Override
    public void stop() {
        System.gc();
        super.stop();
    }
}
