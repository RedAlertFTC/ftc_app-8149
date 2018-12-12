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
    LiftSystem lift = new LiftSystem();
    double temp, gyro,
            x,
            y;
    float velocityDrive,
            strafeDrive,
            rotationDrive;
    BNO055IMU imu;

    boolean invertControls = false;

    boolean lastYInput = false;

    boolean fieldOrient = false;

    @Override
    public void init() {
        drive.init(hardwareMap);
        lift.init(hardwareMap);

        /* Why does the gyro have to be so complicated? */
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    @Override
    public void loop() {
        if (lastYInput != gamepad1.y && !lastYInput) {
            invertControls ^= true;
        }

        velocityDrive = invertControls ? -gamepad1.left_stick_y : gamepad1.left_stick_y * 0.75f;
        strafeDrive = invertControls ? gamepad1.left_stick_x : -gamepad1.left_stick_x * 0.75f;
        rotationDrive = -gamepad1.right_stick_x * 0.75f;

        x = strafeDrive;
        y = velocityDrive;

        /* So we don't have a gyro, so... no. */
        /* Actually we do, it's in the hub... */
        gyro = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;

        telemetry.addData("x", x);
        telemetry.addData("y", y);
        telemetry.addData("gyro", gyro);
        telemetry.addData("velocityDrive", velocityDrive);
        telemetry.addData("strafeDrive", strafeDrive);
        telemetry.addData("rotationDrive", rotationDrive);

	    /* Add the end of a block comment next time... */
        if (fieldOrient) {
            temp = y * Math.toDegrees(Math.cos(Math.toRadians(gyro)) + x * Math.toDegrees(Math.sin(Math.toRadians(gyro))));
            x = -y * Math.toDegrees(Math.sin(Math.toRadians(gyro)) + x * Math.toDegrees(Math.cos(Math.toRadians(gyro))));
            y = temp;
        }

        strafeDrive = (float) x;
        velocityDrive = (float) y;

        drive.update(
                velocityDrive * (1 - ((double) gamepad1.left_trigger * 0.7)),
                strafeDrive * (1 - ((double) gamepad1.left_trigger * 0.7)),
                rotationDrive * (1 - ((double) gamepad1.left_trigger * 0.7))
        );

        if (gamepad2.a) {
            lift.extend();
        } else {
            lift.retract();
        }

        telemetry.addData("left_trigger", gamepad1.left_trigger);
    }

    @Override
    public void stop() {
        System.gc();
        super.stop();
    }
}
