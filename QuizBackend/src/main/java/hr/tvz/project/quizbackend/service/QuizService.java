package hr.tvz.project.quizbackend.service;

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

    public Optional<QuizDB> getQuiz(Long id) {
        return quizRepository.findById(id);
    }

    public List<QuizDB> getAllQuizzes() {
        return quizRepository.findAll();
    }
}
