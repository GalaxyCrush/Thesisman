package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.NonNull;

/**
 * Classe que representa um docente empresarial
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@Entity
public class Empresarial extends CriadorTema {

  @OneToMany(mappedBy = "orientadorExterno")
  private List<Projeto> tesesOrientadas;

  /**
   * Construtor de um docente empresarial
   *
   * @param nome nome do docente
   * @param password password do docente
   * @param descricao descricao do docente
   * @requires nome != null && password != null
   */
  public Empresarial(
      @NonNull String nome,
      @NonNull String email,
      @NonNull String password,
      @NonNull String descricao) {
    super(nome, email, password, descricao);
    this.tesesOrientadas = new ArrayList<>();
  }

  public Empresarial() {
    this.tesesOrientadas = new ArrayList<>();
  }

  /**
   * Método que retorna as teses orientadas pelo docente
   *
   * @return as teses orientadas pelo docente
   */
  public List<Projeto> getTesesOrientadas() {
    return tesesOrientadas;
  }

  /**
   * Método que adiciona uma tese orientada pelo docente
   *
   * @param t tese a adicionar
   * @return true se a tese foi adicionada com sucesso, false caso contrário
   */
  public boolean addTeseOrientada(Projeto t) {
    return this.tesesOrientadas.add(t);
  }

  public boolean removerTeseOrientada(Projeto t) {
    return this.tesesOrientadas.remove(t);
  }

  @Override
  public String toString() {
    return super.toString("Empresarial");
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}
