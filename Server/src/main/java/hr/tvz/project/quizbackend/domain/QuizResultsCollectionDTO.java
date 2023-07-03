package hr.tvz.project.quizbackend.domain;

import hr.tvz.project.quizbackend.entity.ResultDB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultsCollectionDTO {

    Long quizId;
    String quizName;
    List<QuizResultsRowDTO> scoreboard;

    public QuizResultsCollectionDTO(List<ResultDB> results) {
        if (results.size() < 1) {
            throw new Error("List<ResultDB> must contain at least one ResultDB item!");
        }
        this.quizId = results.get(0).getQuiz().getId();
        this.quizName = results.get(0).getQuiz().getName();
        this.scoreboard = results.stream().map(r -> new QuizResultsRowDTO(
                r.getPlayer().getId(),
                r.getPlayer().getUsername(),
                r.getCorrect(),
                r.getTotal(),
                r.getSuccess()
                )
        ).toList();
    }
}
