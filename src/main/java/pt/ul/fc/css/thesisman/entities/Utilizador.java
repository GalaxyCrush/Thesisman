package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

/**
 * Classe abstrata que representa um utilizador do sistema
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@MappedSuperclass
public abstract class Utilizador {

  @NonNull private String nome;

  @NonNull
  @Column(unique = true)
  private String email;

  @NonNull private String password;

  /** Construtor vazio da classe Utilizador por causa do JPA */
  protected Utilizador() {}

  /** Construtor da classe Utilizador */
  protected Utilizador(@NonNull String nome, @NonNull String email, @NonNull String password) {
    this.nome = nome;
    this.email = email;
    this.password = password;
  }

  /**
   * Função que retorna o nome do utilizador
   *
   * @return nome do utilizador
   */
  public String getNome() {
    return this.nome;
  }

  /**
   * Função que retorna o email do utilizador
   *
   * @return email do utilizador
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Função que retorna a password do utilizador
   *
   * @return password do utilizador
   */
  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Utilizador{");
    sb.append("nome='").append(nome).append('\'').append(email).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
