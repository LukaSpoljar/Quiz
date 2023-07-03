package hr.tvz.project.quizbackend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Quiz question answer
 */
@Entity
@Table(name="answer")
@SequenceGenerator(name="answer_gen", sequenceName="answer_gen", initialValue=14)
public class AnswerDB {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "answer_gen")
    private Long id;

    @Column(nullable = false)
    private String content;

    public boolean isCorrect;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="question_id", nullable=false)
    private QuestionDB question;

    public AnswerDB() {
    }

    public AnswerDB(String content, boolean isCorrect) {
        this.content = content;
        this.isCorrect = isCorrect;
    }

    public AnswerDB(String content, boolean isCorrect, QuestionDB questionDB) {
        this.content = content;
        this.isCorrect = isCorrect;
        this.question = questionDB;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public QuestionDB getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDB question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "AnswerDB{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", isCorrect=" + isCorrect +
                ", question_id=" + question.getId() +
                '}';
    }

}
