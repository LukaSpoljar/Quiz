package hr.tvz.project.quizbackend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
@Table(name="result")
@SequenceGenerator(name="result_gen", sequenceName="result_gen", initialValue=5)
public class ResultDB {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "result_gen")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id", nullable=false)
    private PlayerDB player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="quiz_id", nullable=false)
    private QuizDB quiz;

    @Column
    private int correct;

    public ResultDB() {
    }

    public ResultDB(PlayerDB player, QuizDB quiz, int correct) {
        this.player = player;
        this.quiz = quiz;
        this.correct = correct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerDB getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDB player) {
        this.player = player;
    }

    public QuizDB getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizDB quiz) {
        this.quiz = quiz;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    /**
     * @return Total number of questions the quiz contains
     */
    public int getTotal() {
        return quiz.getQuestions().size();
    }

    /**
     * @return A percentage (0.0 to 1.0) of completion
     */
    public BigDecimal getSuccess() {
        BigDecimal correctDecimal = new BigDecimal(correct);
        BigDecimal totalDecimal = new BigDecimal(getTotal());
        return correctDecimal.divide(totalDecimal);
    }

    @Override
    public String toString() {
        return "ResultDB{" +
                "id=" + id +
                ", player='" + player.getUsername() + '\'' +
                ", quiz='" + quiz.getName() + '\'' +
                ", correct=" + correct +
                ", total=" + getTotal() +
                ", success=" + getSuccess() +
                '}';
    }
}
