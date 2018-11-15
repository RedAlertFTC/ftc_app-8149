package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.teamcode.utils.MecanumDrive;

public class PanicAutonomousBase extends LinearOpMode {
    MecanumDrive drive = new MecanumDrive();
    TeamColor currentTeam;
    ProgramType currentProgramType;
    OpenGLMatrix whereWeAre = new OpenGLMatrix(), whereWeNeedToGo = new OpenGLMatrix();
LiftSystem lift = new LiftSystem();
    private MecanumDrive drive1;
    private MecanumDrive mecanumDrive;

    @Override
    public void runOpMode() throws InterruptedException {
        drive.InitMotors(hardwareMap); // Init drive train
        lift.initMotor(hardwareMap);
        waitForStart(); // Wait for the start

        /* Landing */
        lift.extend();
        sleep(6750);
        drive.update(0,1, 0);
        sleep(100); /* FIXME: probably way off */
        drive.stop();

        /* Claim */
        /* I have no idea what the numbers should be so I'm commenting it all
        out.  It seem like .3 is a typical drive speed, but I don't know.
        if (currentProgramType == ProgramType.depot) {
            drive.update(-0.3, 0, 0);
            sleep(FIXME);
            Do we need to turn around?
            drive.stop();
        } else {
            drive.update(-0.3, 0, 0);
            sleep(FIXME);
            drive.update(0, 0, 0.3);
            sleep(FIXME);
            drive.update(0.3, 0, 0);
            sleep(FIXME);
            Do we need to turn around?
            drive.stop();
        }
        And we don't have this system built yet, so we can't do anything here.
         */
    }

    enum TeamColor {red, blue}

    enum ProgramType {depot, crater}


}
