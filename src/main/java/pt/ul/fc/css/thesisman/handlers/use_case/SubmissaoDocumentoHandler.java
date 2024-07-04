package pt.ul.fc.css.thesisman.handlers.use_case;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.thesisman.entities.*;
import pt.ul.fc.css.thesisman.enums.Status;
import pt.ul.fc.css.thesisman.enums.TipoEntrega;
import pt.ul.fc.css.thesisman.exceptions.*;
import pt.ul.fc.css.thesisman.repositories.AlunoRepositorio;
import pt.ul.fc.css.thesisman.repositories.EntregaRepositorio;

@Component
public class SubmissaoDocumentoHandler {

  @Autowired
  private EntregaRepositorio entregaRepositorio;
  @Autowired
  private AlunoRepositorio alunoRepositorio;

  @Transactional
  public List<Entrega> submeterDocumentoPropostaTese(Long alunoId, byte[] fc, String fileName) {

    if (!isDocumentAvailable(fc)) {
      throw new ParametroException("Documento não disponível");
    }

    Aluno aluno = alunoRepositorio
        .findById(alunoId)
        .orElseThrow(() -> new EntidadeNotFoundException("Aluno não encontrado"));

    AbsTese tese = aluno.getTese();

    if (tese == null) {
      throw new AlunoTeseException("Aluno não está a realizar tese");
    }
    if (tese.getStatus() != Status.EM_PROGRESSO) {
      throw new TeseEstadoException("Submissão inválida. Tese já passou a proposta de entrega");
    }

    try {
      Entrega entrega = new Entrega(tese, TipoEntrega.PROPOSTA, fc, fileName);
      entrega = entregaRepositorio.save(entrega);
      return tese.getEntregasPropostas();
    } catch (OptimisticLockingFailureException e) {
      throw new CriarEntregaException("Erro ao criar entrega. Por favor, tente novamente");
    }
  }

  @Transactional
  public Entrega submeterDocumentoFinalTese(Long alunoId, byte[] fc, String filename) {
    if (!isDocumentAvailable(fc)) {
      throw new ParametroException("Documento não disponível");
    }

    Aluno aluno = alunoRepositorio
        .findById(alunoId)
        .orElseThrow(() -> new EntidadeNotFoundException("Aluno não encontrado"));

    AbsTese tese = aluno.getTese();

    if (tese == null) {
      throw new AlunoTeseException("Aluno não está realizando tese");
    }

    if (tese.getStatus() != Status.PROPOSTA_PASSADA) {
      throw new TeseEstadoException(
          "Submissão inválida. Tese ainda não passou a proposta de entrega");
    }

    try {
      Entrega entrega = new Entrega(tese, TipoEntrega.FINAL, fc, filename);
      entrega = entregaRepositorio.save(entrega);
      return entrega;
    } catch (OptimisticLockingFailureException e) {
      throw new CriarEntregaException("Erro ao criar entrega. Por favor, tente novamente");
    }
  }

  private boolean isDocumentAvailable(byte[] fc) {
    return fc != null && fc.length != 0;
  }
}
