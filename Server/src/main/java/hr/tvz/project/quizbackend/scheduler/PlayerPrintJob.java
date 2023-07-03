package hr.tvz.project.quizbackend.scheduler;

import hr.tvz.project.quizbackend.entity.PlayerDB;
import hr.tvz.project.quizbackend.repository.PlayerRepository;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.HashSet;
import java.util.Set;

public class PlayerPrintJob extends QuartzJobBean {
    private Logger log = LoggerFactory.getLogger(PlayerPrintJob.class);
    private final PlayerRepository playerRepository;
    public PlayerPrintJob(PlayerRepository playerRepository){this.playerRepository=playerRepository;}

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        final Set<PlayerDB> playerDBSet = new HashSet<PlayerDB>(playerRepository.findAll());

        if(!playerDBSet.isEmpty()){
            log.info("Lista korisnika u sistemu");
            log.info("-------------------------");
            playerDBSet.forEach(playerDB -> log.info(playerDB.toString()));
            log.info("-------------------------");
        } else {
            log.info("Nema korisničkih računa u sistemu");
        }
    }
}
