package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.NonNull;

/**
 * Classe que representa um criador de temas
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class CriadorTema extends Utilizador {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Version private Long version;

  @OneToMany(mappedBy = "criadorTema", cascade = CascadeType.ALL)
  private List<AbsTema> temas_criados;

  private String descricao;

  public CriadorTema() {
    super();
    this.temas_criados = new ArrayList<AbsTema>();
  }

  /**
   * Construtor de um criador de temas
   *
   * @param nome nome do criador de temas
   * @param password password do criador de temas
   * @param descricao descricao do criador de temas
   * @requires nome != null && password != null
   * @ensures this.nome == nome && this.password == password && this.descricao == descricao
   */
  public CriadorTema(
      @NonNull String nome, @NonNull String email, @NonNull String password, String descricao) {
    super(nome, email, password);
    this.temas_criados = new ArrayList<AbsTema>();
    this.descricao = descricao;
  }

  /**
   * Metodo que retorna os temas criados pelo criador de temas
   *
   * @return os temas criados pelo criador de temas
   */
  public List<AbsTema> getTemasCriados() {
    return this.temas_criados;
  }

  /**
   * Metodo que adiciona um tema à lista de temas criados pelo criador de temas
   *
   * @param t tema a adicionar
   * @return true se o tema foi adicionado com sucesso, false caso contrário
   */
  public boolean addTema(AbsTema t) {
    return this.temas_criados.add(t);
  }

  /**
   * Metodo que remove um tema da lista de temas criados pelo criador de temas
   *
   * @param t tema a remover
   * @return true se o tema foi removido com sucesso, false caso contrário
   */
  public Long getId() {
    return id;
  }

  /**
   * Metodo que retorna a descricao do criador de temas
   *
   * @return a descricao do criador de temas
   */
  public String getDescricao() {
    return descricao;
  }

  /**
   * Metodo que atualiza a descricao do criador de temas
   *
   * @param descricao nova descricao do criador de temas
   */
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String toString(String titulo) {
    return titulo
        + "{"
        + "\nid="
        + id
        + "\nnome="
        + super.getNome()
        + "\ndescricao='"
        + descricao
        + "\n}";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CriadorTema criadorTema = (CriadorTema) obj;
    return id.equals(criadorTema.id);
  }
}
