package pt.ul.fc.css.thesisman.enums;

/**
 * Enumeração dos tipos de presença que uma defesa pode ter.
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
public enum TipoPresenca {
  PRESENCIAL,
  REMOTA;

  @Override
  public String toString() {
    if (this == PRESENCIAL) {
      return "Presencial";
    }
    return "Remota";
  }
}
