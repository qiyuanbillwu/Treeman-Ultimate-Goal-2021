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

            double x   = 0.5 *   gamepad1.left_stick_x; //平移
            double y   = 0.5 *  -gamepad1.left_stick_y; //前进
            double yaw = 0.5 *  gamepad1.right_stick_x; //转弯

            robot.drive.drive(x, y, yaw);

            double speed = 1.1 * (gamepad1.left_trigger + gamepad1.right_trigger);
            robot.launch.launch(speed * 360);

            if (gamepad1.left_bumper) {
                robot.intake.intake(1.0 * 360);
            } else if (gamepad1.right_bumper) {
                robot.intake.spit(1.0 * 360);
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
            robot.launch.loop();
            robot.intake.loop();

            //telemetry.addData("gamepad 1 left stick", "x(%.2f), y(%.2f)", gamepad1.left_stick_x, gamepad1.left_stick_y);
            //telemetry.addData("gamepad 1 right stick", "yaw(%.2f), y(%.2f)", gamepad1.right_stick_x, gamepad1.right_stick_y);
            //telemetry.addData("power", "lf(%.2f), lr(%.2f), rf(%.2f), rr(%.2f)", robot.drive.leftFrontDrive.getVelocity(), robot.drive.leftRearDrive.getVelocity(), robot.drive.rightFrontDrive.getVelocity(), robot.drive.rightRearDrive.getVelocity());
            telemetry.addData("left bumper", gamepad1.left_bumper);
            telemetry.addData("left trigger", gamepad1.left_trigger);
            telemetry.update();
        }

    }

}
