package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.MecanumDrive;

/* So this is just, like, a totally hacked up copy of PanicTestFieldOrientated
to try to find the problem, because it looks like our current stuff done
goofed.
 */


@TeleOp(name = "Panic: Test Driving Algorithms", group = "Panic")
public class PanicTestDriveAlgorithms extends OpMode {
    MecanumDrive drive = new MecanumDrive();
    float velocityDrive;

    @Override
    public void init() {
        drive.InitMotors(hardwareMap);
    }

    @Override
    public void loop() {

        /* Only go forward and backwards because that's where stuff breaks. */
        velocityDrive = gamepad1.left_stick_y * 0.75f;


        telemetry.addData("velocityDrive", velocityDrive);

	    /* There was a bunch of trig here but that didn't really do anything
	    and it's not related to our current problem.
	     */

	    /* So if this doesn't work, then this is probably where the problem
	    is.
	     */
        drive.update(
                velocityDrive * (1 - ((double) gamepad1.left_trigger * 0.7)),
                0,
                0
        );
        /* We could comment that out and instead try moving the motors manually
        to see whether it is a problem with initialization or update().
        We could, like, just:
        drive.motorRightA.setPower(velocityDrive);
        drive.motorRightB.setPower(velocityDrive);
        drive.motorLeftA.setPower(velocityDrive);
        drive.motorLeftB.setPower(velocityDrive);

        And this problem is strange because both A and B on each side are
        initialized in the same way and given the same velocity, yet the
        rear wheels go the correct way and the front wheels go the wrong way.
         */

        telemetry.addData("left_trigger", gamepad1.left_trigger);
    }

    @Override
    public void stop() {
        System.gc();
        super.stop();
    }
}
