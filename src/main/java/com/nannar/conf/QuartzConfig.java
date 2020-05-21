package com.nannar.conf;
import com.nannar.job.MakeAlarmWaveOffsetJob;
import com.nannar.job.ReadWaveOffsetFileJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Date;
/**
 * @author yuhui_cai
 */
@PropertySource({"classpath:schedule-cron.properties"})
@Configuration
public class QuartzConfig {
    private final Scheduler scheduler;
    @Autowired
    public QuartzConfig(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
    @Value("${schedule.read.wave.offset.file.job.cron}")
    private String readWaveOffsetFileCron;
    @Value("${schedule.read.wave.offset.file.job.name}")
    private String readWaveOffsetFileJob;
    @Value("${schedule.read.wave.offset.file.job.group}")
    private String readWaveOffsetFileGroup;
    @Value("${schedule.make.alarm.wave.offset.cron}")
    private String makeAlarmWaveOffsetCorn;
    @Value("${schedule.make.alarm.wave.offset.name}")
    private String makeAlarmWaveOffsetJob;
    @Value("${schedule.make.alarm.wave.offset.group}")
    private String makeAlarmWaveOffsetGroup;


    /**
     * 初始注入scheduler
     * @return
     * @throws SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException{
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler();
    }

    /**
     * 开始执行所有任务
     *
     * @throws SchedulerException
     */
    public void startJob() throws SchedulerException {
        readWaveOffsetFileJob(scheduler);
        makeAlarmWaveOffsetJob(scheduler);
        scheduler.start();
    }

    private void readWaveOffsetFileJob(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail1 = JobBuilder.newJob(ReadWaveOffsetFileJob.class).withIdentity(readWaveOffsetFileJob, readWaveOffsetFileGroup).build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(readWaveOffsetFileCron);
        CronTrigger cronTrigger1 = TriggerBuilder.newTrigger().withIdentity(readWaveOffsetFileJob, readWaveOffsetFileGroup).withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail1, cronTrigger1);
    }
    private void makeAlarmWaveOffsetJob(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail2 = JobBuilder.newJob(MakeAlarmWaveOffsetJob.class).withIdentity(makeAlarmWaveOffsetJob, makeAlarmWaveOffsetGroup).build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(makeAlarmWaveOffsetCorn);
        CronTrigger cronTrigger2 = TriggerBuilder.newTrigger().withIdentity(makeAlarmWaveOffsetJob, makeAlarmWaveOffsetGroup).withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail2, cronTrigger2);
    }

    /**
     * 修改某个任务的执行时间
     * 时间修改后立即生效，spring task定时任务做不到
     * @param name
     * @param group
     * @param time
     * @return
     * @throws SchedulerException
     */
    public boolean modifyJob(String name, String group, String time) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(time)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                    .withSchedule(cronScheduleBuilder).build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }

    /**
     * 获取Job信息
     *
     * @param name
     * @param group
     * @return
     * @throws SchedulerException
     */
    public String getJobInfo(String name, String group) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time:%s,state:%s", cronTrigger.getCronExpression(), scheduler.getTriggerState(triggerKey).name());
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void pauseJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复所有任务
     *
     * @throws SchedulerException
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void resumeJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void deleteJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.deleteJob(jobKey);
    }
}
