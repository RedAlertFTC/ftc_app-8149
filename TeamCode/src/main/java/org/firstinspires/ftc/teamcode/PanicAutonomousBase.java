package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.MecanumDrive;

public class PanicAutonomousBase extends LinearOpMode {
    MecanumDrive drive = new MecanumDrive();
    TeamColor currentTeam;
    ProgramType currentProgramType;
    // OpenGLMatrix whereWeAre = new OpenGLMatrix(), whereWeNeedToGo = new OpenGLMatrix();
    /* This might change, as we might combine the collection and lift arms. */
    LiftSystem lift = new LiftSystem();

    @Override
    public void runOpMode() throws InterruptedException {
        drive.InitMotors(hardwareMap); // Init drive train
        lift.initMotor(hardwareMap);
        waitForStart(); // Wait for the start

        /* Landing */
        lift.extend();
        sleep(6750);
        drive.update(0, 0,25, 0);
        sleep(100); /* FIXME: probably way off */
        drive.stop();

        /* Claim: go to the depot. */
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
        /* From now on we probably won't need to check currentProgramType any
         * more because we will be in the same place now: at the depot. */

        /* Claim: drop the marker. */

        /* Now sampling.  Ideally we vould be able to pass flags at invocation
         * time to specify what sampling we are doing depending on what the
         * other team does, but is seems that Java runtime does not support
         * that in any convenient way. */

        /* Sampling 1: drive to the first sampling place. */

        /* Sampling 1: go until we find the gold mineral. */

        /* Sampling 1: do something to the mineral.  Will we pick it up or just
         * bump it? */

        /* Sampling 2: drive to the second sampling place. */

        /* Sampling 2: go until we find the gold mineral */

        /* Sampling 2: do something to the mineral. */

        /* If we picked up a mineral, maybe we could put it in the depot or
         * the cargo hold. */

        /* Is that all? */
    }

    enum TeamColor {red, blue}

    enum ProgramType {depot, crater}


}
