package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.utils.MecanumDrive;

public class PanicAutonomousDemo extends LinearOpMode {
    GoldAlignDetector detector;
    MecanumDrive drive = new MecanumDrive();
    LiftSystem lift = new LiftSystem();
    CollectionSystem collection = new CollectionSystem();
    TeamColor currentTeam;
    ProgramType currentProgramType;

    /* This might change, as we might combine the collection and lift arms. */
    // OpenGLMatrix whereWeAre = new OpenGLMatrix(), whereWeNeedToGo = new OpenGLMatrix();
    int FIXME = 0; // for error free builds!

    @Override
    public void runOpMode() throws InterruptedException {
        //Init drive train
        drive.init(hardwareMap);

        drive.setMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
        waitForStart(); // Wait for the start
        {
            drive.updateTarget(1, 0, 0);
            sleep(1250); //rough value
            drive.updateTarget(0, 0, 1);
            sleep(250); //rough value
            drive.updateTarget(0, 1, 0);
            sleep(4000); //rough value
            drive.updateTarget(-1,0,0);
            sleep(500); //rough value
            drive.updateTarget(1,1,1);
            drive.stop();

        }


    }
    enum TeamColor {red, blue}

    enum ProgramType {depot, crater}
}
