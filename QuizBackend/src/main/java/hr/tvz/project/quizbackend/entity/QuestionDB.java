package hr.tvz.project.quizbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Quiz question
 */
@Entity
@Table(name="question")
public class QuestionDB {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="quiz_id", nullable=false)
    private QuizDB quiz;

    @OneToMany(mappedBy="question", fetch = FetchType.EAGER)
    private List<AnswerDB> answers;

    public QuestionDB() {
    }

    public QuestionDB(String text, QuizDB quiz) {
        this.text = text;
        this.quiz = quiz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuizDB getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizDB quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "QuestionDB{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", quiz_id='" + quiz.getId() + '\'' +
                ", answers=" + answers.size() +
                '}';
    }

}
