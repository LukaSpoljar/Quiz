package hr.tvz.project.quizbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultsRowDTO {

    Long playerId;
    String username;
    int correct;
    int total;
    BigDecimal success;
}
