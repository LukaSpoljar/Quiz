package hr.tvz.project.quizbackend.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * Quiz
 */
@Entity
@Table(name="quiz")
public class QuizDB {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy="quiz")
    private List<QuestionDB> questions;

    public QuizDB() {
    }

    public QuizDB(String name, List<QuestionDB> questions) {
        this.name = name;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuestionDB> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDB> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "QuizDB{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
