package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.lang.NonNull;

@Entity
public class Projeto extends AbsTese {

  @ManyToOne
  @JoinColumn(name = "orientador_interno_id")
  private Docente orientadorInterno;

  @ManyToOne
  @JoinColumn(name = "orientador_externo_id", nullable = false)
  private Empresarial orientadorExterno;

  public Projeto() {}

  public Projeto(@NonNull Aluno aluno, @NonNull TemaProjeto t) {

    super(aluno, t);
    this.orientadorInterno = t.getDocenteEncarregue();
    this.orientadorExterno = (Empresarial) t.getCriadorTema();

    if (this.orientadorInterno != null) {
      this.orientadorInterno.addTeseOrientada(this);
    }
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
   * Função que retorna o orientador externo da tese
   *
   * @return orientador externo da tese
   */
  public Empresarial getOrientadorExterno() {
    return this.orientadorExterno;
  }

  /**
   * Função que altera o orientador externo da tese
   *
   * @param e novo orientador externo da tese
   */
  public void setOrientadorExterno(Empresarial e) {
    this.orientadorExterno = e;
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
    sb.append(", orientadorExterno=").append(orientadorExterno.getNome());
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
    return ((Projeto) obj).getId().equals(super.getId());
  }
}
