package pt.ul.fc.css.thesisman.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class AlunoDTO {

  private Long id;
  private String nome;
  private String email;
  @Min(value = 0, message = "A média não pode ser negativa")
  @Max(value = 20, message = "A média não pode ser superior a 20")
  private Double media;

  public AlunoDTO() {
  }

  public AlunoDTO(Long id, String nome, String email, Double media) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.media = media;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public Double getMedia() {
    return media;
  }

  public void setMedia(Double media) {
    this.media = media;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
