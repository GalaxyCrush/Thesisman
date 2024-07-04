package pt.ul.fc.css.thesisman.dtos;

import java.util.List;

public class TeseDTO {

  private Long id;
  private AlunoDTO aluno;
  private TemaDTO tema;
  private String status;
  private List<EntregaDTO> entregas;
  private EntregaDTO entregaFinal;

  public TeseDTO() {
  }

  public TeseDTO(Long id, AlunoDTO aluno, TemaDTO tema, String status, List<EntregaDTO> entregas,
      EntregaDTO entregaFinal) {
    this.id = id;
    this.aluno = aluno;
    this.tema = tema;
    this.status = status;
    this.entregas = entregas;
    this.entregaFinal = entregaFinal;
  }

  public List<EntregaDTO> getEntregas() {
    return entregas;
  }

  public EntregaDTO getEntregaFinal() {
    return entregaFinal;
  }

  public Long getId() {
    return id;
  }

  public AlunoDTO getAluno() {
    return aluno;
  }

  public TemaDTO getTema() {
    return tema;
  }

  public String getStatus() {
    return status;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAluno(AlunoDTO aluno) {
    this.aluno = aluno;
  }

  public void setTema(TemaDTO tema) {
    this.tema = tema;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setEntregas(List<EntregaDTO> entregas) {
    this.entregas = entregas;
  }

  public void setEntregaFinal(EntregaDTO entregaFinal) {
    this.entregaFinal = entregaFinal;
  }

}
