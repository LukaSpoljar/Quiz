package hr.tvz.project.quizbackend;

import hr.tvz.project.quizbackend.repository.*;
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
	public CommandLineRunner run(
			PlayerRepository playerRepository,
			CategoryRepository categoryRepository,
			QuizRepository quizRepository,
			QuestionRepository questionRepository,
			AnswerRepository answerRepository,
			ResultRepository resultRepository
			)
	{
		return (args -> {

			System.out.println("Players...");
			playerRepository.findAll().stream().forEach(player -> {
				System.out.println("\t" + player);
			});

			System.out.println("Categories...");
			categoryRepository.findAll().stream().forEach(category -> {
				System.out.println("\t" + category);
			});

			System.out.println("Quizzes...");
			quizRepository.findAll().stream().forEach(quiz -> {
				System.out.println("\t" + quiz);
			});

			System.out.println("Questions...");
			questionRepository.findAll().stream().forEach(question -> {
				System.out.println("\t" + question);
			});

			System.out.println("Answers...");
			answerRepository.findAll().stream().forEach(answer -> {
				System.out.println("\t" + answer);
			});

			System.out.println("Results...");
			resultRepository.findAll().stream().forEach(result -> {
				System.out.println("\t" + result);
			});

		});
	}

}
