package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by thebiteffect on 11/27/17.
 */

@Autonomous(name = "Panic: Blue Two", group = "Panic")
public class PanicAutonomousBluTwo extends PanicAutonomousBase {
    @Override
    public void runOpMode() throws InterruptedException {
        currentTeam = TeamColor.blue;
        currentProgramType = ProgramType.far;
        super.runOpMode();
    }
}
