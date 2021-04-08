package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

import java.util.Timer;
import java.util.TimerTask;


/***
 *
 */
public class MecanumDrive {

    public DcMotorEx leftFrontDrive = null;
    public DcMotorEx leftRearDrive = null;
    public DcMotorEx rightFrontDrive = null;
    public DcMotorEx rightRearDrive = null;
    public HardwareMap hardwareMap = null;

    private double turnAngleThres = 20;
    private double goAngleThres = 5;

    private double leftFrontPower=0.0;
    private double leftRearPower=0.0;
    private double rightFrontPower=0.0;
    private double rightRearPower=0.0;

    Timer timer = new Timer();

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 560 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 100 / 25.4 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;


    public MecanumDrive() {

    }


    public void init(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        leftFrontDrive= hardwareMap.get(DcMotorEx.class,"lf");
        leftRearDrive= hardwareMap.get(DcMotorEx.class,"lr");
        rightFrontDrive= hardwareMap.get(DcMotorEx.class,"rf");
        rightRearDrive= hardwareMap.get(DcMotorEx.class,"rr");

        leftFrontDrive.setDirection(DcMotorEx.Direction.REVERSE);
        leftRearDrive.setDirection(DcMotorEx.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorEx.Direction.FORWARD);
        rightRearDrive.setDirection(DcMotorEx.Direction.FORWARD);

    }

