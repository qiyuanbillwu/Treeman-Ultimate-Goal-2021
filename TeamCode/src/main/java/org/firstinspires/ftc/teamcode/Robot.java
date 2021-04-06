package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.subsystems.*;
import java.util.*;

public class Robot {

    public MecanumDrive drive;
    public LaunchSystem launch;
    public Navigation navigation;
    public IntakeSystem intake;
    public Arm arm;
    public TensorFlowObjectDetection detector;

    ArrayList<DeviceInterface> devices = new ArrayList<>();

    public Robot() {

        drive = new MecanumDrive();
        launch = new LaunchSystem();
        navigation = new Navigation();
        intake = new IntakeSystem();
        arm = new Arm();
        detector = new TensorFlowObjectDetection();

    }

    public void init(HardwareMap hardwareMap) {

        drive.init(hardwareMap);
        launch.init(hardwareMap);
        navigation.init(hardwareMap);
        intake.init(hardwareMap);
        arm.init(hardwareMap);
        detector.init(hardwareMap);

    }

    public void stop(){
        drive.stop();
        launch.stop();
        intake.stop();
        arm.stop();
        detector.stop();

    }

}
