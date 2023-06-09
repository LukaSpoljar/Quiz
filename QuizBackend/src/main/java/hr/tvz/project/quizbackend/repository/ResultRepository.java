package hr.tvz.project.quizbackend.repository;

import hr.tvz.project.quizbackend.entity.PlayerDB;
import hr.tvz.project.quizbackend.entity.ResultDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<ResultDB, Long> {
}