    public void initEncoder(){
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRearDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int lfTarget;
        int lrTarget;
        int rfTarget;
        int rrTarget;

        // Determine new target position, and pass to motor controller
        lfTarget = leftFrontDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
        lrTarget = leftRearDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
        rfTarget = rightFrontDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
        rrTarget = rightRearDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
        leftFrontDrive.setTargetPosition(lfTarget);
        leftRearDrive.setTargetPosition(lrTarget);
        rightFrontDrive.setTargetPosition(rfTarget);
        rightRearDrive.setTargetPosition(rrTarget);

        // Turn On RUN_TO_POSITION
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        leftFrontDrive.setPower(Math.abs(speed));
        leftRearDrive.setPower(Math.abs(speed));
        rightFrontDrive.setPower(Math.abs(speed));
        rightRearDrive.setPower(Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.
        while ((runtime.seconds() < timeoutS) &&
                (leftFrontDrive.isBusy() && leftRearDrive.isBusy() && rightFrontDrive.isBusy() && rightRearDrive.isBusy())) {

        }

        // Stop all motion;
        leftFrontDrive.setPower(0);
        leftRearDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightRearDrive.setPower(0);

        // Turn off RUN_TO_POSITION
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //  sleep(250);   // optional pause after each move
    }

    public void drive(double y, double x, double rotation, double timeInSecs) {
        drive(y,x,rotation);
        timer.schedule(new TaskStop(), (long) (timeInSecs*1000));
    }

//    public void initMotor("")
    class TaskStop extends TimerTask {

        @Override
        public void run() {
            stop();
        }
    }

    /***
     *
     * @param y 前进后退;大于0向前，小于0向后；
     * @param x 左右平移；大于0向右，小于0向左；
     * @param rotation 左右旋转；大于0右转，小于0左转；
     */
    public void drive(double y, double x, double rotation) {

        // 计算四个轮子的速度值
        leftFrontPower = y + x + rotation;
        leftRearPower = y - x + rotation;
        rightFrontPower = y - x - rotation;
        rightRearPower = y + x - rotation;

        // 速度数值归一化。  将他们按比例收缩到-1到1之间。
        double maxMagnitude = Math.abs(leftFrontPower);
        maxMagnitude = (Math.max(maxMagnitude, Math.abs(leftRearPower)));
        maxMagnitude = (Math.max(maxMagnitude, Math.abs(rightFrontPower)));
        maxMagnitude = (Math.max(maxMagnitude, Math.abs(rightRearPower)));

        if (maxMagnitude > 1.0) {
            leftFrontPower = leftFrontPower/maxMagnitude;
            leftRearPower = leftRearPower/maxMagnitude;
            rightFrontPower = rightFrontPower/maxMagnitude;
            rightRearPower = rightRearPower/maxMagnitude;
        }

        rightFrontDrive.setPower(rightFrontPower);
        leftFrontDrive.setPower(leftFrontPower);
        rightRearDrive.setPower(rightRearPower);
        leftRearDrive.setPower(leftRearPower);

    }

    /***
     * 直行。
     * @param speed 速度；
     */
    public void go(double speed) {
        drive(speed, 0, 0);
    }

    /**
     * 按指定角度直行。
     *
     * @param speed        速度；
     * @param targetAngle  目标角度；
     * @param currentAngle 当前角度；
     */
    public boolean go(double speed, double targetAngle, double currentAngle) {
        if (Math.abs(speed) < 0.001) {
            drive(0, 0, 0);
            return true;
        }

        speed = Range.clip(speed, -1, 1);
        if (Math.abs(speed) > 0.9)
            speed *= 0.9;

        double angle = currentAngle - targetAngle;
        angle = Range.clip(angle, -goAngleThres, goAngleThres);
        double angleDelta = (angle / goAngleThres) * 0.10;

        drive(speed, 0, angleDelta);
        return false;
    }

    /**
     * 转弯
     *
     * @param speed 速度；
     */
    public void turn(double speed) {
        drive(0, 0, speed);
    }

    /**
     * 转弯到指定角度
     *
     * @param speed        速度；
     * @param targetAngle  目标角度；
     * @param currentAngle 当前角度；
     */
    public boolean turn(double speed, double targetAngle, double currentAngle) {
        if (Math.abs(speed) < 0.001) {
            drive(0, 0, 0);
            return true;
        }
        double angleError = Range.clip((currentAngle-targetAngle), -turnAngleThres, turnAngleThres) / turnAngleThres;
        drive(0, 0, Math.abs(speed)  * angleError);
        return Math.abs(targetAngle - currentAngle) < 1;
    }

    /**
     * 平移
     *
     * @param speed 速度；
     */
    public void drift(double speed) {
        drive(0, speed, 0);
    }

    /**
     * 平移；
     *
     * @param speed        速度；
     * @param targetAngle  目标角度；
     * @param currentAngle 当前角度；
     */
    public boolean drift(double speed, double targetAngle, double currentAngle) {
        if (Math.abs(speed) < 0.001) {
            drive(0, 0, 0);
            return true;
        }
        speed = Range.clip(speed, -1, 1);
        if (Math.abs(speed) > 0.8)
            speed *= 0.8;

        double angleErr = currentAngle-targetAngle;
        angleErr = Range.clip(angleErr, -goAngleThres, goAngleThres);
        double angleDelta = (angleErr / goAngleThres) * 0.1;

        drive(0, speed, angleDelta);
        return false;
    }

    public void stop() {
        drive(0, 0, 0);
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void goEncoder(double speed,
                             int leftFrontDistance, int leftRearDistance,
                             int rightFrontDistance, int rightRearDistance ) {

        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition()+leftFrontDistance);
        leftRearDrive.setTargetPosition(leftRearDrive.getCurrentPosition()+leftFrontDistance);
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition()+leftFrontDistance);
        rightRearDrive.setTargetPosition(rightRearDrive.getCurrentPosition()+leftFrontDistance);

            // Turn On RUN_TO_POSITION
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRearDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }

    public void initDriveMode(){
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRearDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public void loop(){
        rightFrontDrive.setPower(rightFrontPower);
        leftFrontDrive.setPower(leftFrontPower);
        rightRearDrive.setPower(leftRearPower);
        leftRearDrive.setPower(rightRearPower);
    }

    @Override
    public String toString(){

        StringBuilder builder = new StringBuilder();
        builder.append("driver_power_rf="+rightFrontPower);
        builder.append("driver_power_lf="+leftFrontPower);
        builder.append("driver_power_lr="+leftRearPower);
        builder.append("driver_power_rr="+rightRearPower);
        return builder.toString();
    }
}
