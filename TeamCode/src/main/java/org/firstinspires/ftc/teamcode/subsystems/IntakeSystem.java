package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeSystem implements DeviceInterface {

    public DcMotorEx collector = null;
    Servo kicker = null;

    public void init(HardwareMap hardwareMap) {
        collector = hardwareMap.get(DcMotorEx.class, "intake");
        collector.setDirection(DcMotorEx.Direction.FORWARD);
        collector.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        kicker = hardwareMap.get(Servo.class, "kicker");
        kicker.setPosition(0.0);
    }

    public void loop() {

    }

    public void prepareKick(){
        kicker.setPosition(0.1);
    }

    public void kick(){
        kicker.setPosition(0.6);
    }

    public void intake(double speed) {
        collector.setVelocity(speed);
    }

    public void spit(double speed) {
        collector.setVelocity(speed);
    }

    public void stop() {
        collector.setVelocity(0);
    }

}
