package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.utils.MecanumDrive;

/**
 * Created by thebiteffect on 9/23/17.
 */


@TeleOp(name = "Panic: Drive!™", group = "Panic")
public class PanicTestFieldOrientated extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    double temp, gyro,
            x,
            y;
    float velocityDrive,
            strafeDrive,
            rotationDrive;
    BNO055IMU imu;

    boolean lastYInput = false;

    boolean fieldOrient = false;

    @Override
    public void init() {
        drive.InitMotors(hardwareMap);
    }

    @Override
    public void loop() {


        velocityDrive = gamepad1.left_stick_y * 0.75f;
        strafeDrive = -gamepad1.left_stick_x * 0.75f;
        rotationDrive = gamepad1.right_stick_x * 0.75f;

        x = strafeDrive;
        y = velocityDrive;
	    /* So we don't have a gyro, so... no. */
        /*gyro = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;*/
	 
        telemetry.addData("x", x);
        telemetry.addData("y", y);
        /*telemetry.addData("gyro", gyro);*/
        telemetry.addData("velocityDrive", velocityDrive);
        telemetry.addData("strafeDrive", strafeDrive);
        telemetry.addData("rotationDrive", rotationDrive);

	    /*
        if (fieldOrient) {
            temp = y * Math.toDegrees(Math.cos(Math.toRadians(gyro)) + x * Math.toDegrees(Math.sin(Math.toRadians(gyro))));
            x = -y * Math.toDegrees(Math.sin(Math.toRadians(gyro)) + x * Math.toDegrees(Math.cos(Math.toRadians(gyro))));
            y = temp;
        }

        strafeDrive = (float) x;
        velocityDrive = (float) y;
        */

        drive.update(
                velocityDrive * (1 - ((double) gamepad1.left_trigger * 0.7)),
                strafeDrive * (1 - ((double) gamepad1.left_trigger * 0.7)),
                rotationDrive * (1 - ((double) gamepad1.left_trigger * 0.7))
        );

        telemetry.addData("left_trigger", gamepad1.left_trigger);
    }

    @Override
    public void stop() {
        System.gc();
        super.stop();
    }
}
