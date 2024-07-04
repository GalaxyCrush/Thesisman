/**
 * Trabalho realizado por
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
package pt.ul.fc.css.thesisman.exceptions;

/** Exceção para o caso de uma defesa ser inválida */
public class InvalidDefesaException extends RuntimeException {

  /**
   * Construtor da exceção
   *
   * @param message mensagem de erro
   */
  public InvalidDefesaException(String message) {
    super(message);
  }
}
