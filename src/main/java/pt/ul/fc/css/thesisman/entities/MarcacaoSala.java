package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.lang.NonNull;

/**
 * Classe que representa uma marcação de uma sala.
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@Embeddable
public class MarcacaoSala {

  @NonNull
  @Column(name = "horaInicio", columnDefinition = "TIME")
  private LocalTime horaInicio;

  @NonNull
  @Column(name = "horaFim", columnDefinition = "TIME")
  private LocalTime horaFim;

  @NonNull
  @Column(name = "data", columnDefinition = "DATE")
  private LocalDate data;

  /**
   * Constructor de uma MarcacaoSala.
   *
   * @param data       Data da marcação.
   * @param horaInicio Hora de início da marcação.
   * @param horaFim    Hora de fim da marcação.
   */
  public MarcacaoSala(
      @NonNull LocalDate data, @NonNull LocalTime horaInicio, @NonNull LocalTime horaFim) {
    this.data = data;
    this.horaInicio = horaInicio;
    this.horaFim = horaFim;
  }

  /** Constructor de uma MarcacaoSala vazio para JPA. */
  public MarcacaoSala() {
  }

  /**
   * Função que devolve a hora de início da marcação.
   *
   * @return horaInicio
   */
  public LocalTime getHoraInicio() {
    return this.horaInicio;
  }

  /**
   * Função que devolve a hora de fim da marcação.
   *
   * @return horaFim
   */
  public LocalTime getHoraFim() {
    return this.horaFim;
  }

  /**
   * Função que devolve a data da marcação.
   *
   * @return data
   */
  public LocalDate getData() {
    return this.data;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("MarcacaoSala{");
    sb.append("horaInicio=").append(horaInicio.toString());
    sb.append(", horaFim=").append(horaFim.toString());
    sb.append(", data=").append(data.toString());
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    MarcacaoSala marcacao = (MarcacaoSala) o;

    return horaInicio.equals(marcacao.horaInicio)
        && horaFim.equals(marcacao.horaFim)
        && data.equals(marcacao.data);
  }
}
