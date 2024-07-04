package pt.ul.fc.css.thesisman.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.thesisman.entities.Dissertacao;
import pt.ul.fc.css.thesisman.enums.Status;

/**
 * Interface que representa um repositório de teses
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */

@Repository
public interface DissertacaoRepositorio extends JpaRepository<Dissertacao, Long> {

    @Query("SELECT d FROM Dissertacao d WHERE d.id = :id")
    Optional<Dissertacao> findById(@Param("id") Long id);

    @Query("SELECT d FROM Dissertacao d WHERE d.orientadorInterno.id = :orientadorId AND d.status IN :statuses")
    List<Dissertacao> findByOrientadorInternoIdAndStatusIn(Long orientadorId, List<Status> statuses);

}