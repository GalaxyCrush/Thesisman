package pt.ul.fc.css.thesisman.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.thesisman.entities.Aluno;

/**
 * Interface que representa um repositório de alunos
 *
 * @autor João Pereira fc58189
 * @autor Martim Pereira fc58223
 * @autor Daniel Nunes fc58257
 */
@Repository
public interface AlunoRepositorio extends JpaRepository<Aluno, Long> {

  @Query("SELECT a FROM Aluno a WHERE a.id = :id")
  Optional<Aluno> findById(@Param("id") Long id);

  @Query("SELECT a FROM Aluno a WHERE a.email = :email")
  Optional<Aluno> findByEmail(@Param("email") String email);

  @Query("SELECT a FROM Aluno a ORDER BY a.media DESC ")
  List<Aluno> findAllSortedByMedia();

  @Query("SELECT a FROM Aluno a WHERE a.tese.tema.anoLetivo = :ano")
  List<Aluno> findAllByAnoLetivoTese(@Param("ano") String ano);

  @Query("SELECT a FROM Aluno a WHERE a.tese IS NULL")
  List<Aluno> findAllSemTema();

  @Query("SELECT a FROM Aluno a WHERE a.tese IS NULL AND a.mestrado.id = :mId")
  List<Aluno> findAllSemTemaPorMestrado(@Param("mId") Long mId);

  boolean existsByEmail(String email);
}
