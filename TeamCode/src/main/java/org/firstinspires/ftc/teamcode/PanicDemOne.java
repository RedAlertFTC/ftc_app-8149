package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by thebiteffect on 11/27/17.
 */

@Autonomous(name = "Panic: Demo 1", group = "Panic")
public class PanicDemOne extends PanicAutonomousBase {
    @Override
    public void runOpMode() throws InterruptedException {
        currentTeam = TeamColor.blue;
        currentProgramType = ProgramType.depot;
        super.runOpMode();
    }
}
