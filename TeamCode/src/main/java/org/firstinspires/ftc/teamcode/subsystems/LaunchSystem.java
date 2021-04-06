package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.*;

import java.util.Timer;
import java.util.TimerTask;

public class LaunchSystem extends DeviceInterface {

    public DcMotorEx leftFlywheel = null;
    public DcMotorEx rightFlywheel = null;
    Servo pusher = null;
    Timer timer = new Timer();

    public void init(HardwareMap hardwareMap) {

        leftFlywheel = hardwareMap.get(DcMotorEx.class, "left");
        rightFlywheel = hardwareMap.get(DcMotorEx.class, "right");

        leftFlywheel.setDirection(DcMotorEx.Direction.FORWARD);
        rightFlywheel.setDirection(DcMotorEx.Direction.REVERSE);

        leftFlywheel.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightFlywheel.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        leftFlywheel.setVelocityPIDFCoefficients(6,1,0,0);
        rightFlywheel.setVelocityPIDFCoefficients(6,1,0,0);

        pusher = hardwareMap.get(Servo.class, "pusher");
        pusher.setPosition(0.0);
    }

    public void loop() {

    }

    public void launch(double speed) {
        leftFlywheel.setVelocity(speed);
        rightFlywheel.setVelocity(speed);
    }

    public void push(){
        pusher.setPosition(1.0);
        timer.schedule(new TaskRetract(),500);
    }

    public void retract(){
        pusher.setPosition(0.0);
    }

    class TaskRetract extends TimerTask{

        @Override
        public void run() {
            retract();
        }
    }

    public void stop() {
        leftFlywheel.setVelocity(0);
        rightFlywheel.setVelocity(0);
    }

}
