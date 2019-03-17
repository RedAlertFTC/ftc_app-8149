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
    /* This might change, as we might combine the collection and lift arms. */
    // OpenGLMatrix whereWeAre = new OpenGLMatrix(), whereWeNeedToGo = new OpenGLMatrix();
    int FIXME = 0; // for error free builds!

    @Override
    public void runOpMode() throws InterruptedException {

    }

    enum TeamColor {red, blue}



}
