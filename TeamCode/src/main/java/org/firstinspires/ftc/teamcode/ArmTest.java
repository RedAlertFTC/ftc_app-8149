package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.Thread.yield;

@TeleOp(name = "test")
public class ArmTest extends OpMode {
    public ArmTest() {
        super();
    }

    int enc = -1000;

    LiftSystem lift = new LiftSystem();

    @Override
    public void init() {
        lift.init(hardwareMap);
        lift.liftMotor.setDirection(DcMotor.Direction.REVERSE);
        lift.liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void init_loop() {
        super.init_loop();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        if (gamepad1.x) {
            lift.liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (gamepad1.dpad_up) {
                enc += gamepad1.right_bumper ? 10 : 1;
            }
            if (gamepad1.dpad_down) {
                enc -= gamepad1.right_bumper ? 10 : 1;
            }
            telemetry.addData("enc", enc);
            telemetry.addData("encVal", lift.liftMotor.getCurrentPosition());
            lift.setTargetPosition(enc);
            lift.setPower(1);
        }
        if (gamepad1.y) {
        }
            if (gamepad1.dpad_up) {
                lift.setPower(1);
            }
            else if (gamepad1.dpad_down) {
                lift.setPower(-1);
            }
            else {
                lift.setPower(0);
            }

    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public double getRuntime() {
        return super.getRuntime();
    }

    @Override
    public void resetStartTime() {
        super.resetStartTime();
    }

    @Override
    public void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);
    }

    @Override
    public void internalPreInit() {
        super.internalPreInit();
    }

    @Override
    public void internalPostInitLoop() {
        super.internalPostInitLoop();
    }

    @Override
    public void internalPostLoop() {
        super.internalPostLoop();
        yield();
    }
}
