package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Timer;
import java.util.TimerTask;

public class IntakeSystem extends DeviceInterface {

    public DcMotorEx collector = null;
    Servo kicker = null;

    Timer timer = new Timer();


    public void init(HardwareMap hardwareMap) {
        collector = hardwareMap.get(DcMotorEx.class, "intake");
        collector.setDirection(DcMotorEx.Direction.FORWARD);
        collector.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        kicker = hardwareMap.get(Servo.class, "kicker");
        kicker.setPosition(0.2);
    }

    public void loop() {

    }

    public void prepareKick(){
        kicker.setPosition(0.2);
    }

    public void kick(){
        kicker.setPosition(0.7);
    }

    public void intake(double speed) {
        collector.setVelocity(speed);
    }

    public void intake(double speed, double timeInSecs) {
        intake(speed);
        timer.schedule(new TaskStop(), (long) (timeInSecs*1000));
    }

    //    public void initMotor("")
    class TaskStop extends TimerTask {
        @Override
        public void run() {
            stop();
        }
    }

    public void spit(double speed) {
        collector.setVelocity(-speed);
    }

    public void stop() {
        collector.setVelocity(0);
    }

}
