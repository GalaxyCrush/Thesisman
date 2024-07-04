package pt.ul.fc.css.thesisman.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.dtos.DefesaDTO;
import pt.ul.fc.css.thesisman.entities.Defesa;

@Component
public class DefesaMapper {

  @Autowired private EntregaMapper entregaMapper;

  public DefesaDTO toDTO(Defesa d) {
    DefesaDTO defesaDto = new DefesaDTO(d.getId(), d.getTipo().toString());
    defesaDto.setEntrega(entregaMapper.toDTO(d.getEntrega()));

    return defesaDto;
  }
}
