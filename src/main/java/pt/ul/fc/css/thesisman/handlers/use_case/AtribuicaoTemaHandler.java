package pt.ul.fc.css.thesisman.handlers.use_case;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.thesisman.entities.*;
import pt.ul.fc.css.thesisman.exceptions.EntidadeNotFoundException;
import pt.ul.fc.css.thesisman.repositories.*;

@Component
public class AtribuicaoTemaHandler {

  @Autowired private TemaRepositorio temaRepositorio;
  @Autowired private AlunoRepositorio alunoRepositorio;
  @Autowired private MestradoRepositorio mestradoRepositorio;
  @Autowired private CriadorTemaRepositorio criadorTemaRepositorio;
  @Autowired private DissertacaoRepositorio dissertacaoRepositorio;
  @Autowired private ProjetoRepositorio projetoRepositorio;

  @Transactional
  public void atribuirTemaParaAluno(Long alunoId, Long temaId) {
    Aluno aluno =
        alunoRepositorio
            .findById(alunoId)
            .orElseThrow(() -> new EntidadeNotFoundException("Aluno não encontrado"));

    AbsTema tema =
        temaRepositorio
            .findById(temaId)
            .orElseThrow(() -> new EntidadeNotFoundException("Tema não encontrado"));

    AbsTese tese = null;
    if (tema instanceof TemaDissertacao) {
      tese = new Dissertacao(aluno, (TemaDissertacao) tema);
    } else {
      tese = new Projeto(aluno, (TemaProjeto) tema);
    }

    try {
      if (tese instanceof Dissertacao) {
        dissertacaoRepositorio.save((Dissertacao) tese);
      } else {
        projetoRepositorio.save((Projeto) tese);
      }
    } catch (OptimisticLockingFailureException e) {
      // FIXME - custom exception
      throw new RuntimeException(
          "O tema já não se encontra disponivel. Por favor dê refresh da página");
    }
  }

  @Transactional
  public void atribuirTemaParaAluno(Long alunoId, Long temaId, Long docenteId) {
    Aluno aluno =
        alunoRepositorio
            .findById(alunoId)
            .orElseThrow(() -> new EntidadeNotFoundException("Aluno não encontrado"));

    TemaProjeto tema =
        (TemaProjeto)
            temaRepositorio
                .findTemaProjetoById(temaId)
                .orElseThrow(() -> new EntidadeNotFoundException("Tema não encontrado"));

    Docente docente =
        (Docente)
            criadorTemaRepositorio
                .findDocenteById(docenteId)
                .orElseThrow(() -> new EntidadeNotFoundException("Docente não encontrado"));

    tema.setDocenteEncarregue(docente);
    Projeto tese = new Projeto(aluno, tema);
    projetoRepositorio.save(tese);
  }

  @Transactional(readOnly = true)
  public Mestrado obterMestradoAdmin(Long adminId) {
    return mestradoRepositorio
        .findByAdminId(adminId)
        .orElseThrow(
            () -> new EntidadeNotFoundException("Docente não é administrador de mestrado"));
  }

  @Transactional(readOnly = true)
  public boolean hasOrientador(Long temaId) {
    TemaProjeto tema =
        temaRepositorio
            .findTemaProjetoById(temaId)
            .orElseThrow(() -> new EntidadeNotFoundException("Tema não encontrado"));
    return tema.getDocenteEncarregue() != null;
  }

  @Transactional(readOnly = true)
  public List<Aluno> listarAlunosDeMestradoSemTema(Mestrado m) {
    return alunoRepositorio.findAllSemTemaPorMestrado(m.getId());
  }
}
