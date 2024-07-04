package pt.ul.fc.css.thesisman.enums;

/**
 * Enumeração dos possíveis estados de uma tese.
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
public enum Status {
  EM_PROGRESSO,
  PROPOSTA_ENTREGUE,
  DEFESA_PROPOSTA_MARCADA,
  PROPOSTA_PASSADA,
  FINAL_ENTREGUE,
  DEFESA_FINAL_MARCADA,
  FINAL_PASSADA,
  CHUMBADA;

  @Override
  public String toString() {
    switch (this) {
      case EM_PROGRESSO:
        return "Em Progresso";
      case PROPOSTA_ENTREGUE:
        return "Proposta Entregue";
      case PROPOSTA_PASSADA:
        return "Proposta Passada";
      case FINAL_ENTREGUE:
        return "Final Entregue";
      case FINAL_PASSADA:
        return "Final Passada";
      case CHUMBADA:
        return "Chumbada";
      case DEFESA_PROPOSTA_MARCADA:
        return "Defesa Proposta Marcada";
      case DEFESA_FINAL_MARCADA:
        return "Defesa Final Marcada";
      default:
        return "Status desconhecido";
    }
  }
}
