package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import java.util.List;

public class ObjectDetector implements DeviceInterface {

    private static final String VUFORIA_KEY =
            "AbIs3zX/////AAABmW18z6hIVUb8v9UqVroA/sB9vPF6KSi0S3SOt0PPR23Hg85RfenmDj/G3gL1lD6u9iaYFdL3uCkLrJqm1A9gwyb8Y95GcSXpJPyGXcdmm9NjfY6Fc5RxxkfvgqZjvrKPfg69sitWE3K3GwPHVJFXEBS/7pHJvvvwx6o6m5xXBiRprwK3zMBfeAj0YJtIHj0ysmgAITz7mmTav1OvO/QsFv4MJoDM0dLm8f9d215z9qFOzQNNO/eNvPXh22i5yD3u12BA7UiGTGQBlMIerSBYo7CM8soLKU7J8PR1qAH+Bprupbx+FmPzaq6J+b2ZCMhru8aI9Wq7GZGYT7M+2qcArBOZi4XWyPMKZhOEpYIlo+Mf";

    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private VuforiaLocalizer vuforia = null;
    private TFObjectDetector tfod = null;

    private HardwareMap hardwareMap = null;

    public void init(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        initVuforia();
        initTfod();
        if (tfod != null) tfod.activate();

    }

    public void loop() {

    }

    private void initVuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

    }

    private void initTfod() {

        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(
                tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);

    }

    public String detectionResult() {

        init(hardwareMap);

        if (tfod == null) return "";

        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        if (updatedRecognitions == null) return "";

        for (Recognition recognition : updatedRecognitions) {
            String label = recognition.getLabel();
            if (label.equals(LABEL_FIRST_ELEMENT) || label.equals(LABEL_SECOND_ELEMENT)) {
                return label;
            }
        }
        return "";

    }

    public void stop() {
        if (tfod != null) tfod.shutdown();
    }

}
