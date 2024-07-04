package pt.ul.fc.css.thesisman.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.thesisman.entities.Mestrado;

/**
 * Interface que representa um repositório de mestrados
 *
 * @autor João Pereira fc58189
 * @autor Martim Pereira fc58223
 * @autor Daniel Nunes fc58257
 */
@Repository
public interface MestradoRepositorio extends JpaRepository<Mestrado, Long> {

  @Query("SELECT m FROM Mestrado m WHERE m.id = :id")
  Optional<Mestrado> findById(@Param("id") Long id);

  @Query(
      "SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Mestrado m WHERE m.administrador.id = :id")
  boolean existsAdminById(@Param("id") Long id);

  @Query("SELECT m FROM Mestrado m WHERE m.administrador.id = :id")
  Optional<Mestrado> findByAdminId(@Param("id") Long adminId);

  @Query(
      "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CriadorTema c WHERE c.email = :email AND TYPE(c) = :type")
  boolean existsByEmailAndType(@Param("email") String email, @Param("type") String type);
}
