package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.springframework.lang.NonNull;
import pt.ul.fc.css.thesisman.enums.Status;
import pt.ul.fc.css.thesisman.enums.TipoEntrega;
import pt.ul.fc.css.thesisman.enums.TipoPresenca;
import pt.ul.fc.css.thesisman.exceptions.InvalidDefesaException;
import pt.ul.fc.css.thesisman.exceptions.SalaIndisponivelException;

/**
 * Classe que representa uma defesa de uma tese
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@Entity
public class Defesa {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Version
  private Long version;

  @NonNull
  @Column(columnDefinition = "DATE")
  private LocalDate data;

  @NonNull
  @Column(columnDefinition = "TIME")
  private LocalTime horaInicio;

  @NonNull
  private int duracao;

  @NonNull
  @Enumerated(EnumType.STRING)
  private TipoPresenca tipo;

  @ManyToOne
  @JoinColumn(name = "orientador_id", nullable = false)
  private Docente orientador;

  @ManyToOne
  @JoinColumn(name = "arguente_id", nullable = false)
  private Docente arguente;

  @ManyToOne
  @JoinColumn(name = "presidente_id")
  private Docente presidente;

  @ManyToOne
  @JoinColumn(name = "sala_id")
  private Sala sala;

  @NonNull
  @OneToOne(mappedBy = "defesa", cascade = CascadeType.PERSIST)
  private Entrega entrega;

  private int nota;

  public Defesa() {
  }

  /**
   * Construtor de uma defesa
   *
   * @param e  entrega Entrega da tese
   * @param s  sala Sala onde se realiza a defesa
   * @param d  data Data da defesa
   * @param hi horaInicio Hora de inicio da defesa
   * @param o  orientador Orientador da tese
   * @param ar arguente Arguente da tese
   * @param pr presidente Presidente do juri
   */
  public Defesa(
      @NonNull Entrega e,
      Sala s,
      @NonNull LocalDate d,
      @NonNull LocalTime hi,
      @NonNull Docente o,
      @NonNull Docente ar,
      Docente pr) {

    if (!e.getTese().getOrientadorInterno().equals(o)) {
      throw new InvalidDefesaException(
          "Defesa inválida!\n O orientador interno da tese não é o mesmo que o orientador da defesa!\n");
    }

    this.entrega = e;
    this.entrega.setDefesa(this);

    this.duracao = e.getTipoEntrega().getDuracao();

    if (s != null) {
      this.sala = s;
      this.tipo = TipoPresenca.PRESENCIAL;

      MarcacaoSala m = new MarcacaoSala(d, hi, hi.plusMinutes(duracao));

      if (!sala.addMarcacaoSala(m))
        throw new SalaIndisponivelException("Sala já está ocupada neste horário!");

    } else {
      this.tipo = TipoPresenca.REMOTA;
    }

    this.data = d;
    this.horaInicio = hi;
    this.orientador = o;
    this.arguente = ar;
    this.presidente = pr;
    this.nota = -1;

    orientador.addDefesaOrientador(this);
    arguente.addDefesaArguente(this);

    if (pr != null) {
      pr.addDefesaPresidente(this);
      this.entrega.getTese().setStatus(Status.DEFESA_FINAL_MARCADA);
    } else {
      this.entrega.getTese().setStatus(Status.DEFESA_PROPOSTA_MARCADA);
    }
  }

  /**
   * Método que retorna o id de uma defesa
   *
   * @return id da defesa
   */
  public Long getId() {
    return id;
  }

  /**
   * Método que retorna a entrega da defesa
   *
   * @return entrega da defesa
   */
  public Entrega getEntrega() {
    return entrega;
  }

  /**
   * Método que retorna a data da defesa
   *
   * @return data da defesa
   */
  public LocalDate getData() {
    return data;
  }

  /**
   * Método que retorna a hora de inicio da defesa
   *
   * @return hora de inicio da defesa
   */
  public LocalTime getHoraInicio() {
    return horaInicio;
  }

  /**
   * Método que retorna a duração da defesa
   *
   * @return duração da defesa
   */
  public int getDuracao() {
    return duracao;
  }

  /**
   * Método que retorna o tipo de presença da defesa
   *
   * @return tipo de presença da defesa
   */
  public TipoPresenca getTipo() {
    return tipo;
  }

  /**
   * Método que retorna o arguente da defesa
   *
   * @return arguente da defesa
   */
  public Docente getArguente() {
    return this.arguente;
  }

  /**
   * Método que altera o arguente da defesa
   *
   * @param d novo arguente da defesa
   */
  public void setArguente(@NonNull Docente d) {
    this.arguente = d;
  }

  /**
   * Método que retorna o orientador da defesa
   *
   * @return orientador da defesa
   */
  public Docente getOrientador() {
    return this.orientador;
  }

  /**
   * Método que altera o orientador da defesa
   *
   * @param d novo orientador da defesa
   */
  public void setOrientador(@NonNull Docente d) {
    this.orientador = d;
  }

  /**
   * Método que retorna o presidente do juri
   *
   * @return presidente do juri
   */
  public Optional<Docente> getPresidenteJuri() {
    return Optional.ofNullable(this.presidente);
  }

  /**
   * Método que altera o presidente do juri
   *
   * @param d novo presidente do juri
   */
  public void setPresidenteJuri(Docente d) {
    this.presidente = d;
  }

  /**
   * Método que retorna a sala onde se realiza a defesa
   *
   * @return sala onde se realiza a defesa
   */
  public Sala getSala() {
    return this.sala;
  }

  /**
   * Método que altera a sala onde se realiza a defesa
   *
   * @param s nova sala onde se realiza a defesa
   */
  public void setSala(@NonNull Sala s) {
    this.sala = s;
  }

  /** Método que altera a hora de inicio da defesa */
  public void setHoraInicio(@NonNull LocalTime hi) {
    this.horaInicio = hi;
  }

  /**
   * Método que altera a duracao da defesa
   *
   * @param d nova hora de inicio da defesa
   */
  public void setDuracao(@NonNull int d) {
    this.duracao = d;
  }

  /**
   * Método que altera o tipo de presenca da defesa
   *
   * @param tp tipo de presença da defesa
   */
  public void setTipo(@NonNull TipoPresenca tp) {
    this.tipo = tp;
  }

  /**
   * Método que altera o tipo de presença da defesa
   *
   * @param d data da defesa
   */
  public void setData(@NonNull LocalDate d) {
    this.data = d;
  }

  /**
   * Método que a entrega da defesa
   *
   * @param e entrega que originou defesa
   */
  public void setEntrega(@NonNull Entrega e) {
    this.entrega = e;
  }

  /**
   * Método que obtêm a nota da defesa
   *
   * @return nota da defesa
   */
  public int getNota() {
    return nota;
  }

  /**
   * Método que retorna a nota da defesa
   *
   * @return nota da defesa
   */
  public void setNota(int nota) {
    this.nota = nota;
    if (this.entrega.getTipoEntrega().equals(TipoEntrega.FINAL)) {
      this.entrega.getTese().setStatus(nota >= 10 ? Status.FINAL_PASSADA : Status.CHUMBADA);
    } else {
      this.entrega.getTese().setStatus(nota >= 10 ? Status.PROPOSTA_PASSADA : Status.EM_PROGRESSO);
    }
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Defesa{");
    sb.append("id=").append(id);
    sb.append(", data=").append(data);
    sb.append(", horaInicio=").append(horaInicio);
    sb.append(", duracao=").append(duracao);
    if (tipo == TipoPresenca.PRESENCIAL) {
      sb.append(", sala=").append(sala.toString());
    } else {
      sb.append(", Remota");
    }
    sb.append(", orientador=").append(orientador.getNome());
    sb.append(", arguente=").append(arguente.getNome());
    if (presidente != null) {
      sb.append(", presidente=").append(presidente.getNome());
    }
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Defesa defesa = (Defesa) obj;
    return id.equals(defesa.id);
  }
}
