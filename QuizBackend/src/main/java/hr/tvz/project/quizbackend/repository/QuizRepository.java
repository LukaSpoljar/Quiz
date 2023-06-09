package hr.tvz.project.quizbackend.repository;

import hr.tvz.project.quizbackend.entity.QuizDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<QuizDB, Long> {

    Optional<QuizDB> findById(Long id);

}
