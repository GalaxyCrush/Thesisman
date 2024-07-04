package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.NonNull;

/**
 * Classe que representa um aluno
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@Entity
public class Aluno extends Utilizador {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Version
  private Long version;

  @NonNull
  private Double media;

  @ManyToOne
  @JoinColumn(name = "mestrado_id", nullable = false)
  private Mestrado mestrado;

  @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
  private List<Candidatura> candidaturas = new ArrayList<>();

  @OneToOne
  @JoinColumn(name = "tese_id")
  private AbsTese tese;

  public Aluno() {
  }

  /**
   * Construtor de um aluno
   *
   * @param nome     nome do aluno
   * @param password password do aluno
   * @param media    media do aluno
   * @param mestrado mestrado do aluno
   * @requires nome != null && password != null && media != null && mestrado !=
   *           null
   * @ensures this.nome == nome && this.password == password && this.media ==
   *          media && this.mestrado
   *          == mestrado
   */
  public Aluno(
      @NonNull String nome,
      @NonNull String email,
      @NonNull String password,
      @NonNull Double media,
      @NonNull Mestrado mestrado) {
    super(nome, email, password);
    this.media = media;
    this.mestrado = mestrado;
  }

  /**
   * Método que retorna o id de um aluno
   *
   * @return id do aluno
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Método que retorna a media de um aluno
   *
   * @return A media do aluno
   */
  public Double getMedia() {
    return media;
  }

  /**
   * Método que retorna a lista de mestrados de um aluno
   *
   * @return O mestrado atual do aluno
   */
  public Mestrado getMestrado() {
    return mestrado;
  }

  /**
   * Método que retorna as candidaturas realizadas pelo aluno
   *
   * @return Lista de candidaturas do aluno
   */
  public List<Candidatura> getCandidaturas() {
    return candidaturas;
  }

  /**
   * Método que atualiza a media do aluno
   *
   * @param media nova media
   */
  public void setMedia(@NonNull Double media) {
    this.media = media;
  }

  /**
   * Metodo que altera o mestrado de um aluno
   *
   * @param m mestrado dado
   */
  public void setMestrado(@NonNull Mestrado m) {
    this.mestrado = m;
  }

  /**
   * Método que adiciona uma candidatura na lista de candidatura de um aluno
   * tendoem conta que o
   * aluno apenas pode realizar no máximo 5 condidaturas
   *
   * @param c candidatura a adicionar
   * @requires this.candidaturas.size() < 5
   * @return true (as specified by Collection.add)
   */
  public boolean addCandidatura(Candidatura c) {
    return candidaturas.add(c);
  }

  /**
   * Método que remove uma candidatura da lista de candiatrue se a candidatura
   * estava presente na
   * lista e foi removida com sucesso, false caso contrario
   *
   * @param c candidatura a remover
   * @return true se a candidatura c estava presente na lista e foi removida com
   *         sucesso, false caso
   *         contrário
   */
  public boolean removeCandidatura(Candidatura c) {
    return candidaturas.remove(c);
  }

  /**
   * Método que retorna a tese de um aluno
   *
   * @return tese do aluno ou null caso o aluno ainda não esteja associado a uma
   *         tese
   */
  public AbsTese getTese() {
    return tese;
  }

  /**
   * Método que atualiza a tese de um aluno
   *
   * @param tese nova tese
   * @ensure this.tese == tese
   */
  public void setTese(AbsTese tese) {
    this.tese = tese;
  }

  @Override
  public String toString() {
    return "Aluno{"
        + "\nid="
        + id
        + "\nnome='"
        + super.getNome()
        + "\nmedia="
        + media
        + "\nmestrado="
        + (mestrado != null ? mestrado.toString() : "null")
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
    Aluno aluno = (Aluno) obj;
    return Objects.equals(id, aluno.id);
  }
}
