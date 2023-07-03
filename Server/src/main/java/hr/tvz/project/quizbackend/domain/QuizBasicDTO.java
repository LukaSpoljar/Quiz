package hr.tvz.project.quizbackend.domain;


import hr.tvz.project.quizbackend.entity.CategoryDB;
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
public class QuizBasicDTO {

    Long id;
    String name;
    String author;
    CategoryDB category;
    String uuid;
    int questionCount;

    public QuizBasicDTO(QuizDB quizDb) {
        id = quizDb.getId();
        name = quizDb.getName();
        author = quizDb.getAuthor().getUsername();
        category = quizDb.getCategory();
        uuid = quizDb.getUuid();
        questionCount = quizDb.getQuestions().size();
    }

}
