package pt.ul.fc.css.thesisman.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.thesisman.entities.Entrega;
import pt.ul.fc.css.thesisman.enums.TipoEntrega;

/**
 * Interface que representa um repositório de entregas
 *
 * @autor João Pereira fc58189
 * @autor Martim Pereira fc58223
 * @autor Daniel Nunes fc58257
 */
@Repository
public interface EntregaRepositorio extends JpaRepository<Entrega, Long> {

  @Query("SELECT e FROM Entrega e WHERE e.id = :id")
  Optional<Entrega> findById(@Param("id") Long id);

  @Query("SELECT e FROM Entrega e WHERE e.tese.id = :id AND e.tipoEntrega = :tipo")
  List<Entrega> findByTeseIdAndTipo(@Param("id") Long id, @Param("tipo") TipoEntrega tipo);
}
