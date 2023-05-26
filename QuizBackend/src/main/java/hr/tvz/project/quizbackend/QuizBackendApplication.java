package hr.tvz.project.quizbackend;

import hr.tvz.project.quizbackend.logic.Database;
import hr.tvz.project.quizbackend.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QuizBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(PlayerRepository playerRepository)
	{
		return (args -> {

			System.out.println("Generating players...");
			Database.InitializePlayers(playerRepository);
			playerRepository.findAll().stream().forEach(player -> {
				System.out.println("\t Generated " + player.toString());
			});

		});
	}

}
