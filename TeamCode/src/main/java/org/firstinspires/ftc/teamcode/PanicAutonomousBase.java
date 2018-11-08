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
        lift.extend();
        sleep(6750);
        drive.update(0,1, 0);
    }

    enum TeamColor {red, blue}

    enum ProgramType {near, far}


}
