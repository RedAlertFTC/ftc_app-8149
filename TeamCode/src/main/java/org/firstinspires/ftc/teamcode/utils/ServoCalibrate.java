package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by thebiteffect on 10/21/17.
 */

@TeleOp(name = "PanicDEBUG: Calibrate Servo", group = "PanicDEBUG")
public class ServoCalibrate extends LinearOpMode {

    Servo servoA,
            servoB;

    @Override
    public void runOpMode() throws InterruptedException {

        servoA = hardwareMap.servo.get("servoA");
        servoB = hardwareMap.servo.get("servoB");

        servoA.setPosition(0);
        servoB.setPosition(0);

        waitForStart();

        requestOpModeStop();
    }
}
