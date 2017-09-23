package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "PanicDEBUG: Rev Test", group = "PanicDEBUG")
public class PanicTestRev extends OpMode {

    DcMotor motorLeft;
    DcMotor motorRight;

    @Override
    public void init() {
        motorLeft = hardwareMap.dcMotor.get("Left Motor");
        motorRight = hardwareMap.dcMotor.get("Right Motor");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorRight.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            motorRight.setPower(1);
            motorLeft.setPower(1);
        } else if (gamepad1.x) {
            motorRight.setPower(-1);
            motorLeft.setPower(-1);
        }

    }

    @Override
    public void stop() {
        System.gc();
        super.stop();
    }
}
