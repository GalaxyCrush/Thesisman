package pt.ul.fc.css.thesisman.dtos;

public class DefesaDTO {

  private Long id;
  private String tipo;
  private EntregaDTO entrega;

  public DefesaDTO() {}

  public DefesaDTO(Long id, String tipo) {
    this.id = id;
    this.tipo = tipo;
  }

  public Long getId() {
    return id;
  }

  public String getTipo() {
    return tipo;
  }

  public EntregaDTO getEntrega() {
    return entrega;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public void setEntrega(EntregaDTO entrega) {
    this.entrega = entrega;
  }
}
