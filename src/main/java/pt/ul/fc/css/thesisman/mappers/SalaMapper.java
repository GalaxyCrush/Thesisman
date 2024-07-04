package pt.ul.fc.css.thesisman.mappers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.dtos.MarcacaoSalaDTO;
import pt.ul.fc.css.thesisman.dtos.SalaDTO;
import pt.ul.fc.css.thesisman.entities.MarcacaoSala;
import pt.ul.fc.css.thesisman.entities.Sala;

@Component
public class SalaMapper {

  public SalaDTO toDTO(Sala s) {
    SalaDTO salaDTO = new SalaDTO();
    salaDTO.setId(s.getId());
    salaDTO.setLocalizacao(s.getLocalizacao());
    List<MarcacaoSalaDTO> marcacoes =
        s.getMarcacoes().stream().map(this::marcacaoToDTO).collect(Collectors.toList());
    salaDTO.setMarcacoes(marcacoes);
    return salaDTO;
  }

  private MarcacaoSalaDTO marcacaoToDTO(MarcacaoSala m) {
    return new MarcacaoSalaDTO(m.getData(), m.getHoraInicio(), m.getHoraFim());
  }
}
