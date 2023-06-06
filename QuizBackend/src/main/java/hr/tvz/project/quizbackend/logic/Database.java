package hr.tvz.project.quizbackend.logic;

import hr.tvz.project.quizbackend.entity.PlayerDB;
import hr.tvz.project.quizbackend.entity.QuestionDB;
import hr.tvz.project.quizbackend.entity.QuizDB;
import hr.tvz.project.quizbackend.repository.PlayerRepository;
import hr.tvz.project.quizbackend.repository.QuizRepository;

import java.util.ArrayList;
import java.util.List;

public class Database {

    public static void initializePlayers(PlayerRepository playerRepository) {
        playerRepository.save(new PlayerDB("štef", "lozinka"));
        playerRepository.save(new PlayerDB("luka", "lozinka"));
        playerRepository.save(new PlayerDB("ivan", "lozinka"));
    }

    public static void initializeQuizes(QuizRepository quizRepository) {

        List<QuestionDB> quizQuestions = new ArrayList<>();
//        quizQuestions.add(
//            new QuestionDB("asd")
//        );

        QuizDB quiz = new QuizDB("My first quiz!", quizQuestions);
        quizRepository.save(quiz);
    }

}
