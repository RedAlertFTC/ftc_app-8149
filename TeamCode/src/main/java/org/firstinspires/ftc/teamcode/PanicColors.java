package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by thebiteffect on 11/13/17.
 */

@Autonomous(name = "PanicDEBUG: COLORS!!!", group = "PanicDebug")
public class PanicColors extends OpMode {

    ColorSensor colorSensor;

    @Override
    public void init() {
     colorSensor = hardwareMap.colorSensor.get("Color Sensor");
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            colorSensor.enableLed(true);
        } else if (gamepad1.b) {
            colorSensor.enableLed(false);
        }
        telemetry.addData("A", colorSensor.alpha());
        telemetry.addData("R", colorSensor.red());
        telemetry.addData("G", colorSensor.green());
        telemetry.addData("B", colorSensor.blue());
    }
}
