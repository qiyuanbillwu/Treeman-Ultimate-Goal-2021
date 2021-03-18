package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSystem implements DeviceInterface {

    public DcMotorEx collector = null;

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

    public void spit(double speed) {
        collector.setVelocity(speed);
    }

    public void stop() {
        collector.setVelocity(0);
    }

}
