package pt.ul.fc.css.thesisman.handlers.use_case;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.thesisman.dtos.EstatisticasInfo;
import pt.ul.fc.css.thesisman.entities.Aluno;
import pt.ul.fc.css.thesisman.enums.Status;
import pt.ul.fc.css.thesisman.repositories.AlunoRepositorio;

@Component
public class EstatisticasHandler {

  @Autowired private AlunoRepositorio alunoRepositorio;

  public EstatisticasHandler() {}

  @Transactional(readOnly = true)
  public EstatisticasInfo obterEstatisticasAlunos() {

    List<Aluno> alunosTotal = alunoRepositorio.findAll();
    String anoLetivo = getAnoLetivo();

    int numAlunosAnoAtual = 0;
    int numAlunosChumbadosAnoAtual = 0;
    int numAlunosPassadosAnoAtual = 0;
    List<Integer> notasAlunosAnoAtual = new ArrayList<>();

    int numAlunosTotal = 0;
    int numAlunosChumbadosTotal = 0;
    int numAlunosPassadosTotal = 0;
    List<Integer> notasAlunosTotal = new ArrayList<>();

    if (alunosTotal.isEmpty()) {
      return null;
    }

    for (Aluno aluno : alunosTotal) {
      if (aluno.getTese() != null) {
        numAlunosTotal++;
        if (aluno.getTese().getTema().getAnoLetivo().equals(anoLetivo)) {
          numAlunosAnoAtual++;
        }

        if (aluno.getTese().getStatus().equals(Status.CHUMBADA)) {
          numAlunosChumbadosTotal++;
          notasAlunosTotal.add(aluno.getTese().getEntregaFinal().getDefesa().getNota());
          if (aluno.getTese().getTema().getAnoLetivo().equals(anoLetivo)) {
            numAlunosChumbadosAnoAtual++;
            notasAlunosAnoAtual.add(aluno.getTese().getEntregaFinal().getDefesa().getNota());
          }
        } else if (aluno.getTese().getStatus().equals(Status.FINAL_PASSADA)) {
          numAlunosPassadosTotal++;
          notasAlunosTotal.add(aluno.getTese().getEntregaFinal().getDefesa().getNota());
          if (aluno.getTese().getTema().getAnoLetivo().equals(anoLetivo)) {
            numAlunosPassadosAnoAtual++;
            notasAlunosAnoAtual.add(aluno.getTese().getEntregaFinal().getDefesa().getNota());
          }
        }
      }
    }

    float mediaNotasAnoAtual = 0.0f;
    float mediaNotasTotal = 0.0f;

    if (!notasAlunosAnoAtual.isEmpty()) {
      mediaNotasAnoAtual =
          (float) notasAlunosAnoAtual.stream().mapToInt(Integer::intValue).sum()
              / notasAlunosAnoAtual.size();
    }
    if (!notasAlunosTotal.isEmpty()) {
      mediaNotasTotal =
          (float) notasAlunosTotal.stream().mapToInt(Integer::intValue).sum()
              / notasAlunosTotal.size();
    }

    return new EstatisticasInfo(
        numAlunosAnoAtual,
        numAlunosChumbadosAnoAtual,
        numAlunosPassadosAnoAtual,
        mediaNotasAnoAtual,
        numAlunosTotal,
        numAlunosChumbadosTotal,
        numAlunosPassadosTotal,
        mediaNotasTotal);
  }

  private String getAnoLetivo() {
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH);
    if (currentMonth < Calendar.SEPTEMBER) {
      return (currentYear - 1) + "/" + currentYear;
    } else {
      return currentYear + "/" + (currentYear + 1);
    }
  }
}
