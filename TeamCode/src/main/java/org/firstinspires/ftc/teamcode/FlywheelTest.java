package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import org.firstinspires.ftc.teamcode.subsystems.LaunchSystem;

@TeleOp(name="Flywheel Test", group="Test")

public class FlywheelTest extends LinearOpMode {

    LaunchSystem launch = new LaunchSystem();

    @Override
    public void runOpMode() {

        launch.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            launch.launch(450);

            telemetry.addData("Velocity", "Left Velocity: " + launch.leftFlywheel.getVelocity());
            telemetry.addData("Velocity", "Right Velocity: " + launch.rightFlywheel.getVelocity());
            telemetry.update();

        }

    }

}
