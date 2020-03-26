package time.wheel;

public class TimingWheelTest {
    public static void main(String[] args) {
//        TimingWheelUtils.addTask(TimingWheelUtils.DEFAULT_WHEEL_KEY,new TaskForMe(3,2));

        IWheel iWheel = TimingWheelUtils.getWheels().get(TimingWheelUtils.DEFAULT_WHEEL_KEY);
        iWheel.addTask(new TaskForMeTwo(2,1));
        iWheel.addTask(new TaskForMe(3,1));
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            TimingWheelUtils.destory(TimingWheelUtils.DEFAULT_WHEEL_KEY);
        }
    }
}
