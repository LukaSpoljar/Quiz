package hr.tvz.project.quizbackend.repository;

import hr.tvz.project.quizbackend.entity.ResultDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<ResultDB, Long> {

    @Query("SELECT r FROM ResultDB r JOIN r.quiz q JOIN r.player p WHERE q.id = :quizId")
    List<ResultDB> findAllByQuizId(@Param("quizId") Long quizId);

    @Query("SELECT r FROM ResultDB r JOIN r.quiz q JOIN r.player p WHERE q.id = :quizId AND p.id = :playerId")
    Optional<ResultDB> findAllByQuizIdAndPlayerId(@Param("quizId") Long quizId, @Param("playerId") Long playerId);

}
