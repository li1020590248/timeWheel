package time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class TimingTask extends TimerTask {

    public static AtomicInteger fiveFlag = new AtomicInteger(0);

    private static Logger logger = LoggerFactory.getLogger(MyTimer.class);
    @Override
    public void run() {

        logger.info("这是任务开始" + fiveFlag.get());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fiveFlag.incrementAndGet();
    }
}
