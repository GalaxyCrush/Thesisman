package pt.ul.fc.css.thesisman.exceptions;

public class LoginException extends RuntimeException {

  /**
   * Construtor da exceção
   *
   * @param message mensagem de erro
   */
  public LoginException(String message) {
    super(message);
  }
}
