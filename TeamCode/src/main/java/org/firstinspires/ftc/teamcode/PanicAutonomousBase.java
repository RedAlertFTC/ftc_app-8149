package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.teamcode.utils.MecanumDrive;

public class PanicAutonomousBase extends LinearOpMode {
    MecanumDrive drive = new MecanumDrive();
    TeamColor currentTeam;
    ProgramType currentProgramType;
    OpenGLMatrix whereWeAre = new OpenGLMatrix(), whereWeNeedToGo = new OpenGLMatrix();

    @Override
    public void runOpMode() throws InterruptedException {
        drive.InitMotors(hardwareMap); // Init drive train
        waitForStart(); // Wait for the start
    }

    enum TeamColor {red, blue}

    enum ProgramType {near, far}


}
