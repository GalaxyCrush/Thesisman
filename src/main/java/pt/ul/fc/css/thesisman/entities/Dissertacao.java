package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.lang.NonNull;

@Entity
public class Dissertacao extends AbsTese {

  @ManyToOne
  @JoinColumn(name = "orientador_interno_id", nullable = false)
  private Docente orientadorInterno;

  public Dissertacao() {}

  public Dissertacao(@NonNull Aluno aluno, @NonNull TemaDissertacao t) {
    super(aluno, t);
    this.orientadorInterno = (Docente) t.getCriadorTema();
    this.orientadorInterno.addTeseOrientada(this);
  }

  /**
   * Função que retorna o orientador interno da tese
   *
   * @return orientador interno da tese
   */
  public Docente getOrientadorInterno() {
    return this.orientadorInterno;
  }

  /**
   * Função que altera o orientador interno da tese
   *
   * @param d novo orientador interno da tese
   */
  public void setOrientadorInterno(Docente d) {
    this.orientadorInterno = d;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Tese{");
    sb.append("id=").append(super.getId());
    sb.append(", status=").append(super.status.toString());
    sb.append(", aluno=").append(aluno.getNome());
    sb.append(", orientadorInterno=").append(orientadorInterno.getNome());
    sb.append(", tema=").append(tema.getTitulo());
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    return ((Dissertacao) obj).getId().equals(super.getId());
  }
}
