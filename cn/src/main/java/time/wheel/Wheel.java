package time.wheel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Wheel implements IWheel{

    private static Logger logger = LoggerFactory.getLogger(Wheel.class);

    //定时器
    private Timer timer;

    /** 定时频率间隔(秒)*/
    private int interval;

    //轮子
    private Set<ITask>[] wheel;

    private int wheelSize;

    /** 第一次运行隔几个偏移（新加任务隔几个偏移后执行）*/
    private int firstOffset;

    //当前偏移量
    private int currentOffset;

    public Wheel(int wheelSize, int interval, int firstOffset) throws Exception {
        if(wheelSize < 0 || interval < 0 || firstOffset < 0){
            throw new Exception("轮子大小、频率、首次间隔必须大于0");
        }
         this.interval = interval;
         this.wheelSize = wheelSize;
         this.firstOffset = firstOffset;
         this.wheel = new HashSet[wheelSize];
         for (int i = 0 ;i<wheelSize;i++){
             this.wheel[i] = new HashSet<>();
         }
         timer = new Timer();
         timer.schedule(new WheelTimerTask(),0,interval * 1000);
    }
    @Override
    public void addTask(ITask iTask) {
        int addr = (firstOffset + currentOffset + iTask.getNextOffset()) % wheelSize;
        this.wheel[addr].add(iTask);
        logger.info("添加任务完成");
    }

    @Override
    public void destoryWheel() {
        //取消轮子
        timer.cancel();
        //因为档次的timer会继续执行，所以这样处理后，会尽可能多的中断任务。
        wheel = null;
        logger.debug("注销履带");
    }

    class WheelTimerTask extends TimerTask {
        public void run() {
            Long currentTime = System.currentTimeMillis();
            int n = currentOffset;
            currentOffset++;
            currentOffset = currentOffset % wheelSize;
            if(wheel[n].size() == 0){
                return;
            }
            Set<ITask> tasks = wheel[n];
            Iterator<ITask> it = tasks.iterator();
            while(it.hasNext()){
                processTask(it.next(),currentTime,n);
                it.remove();
            }
            logger.debug("第{}个刻度执行",n);
        }
    }

    //执行任务
    private void processTask(ITask task,Long currentTime,int currentOffset){
        try {
            task.exec(task);
        }catch (Exception e){
            if(task.getTimeOutCount() > 0){
                logger.info("当前任务重试次数timeOutCount = {}",task.getTimeOutCount());
                task.decreTimeOut();
                processTask(task,currentTime,currentOffset);
            }else {
                logger.error("重试次数完毕",e);
            }
        }

    }
}
