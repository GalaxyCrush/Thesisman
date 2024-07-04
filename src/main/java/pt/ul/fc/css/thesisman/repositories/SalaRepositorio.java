package pt.ul.fc.css.thesisman.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.thesisman.entities.Sala;

/**
 * Interface que representa um repositório de salas
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */

@Repository
public interface SalaRepositorio extends JpaRepository<Sala, Long> {

  @Query("SELECT s FROM Sala s WHERE s.id = :id")
  Optional<Sala> findById(@Param("id") Long id);
}
