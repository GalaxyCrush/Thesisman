package pt.ul.fc.css.thesisman.mappers;

import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.dtos.EntregaDTO;
import pt.ul.fc.css.thesisman.entities.Entrega;

@Component
public class EntregaMapper {

  public EntregaDTO toDTO(Entrega e) {
    if (e.getDefesa() == null) {
      return new EntregaDTO(
          e.getId(),
          e.getTese().getId(),
          e.getTese().getAluno().getNome(),
          e.getData().toString(),
          e.getFileContents(), e.getFileName(), e.getTese().getStatus().toString(),
          "Sem defesa associada");
    }
    return new EntregaDTO(
        e.getId(),
        e.getTese().getId(),
        e.getTese().getAluno().getNome(),
        e.getData().toString(),
        e.getFileContents(), e.getFileName(), e.getTese().getStatus().toString(),
        String.valueOf(e.getDefesa().getNota()));
  }
}
