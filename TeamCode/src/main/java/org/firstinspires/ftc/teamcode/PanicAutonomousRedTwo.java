package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Panic: Red Two", group = "Panic")
public class PanicAutonomousRedTwo extends PanicAutonomousBase {
    @Override
    public void runOpMode() throws InterruptedException {
        currentTeam = teamColor.red;
        currentProgramType = programType.far;
        super.runOpMode();
    }
}
