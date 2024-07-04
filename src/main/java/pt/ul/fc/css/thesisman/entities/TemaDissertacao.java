package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.Entity;
import java.util.List;
import org.springframework.lang.NonNull;

@Entity
public class TemaDissertacao extends AbsTema {
  public TemaDissertacao() {}

  public TemaDissertacao(
      @NonNull String titulo,
      @NonNull String descricao,
      Double renumeracaoMensal,
      @NonNull List<Mestrado> mestradosCompativeis,
      @NonNull CriadorTema criadorTema) {

    super(titulo, descricao, renumeracaoMensal, mestradosCompativeis, criadorTema);
  }
}
