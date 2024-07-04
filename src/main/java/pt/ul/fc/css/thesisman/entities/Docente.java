package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.NonNull;

/**
 * Classe que representa um docente
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@Entity
public class Docente extends CriadorTema {

  @OneToMany(mappedBy = "orientadorInterno")
  private List<Dissertacao> dissertacoesOrientadas;

  @OneToMany(mappedBy = "orientadorInterno")
  private List<Projeto> projetosOrientados;

  @OneToMany(mappedBy = "orientador")
  private List<Defesa> defesasComoOrientador;

  @OneToMany(mappedBy = "presidente")
  private List<Defesa> defesasComoPresidente;

  @OneToMany(mappedBy = "arguente")
  private List<Defesa> defesasComoArguente;

  public Docente() {
    super();
  }

  /**
   * Construtor de um docente
   *
   * @param nome nome do docente
   * @param password password do docente
   * @param descricao descricao do docente
   * @requires nome != null && password != null
   * @ensures this.nome == nome && this.password == password && this.descricao == descricao
   */
  public Docente(
      @NonNull String nome, @NonNull String email, @NonNull String password, String descricao) {
    super(nome, email, password, descricao);
    this.dissertacoesOrientadas = new ArrayList<>();
    this.projetosOrientados = new ArrayList<>();
    this.defesasComoOrientador = new ArrayList<>();
    this.defesasComoPresidente = new ArrayList<>();
    this.defesasComoArguente = new ArrayList<>();
  }

  /**
   * Método que retorna as teses orientadas pelo docente
   *
   * @return as teses orientadas pelo docente
   */
  public List<AbsTese> getTesesOrientadas() {
    List<AbsTese> tesesOrientadas = new ArrayList<>();
    tesesOrientadas.addAll(dissertacoesOrientadas);
    tesesOrientadas.addAll(projetosOrientados);
    return tesesOrientadas;
  }

  /**
   * Método que retorna as defesas em que o docente é orientador
   *
   * @return as defesas em que o docente é orientador
   */
  public List<Defesa> getDefesasOrientador() {
    return defesasComoOrientador;
  }

  /**
   * Método que retorna as defesas em que o docente é presidente
   *
   * @return as defesas em que o docente é presidente
   */
  public List<Defesa> getDefesasPresidente() {
    return defesasComoPresidente;
  }

  /**
   * Método que retorna as teses orientadas pelo docente
   *
   * @return as teses orientadas pelo docente
   */
  public boolean addDefesaOrientador(Defesa d) {
    return this.defesasComoOrientador.add(d);
  }

  /**
   * Método que retorna as teses orientadas pelo docente
   *
   * @return as teses orientadas pelo docente
   */
  public boolean addDefesaPresidente(Defesa d) {
    return this.defesasComoPresidente.add(d);
  }

  /**
   * Método que retorna as teses orientadas pelo docente
   *
   * @return as teses orientadas pelo docente
   */
  public boolean removeDefesaOrientador(Defesa d) {
    return this.defesasComoOrientador.remove(d);
  }

  /**
   * Método que retorna as teses orientadas pelo docente
   *
   * @return as teses orientadas pelo docente
   */
  public boolean removeDefesaPresidente(Defesa d) {
    return this.defesasComoPresidente.remove(d);
  }

  /**
   * Método que retorna as teses orientadas pelo docente
   *
   * @return as teses orientadas pelo docente
   */
  public boolean addTeseOrientada(AbsTese t) {
    if (t instanceof Dissertacao) {
      return this.dissertacoesOrientadas.add((Dissertacao) t);
    } else {
      return this.projetosOrientados.add((Projeto) t);
    }
  }

  /**
   * Método que retorna as teses orientadas pelo docente
   *
   * @return as teses orientadas pelo docente
   */
  public boolean removeTeseOrientada(AbsTese t) {
    if (t instanceof Dissertacao) {
      return this.dissertacoesOrientadas.remove((Dissertacao) t);
    } else {
      return this.projetosOrientados.remove((Projeto) t);
    }
  }

  /**
   * Método que retorna as teses orientadas pelo docente
   *
   * @return as teses orientadas pelo docente
   */
  public List<Defesa> getDefesasArguente() {
    return defesasComoArguente;
  }

  /**
   * Método que retorna as teses orientadas pelo docente
   *
   * @return as teses orientadas pelo docente
   */
  public boolean addDefesaArguente(Defesa d) {
    return this.defesasComoArguente.add(d);
  }

  /**
   * Método que retorna as teses orientadas pelo docente
   *
   * @return as teses orientadas pelo docente
   */
  public boolean removeDefesaArguente(Defesa d) {
    return this.defesasComoArguente.remove(d);
  }

  @Override
  public String toString() {
    return super.toString("Docente");
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}
