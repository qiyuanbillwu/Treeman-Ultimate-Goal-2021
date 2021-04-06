package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Timer;
import java.util.TimerTask;

public class Arm extends DeviceInterface {

    public DcMotorEx arm = null;
    Servo claw = null;

    Timer timer = new Timer();


    public void init(HardwareMap hardwareMap) {
        arm = hardwareMap.get(DcMotorEx.class, "arm");
        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        arm.setDirection(DcMotorEx.Direction.REVERSE);
        arm.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        claw = hardwareMap.get(Servo.class, "claw");
        claw.setPosition(1.0);
    }

    public void rotate(double power){
        arm.setPower(power);
    }

    public void open(){
        claw.setPosition(0.0);
    }

    public void close(){
        claw.setPosition(0.9);
    }

    public void loop() {

    }

    public void stop() {
        arm.setVelocity(0);
    }

}
