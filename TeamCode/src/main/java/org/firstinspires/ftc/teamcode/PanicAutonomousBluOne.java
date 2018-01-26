package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Panic: Blue One", group = "Panic")
public class PanicAutonomousBluOne extends PanicAutonomousBase {
    @Override
    public void runOpMode() throws InterruptedException {
        currentTeam = teamColor.blue;
        currentProgramType = programType.near;
        super.runOpMode();
    }
}
