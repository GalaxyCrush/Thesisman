package pt.ul.fc.css.thesisman.handlers.use_case;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.thesisman.entities.AbsTema;
import pt.ul.fc.css.thesisman.entities.Aluno;
import pt.ul.fc.css.thesisman.entities.Candidatura;
import pt.ul.fc.css.thesisman.exceptions.CriarCandidaturaException;
import pt.ul.fc.css.thesisman.exceptions.EntidadeNotFoundException;
import pt.ul.fc.css.thesisman.exceptions.InvalidCandidaturaException;
import pt.ul.fc.css.thesisman.exceptions.MestradoIncompativelException;
import pt.ul.fc.css.thesisman.repositories.AlunoRepositorio;
import pt.ul.fc.css.thesisman.repositories.CandidaturaRepositorio;
import pt.ul.fc.css.thesisman.repositories.TemaRepositorio;

@Component
public class CriarCandidaturaHandler {

  @Autowired
  private CandidaturaRepositorio candidaturaRepositorio;
  @Autowired
  private TemaRepositorio temaRepositorio;
  @Autowired
  private AlunoRepositorio alunoRepositorio;

  @Autowired
  private EntityManager entityManager;

  @Transactional
  public void criarCandidatura(Long alunoId, Long temaId)
      throws EntidadeNotFoundException {

    Aluno aluno = alunoRepositorio
        .findById(alunoId)
        .orElseThrow(() -> new EntidadeNotFoundException("Aluno não encontrado"));

    AbsTema tema = temaRepositorio
        .findById(temaId)
        .orElseThrow(() -> new EntidadeNotFoundException("Tema não encontrado"));

    if (!isMestradoCompativel(aluno, tema)) {
      throw new MestradoIncompativelException("Mestrado do aluno não é compativel com o tema");
    }
    if (aluno.getCandidaturas().stream().anyMatch(c -> c.getTema().equals(tema))) {
      throw new InvalidCandidaturaException("Aluno já se candidatou a este tema");
    }
    try {
      for (Candidatura candidatura : aluno.getCandidaturas()) {
        entityManager.lock(candidatura, LockModeType.PESSIMISTIC_WRITE);
      }
      Candidatura candidatura = new Candidatura(tema, aluno);
      candidaturaRepositorio.save(candidatura);
    } catch (OptimisticLockException e) {
      throw new CriarCandidaturaException("Erro ao criar candidatura. Por favor, tente novamente");
    }
  }

  private boolean isMestradoCompativel(Aluno aluno, AbsTema tema) {
    return tema.getMestradosCompativeis().contains(aluno.getMestrado());
  }
}
