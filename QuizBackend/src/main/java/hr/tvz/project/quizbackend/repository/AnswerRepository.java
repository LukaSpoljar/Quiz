package hr.tvz.project.quizbackend.repository;

import hr.tvz.project.quizbackend.entity.AnswerDB;
import hr.tvz.project.quizbackend.entity.QuestionDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<AnswerDB, Long> {

    Optional<AnswerDB> findById(Long id);

}
