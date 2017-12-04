package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import static android.os.SystemClock.sleep;

/**
 * Created by thebiteffect on 10/21/17.
 */

@TeleOp(name = "PanicDEBUG: Calibrate Servo", group = "PanicDEBUG")
public class ServoCalibrate extends LinearOpMode {

    Servo servoA,
            servoB;

    int servoAPos = 0, servoBPos = 0, servoDegrees = 180;
    boolean thisDpadLeftInput = false,
    thisDpadRightInput = false,
    thisDpadDownInput = false,
    thisDpadUpInput = false,
    lastDpadLeftInput = false,
    lastDpadRightInput = false,
    lastDpadDownInput = false,
    lastDpadUpInput = false;

    @Override
    public void runOpMode() throws InterruptedException {

        servoA = hardwareMap.servo.get("servoA");
        servoB = hardwareMap.servo.get("servoB");

        servoA.setPosition(0);
        servoB.setPosition(0);

        telemetry.addData("servoA", servoAPos);
        telemetry.addData("servoB", servoBPos);

        waitForStart();
            while (opModeIsActive() && !gamepad1.x) {
                thisDpadLeftInput = gamepad1.dpad_left;
                thisDpadRightInput = gamepad1.dpad_right;
                thisDpadDownInput = gamepad1.dpad_down;
                thisDpadUpInput = gamepad1.dpad_up;


                if (thisDpadLeftInput && lastDpadLeftInput) {
                    servoAPos += -1;
                    servoAPos = Range.clip(servoAPos, 0, 180);
                    servoA.setPosition((float) servoAPos / servoDegrees);
                }
                if (thisDpadRightInput && lastDpadRightInput) {
                    servoAPos += 1;
                    servoAPos = Range.clip(servoAPos, 0, 180);
                    servoA.setPosition((float) servoAPos / servoDegrees);
                }
                if (thisDpadDownInput && lastDpadDownInput) {
                    servoBPos += -1;
                    servoBPos = Range.clip(servoAPos, 0, 180);
                    servoB.setPosition((float) servoBPos / servoDegrees);
                }
                if (thisDpadUpInput && lastDpadUpInput) {
                    servoBPos += 1;
                    servoBPos = Range.clip(servoBPos, 0, 180);
                    servoB.setPosition((float) servoBPos / servoDegrees);
                }
                lastDpadLeftInput = !thisDpadLeftInput;
                lastDpadRightInput = !thisDpadRightInput;
                lastDpadUpInput = !thisDpadUpInput;
                lastDpadDownInput = !thisDpadDownInput;

                telemetry.addData("servoA", servoAPos);
                telemetry.addData("servoB", servoBPos);
                telemetry.update();
            }
        requestOpModeStop();
    }
}
