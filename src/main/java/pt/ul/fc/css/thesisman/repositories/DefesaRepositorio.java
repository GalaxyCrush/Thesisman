package pt.ul.fc.css.thesisman.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.thesisman.entities.Defesa;

/**
 * Interface que representa um repositório de defesas
 *
 * @autor João Pereira fc58189
 * @autor Martim Pereira fc58223
 * @autor Daniel Nunes fc58257
 */

@Repository
public interface DefesaRepositorio extends JpaRepository<Defesa, Long> {

  @Query("SELECT d FROM Defesa d WHERE d.id = :id")
  Optional<Defesa> findById(@Param("id") Long id);
}
