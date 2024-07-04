package pt.ul.fc.css.thesisman.dtos;

import java.util.List;

public class TemaDTO {

  private Long id;
  private String titulo;
  private String descricao;
  private Double renumeracaoMensal;
  private List<MestradoDTO> mestradosCompativeis;
  private CriadorTemaDTO criadorTema;
  private String tipoTema;

  public TemaDTO() {}

  public TemaDTO(
      Long id,
      String titulo,
      String descricao,
      Double renumeracaoMensal,
      List<MestradoDTO> mestradosCompativeis,
      CriadorTemaDTO criadorTema,
      String tipoTema) {
    this.id = id;
    this.titulo = titulo;
    this.descricao = descricao;
    this.renumeracaoMensal = renumeracaoMensal;
    this.mestradosCompativeis = mestradosCompativeis;
    this.criadorTema = criadorTema;
    this.tipoTema = tipoTema;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setRenumeracaoMensal(Double renumeracaoMensal) {
    this.renumeracaoMensal = renumeracaoMensal;
  }

  public Double getRenumeracaoMensal() {
    return renumeracaoMensal;
  }

  public void setTipoTema(String tipoTema) {
    this.tipoTema = tipoTema;
  }

  public String getTipoTema() {
    return tipoTema;
  }

  public void setMestradosCompativeis(List<MestradoDTO> mestradosCompativeis) {
    this.mestradosCompativeis = mestradosCompativeis;
  }

  public List<MestradoDTO> getMestradosCompativeis() {
    return mestradosCompativeis;
  }

  public void setCriadorTema(CriadorTemaDTO criadorTema) {
    this.criadorTema = criadorTema;
  }

  public CriadorTemaDTO getCriadorTema() {
    return criadorTema;
  }
}
