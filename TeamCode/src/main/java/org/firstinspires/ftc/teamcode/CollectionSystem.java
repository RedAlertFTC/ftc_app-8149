package org.firstinspires.ftc.teamcode;

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
    public DcMotor CollectionMotor;
    public String CollectionMotorName;

    /* Why make it double?  I don't know.  I felt like it. */
    /* This variable is not really used; it jus follows the speed of the motor
     * in case someone wants to look at it.  Is this necessary?
     */
    public double CollectionSpeed;

    /* FIXME: initialize this. It should be positive. Make
    * This is a constant; it would be a macro if Java had a preprocessor. */
    public double UsualSpeed;

    public CollectionSystem(String MotorName) {
        CollectionMotorName = MotorName;
    }

    public CollectionSystem() {
        new CollectionSystem("motorCollection");
        /* Should InitMotor be called here? */
    }


    /* I don't know if I'm doing this right.  I'm basically doing whatever was
     * in utils/MecanumDrive.java.
     */
    public void initMotor(HardwareMap HardwareMap) {
        /* I think that thebiteffect did this part wrong; they didn't actually
        use the strings they got from the constructor, assuming the defaults.
         */
        CollectionMotor = HardwareMap.dcMotor.get(CollectionMotorName);

        CollectionMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        CollectionMotor.setDirection(DcMotor.Direction.FORWARD);

        CollectionSpeed = 0;
    }

    public void setSpeed(double NewSpeed) {
        CollectionMotor.setPower(NewSpeed);
        CollectionSpeed = NewSpeed;
    }

    public void collect() {
        setSpeed(UsualSpeed);
    }

    public void eject() {
        setSpeed(-UsualSpeed);
    }

    public void stop() {
        setSpeed(0);
    }
}
