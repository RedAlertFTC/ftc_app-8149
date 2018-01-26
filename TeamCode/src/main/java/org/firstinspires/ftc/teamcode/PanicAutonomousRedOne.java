package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Panic: Red One", group = "Panic")
public class PanicAutonomousRedOne extends PanicAutonomousBase {
    @Override
    public void runOpMode() throws InterruptedException {
        currentTeam = teamColor.red;
        currentProgramType = programType.near;
        super.runOpMode();
    }
}
