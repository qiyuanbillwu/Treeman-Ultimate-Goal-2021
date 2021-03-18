package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.util.*;

@TeleOp(name="Manual 8515", group="Manual")

public class OpMode_Manual extends LinearOpMode {

    Robot robot = new Robot();
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        runtime.reset();
        while (opModeIsActive()) {

            double x   = 0.5 *   gamepad1.left_stick_x;
            double y   = 0.5 *  -gamepad1.left_stick_y;
            double yaw = 0.5 *  gamepad1.right_stick_x;

            robot.drive.drive(x, y, yaw);

            telemetry.update();
        }

    }

}
