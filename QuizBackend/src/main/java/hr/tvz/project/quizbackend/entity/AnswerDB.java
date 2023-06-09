package hr.tvz.project.quizbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Quiz question answer
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="answer")
public class AnswerDB {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String content;

    public boolean isCorrect;

}
