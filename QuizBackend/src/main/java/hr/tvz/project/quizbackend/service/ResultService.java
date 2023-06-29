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
public class ResultService {
    private final QuizRepository quizRepository;
    private final ResultRepository resultRepository;
    private final PlayerRepository playerRepository;

    public ResultService(
            QuizRepository quizRepository,
            ResultRepository resultRepository,
            PlayerRepository playerRepository
    ) {
        this.quizRepository = quizRepository;
        this.resultRepository = resultRepository;
        this.playerRepository = playerRepository;
    }

    public Optional<ResultDB> get(Long quizId, Long playerId) {
        return resultRepository.findAllByQuizIdAndPlayerId(quizId, playerId);
    }

    public QuizResultsCollectionDTO getByQuizId(Long quizId) {
        List<ResultDB> results = resultRepository.findAllByQuizId(quizId);
        return new QuizResultsCollectionDTO(results);
    }

    public ResultDB create(PlayerDB player, QuizDB quiz, int countCorrect) {
        ResultDB result = new ResultDB(player, quiz, countCorrect);
        resultRepository.save(result);
        return result;
    }

    public SolveQuizResponse solveQuiz(SolveQuizForm form) {

        // Make sure that quiz exists
        Optional<QuizDB> quiz = quizRepository.findByUuid(form.getQuizUuid());
        if (quiz.isEmpty()) {
            return SolveQuizResponse.invalid("QUIZ_NOT_FOUND");
        }

        // Make sure that player exists
        Optional<PlayerDB> player = playerRepository.findByUuid(form.getPlayerUuid());
        if (player.isEmpty()) {
            return SolveQuizResponse.invalid("PLAYER_NOT_FOUND");
        }

        // Make sure that quiz has not been solved already
        Optional<ResultDB> previousResult = get(quiz.get().getId(), player.get().getId());
        if (previousResult.isPresent()) {
            return SolveQuizResponse.invalid("QUIZ_ALREADY_SOLVED");
        }

        // Calculate number of correct answers
        List<QuestionDB> quizQuestions = quiz.get().getQuestions();
        int total = quizQuestions.size();
        int correct = (int) form.getAnswers().stream().filter(answer -> {
            Long qid = answer.getQuestionId();
            Long aid = answer.getAnswerId();
            boolean isCorrect = quizQuestions.stream().filter(quizQuestion -> {
                AnswerDB correctAnswer = quizQuestion.getAnswers()
                        .stream().filter(a -> a.isCorrect()).findFirst().get();
                if (qid == quizQuestion.getId() && aid == correctAnswer.getId()) {
                    return true;
                }
                return false;
            }).count() > 0;
            return isCorrect;
        }).count();

        ResultDB createResult = create(player.get(), quiz.get(), correct);
        QuizResultsRowDTO resultDTO = new QuizResultsRowDTO(
                createResult.getPlayer().getId(),
                createResult.getPlayer().getUsername(),
                createResult.getCorrect(),
                createResult.getTotal(),
                createResult.getSuccess()
        );

        SolveQuizResponse solveResponse = SolveQuizResponse.valid(resultDTO);
        return solveResponse;
    }

}
