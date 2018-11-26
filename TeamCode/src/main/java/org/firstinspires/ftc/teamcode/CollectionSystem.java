package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/* This class, I guess, does not include the extension arm, I guess.  This is
   just the part that actually collects and uncollects stuff.  Maybe someone'll
   decide that should be done differently, though?  But, for now, this is just
   that part.
   FIXME: It's not done.
 */
public class CollectionSystem {

    /* Specify for now that this is the one that actually collects, in case we
    control multiple motors with this class. */
    /* Is this a DC Motor? Probably. */
    public CRServo CollectionServoL;
    public String CollectionServoLName;
    public CRServo CollectionServoR;
    public String CollectionServoRName;

    /* FIXME: initialize this. It should be positive. Make
     * This is a constant; it would be a macro if Java had a preprocessor. */
    public double UsualSpeed;

    /* Why make it double?  I don't know.  I felt like it. */
    /* This variable is not really used; it jus follows the speed of the motor
     * in case someone wants to look at it.  Is this necessary?
     */
    public double CollectionSpeed = UsualSpeed;

    public CollectionSystem(String MotorLName, String MotorRName) {
        CollectionServoLName = MotorLName;
        CollectionServoRName = MotorRName;
    }

    public CollectionSystem() {
        new CollectionSystem("servoCollectionL", "servoCollectionR");
        /* Should InitMotor be called here? */
    }


    /* I don't know if I'm doing this right.  I'm basically doing whatever was
     * in utils/MecanumDrive.java.
     */
    public void initMotor(HardwareMap HardwareMap) {
        /* I think that thebiteffect did this part wrong; they didn't actually
        use the strings they got from the constructor, assuming the defaults.
         */
        CollectionServoL = HardwareMap.crservo.get(CollectionServoLName);
        CollectionServoR = HardwareMap.crservo.get(CollectionServoRName);

        CollectionServoL.setDirection(DcMotor.Direction.FORWARD);
        CollectionServoR.setDirection(DcMotor.Direction.FORWARD);

        CollectionSpeed = 0;
    }

    public void setSpeed(double NewSpeed) {
        CollectionSpeed = NewSpeed;
        CollectionServoR.setPower(NewSpeed);
        CollectionServoL.setPower(NewSpeed);
    }

    public void collect() {
        setSpeed(CollectionSpeed);
    }

    public void eject() {
        setSpeed(-CollectionSpeed);
    }

    public void stop() {
        setSpeed(0);
    }
}
