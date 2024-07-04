package pt.ul.fc.css.thesisman.mappers;

import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.dtos.MestradoDTO;
import pt.ul.fc.css.thesisman.entities.Mestrado;

@Component
public class MestradoMapper {

  public MestradoDTO toDTO(Mestrado m) {
    return new MestradoDTO(m.getId(), m.getNome(), m.getDepartamento());
  }
}
