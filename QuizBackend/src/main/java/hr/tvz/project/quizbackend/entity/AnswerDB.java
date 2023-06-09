package hr.tvz.project.quizbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Quiz question answer
 */
@Entity
@Table(name="answer")
public class AnswerDB {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String content;

    public boolean isCorrect;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="question_id", nullable=false)
    private QuestionDB question;

    public AnswerDB() {
    }

    public AnswerDB(String content, boolean isCorrect) {
        this.content = content;
        this.isCorrect = isCorrect;
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
