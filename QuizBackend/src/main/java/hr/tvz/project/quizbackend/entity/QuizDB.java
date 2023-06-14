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

    @Column (unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="author_id", nullable = false)
    private PlayerDB author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id", nullable = false)
    private CategoryDB category;

    @GeneratedValue
    @Column(columnDefinition = "uuid", unique = true, updatable = false, nullable = false)
    private String uuid;

    @OneToMany(mappedBy="quiz", fetch = FetchType.EAGER)
    private List<QuestionDB> questions;

    public QuizDB() {
    }

    public QuizDB(
            String name,
            PlayerDB author,
            CategoryDB category,
            String uuid,
            List<QuestionDB> questions)
    {
        this.name = name;
        this.author = author;
        this.category = category;
        this.uuid = uuid;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerDB getAuthor() {
        return author;
    }

    public void setAuthor(PlayerDB author) {
        this.author = author;
    }

    public CategoryDB getCategory() {
        return category;
    }

    public void setCategory(CategoryDB category) {
        this.category = category;
    }

    public String getUuid() {
        return uuid;
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
                ", author='" + author.getUsername() + '\'' +
                ", category='" + category.getName() + '\'' +
                ", uuid='" + uuid + '\'' +
                ", questions=" + questions.size() +
                '}';
    }

}
