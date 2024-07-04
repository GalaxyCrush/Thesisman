package pt.ul.fc.css.thesisman.handlers.use_case;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.thesisman.entities.Aluno;
import pt.ul.fc.css.thesisman.entities.Candidatura;
import pt.ul.fc.css.thesisman.exceptions.CancelarCandidaturaException;
import pt.ul.fc.css.thesisman.exceptions.EntidadeNotFoundException;
import pt.ul.fc.css.thesisman.repositories.CandidaturaRepositorio;

@Component
public class CancelarCandidaturaHandler {

  @Autowired
  private CandidaturaRepositorio candidaturaRepositorio;

  @Transactional()
  public List<Candidatura> cancelarCandidatura(Long candidaturaId) {
    try {
      Candidatura candidatura = candidaturaRepositorio.findById(candidaturaId)
          .orElseThrow(() -> new EntidadeNotFoundException("Candidatura não encontrada"));
      Aluno aluno = candidatura.getAluno();
      aluno.removeCandidatura(candidatura);
      candidaturaRepositorio.delete(candidatura);
      return aluno.getCandidaturas();
    } catch (DataIntegrityViolationException | ObjectOptimisticLockingFailureException e) {
      throw new CancelarCandidaturaException(
          "Erro ao cancelar candidatura. Por favor tente novamente");
    }
  }
}
