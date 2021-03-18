package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.subsystems.*;
import java.util.*;

public class Robot {

    public MecanumDrive drive;
    public LaunchSystem launch;
    public ObjectDetector detector;
    public Navigation navigation;
    public IntakeSystem intake;

    ArrayList<DeviceInterface> devices = new ArrayList<>();

    public Robot() {

        drive = new MecanumDrive();
        launch = new LaunchSystem();
        detector = new ObjectDetector();
        navigation = new Navigation();
        intake = new IntakeSystem();

    }

    public void init(HardwareMap hardwareMap) {

        drive.init(hardwareMap);
        launch.init(hardwareMap);
        detector.init(hardwareMap);
        navigation.init(hardwareMap);
        intake.init(hardwareMap);

    }

}
