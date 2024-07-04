package pt.ul.fc.css.thesisman.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.thesisman.entities.Candidatura;

/**
 * Interface que representa um repositório de candidaturas
 *
 * @autor João Pereira fc58189
 * @autor Martim Pereira fc58223
 * @autor Daniel Nunes fc58257
 */

@Repository
public interface CandidaturaRepositorio extends JpaRepository<Candidatura, Long> {

  @Query("SELECT c FROM Candidatura c WHERE c.id = :id")
  Optional<Candidatura> findById(@Param("id") Long id);

  @Query("SELECT c FROM Candidatura c WHERE c.aceite = true AND c.aluno.id = :aluno_id")
  List<Candidatura> findAllAcceptedByAlunoId(@Param("aluno_id") Long id);

  @Query("SELECT c FROM Candidatura c WHERE c.aluno.id = :aluno_id")
  List<Candidatura> findAllByAlunoId(@Param("aluno_id") Long id);
}
