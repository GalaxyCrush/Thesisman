package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;
import org.springframework.lang.NonNull;

@Entity
public class TemaProjeto extends AbsTema {

  @ManyToOne
  @JoinColumn(name = "docente_encarregue_id")
  private Docente docenteEncarregue;

  public TemaProjeto() {}

  public TemaProjeto(
      @NonNull String titulo,
      @NonNull String descricao,
      Double renumeracaoMensal,
      @NonNull List<Mestrado> mestradosCompativeis,
      @NonNull CriadorTema criadorTema,
      Docente docenteEncarregue) {

    super(titulo, descricao, renumeracaoMensal, mestradosCompativeis, criadorTema);
    this.docenteEncarregue = docenteEncarregue;
  }

  public Docente getDocenteEncarregue() {
    return docenteEncarregue;
  }

  public void setDocenteEncarregue(Docente docenteEncarregue) {
    this.docenteEncarregue = docenteEncarregue;
  }
}
