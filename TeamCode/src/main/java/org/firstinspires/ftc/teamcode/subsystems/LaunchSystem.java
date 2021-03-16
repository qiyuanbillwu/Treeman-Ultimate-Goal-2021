package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.*;

public class LaunchSystem implements DeviceInterface {

    public DcMotorEx leftFlywheel = null;
    public DcMotorEx rightFlywheel = null;

    public void init(HardwareMap hardwareMap) {

        leftFlywheel = hardwareMap.get(DcMotorEx.class, "left");
        rightFlywheel = hardwareMap.get(DcMotorEx.class, "right");

        leftFlywheel.setDirection(DcMotorEx.Direction.FORWARD);
        rightFlywheel.setDirection(DcMotorEx.Direction.REVERSE);

        leftFlywheel.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightFlywheel.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

    }

    public void loop() {

    }

    public void launch(double speed) {
        leftFlywheel.setVelocity(speed);
        rightFlywheel.setVelocity(speed);
    }

    public void stop() {
        leftFlywheel.setVelocity(0);
        rightFlywheel.setVelocity(0);
    }

}
