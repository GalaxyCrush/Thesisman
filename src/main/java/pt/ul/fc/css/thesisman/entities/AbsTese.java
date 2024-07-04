package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.NonNull;
import pt.ul.fc.css.thesisman.enums.Status;
import pt.ul.fc.css.thesisman.enums.TipoEntrega;

/**
 * Classe que representa uma tese
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbsTese {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Version
  private Long version;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  protected Status status;

  @OneToOne(mappedBy = "tese")
  protected Aluno aluno;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "tema_id", nullable = false)
  protected AbsTema tema;

  @OneToMany(mappedBy = "tese", cascade = CascadeType.ALL)
  protected List<Entrega> entregas;

  /** Construtor vazio para JPA */
  public AbsTese() {
  }

  /**
   * Construtor de uma tese em caso de dissertação
   *
   * @param a aluno Aluno que faz a tese
   * @param t tema O tema que o aluno se candidatou para a tese
   */
  public AbsTese(@NonNull Aluno a, @NonNull AbsTema t) {
    this.aluno = a;
    this.tema = t;
    this.status = Status.EM_PROGRESSO;
    this.entregas = new ArrayList<>();
    this.aluno.setTese(this);
    this.tema.setDisponibilidade(false);
  }

  /**
   * Função que retorna o id da tese
   *
   * @return id da tese
   */
  public Long getId() {
    return id;
  }

  /**
   * Função que retorna o status da tese
   *
   * @return status da tese
   */
  public Status getStatus() {
    return this.status;
  }

  /**
   * Função que altera o status da tese
   *
   * @param status novo status da tese
   */
  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * Função que retorna o aluno da tese
   *
   * @return aluno responsável da tese
   */
  public Aluno getAluno() {
    return this.aluno;
  }

  /**
   * Função que retorna o tema da tese
   *
   * @return tema da tese
   */
  public AbsTema getTema() {
    return this.tema;
  }

  /**
   * Função que retorna a entrega final da tese
   *
   * @return entrega final da tese
   */
  public Entrega getEntregaFinal() {
    return this.entregas.stream()
        .filter(e -> e.getTipoEntrega() == TipoEntrega.FINAL)
        .findFirst()
        .orElse(null);
  }

  /**
   * Função que retorna as entregas propostas da tese
   *
   * @return entregas propostas da tese
   */
  public List<Entrega> getEntregasPropostas() {
    return this.entregas.stream()
        .filter(e -> e.getTipoEntrega() != TipoEntrega.FINAL).toList();
  }

  /**
   * Função que adiciona uma entrega proposta à tese
   *
   * @param e entrega proposta a adicionar
   * @return true se a entrega foi adicionada com sucesso, false caso contrário
   */
  public boolean addEntrega(Entrega e) {
    if (e.getTipoEntrega() == TipoEntrega.FINAL) {
      this.status = Status.FINAL_ENTREGUE;
    } else {
      this.status = Status.PROPOSTA_ENTREGUE;
    }
    return this.entregas.add(e);
  }

  /**
   * Função que remove uma entrega proposta da tese
   *
   * @param e entrega proposta a remover
   * @return true se a entrega foi removida com sucesso, false caso contrário
   */
  public boolean removeEntrega(Entrega e) {
    return this.entregas.remove(e);
  }

  public abstract Docente getOrientadorInterno();
}
