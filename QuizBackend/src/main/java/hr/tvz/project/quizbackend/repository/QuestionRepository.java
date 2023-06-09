package hr.tvz.project.quizbackend.repository;

import hr.tvz.project.quizbackend.entity.QuestionDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<QuestionDB, Long> {

    @Override
    Optional<QuestionDB> findById(Long id);

}
