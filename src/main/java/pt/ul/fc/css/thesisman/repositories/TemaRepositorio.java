package pt.ul.fc.css.thesisman.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.thesisman.entities.AbsTema;
import pt.ul.fc.css.thesisman.entities.Mestrado;
import pt.ul.fc.css.thesisman.entities.TemaDissertacao;
import pt.ul.fc.css.thesisman.entities.TemaProjeto;

/**
 * Interface que representa um repositório de temas
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@Repository
public interface TemaRepositorio extends JpaRepository<AbsTema, Long> {

  @Query("SELECT t FROM AbsTema t WHERE t.id = :id")
  Optional<AbsTema> findById(@Param("id") Long id);

  @Query(
      "SELECT t FROM AbsTema t WHERE :m MEMBER OF t.mestradosCompativeis AND t.disponivel = true")
  List<AbsTema> findAllCompativeis(@Param("m") Mestrado m);

  @Query("SELECT t FROM AbsTema t WHERE t.criadorTema.id = :id")
  List<AbsTema> findByCriadorId(@Param("id") Long id);

  @Query("SELECT t FROM AbsTema t WHERE t.anoLetivo = :ano")
  List<AbsTema> findAllByAno(@Param("ano") String ano);

  @Query("SELECT t FROM TemaDissertacao t WHERE t.id = :id")
  Optional<TemaDissertacao> findTemaDissertacaoById(@Param("id") Long id);

  @Query("SELECT p FROM TemaProjeto p WHERE p.id = :id")
  Optional<TemaProjeto> findTemaProjetoById(@Param("id") Long id);

  boolean existsByTitulo(String titulo);
}
