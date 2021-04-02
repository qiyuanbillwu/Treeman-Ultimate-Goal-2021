/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.subsystems;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TimerTask;

import static java.util.Collections.max;

/**
 * This 2020-2021 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Ultimate Goal game elements.
 * <p>
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 * <p>
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */

public class TensorFlowObjectDetection  extends DeviceInterface{
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    Queue<String> labelQueue = new LinkedList<>();

    Map<String,Integer> labelNo = new HashMap<>();

    int[] labelCount = new int[]{0,0,0};

    public int stableResult = 0;
    String currLabel = "";
    String dbgMsg = "";

    private static final String VUFORIA_KEY =
            "AT59qW//////AAABmaLSQ01XOkhzlv+8L5c2m+sDnhON44SNNOxJknTR36zZZ96pBKYDPaSj7I2K/DrewwzqNvsDtrOFUfpQrYaAUWkSDEbIwVd7jIyW9ro0m9PXMR3LJr85vNIAtpSJ2PyfTZpIne6XiNlr25B/asndCAb+W14uTREwsHwRoU9mdszsCWUNFef10DpYMOlicl2+qIjrXePG1NUOfvHXcsi5yU9pb23h3Dh/Ly5jxMDxykd1j0RlWz/vqhOE9crcBR2EUP+hf7Bew9Zvh+Y0vlg0YizRcKx9ytqcfIQq4WZWKF7vkUHc78JHTozyacgcyzd+9IzSzaui8cpjiDn2h7+xtEeioC/sNGkE5wTqEJ79gGoV";
    HardwareMap hardwareMap = null;
    Telemetry telemetry = null;

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;
    boolean isRunning = false;

    public TensorFlowObjectDetection(){
        labelNo.put(LABEL_FIRST_ELEMENT,2);
        labelNo.put(LABEL_SECOND_ELEMENT,1);
        labelNo.put("",0);

    }

    @Override
    public void init(HardwareMap hardwareMap) {
        init(hardwareMap, null);
    }

    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        initVuforia();
        initTfod();
    }

    @Override
    public void start(){
        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(2.5, 16.0 / 9.0);
        }
        scheduleTimer.scheduleAtFixedRate(new TaskScheduleDetect(),2,200);
    }


    public int detect() {
        if (tfod == null)
            return 0;
        if(isRunning)
            return 0;
        isRunning = true;

        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        if (updatedRecognitions == null)
            return 0;
        String label = "";
        for (Recognition recognition : updatedRecognitions) {
            //需要根据recognition结果中的左上、右下坐标设计算法进行检测
            label = recognition.getLabel();
            if (label.equals(LABEL_FIRST_ELEMENT) || label.equals(LABEL_SECOND_ELEMENT))
                break;
        }
        currLabel = label;
        labelQueue.offer(label);
        int no = labelNo.get(label);
        Log.i("test",""+no);
        labelCount[no] ++;
        if(labelQueue.size()>10){
            String first = labelQueue.poll();
            labelCount[labelNo.get(first)]--;
        }
        dbgMsg = String.format("%d %d %d",labelCount[0],labelCount[1],labelCount[2]);
        stableResult = labelCount[0]>labelCount[1]?0:1;
        int m = Math.max(labelCount[0],labelCount[1]);
        stableResult = m>labelCount[2]?stableResult:2;
        isRunning = false;
        return no;
    }

    class TaskScheduleDetect extends TimerTask {
        @Override
        public void run() {
            if(!isRunning)
                detect();
        }
    }

    public void stop() {
        if (tfod != null) {
            tfod.shutdown();
        }
        scheduleTimer.cancel();
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = this.hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.6f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
