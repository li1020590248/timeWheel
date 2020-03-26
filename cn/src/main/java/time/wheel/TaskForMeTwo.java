package time.wheel;

public class TaskForMeTwo extends ITask {

    public TaskForMeTwo(int nextOffset, int timeOutCount){
        this.nextOffset = nextOffset;
        this.timeOutCount = timeOutCount;
    }

    @Override
    public void exec(ITask task) {
        System.out.println("这里是我任务的核心了11111111");
        int i = 3/0;
    }
}
