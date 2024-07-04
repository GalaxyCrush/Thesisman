package pt.ul.fc.css.thesisman.mappers;

import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.dtos.CriadorTemaDTO;
import pt.ul.fc.css.thesisman.entities.CriadorTema;

@Component
public class CriadorTemaMapper {
  public CriadorTemaDTO toDTO(CriadorTema d) {
    return new CriadorTemaDTO(d.getId(), d.getNome(), d.getEmail(), d.getDescricao());
  }
}
