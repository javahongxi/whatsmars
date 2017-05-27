package com.itlong.whatsmars.earth.support.web.job.base;

/**
 * Created by shenhongxi on 2016/7/11.
 * 1. 一个job的多个实例，谁先成功锁定任务，谁先处理任务，若处理失败则解锁任务
 * 2. 对于1中解锁失败的，要利用另外的job来专门进行解锁
 * 3. 将任务分成几批，并行处理
 * 4. 这些任务的子任务分批串行处理，同样有锁定-处理-失败解锁
 * 5. 对于4中解锁失败的，同样要利用另外的job来专门进行解锁
 */
public interface JobExecutor {

    void execute() throws Exception;
}
