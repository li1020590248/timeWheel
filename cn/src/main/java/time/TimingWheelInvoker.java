package time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import time.wheel.IWheel;
import time.wheel.Wheel;

public class TimingWheelInvoker {

    private static Logger logger = LoggerFactory.getLogger(TimingWheelInvoker.class);
    public static void main(String[] args) {
        try {
            IWheel iWheel = new Wheel(10,-1,0);
        } catch (Exception e) {
            logger.error("",e);
        }
    }
}
