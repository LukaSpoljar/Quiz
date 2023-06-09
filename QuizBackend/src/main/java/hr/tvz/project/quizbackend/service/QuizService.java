package hr.tvz.project.quizbackend.service;

import hr.tvz.project.quizbackend.entity.QuizDB;
import hr.tvz.project.quizbackend.repository.PlayerRepository;
import hr.tvz.project.quizbackend.repository.QuizRepository;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

}
