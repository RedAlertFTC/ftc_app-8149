package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.utils.MecanumDrive;

public class PanicAutonomousBase extends LinearOpMode {
    GoldAlignDetector detector;
    MecanumDrive drive = new MecanumDrive();
    LiftSystem lift = new LiftSystem();
    CollectionSystem collection = new CollectionSystem();
    TeamColor currentTeam;
    ProgramType currentProgramType;
    /* This might change, as we might combine the collection and lift arms. */1
    // OpenGLMatrix whereWeAre = new OpenGLMatrix(), whereWeNeedToGo = new OpenGLMatrix();
    int FIXME = 0; // for error free builds!

    @Override
    public void runOpMode() throws InterruptedException {
        // Set up detector
        detector = new GoldAlignDetector(); // Create detector-
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        // Optional tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        detector.enable(); // Start the detector!

        drive.init(hardwareMap); // Init drive train
        lift.init(hardwareMap);

        drive.setMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
        collection.init(hardwareMap);
        waitForStart(); // Wait for the start

        /* Landing */
        lift.extend();
        sleep(6750);
        drive.updateTarget(0, 0.5, 0);
        sleep(250); /* FIXME: probably way off */
        drive.stop();

        /* Claim: go to the depot. */
        /* I have no idea what the numbers should be so I'm commenting it all
        out.  It seem like .3 is a typical drive speed, but I don't know. */
        if (currentProgramType == ProgramType.depot) {
            drive.update(-0.3, 0, 0);
            sleep(FIXME);
            /*  Do we need to turn around? */
            drive.stop();

            while(opModeIsActive()) {}
        } else {
            drive.updateTarget(FIXME);
            sleep(FIXME); //Is this needed?
            //I think we need to turn like 45 degrees here
            drive.updateTarget(FIXME);
            sleep(FIXME);
            drive.updateTarget(FIXME);
            sleep(FIXME);
            /*  Do we need to turn around? */
            drive.stop();
        }
        /* And we don't have this system built yet, so we can't do anything here.
         * From now on we probably won't need to check currentProgramType any
         * more because we will be in the same place now: at the depot. */

        /* Claim: drop the marker. */

        /* Now sampling.  Ideally we would be able to pass flags at invocation
        /* This will likely need a new system in the hardware. */

        /* Now sampling.  Ideally we would be able to pass flags at invocation
         * time to specify what sampling we are doing depending on what the
         * other team does, but is seems that Java runtime does not support
         * that in any convenient way. */

        /* Sampling 1: drive to the first sampling place. */

        /* Sampling 1: go until we find the gold mineral. */
        do {
            drive.update(0, -0.3, 0);
        } while (!detector.getAligned());
0
        drive.stop();
        /* Sampling 1: do something to the mineral.  Will we pick it up or just
         * bump it? */

        /* bump.mp4 */

        /* Sampling 2: drive to the second sampling place. */
        /* We will... */
        drive.update(1, 0, 0);
        sleep(250);
        drive.stop();
        sleep(500);
        drive.update(-0.5, 0, 0);
        sleep(500);
        /* Sampling 2: drive to the second sampling place. IF we are  */

        /* Sampling 2: go until we find the gold mineral */
        do {
            drive.update(0, -0.3, 0);
        } while (!detector.getAligned());
        /* Sampling 2: do something to the mineral. */
        drive.update(1, 0, 0);
        sleep(250);
        drive.stop();
        sleep(500);
        drive.update(-0.5, 0, 0);
        sleep(500);
        /* If we picked up a mineral, maybe we could put it in the depot or
         * the cargo hold. */

        /* Is that all? */

        /*No, we might want to park halfway in the crater*/
    }

    enum TeamColor {red, blue}

    enum ProgramType {depot, crater}


}
