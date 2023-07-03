package hr.tvz.project.quizbackend.entity;

import jakarta.persistence.*;


/**
 * Quiz category
 */
@Entity
@Table(name="category")
@SequenceGenerator(name="category_gen", sequenceName="category_gen", initialValue=6)
public class CategoryDB {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "category_gen")
    private Long id;

    @Column(nullable = false)
    private String name;

    public CategoryDB() {
    }

    public CategoryDB(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "CategoryDB{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
