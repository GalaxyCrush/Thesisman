package pt.ul.fc.css.thesisman.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class MarcacaoSalaDTO {

  private LocalDate data;
  private LocalTime horaInicio;
  private LocalTime horaFim;

  public MarcacaoSalaDTO(LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
    this.data = data;
    this.horaInicio = horaInicio;
    this.horaFim = horaFim;
  }

  public MarcacaoSalaDTO() {}

  public LocalDate getData() {
    return data;
  }

  public LocalTime getHoraInicio() {
    return horaInicio;
  }

  public LocalTime getHoraFim() {
    return horaFim;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public void setHoraInicio(LocalTime horaInicio) {
    this.horaInicio = horaInicio;
  }

  public void setHoraFim(LocalTime horaFim) {
    this.horaFim = horaFim;
  }
}
