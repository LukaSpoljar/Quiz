package hr.tvz.project.quizbackend.domain;


import hr.tvz.project.quizbackend.entity.CategoryDB;
import hr.tvz.project.quizbackend.entity.PlayerDB;
import hr.tvz.project.quizbackend.entity.QuestionDB;
import hr.tvz.project.quizbackend.entity.QuizDB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A class used in communication with clients.
 * It hides vulnerable data such as passwords.
 * It may be initialized using QuizDB class.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO extends QuizBasicDTO {

    List<QuestionDB> questions;

    public QuizDTO(QuizDB quizDb) {
        // Initialize basic quiz data
        super(quizDb);
        // Initialize questions
        questions = quizDb.getQuestions();
    }

}
