package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;
import pt.ul.fc.css.thesisman.exceptions.CriarCandidaturaException;

/**
 * Classe que representa uma candidatura
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@Entity
public class Candidatura {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Version
  private Long version;

  @ManyToOne
  @JoinColumn(name = "tema_id", nullable = false)
  private AbsTema tema;

  @ManyToOne
  @JoinColumn(name = "aluno_id", nullable = false)
  private Aluno aluno;

  private boolean aceite = false;

  public Candidatura() {
  }

  /**
   * Construtor de uma candidatura
   *
   * @param tema  tema ao qual o aluno se candidata
   * @param aluno aluno da candidatura
   */
  public Candidatura(@NonNull AbsTema tema, @NonNull Aluno aluno) {
    if (aluno.getCandidaturas().size() >= 5) {
      throw new CriarCandidaturaException(
          "Candidatura inválida.\nAluno já atingiu o limite de 5 candidaturas!\n");
    }
    this.tema = tema;
    this.aluno = aluno;
    if (!this.aluno.addCandidatura(this)) {
      throw new CriarCandidaturaException(
          "Candidatura inválida.\nAluno já se candidatou a este tema!\n");
    }
  }

  /**
   * Metodo que retorna o tema da candidatura
   *
   * @return o tema da candidatura
   */
  public AbsTema getTema() {
    return this.tema;
  }

  /**
   * Metodo que retorna o id da candidatura
   *
   * @return o id da candidatura
   */
  public Long getId() {
    return id;
  }

  /**
   * Metodo que retorna o aluno da candidatura
   *
   * @return o aluno da candidatura
   */
  public Aluno getAluno() {
    return this.aluno;
  }

  /**
   * Metodo que retorna se a candidatura foi aceite
   *
   * @return true se a candidatura foi aceite, false caso contrário
   */
  public boolean isAceite() {
    return aceite;
  }

  /** Metodo que aceita a candidatura */
  public void aceitarCandidatura() {
    this.aceite = true;
  }

  @Override
  public String toString() {
    return "Candidatura{" + "\nid=" + id + "\ntema=" + this.tema.toString() + "\n}";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Candidatura that = (Candidatura) obj;
    if (this.aluno.getId().equals(that.aluno.getId()) && this.tema.getId().equals(that.tema.getId())) {
      return true;
    }
    return false;
  }
}
