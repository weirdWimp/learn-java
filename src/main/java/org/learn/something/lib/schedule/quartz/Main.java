package org.learn.something.lib.schedule.quartz;

import lombok.SneakyThrows;
import org.quartz.*;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author guo
 * @date 2021/5/20
 */
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class)
                .withIdentity("sayHi", "group")
                .usingJobData("name", "Jason")
                .build();

        SimpleScheduleBuilder.repeatSecondlyForever(10);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("hiTrigger", "group")
                .usingJobData("weather", "Sunny")
                .usingJobData("name", "hui")
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
                .build();

        // DirectSchedulerFactory factory = DirectSchedulerFactory.getInstance();
        // factory.createVolatileScheduler(2);

        StdSchedulerFactory factory = new StdSchedulerFactory("quartz.properties");
        factory.getScheduler().start();

        factory.getScheduler().scheduleJob(jobDetail, trigger);
    }
}
