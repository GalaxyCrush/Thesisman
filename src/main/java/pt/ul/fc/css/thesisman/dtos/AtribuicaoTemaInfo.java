package pt.ul.fc.css.thesisman.dtos;

import java.util.List;

public class AtribuicaoTemaInfo {

  private List<TemaDTO> temas;

  private List<AlunoDTO> alunos;

  private List<CriadorTemaDTO> docentes;

  public AtribuicaoTemaInfo() {
    super();
  }

  public AtribuicaoTemaInfo(
      List<TemaDTO> temas, List<AlunoDTO> alunos, List<CriadorTemaDTO> docentes) {
    super();
    this.temas = temas;
    this.alunos = alunos;
    this.docentes = docentes;
  }

  public List<TemaDTO> getTemas() {
    return temas;
  }

  public void setTemas(List<TemaDTO> temas) {
    this.temas = temas;
  }

  public List<AlunoDTO> getAlunos() {
    return alunos;
  }

  public void setAlunos(List<AlunoDTO> alunos) {
    this.alunos = alunos;
  }

  public List<CriadorTemaDTO> getDocentes() {
    return docentes;
  }

  public void setDocentes(List<CriadorTemaDTO> docentes) {
    this.docentes = docentes;
  }

  @Override
  public String toString() {
    return "AtribuicaoTemaInfo [temas="
        + temas
        + ", alunos="
        + alunos
        + ", docentes="
        + docentes
        + "]";
  }
}
