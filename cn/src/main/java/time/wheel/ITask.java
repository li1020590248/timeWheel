package time.wheel;

public abstract class ITask {
    //下一次的偏移量
    public int nextOffset ;

    //允许该任务重新执行的最大次数
    public int timeOutCount;

    public int getTimeOutCount(){
        return timeOutCount;
    }

    public void decreTimeOut(){
        timeOutCount --;
    }


    public int getNextOffset() {
        return this.nextOffset;
    }

    public boolean reachTop() {
        if(timeOutCount <= 0 ){
            return false;
        }
        return true;
    }

    /**
     * 执行任务
     */
    abstract void exec(ITask task);

}
