/**
 * Trabalho realizado por
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
package pt.ul.fc.css.thesisman.exceptions;

/** Exception que é lançada quando uma candidatura é inválida. */
public class CriarCandidaturaException extends RuntimeException {

  /**
   * Construtor da exceção.
   *
   * @param message Mensagem de erro.
   */
  public CriarCandidaturaException(String message) {
    super(message);
  }
}
