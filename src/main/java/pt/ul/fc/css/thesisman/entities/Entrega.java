package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.springframework.lang.NonNull;
import pt.ul.fc.css.thesisman.enums.Status;
import pt.ul.fc.css.thesisman.enums.TipoEntrega;

/**
 * Classe que representa uma entrega de uma tese
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@Entity
public class Entrega {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Version
  private Long version;

  @Lob
  @NonNull
  private byte[] fileContents;

  @Enumerated(EnumType.STRING)
  private TipoEntrega tipoEntrega;

  @Column(columnDefinition = "DATE")
  private LocalDate data;

  @ManyToOne
  @JoinColumn(name = "tese_id", nullable = false)
  private AbsTese tese;

  @OneToOne(cascade = CascadeType.REMOVE)
  private Defesa defesa;

  @NonNull
  private String fileName;

  public Entrega() {
    this.data = LocalDate.now();
  }

  /**
   * Construtor de uma entrega
   *
   * @param t  Tese a que a entrega pertence
   * @param te Tipo de entrega
   * @param fc Conteúdo do ficheiro
   */
  public Entrega(@NonNull AbsTese t, @NonNull TipoEntrega te, @NonNull byte[] fc, @NonNull String fileName) {

    if (te.equals(TipoEntrega.FINAL)) {
      if (!t.getStatus().equals(Status.PROPOSTA_PASSADA)) {
        throw new IllegalArgumentException("Tese ainda não passou na defesa de proposta");
      }
    } else if (te.equals(TipoEntrega.PROPOSTA)) {
      if (!t.getStatus().equals(Status.EM_PROGRESSO)) {
        throw new IllegalArgumentException("Tese não está em progresso");
      }
    }
    this.data = LocalDate.now();
    this.tipoEntrega = te;
    this.fileContents = fc;
    this.tese = t;
    this.fileName = fileName;
    this.tese.addEntrega(this);
  }

  /**
   * Método que retorna o id de uma entrega
   *
   * @return id da entrega
   */
  public Long getId() {
    return id;
  }

  /**
   * Método que retorna o tipo de entrega
   *
   * @return tipo de entrega
   */
  public TipoEntrega getTipoEntrega() {
    return this.tipoEntrega;
  }

  /**
   * Método que retorna a data de uma entrega
   *
   * @return data da entrega
   */
  public LocalDate getData() {
    return data;
  }

  /**
   * Método que retorna o conteúdo do ficheiro de uma entrega
   *
   * @return conteúdo do ficheiro
   */
  public byte[] getFileContents() {
    return this.fileContents;
  }

  /**
   * Método que retorna a tese a que a entrega pertence
   *
   * @return tese da entrega
   */
  public AbsTese getTese() {
    return this.tese;
  }

  /**
   * Método que retorna a defesa associada à entrega
   *
   * @return defesa associada à entrega
   */
  public Defesa getDefesa() {
    return defesa;
  }

  /**
   * Método que define a defesa associada à entrega
   *
   * @param d defesa associada à entrega
   */
  public void setDefesa(Defesa d) {
    this.defesa = d;
  }

  /**
   * Método que retorna o nome do ficheiro
   *
   * @return nome do ficheiro
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * Método que define o nome do ficheiro
   *
   * @param fileName nome do ficheiro
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Entrega{");
    sb.append("id=").append(id);
    sb.append(", tipoEntrega=").append(tipoEntrega);
    sb.append(", data=").append(data.toString());
    if (defesa != null) {
      sb.append(", defesa=").append(defesa.getId());
    } else {
      sb.append(", defesa não marcada");
    }
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Entrega entrega = (Entrega) o;

    return id.equals(entrega.id);
  }
}
