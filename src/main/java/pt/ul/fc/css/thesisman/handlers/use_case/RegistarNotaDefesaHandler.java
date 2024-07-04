package pt.ul.fc.css.thesisman.handlers.use_case;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.thesisman.entities.Defesa;
import pt.ul.fc.css.thesisman.entities.Docente;
import pt.ul.fc.css.thesisman.enums.TipoEntrega;
import pt.ul.fc.css.thesisman.exceptions.EntidadeNotFoundException;
import pt.ul.fc.css.thesisman.exceptions.RegistarNotaException;
import pt.ul.fc.css.thesisman.repositories.CriadorTemaRepositorio;
import pt.ul.fc.css.thesisman.repositories.DefesaRepositorio;

@Component
public class RegistarNotaDefesaHandler {

  @Autowired private DefesaRepositorio defesaRepositorio;
  @Autowired private CriadorTemaRepositorio criadorTemaRepositorio;
  @Autowired private EntityManager entityManager;

  @Transactional
  public void registarNotaDefesa(Long defesaId, int nota, Long docenteId) {
    try {
      Defesa defesa =
          defesaRepositorio
              .findById(defesaId)
              .orElseThrow(() -> new EntidadeNotFoundException("Defesa não encontrada"));

      Docente docente =
          (Docente)
              criadorTemaRepositorio
                  .findDocenteById(docenteId)
                  .orElseThrow(() -> new EntidadeNotFoundException("Docente não encontrado"));

      if (defesa.getEntrega().getTipoEntrega() == TipoEntrega.FINAL) {
        if (!defesa.getPresidenteJuri().get().equals(docente)) {
          throw new RuntimeException("Docente não é presidente de júri");
        }
      } else {
        if (!defesa.getOrientador().equals(docente)) {
          throw new RuntimeException("Docente não é orientador interno da tese");
        }
      }

      entityManager.lock(defesa, LockModeType.PESSIMISTIC_WRITE);

      defesa.setNota(nota);
      defesa = defesaRepositorio.save(defesa);
    } catch (OptimisticLockingFailureException e) {
      throw new RegistarNotaException("Algo correu mal a atribuir nota. Tente outra vez");
    }
  }

  @Transactional(readOnly = true)
  public List<Defesa> listarDefesasParaAvaliar(Long docenteId) {

    Docente docente =
        (Docente)
            criadorTemaRepositorio
                .findDocenteById(docenteId)
                .orElseThrow(() -> new EntidadeNotFoundException("Docente não encontrado"));
    List<Defesa> paraAvaliarOrientador =
        docente.getDefesasOrientador().stream()
            .filter(d -> isParaAvaliar(d, true))
            .collect(Collectors.toList());
    List<Defesa> paraAvaliarPresidente =
        docente.getDefesasPresidente().stream()
            .filter(d -> isParaAvaliar(d, false))
            .collect(Collectors.toList());

    paraAvaliarOrientador.addAll(paraAvaliarPresidente);
    return paraAvaliarOrientador;
  }

  private boolean isParaAvaliar(Defesa d, boolean isOrientador) {
    if (isOrientador) {
      if (d.getEntrega().getTipoEntrega().equals(TipoEntrega.FINAL)) {
        return false;
      }
    }

    if (d.getNota() != -1) {
      return false;
    }
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime defesaDataHoraFim =
        LocalDateTime.of(d.getData(), d.getHoraInicio().plusMinutes(d.getDuracao()));

    return now.isAfter(defesaDataHoraFim);
  }
}
