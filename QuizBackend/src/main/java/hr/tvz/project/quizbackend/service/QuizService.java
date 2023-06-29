package hr.tvz.project.quizbackend.service;

import hr.tvz.project.quizbackend.domain.*;
import hr.tvz.project.quizbackend.entity.*;
import hr.tvz.project.quizbackend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final PlayerRepository playerRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuizService(
            QuizRepository quizRepository,
            PlayerRepository playerRepository,
            CategoryRepository categoryRepository,
            QuestionRepository questionRepository,
            AnswerRepository answerRepository
    ) {
        this.quizRepository = quizRepository;
        this.playerRepository = playerRepository;
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Optional<QuizDB> getQuiz(Long id) {
        return quizRepository.findById(id);
    }

    public List<QuizDB> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public List<CategoryDB> getCategories() { return categoryRepository.findAll(); }

    @Transactional
    public CreateQuizResponse createQuiz(CreateQuizForm form) {

        Optional<PlayerDB> author = playerRepository.findById(form.getAuthorId());
        if (author.isEmpty()) {
            return CreateQuizResponse.invalid("AUTHOR_NOT_FOUND");
        }

        Optional<CategoryDB> category = categoryRepository.findById(form.getCategoryId());
        if (category.isEmpty()) {
            return CreateQuizResponse.invalid("CATEGORY_NOT_FOUND");
        }

        // First add quiz with no questions
        QuizDB quizDB = new QuizDB(
                form.getName(),
                author.get(),
                category.get(),
                new ArrayList<>()
        );
        quizRepository.save(quizDB);

        // Init answers to questions
        List<AnswerDB> answers = new ArrayList<>();

        // Now add questions with no answers
        List<QuestionDB> questions = form.getQuestions().stream().map(q -> {
            QuestionDB questionDB = new QuestionDB(
                    q.getText(),
                    quizDB
            );
            // Prepare answers
            answers.addAll(
                q.getIncorrectAnswers()
                    .stream()
                    .map(ia -> new AnswerDB(ia, false, questionDB))
                    .toList()
            );
            answers.add(new AnswerDB(q.getCorrectAnswer(), true, questionDB));
            // Return question
            return questionDB;
        }).toList();
        questionRepository.saveAll(questions);

        // Now add prepared answers
        answerRepository.saveAll(answers);

        // Insert answers to questions
        questions.stream().forEach(q -> {
            q.setAnswers(answers.stream().filter(a -> a.getQuestion() == q).toList());
        });
        // Insert questions to quiz
        quizDB.setQuestions(questions);

        // Return quiz
        return CreateQuizResponse.valid(quizDB);
    }

}
