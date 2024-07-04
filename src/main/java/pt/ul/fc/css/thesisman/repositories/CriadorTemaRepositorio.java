package pt.ul.fc.css.thesisman.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import pt.ul.fc.css.thesisman.entities.CriadorTema;

/**
 * Interface que representa um repositório de criadores de temas
 *
 * @autor João Pereira fc58189
 * @autor Martim Pereira fc58223
 * @autor Daniel Nunes fc58257
 */

@Repository
public interface CriadorTemaRepositorio extends JpaRepository<CriadorTema, Long> {

  @Query("SELECT c FROM CriadorTema c WHERE c.id = :id")
  Optional<CriadorTema> findById(@Param("id") Long id);

  @Query("SELECT c FROM Docente c WHERE c.id = :id")
  Optional<CriadorTema> findDocenteById(@Param("id") Long id);

  @Query("SELECT c FROM Empresarial c WHERE c.id = :id")
  Optional<CriadorTema> findEmpresarialById(@Param("id") Long id);

  @Query("SELECT c FROM Docente c")
  List<CriadorTema> findAllDocentes();

  // FIXME: Verificar como funcemina a query automatica usando @Repository
  boolean existsByEmail(String email);

  @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Docente c WHERE c.id = :id")
  boolean existsDocenteById(@Param("id") Long id);

  @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Docente c WHERE c.email = :email")
  boolean existsDocenteByEmail(@Param("email") String email);

  Optional<CriadorTema> findByEmail(String email);

  @Query("SELECT d FROM Docente d WHERE d.email = :email")
  Optional<CriadorTema> findDocenteByEmail(@Param("email") String email);

  @Query("SELECT e FROM Empresarial e WHERE e.email = :email")
  Optional<CriadorTema> findEmpresarialByEmail(@Param("email") String email);


}
