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

            double y   = 1 *  -gamepad1.left_stick_y; //前进
            double x   = 0.9 *  gamepad1.left_stick_x; //平移
            double yaw = 0.9 *  gamepad1.right_stick_x; //转弯

            robot.drive.drive(y, x, yaw); //

            double armPower = 0.8 * -gamepad2.right_stick_y;
            robot.arm.rotate(armPower);

            if (gamepad2.dpad_left){
                robot.arm.open();
            } else if (gamepad2.dpad_right){
                robot.arm.close();
            }

            double speed = 0.0;

            if (gamepad2.left_bumper || gamepad1.left_trigger > 0){
                speed = 1.1;
            } else if (gamepad2.right_bumper){
                speed = 1.15;
            } else if (gamepad2.left_trigger > 0 || gamepad1.right_trigger > 0){
                speed = 1.5;
            } else if (gamepad2.right_trigger > 0){
                speed = 2.5;
            }

            robot.launch.launch(speed * 360);

            if (gamepad1.left_bumper) {
                robot.intake.intake(10.0 * 360);
            } else if (gamepad1.right_bumper) {
                robot.intake.spit(10.0 * 360);
            } else {
                robot.intake.stop();
            }

            if (gamepad1.a){
                robot.launch.push();
            }

            if (gamepad1.b){
                robot.launch.retract();
            }

            /*
            if (gamepad2.a){
                robot.intake.prepareKick();
            } else if (gamepad2.b){
                robot.intake.kick();
            }

             */

            if (gamepad2.b){
                robot.intake.kick();
            }

            //telemetry.addData("gamepad 1 left stick", "x(%.2f), y(%.2f)", gamepad1.left_stick_x, gamepad1.left_stick_y);
            //telemetry.addData("gamepad 1 right stick", "yaw(%.2f), y(%.2f)", gamepad1.right_stick_x, gamepad1.right_stick_y);
            //telemetry.addData("power", "lf(%.2f), lr(%.2f), rf(%.2f), rr(%.2f)", robot.drive.leftFrontDrive.getVelocity(), robot.drive.leftRearDrive.getVelocity(), robot.drive.rightFrontDrive.getVelocity(), robot.drive.rightRearDrive.getVelocity());
            //telemetry.addData("left bumper", gamepad1.left_bumper);
            //telemetry.addData("left trigger", gamepad1.left_trigger);
            telemetry.addData("left flywheel velocity: ", robot.launch.leftFlywheel.getVelocity());
            telemetry.addData("right flywheel velocity: ", robot.launch.rightFlywheel.getVelocity());
             telemetry.update();
        }

    }

}
