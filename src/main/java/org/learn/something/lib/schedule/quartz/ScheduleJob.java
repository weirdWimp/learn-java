package org.learn.something.lib.schedule.quartz;

import org.quartz.*;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author guo
 * @date 2021/5/20
 */

@PersistJobDataAfterExecution
public class ScheduleJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String name = jobExecutionContext.getJobDetail().getJobDataMap().getString("name");

        System.out.println(name);
        jobExecutionContext.getJobDetail().getJobDataMap().put("name", name + " la");
    }
}
