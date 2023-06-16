package hr.tvz.project.quizbackend.entity;

import hr.tvz.project.quizbackend.logic.HelperFunctions;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="player")
@SequenceGenerator(name="player_gen", sequenceName = "player_gen", initialValue=4)
public class PlayerDB implements UserDetails{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "player_gen")
    private Long id;

    @Column(unique = true, nullable=false)
    private String username;

    @Column(nullable=false)
    private String password;

    @GeneratedValue
    @Column(unique = true, name = "uuid", nullable = false)
    private String uuid = UUID.randomUUID().toString().toUpperCase();

    public PlayerDB() {
    }

    public String getUuid() {
        return uuid;
    }

    public PlayerDB(String username, String password) {
        this.username = username;
        byte[] hashedPasswordBytes = HelperFunctions.stringToSha256(password);
        this.password = HelperFunctions.bytesToHex(hashedPasswordBytes);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String nonHashedPassword) {
        byte[] hashedPasswordBytes = HelperFunctions.stringToSha256(nonHashedPassword);
        String hashedPassword = HelperFunctions.bytesToHex(hashedPasswordBytes);
        this.password = hashedPassword;
    }

    /**
     * Check if input password matches the one in player.
     * Useful in login checking.
     *
     * @param password A raw password string (not hashed).
     * @return True if password hash matches the one in Player
     */
    public boolean checkPassword(String password) {
        byte[] hashedPasswordBytes = HelperFunctions.stringToSha256(password);
        String hashedPassword = HelperFunctions.bytesToHex(hashedPasswordBytes);

        boolean passwordsMatch = this.getPassword().equals(hashedPassword);
        return passwordsMatch;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
    @Enumerated(EnumType.STRING)
     private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public  boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
    @Override
    public boolean isEnabled(){
        return true;
    }
}
