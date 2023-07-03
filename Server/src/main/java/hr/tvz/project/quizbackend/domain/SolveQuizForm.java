package hr.tvz.project.quizbackend.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolveQuizForm {

    @NotNull
    String playerUuid;

    @NotNull
    String quizUuid;

    @NotNull
    List<SolveQuizAnswer> answers;

}
