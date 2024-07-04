package pt.ul.fc.css.thesisman.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Classe que representa uma sala
 *
 * @author João Pereira fc58189
 * @author Martim Pereira fc58223
 * @author Daniel Nunes fc58257
 */
@Entity
public class Sala {

  @Version
  private Long version;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String localizacao;

  @ElementCollection
  private List<MarcacaoSala> marcacoes;

  /** Construtor para a sala */
  public Sala(String localizacao) {
    this.localizacao = localizacao;
    this.marcacoes = new ArrayList<>();
  }

  public Sala() {
  }

  public String getLocalizacao() {
    return localizacao;
  }

  /**
   * Função que retorna as marcações da sala
   *
   * @return Lista de marcações da sala
   */
  public List<MarcacaoSala> getMarcacoes() {
    return marcacoes;
  }

  /**
   * Função que adiciona uma marcação à sala
   *
   * @param marcacao Marcação a adicionar
   * @return True se a marcação foi adicionada, False caso contrário
   */
  public boolean addMarcacaoSala(MarcacaoSala marcacao) {
    marcacoes.sort(Comparator.comparing(MarcacaoSala::getHoraInicio));
    for (MarcacaoSala m : marcacoes) {
      if (!marcacao.getData().equals(m.getData())) {
        continue;
      }
      System.out.println("\n\n\n MARCACAO A COMPARAR: " + m.toString() + "\n\n\n");

      if (!marcacao.getHoraFim().isBefore(m.getHoraInicio()) && !marcacao.getHoraInicio().isAfter(m.getHoraFim())) {
        if (marcacao.getHoraInicio().equals(m.getHoraFim())) {
          continue;
        }
        if (marcacao.getHoraFim().equals(m.getHoraInicio())) {
          continue;
        }
        return false;
      }

      // if (!marcacao.getHoraFim().isBefore(m.getHoraInicio())
      // && !marcacao.getHoraInicio().isAfter(m.getHoraFim())) {
      // return false;
      // }
    }
    return marcacoes.add(marcacao);
  }

  /**
   * Função que remove uma marcação da sala
   *
   * @param m Marcação a remover
   * @return True se a marcação foi removida, False caso contrário
   */
  public boolean removeMarcacaoSala(MarcacaoSala m) {
    return this.marcacoes.remove(m);
  }

  /**
   * Função que retorna o id da sala
   *
   * @return Id da sala
   */
  public Long getId() {
    return id;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Sala{");
    sb.append("id=").append(id);
    sb.append(", localizacao='").append(localizacao).append('\'');
    sb.append(", marcacoes={");
    for (MarcacaoSala m : marcacoes) {
      sb.append(m.toString());
    }
    sb.append("}}");
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Sala sala = (Sala) o;

    return id.equals(sala.id) && marcacoes.equals(sala.marcacoes);
  }
}
