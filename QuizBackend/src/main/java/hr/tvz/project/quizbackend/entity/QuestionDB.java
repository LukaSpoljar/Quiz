package hr.tvz.project.quizbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Quiz question
 */
@Entity
@Table(name="question")
@SequenceGenerator(name="question_gen", sequenceName="question_gen", initialValue=5)
public class QuestionDB {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "question_gen")
    private Long id;

    @Column(nullable=false)
    private String text;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="quiz_id", nullable=false)
    private QuizDB quiz;

    @JsonManagedReference
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

    public List<AnswerDB> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDB> answers) {
        this.answers = answers;
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
