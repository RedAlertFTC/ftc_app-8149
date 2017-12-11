package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by thebiteffect on 11/27/17.
 */

@Autonomous(name = "Panic: Red One", group = "Panic")
public class PanicAutonomousBluTwo extends PanicAutonomousBase {
    @Override
    public void runOpMode() throws InterruptedException {
        currentTeam = teamColor.blue;
        isFar = true;
        super.runOpMode();
    }
}
