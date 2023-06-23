package hr.tvz.project.quizbackend.service;

import hr.tvz.project.quizbackend.domain.QuizResultsCollectionDTO;
import hr.tvz.project.quizbackend.entity.QuizDB;
import hr.tvz.project.quizbackend.entity.ResultDB;
import hr.tvz.project.quizbackend.repository.QuizRepository;
import hr.tvz.project.quizbackend.repository.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final ResultRepository resultRepository;

    public QuizService(
            QuizRepository quizRepository,
            ResultRepository resultRepository
    ) {
        this.quizRepository = quizRepository;
        this.resultRepository = resultRepository;
    }

    public Optional<QuizDB> getQuiz(Long id) {
        return quizRepository.findById(id);
    }

    public List<QuizDB> getAllQuizzes() {
        return quizRepository.findAll();
    }

    //public QuizResultsDTO getResults(Long id) {
    public QuizResultsCollectionDTO getResults(Long id) {
        List<ResultDB> results = resultRepository.findAllByQuizId(id);
        return new QuizResultsCollectionDTO(results);
    }

}
