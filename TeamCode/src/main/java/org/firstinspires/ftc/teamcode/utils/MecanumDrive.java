package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by thebiteffect on 11/8/17.
 */

public class MecanumDrive {

    public DcMotor motorLeftA,
            motorRightA,
            motorLeftB,
            motorRightB;

    double powerRightA,
            powerRightB,
            powerLeftA,
            powerLeftB,

    velocityDrive,
            strafeDrive,
            rotationDrive;





    public MecanumDrive(boolean fieldOrientate, String[] mapValues) {

    }

    public MecanumDrive() {
        String[] mapValues = {"motorLeftA", "motorRightA", "motorLeftB", "motorRightB"};
        new MecanumDrive(false, mapValues);
    }

    public void InitMotors(HardwareMap hardwareMap) {
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
    }

    public void update(double velocity, double strafe, double rotation) {

        strafe = -strafe;
        rotation = -rotation;

        powerRightA = velocity + rotation + strafe;
        powerRightB = velocity + rotation - strafe;
        powerLeftA = velocity - rotation - strafe;
        powerLeftB = velocity - rotation + strafe;

        motorRightA.setPower(powerRightA);
        motorRightB.setPower(powerRightB);
        motorLeftA.setPower(powerLeftA);
        motorLeftB.setPower(powerLeftB);

    }

    public void stop() {
        motorRightA.setPower(0);
        motorRightB.setPower(0);
        motorLeftA.setPower(0);
        motorLeftB.setPower(0);
    }


}
