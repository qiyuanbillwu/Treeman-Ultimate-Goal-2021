package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Timer;
import java.util.TimerTask;

public class IntakeSystem implements DeviceInterface {

    public DcMotorEx collector = null;

    Timer timer = new Timer();


    public void init(HardwareMap hardwareMap) {
        collector = hardwareMap.get(DcMotorEx.class, "intake");
        collector.setDirection(DcMotorEx.Direction.FORWARD);
        collector.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void loop() {

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
        collector.setVelocity(speed);
    }

    public void stop() {
        collector.setVelocity(0);
    }

}
