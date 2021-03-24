package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.Range;

public class DriveSystem implements DeviceInterface {

    public DcMotor lfd = null;
    public DcMotor rfd = null;
    public DcMotor lrd = null;
    public DcMotor rrd = null;

    public void init(HardwareMap hardwareMap) {

        lfd = hardwareMap.get(DcMotor.class, "lf");
        rfd = hardwareMap.get(DcMotor.class, "rf");
        lrd = hardwareMap.get(DcMotor.class, "lr");
        rrd = hardwareMap.get(DcMotor.class, "rr");

    }

    public void loop() {

    }

    public void drive(double x, double y, double yaw) {

        lfd.setPower(Range.clip(- x - y - yaw, -1, 1));
        rfd.setPower(Range.clip(- x + y - yaw, -1, 1));
        lrd.setPower(Range.clip(+ x - y - yaw, -1, 1));
        rrd.setPower(Range.clip(+ x + y - yaw, -1, 1));

    }

}
