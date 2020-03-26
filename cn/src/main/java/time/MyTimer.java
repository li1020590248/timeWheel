package time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

public class MyTimer {

    private static Logger logger = LoggerFactory.getLogger(MyTimer.class);
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new TimingTask(),0,1000);
        Thread.sleep(6000);
        if(TimingTask.fiveFlag.get() > 0){
            timer.cancel();
            System.out.println("complete");

        }
        System.out.println("下来");
    }
}
