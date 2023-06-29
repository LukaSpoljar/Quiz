package hr.tvz.project.quizbackend.service;

import hr.tvz.project.quizbackend.domain.QuizResultsCollectionDTO;
import hr.tvz.project.quizbackend.domain.QuizResultsRowDTO;
import hr.tvz.project.quizbackend.domain.SolveQuizForm;
import hr.tvz.project.quizbackend.domain.SolveQuizResponse;
import hr.tvz.project.quizbackend.entity.*;
import hr.tvz.project.quizbackend.repository.PlayerRepository;
import hr.tvz.project.quizbackend.repository.QuizRepository;
import hr.tvz.project.quizbackend.repository.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final ResultRepository resultRepository;
    private final PlayerRepository playerRepository;

    public QuizService(
            QuizRepository quizRepository,
            ResultRepository resultRepository,
            PlayerRepository playerRepository
    ) {
        this.quizRepository = quizRepository;
        this.resultRepository = resultRepository;
        this.playerRepository = playerRepository;
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

    // Create Quiz result. If player already solved the quiz, returns Optional.empty()
    public ResultDB createQuizResult(PlayerDB playerDB, QuizDB quizDB, int countCorrect) {
        ResultDB resultDB = new ResultDB(playerDB, quizDB, countCorrect);
        resultRepository.save(resultDB);
        return resultDB;
    }

    public SolveQuizResponse solveQuiz(SolveQuizForm solveQuizForm) {

        Optional<QuizDB> quiz = quizRepository.findByUuid(solveQuizForm.getQuizUuid());
        if (quiz.isEmpty()) {
            return SolveQuizResponse.invalid("QUIZ_NOT_FOUND");
        }
        Optional<PlayerDB> player = playerRepository.findByUuid(solveQuizForm.getPlayerUuid());
        if (player.isEmpty()) {
            return SolveQuizResponse.invalid("PLAYER_NOT_FOUND");
        }

        List<QuestionDB> quizQuestions = quiz.get().getQuestions();
        int correct = 0;
        int total = quizQuestions.size();

        // Filter sent answers that are correct and save count
        correct = (int) solveQuizForm.getAnswers().stream().filter(answer -> {
            Long questionId = answer.getQuestionId();
            Long answerId = answer.getAnswerId();

            // Iterate quiz questions
            boolean isCorrect = quizQuestions.stream().filter(q -> {
                List<AnswerDB> answ = q.getAnswers();
                long countCorrect = answ.stream().filter(a -> a.isCorrect() && a.getId() == answerId).count();

                // If correct, take it
                if (q.getId() == questionId && countCorrect > 0) {
                    return true;
                }
                return false;
            }).count() > 0;

            return isCorrect;
        }).count();


        Optional<ResultDB> previousResult = resultRepository.findAllByQuizIdAndPlayerId(quiz.get().getId(), player.get().getId());
        if (previousResult.isPresent()) {
            return SolveQuizResponse.invalid("QUIZ_ALREADY_SOLVED");
        }

        ResultDB resultDB = createQuizResult(player.get(), quiz.get(), correct);
        QuizResultsRowDTO resultDTO = new QuizResultsRowDTO(
                resultDB.getPlayer().getId(),
                resultDB.getPlayer().getUsername(),
                resultDB.getCorrect(),
                resultDB.getTotal(),
                resultDB.getSuccess()
        );

        SolveQuizResponse solveResponse = SolveQuizResponse.valid(resultDTO);
        return solveResponse;
    }

}
