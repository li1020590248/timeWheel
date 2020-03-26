package time.wheel;

public class TaskForMe extends ITask {

    public TaskForMe(int nextOffset,int timeOutCount){
        this.nextOffset = nextOffset;
        this.timeOutCount = timeOutCount;
    }

    @Override
    public void exec(ITask task) {
        System.out.println("这里是我任务的核心了");
        int i = 3/0;
        }

}
