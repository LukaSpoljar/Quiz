package hr.tvz.project.quizbackend.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Player {
    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String username;

    @Column(nullable=false)
    private String password;

    @Column(unique = true, name = "uuid", nullable = false)
    private String uuid = UUID.randomUUID().toString().toUpperCase();

    public Player() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
