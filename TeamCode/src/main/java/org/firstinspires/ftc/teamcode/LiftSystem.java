package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/* This class, holds the lift system... If you want the Collection System, see CollectionSystem.java
   FIXME: It's not done.
   TODO: JavaDocs (Not doing to teach people later...)
 */
public class LiftSystem {

    /* Used to remember the min and max position */
    public final int minPos;
    public final int maxPos;
    /* Specify for now that this is the one that actually collects, in case we
    control multiple motors with this class. */
    /* This is supposedly a motor ¯\_(ツ)_/¯ */
    /* ^ Actually a vex motor*/
    public DcMotor liftMotor;
    public String liftMotorName;
    /* Used to remember what the motor speed was before turning it off */
    public double liftSpeed;

    public LiftSystem(double liftSpeed, int minimumPosition, int maximumPosition, String motorName) {
        this.liftSpeed = liftSpeed;
        this.minPos = minimumPosition;
        this.maxPos = maximumPosition;
        this.liftMotorName = motorName;
    }

    public LiftSystem(double liftSpeed, int minimumPosition, int maximumPosition) {
        this(liftSpeed, minimumPosition, maximumPosition, "motorLift");
    }

    public LiftSystem() {
        this(1, 0, 288 * 10);
    }

    public void init(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.dcMotor.get(liftMotorName);

        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    public void moveMotorToPosition(int targetPosition) {
        moveMotorToPosition(targetPosition, liftSpeed);
    }

    public void moveMotorToPosition(int targetPosition, double speed) {
        this.liftSpeed = speed;

        liftMotor.setTargetPosition(targetPosition);
        liftMotor.setPower(this.liftSpeed);
    }

    public void retract() {
        this.moveMotorToPosition(minPos);
    }

    public void extend() {
        this.moveMotorToPosition(maxPos);
    }

    public void setSpeed(double newSpeed) {
        this.liftMotor.setPower(newSpeed);
        this.liftSpeed = newSpeed;
    }

    public void stop() {
        this.setSpeed(0);
    }
}
