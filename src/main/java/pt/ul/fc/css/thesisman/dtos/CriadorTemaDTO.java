package pt.ul.fc.css.thesisman.dtos;

public class CriadorTemaDTO {

  private Long id;
  private String nome;
  private String email;
  private String descricao;

  protected CriadorTemaDTO() {
  }

  public CriadorTemaDTO(Long id, String nome, String email, String descricao) {
    this.id = id;
    this.nome = nome;
    this.descricao = descricao;
    this.email = email;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
}
