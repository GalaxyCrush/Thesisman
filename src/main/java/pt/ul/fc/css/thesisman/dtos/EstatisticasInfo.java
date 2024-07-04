package pt.ul.fc.css.thesisman.dtos;

public class EstatisticasInfo {

  private int numAlunosAnoAtual;
  private int numAlunosChumbadosAnoAtual;
  private int numAlunosPassadosAnoAtual;
  private float mediaNotasAnoAtual;

  private int numAlunosTotal;
  private int numAlunosChumbadosTotal;
  private int numAlunosPassadosTotal;
  private float mediaNotasTotal;

  public EstatisticasInfo() {
    super();
  }

  public EstatisticasInfo(
      int numAlunosAnoAtual,
      int numAlunosChumbadosAnoAtual,
      int numAlunosPassadosAnoAtual,
      float mediaNotasAnoAtual,
      int numAlunosTotal,
      int numAlunosChumbadosTotal,
      int numAlunosPassadosTotal,
      float mediaNotasTotal) {
    super();
    this.numAlunosAnoAtual = numAlunosAnoAtual;
    this.numAlunosChumbadosAnoAtual = numAlunosChumbadosAnoAtual;
    this.numAlunosPassadosAnoAtual = numAlunosPassadosAnoAtual;
    this.mediaNotasAnoAtual = mediaNotasAnoAtual;
    this.numAlunosTotal = numAlunosTotal;
    this.numAlunosChumbadosTotal = numAlunosChumbadosTotal;
    this.numAlunosPassadosTotal = numAlunosPassadosTotal;
    this.mediaNotasTotal = mediaNotasTotal;
  }

  public int getNumAlunosAnoAtual() {
    return numAlunosAnoAtual;
  }

  public void setNumAlunosAnoAtual(int numAlunosAnoAtual) {
    this.numAlunosAnoAtual = numAlunosAnoAtual;
  }

  public int getNumAlunosChumbadosAnoAtual() {
    return numAlunosChumbadosAnoAtual;
  }

  public void setNumAlunosChumbadosAnoAtual(int numAlunosChumbadosAnoAtual) {
    this.numAlunosChumbadosAnoAtual = numAlunosChumbadosAnoAtual;
  }

  public int getNumAlunosPassadosAnoAtual() {
    return numAlunosPassadosAnoAtual;
  }

  public void setNumAlunosPassadosAnoAtual(int numAlunosPassadosAnoAtual) {
    this.numAlunosPassadosAnoAtual = numAlunosPassadosAnoAtual;
  }

  public float getMediaNotasAnoAtual() {
    return mediaNotasAnoAtual;
  }

  public void setMediaNotasAnoAtual(float mediaNotasAnoAtual) {
    this.mediaNotasAnoAtual = mediaNotasAnoAtual;
  }

  public int getNumAlunosTotal() {
    return numAlunosTotal;
  }

  public void setNumAlunosTotal(int numAlunosTotal) {
    this.numAlunosTotal = numAlunosTotal;
  }

  public int getNumAlunosChumbadosTotal() {
    return numAlunosChumbadosTotal;
  }

  public void setNumAlunosChumbadosTotal(int numAlunosChumbadosTotal) {
    this.numAlunosChumbadosTotal = numAlunosChumbadosTotal;
  }

  public int getNumAlunosPassadosTotal() {
    return numAlunosPassadosTotal;
  }

  public void setNumAlunosPassadosTotal(int numAlunosPassadosTotal) {
    this.numAlunosPassadosTotal = numAlunosPassadosTotal;
  }

  public float getMediaNotasTotal() {
    return mediaNotasTotal;
  }

  public void setMediaNotasTotal(float mediaNotasTotal) {
    this.mediaNotasTotal = mediaNotasTotal;
  }

  @Override
  public String toString() {
    return "EstatisticasInfo [numAlunosAnoAtual="
        + numAlunosAnoAtual
        + ", numAlunosChumbadosAnoAtual="
        + numAlunosChumbadosAnoAtual
        + ", numAlunosPassadosAnoAtual="
        + numAlunosPassadosAnoAtual
        + ", mediaNotasAnoAtual="
        + mediaNotasAnoAtual
        + ", numAlunosTotal="
        + numAlunosTotal
        + ", numAlunosChumbadosTotal="
        + numAlunosChumbadosTotal
        + ", numAlunosPassadosTotal="
        + numAlunosPassadosTotal
        + ", mediaNotasTotal="
        + mediaNotasTotal
        + "]";
  }
}
