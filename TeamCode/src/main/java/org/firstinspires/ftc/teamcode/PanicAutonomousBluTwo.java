package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Panic: Blue Two", group = "Panic")
public class PanicAutonomousBluTwo extends PanicAutonomousBase {
    @Override
    public void runOpMode() throws InterruptedException {
        currentTeam = teamColor.blue;
        currentProgramType = programType.far;
        super.runOpMode();
    }
}
