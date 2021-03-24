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

            double speed = 0.4 * (gamepad1.left_trigger + gamepad1.right_trigger);
            robot.launch.launch(speed);

            if (gamepad1.left_bumper) {
                robot.intake.intake(1.0);
            } else if (gamepad1.right_bumper) {
                robot.intake.spit(1.0);
            } else {
                robot.intake.stop();
            }

            if (gamepad1.a){
                robot.launch.push();
            } else if (gamepad1.b){
                robot.launch.retract();
            }

            if (gamepad2.a){
                robot.intake.prepareKick();
            } else if (gamepad2.b){
                robot.intake.kick();
            }

            robot.drive.loop();

            telemetry.addData("gamepad 1 left stick", "x(%.2f), y(%.2f)", gamepad1.left_stick_x, gamepad1.left_stick_y);
            telemetry.addData("gamepad 1 right stick", "x(%.2f), y(%.2f)", gamepad1.right_stick_x, gamepad1.right_stick_y);
            telemetry.addData("power", "lf(%.2f)", robot.drive.leftFrontDrive.getVelocity());
            telemetry.update();
        }

    }

}
