package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;

/**
 * Classe que representa um mestrado.
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@Entity
public class Mestrado {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Version
  private Long version;

  @NonNull
  @Column(unique = true)
  private String nome;

  @NonNull
  private String departamento;

  @OneToOne
  @JoinColumn(name = "docente_id", nullable = false)
  private Docente administrador;

  /** Construtor vazio para o JPA. */
  public Mestrado() {
  }

  /**
   * Construtor de um mestrado.
   *
   * @param nome         Nome do mestrado.
   * @param admin        Docente administrador do mestrado.
   * @param departamento Departamento do mestrado.
   */
  public Mestrado(String nome, Docente admin, String departamento) {
    this.administrador = admin;
    this.nome = nome;
    this.departamento = departamento;
  }

  /**
   * Getter do id do mestrado.
   *
   * @return Id do mestrado.
   */
  public Long getId() {
    return id;
  }

  /**
   * Getter do administrador do mestrado.
   *
   * @return Docente administrador do mestrado.
   */
  public Docente getAdministrador() {
    return this.administrador;
  }

  /**
   * Getter do nome do mestrado.
   *
   * @return Nome do mestrado.
   */
  public String getNome() {
    return this.nome;
  }

  /**
   * Getter do departamento do mestrado.
   *
   * @return Departamento do mestrado.
   */
  public String getDepartamento() {
    return this.departamento;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Mestrado{");
    sb.append("id=").append(id);
    sb.append(", nome='").append(nome).append('\'');
    sb.append(", departamento='").append(departamento).append('\'');
    sb.append(", administrador=").append(administrador.getNome());
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Mestrado mestrado = (Mestrado) o;

    return id.equals(mestrado.id);
  }
}
