package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Timer;
import java.util.TimerTask;

public class DeviceInterface {
    Timer scheduleTimer = new Timer();

    public void init(HardwareMap hardwareMap) {

    }

    public void start() {

    }

    public void loop() {

    }

    public void stop() {

    }

    //定时器任务需要继承TimerTask类
    class Task extends TimerTask {
        @Override
        public void run() {
            //todo:在此编写计时器触发的具体任务
            stop();
        }
    }
    //定时器任务需要继承TimerTask类
    class TaskStop extends TimerTask {
        @Override
        public void run() {
            //todo:在此编写计时器触发的具体任务
            stop();
        }
    }

    public void stop(long ms){
        scheduleTimer.schedule(new TaskStop(),ms);
    }

}
