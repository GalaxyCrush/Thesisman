package pt.ul.fc.css.thesisman.dtos;

public class RespostaLogin {

  private Long id;
  private boolean isAdmin;
  private boolean isDocente;

  public RespostaLogin(Long id, boolean isAdmin, boolean isDocente) {
    this.id = id;
    this.isAdmin = isAdmin;
    this.isDocente = isDocente;
  }

  public Long getId() {
    return id;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public boolean isDocente() {
    return isDocente;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public void setDocente(boolean isDocente) {
    this.isDocente = isDocente;
  }
}
