package pt.ul.fc.css.thesisman.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import pt.ul.fc.css.thesisman.entities.Projeto;
import pt.ul.fc.css.thesisman.enums.Status;

/**
 * Interface que representa um repositório de teses
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */

@Repository
public interface ProjetoRepositorio extends JpaRepository<Projeto, Long> {

    @Query("SELECT p FROM Projeto p WHERE p.id = :id")
    Optional<Projeto> findById(@Param("id") Long id);

    @Query("SELECT p FROM Projeto p WHERE p.orientadorInterno.id = :orientadorId AND p.status IN :statuses")
    List<Projeto> findByOrientadorInternoIdAndStatusIn(Long orientadorId, List<Status> statuses);
}
