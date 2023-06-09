package hr.tvz.project.quizbackend.repository;

import hr.tvz.project.quizbackend.entity.CategoryDB;
import hr.tvz.project.quizbackend.entity.PlayerDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryDB, Long> {
}
