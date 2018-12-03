package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by thebiteffect on 11/27/17.
 */

@Autonomous(name = "Panic: Red Depot", group = "Panic")
public class PanicAutonomousRedOne extends PanicAutonomousBase {
    @Override
    public void runOpMode() throws InterruptedException {
        currentTeam = TeamColor.red;
        currentProgramType = ProgramType.depot;
        super.runOpMode();
    }
}
