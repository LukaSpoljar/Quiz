package hr.tvz.project.quizbackend.service;

import hr.tvz.project.quizbackend.domain.*;
import hr.tvz.project.quizbackend.entity.*;
import hr.tvz.project.quizbackend.repository.CategoryRepository;
import hr.tvz.project.quizbackend.repository.PlayerRepository;
import hr.tvz.project.quizbackend.repository.QuizRepository;
import hr.tvz.project.quizbackend.repository.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final PlayerRepository playerRepository;
    private final CategoryRepository categoryRepository;

    public QuizService(
            QuizRepository quizRepository,
            PlayerRepository playerRepository,
            CategoryRepository categoryRepository
    ) {
        this.quizRepository = quizRepository;
        this.playerRepository = playerRepository;
        this.categoryRepository = categoryRepository;
    }

    public Optional<QuizDB> getQuiz(Long id) {
        return quizRepository.findById(id);
    }

    public List<QuizDB> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public List<CategoryDB> getCategories() { return categoryRepository.findAll(); }

    public CreateQuizResponse createQuiz(CreateQuizForm form) {

        Optional<PlayerDB> author = playerRepository.findById(form.getAuthorId());
        if (author.isEmpty()) {
            return CreateQuizResponse.invalid("AUTHOR_NOT_FOUND");
        }
        // TODO
        if (true) {
            return CreateQuizResponse.invalid("NOT_IMPLEMENTED_YET");
        }

        QuizDB quizDB = new QuizDB(
                // form.getName()
        );
        quizRepository.save(quizDB);



        QuizDTO quizDTO = new QuizDTO(quizDB);
        return CreateQuizResponse.valid(quizDTO);
    }

}
