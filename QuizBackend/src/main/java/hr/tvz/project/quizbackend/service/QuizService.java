package hr.tvz.project.quizbackend.service;

import hr.tvz.project.quizbackend.entity.QuestionDB;
import hr.tvz.project.quizbackend.entity.QuizDB;
import hr.tvz.project.quizbackend.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Optional<QuizDB> createQuiz(String name, List<QuestionDB> questions) {
        QuizDB quizDB = new QuizDB(name, questions);
        quizRepository.save(quizDB);
        return Optional.of(quizDB);
    }

}
