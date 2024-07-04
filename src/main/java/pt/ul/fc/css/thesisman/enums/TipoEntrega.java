package pt.ul.fc.css.thesisman.enums;

/**
 * Enumeração dos tipos de entrega que um aluno pode fazer.
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
public enum TipoEntrega {
  PROPOSTA(60),
  FINAL(90);

  private final int duracao;

  TipoEntrega(int i) {
    this.duracao = i;
  }

  public int getDuracao() {
    return duracao;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder(super.toString());
    sb.append("duracao=").append(duracao);
    sb.append('}');
    return sb.toString();
  }
}
