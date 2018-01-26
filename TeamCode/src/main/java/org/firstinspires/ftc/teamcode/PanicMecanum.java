package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Panic: Mecanum Test", group = "PanicDEBUG")
public class PanicMecanum extends PanicTestFieldOrientated {
    @Override
    public void init() {
        super.init();
        fieldOrient = false;
    }

    @Override
    public void loop() {
        super.loop();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
