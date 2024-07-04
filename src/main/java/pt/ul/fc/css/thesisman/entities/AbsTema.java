package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.*;
import java.util.Calendar;
import java.util.List;
import org.springframework.lang.NonNull;

/**
 * Classe que representa um Tema
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbsTema {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Version private Long version;

  @NonNull private String titulo;

  @NonNull private String descricao;

  private Double renumeracaoMensal;

  @NonNull private String anoLetivo;

  private boolean disponivel;

  @ManyToMany private List<Mestrado> mestradosCompativeis;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "criadorTema_id", nullable = false)
  private CriadorTema criadorTema;

  /** Construtor vazio para JPA */
  public AbsTema() {}

  /**
   * Construtor de um Tema
   *
   * @param titulo Titulo do tema
   * @param descricao Descricao do tema
   * @param renumeracaoMensal Remuneracao mensal do tema
   * @param mestradosCompativeis Lista de mestrados compativeis com o tema
   * @param criadorTema Criador do tema
   */
  public AbsTema(
      @NonNull String titulo,
      @NonNull String descricao,
      Double renumeracaoMensal,
      @NonNull List<Mestrado> mestradosCompativeis,
      @NonNull CriadorTema criadorTema) {
    this.titulo = titulo;
    this.descricao = descricao;
    this.renumeracaoMensal = renumeracaoMensal;
    this.mestradosCompativeis = mestradosCompativeis;
    this.criadorTema = criadorTema;

    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH);
    if (currentMonth < Calendar.SEPTEMBER) {
      this.anoLetivo = (currentYear - 1) + "/" + currentYear;
    } else {
      this.anoLetivo = currentYear + "/" + (currentYear + 1);
    }

    this.disponivel = true;
    criadorTema.addTema(this);
  }

  @PreRemove
  private void removeTemaFromCriadorTema() {
    this.criadorTema.getTemasCriados().remove(this);
  }

  /**
   * Funcao que retorna o titulo do tema
   *
   * @return titulo do tema
   */
  public String getTitulo() {
    return titulo;
  }

  /**
   * Funcao que retorna a descricao do tema
   *
   * @return descricao do tema
   */
  public String getDescricao() {
    return descricao;
  }

  /**
   * Funcao que retorna a renumeracao mensal do tema
   *
   * @return renumeracao mensal do tema
   */
  public Double getRenumeracaoMensal() {
    return renumeracaoMensal;
  }

  /**
   * Funcao que retorna o ano letivo do tema
   *
   * @return ano letivo do tema
   */
  public String getAnoLetivo() {
    return anoLetivo;
  }

  /**
   * Funcao que retorna a disponibilidade do tema
   *
   * @return disponibilidade do tema
   */
  public boolean isDisponivel() {
    return disponivel;
  }

  /**
   * Funcao que altera a disponibilidade do tema
   *
   * @param disponivel nova disponibilidade do tema
   */
  public void setDisponibilidade(boolean disponivel) {
    this.disponivel = disponivel;
  }

  /**
   * Funcao que retorna a lista de mestrados compativeis com o tema
   *
   * @return lista de mestrados compativeis com o tema
   */
  public List<Mestrado> getMestradosCompativeis() {
    return mestradosCompativeis;
  }

  /**
   * Funcao que retorna o criador do tema
   *
   * @return criador do tema
   */
  public CriadorTema getCriadorTema() {
    return criadorTema;
  }

  /**
   * Funcao que retorna o id do tema
   *
   * @return id do tema
   */
  public Long getId() {
    return id;
  }

  /**
   * Funcao que adiciona um mestrado compativel com o tema
   *
   * @param m mestrado a adicionar
   * @return true se o mestrado foi adicionado, false caso contrario
   */
  public boolean addMestradoCompativeis(Mestrado m) {
    return this.mestradosCompativeis.add(m);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Tema{");
    sb.append("id=").append(id);
    sb.append(", titulo='").append(titulo).append('\'');
    sb.append(", descricao='").append(descricao).append('\'');
    sb.append(", renumeracaoMensal=").append(renumeracaoMensal);
    sb.append(", anoLetivo='").append(anoLetivo).append('\'');
    sb.append(", disponivel=").append(disponivel);
    sb.append(", proposto por=").append(criadorTema.getNome());
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof AbsTema)) return false;
    AbsTema tema = (AbsTema) obj;
    return id.equals(tema.id);
  }
}
