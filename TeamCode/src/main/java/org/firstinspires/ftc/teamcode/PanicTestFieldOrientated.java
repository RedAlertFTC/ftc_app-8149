package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by thebiteffect on 9/23/17.
 */

@TeleOp(name = "Panic: Field Orientated", group = "PanicDEBUG")
@Disabled
public class PanicTestFieldOrientated extends OpMode {

    DcMotor motorLeftA,
            motorLeftB,
            motorRightA,
            motorRightB;

    GyroSensor gyro;

    double temp,
            x,
            y;

    float powerRightA,
            powerRightB,
            powerLeftA,
            powerLeftB,

    velocityDrive,
            strafeDrive,
            rotationDrive;

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

        //gyro.calibrate();

    }

    @Override
    public void loop() {


        if (fieldOrient) {
            temp = y * Math.toDegrees(Math.cos(Math.toRadians(gyro.getHeading())) + x * Math.sin(Math.toRadians(gyro.getHeading())));
            x = -y * Math.toDegrees(Math.sin(Math.toRadians(gyro.getHeading())) + x * Math.cos(Math.toRadians(gyro.getHeading())));
            y = temp;
        }


        motorRightA.setPower(powerRightA);
        motorRightB.setPower(powerRightB);
        motorLeftA.setPower(powerLeftA);
        motorLeftB.setPower(powerLeftB);

        velocityDrive = -gamepad1.left_stick_y;
        strafeDrive = gamepad1.left_stick_x;
        rotationDrive = gamepad1.right_stick_x;

        powerRightA = velocityDrive + rotationDrive + strafeDrive;
        powerRightB = velocityDrive + rotationDrive - strafeDrive;
        powerLeftA = velocityDrive - rotationDrive - strafeDrive;
        powerLeftB = velocityDrive - rotationDrive + strafeDrive;


        if ((gamepad1.left_stick_y > 0 || gamepad1.left_stick_y < 0) && (gamepad1.right_stick_x > 0 || gamepad1.right_stick_x < 0)) {

            powerRightA = Range.clip(powerRightA, -0.5f, 0.5f);
            powerRightB = Range.clip(powerRightB, -0.5f, 0.5f);
            powerLeftA = Range.clip(powerLeftA, -0.5f, 0.5f);
            powerLeftB = Range.clip(powerLeftB, -0.5f, 0.5f);

        } else if ((gamepad1.left_stick_x > 0 || gamepad1.left_stick_x < 0) && (gamepad1.right_stick_x > 0 || gamepad1.right_stick_x < 0)) {

            powerRightA = Range.clip(powerRightA, -0.5f, 0.5f);
            powerRightB = Range.clip(powerRightB, -0.5f, 0.5f);
            powerLeftA = Range.clip(powerLeftA, -0.5f, 0.5f);
            powerLeftB = Range.clip(powerLeftB, -0.5f, 0.5f);

        } else {

            powerRightA = Range.clip(powerRightA, -1, 1);
            powerRightB = Range.clip(powerRightB, -1, 1);
            powerLeftA = Range.clip(powerLeftA, -1, 1);
            powerLeftB = Range.clip(powerLeftB, -1, 1);

        }

    }

    @Override
    public void stop() {
        System.gc();
        super.stop();
    }
}
