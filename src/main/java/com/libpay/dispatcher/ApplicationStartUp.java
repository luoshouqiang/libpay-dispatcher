package com.libpay.dispatcher;

import com.libpay.dispatcher.entity.JobDetails;
import com.libpay.dispatcher.repository.JobDetailsRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ApplicationStartUp implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    Scheduler scheduler;
    @Autowired
    JobDetailsRepository jobDetailsRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            addJob(scheduler,event.getApplicationContext());

        }  catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void addJob(Scheduler scheduler, ApplicationContext context) throws Exception  {
        List<JobDetails> jobDetailsList= jobDetailsRepository.findByEnable(true);
        for(JobDetails jobDetails:jobDetailsList){

           Job job=(Job) context.getBean(jobDetails.getJobClass());
            JobDetail jobDetail= JobBuilder.newJob( job.getClass()).withIdentity(jobDetails.getJobName()).build();



            //表达式调度构建器(即任务执行的时间,不立即执行)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobDetails.getCron()).withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobDetails.getJobName())
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

}
