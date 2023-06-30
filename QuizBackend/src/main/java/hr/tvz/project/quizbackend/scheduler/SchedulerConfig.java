package hr.tvz.project.quizbackend.scheduler;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {
    private static final String PLAYER_PRINT_JOB_IDENTITY = "playerPrintJob";
    private static final String PLAYER_PRINT_TRIGGER = "playerPrintTrigger";

    @Bean
    public JobDetail playerPrintJobDetail(){
        return JobBuilder.newJob(PlayerPrintJob.class).withIdentity(PLAYER_PRINT_JOB_IDENTITY).storeDurably().build();
    }

    @Bean
    public SimpleTrigger playerPrintTrigger(){
        SimpleScheduleBuilder scheduler = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5).repeatForever();

        return TriggerBuilder.newTrigger().forJob(playerPrintJobDetail())
                .withIdentity(PLAYER_PRINT_TRIGGER).withSchedule(scheduler).build();
    }
}
