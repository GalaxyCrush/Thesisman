package pt.ul.fc.css.thesisman.mappers;

import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.dtos.AlunoDTO;
import pt.ul.fc.css.thesisman.entities.Aluno;

@Component
public class AlunoMapper {

  public AlunoDTO toDTO(Aluno a) {
    return new AlunoDTO(a.getId(), a.getNome(), a.getEmail(), a.getMedia());
  }
}
