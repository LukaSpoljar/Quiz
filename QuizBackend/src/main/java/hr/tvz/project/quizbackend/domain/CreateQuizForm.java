package hr.tvz.project.quizbackend.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuizForm {

    @NotNull
    String name;

    @NotNull
    Long authorId;

    @NotNull
    Long categoryId;

    @NotNull
    List<CreateQuizQuestion> questions;

}
