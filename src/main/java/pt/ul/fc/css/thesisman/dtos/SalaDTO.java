package pt.ul.fc.css.thesisman.dtos;

import java.util.List;

public class SalaDTO {

  // FIXME - criar uma marcacaoSalaDTO prob
  private Long id;
  private List<MarcacaoSalaDTO> marcacoes;

  private String localizacao;

  public SalaDTO() {}

  public SalaDTO(Long id, String localizacao, List<MarcacaoSalaDTO> marcacoes) {
    this.id = id;
    this.localizacao = localizacao;
    this.marcacoes = marcacoes;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public List<MarcacaoSalaDTO> getMarcacoes() {
    return marcacoes;
  }

  public void setMarcacoes(List<MarcacaoSalaDTO> marcacoes) {
    this.marcacoes = marcacoes;
  }

  public void setLocalizacao(String localizacao) {
    this.localizacao = localizacao;
  }

  public String getLocalizacao() {
    return localizacao;
  }
}
