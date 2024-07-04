package pt.ul.fc.css.thesisman.mappers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.dtos.CriadorTemaDTO;
import pt.ul.fc.css.thesisman.dtos.MestradoDTO;
import pt.ul.fc.css.thesisman.dtos.TemaDTO;
import pt.ul.fc.css.thesisman.entities.AbsTema;
import pt.ul.fc.css.thesisman.entities.TemaDissertacao;

@Component
public class TemaMapper {

  @Autowired private MestradoMapper mestradoMapper;

  public TemaDTO toDTO(AbsTema t) {
    List<MestradoDTO> mestradosDTO =
        t.getMestradosCompativeis().stream()
            .map(mestradoMapper::toDTO)
            .collect(Collectors.toList());

    CriadorTemaDTO criadorTemaDTO =
        new CriadorTemaDTO(
            t.getCriadorTema().getId(),
            t.getCriadorTema().getNome(),
            t.getCriadorTema().getEmail(),
            t.getCriadorTema().getDescricao());

    String tipoTema = "";
    if (t instanceof TemaDissertacao) {
      tipoTema = "Dissertação";
    } else {
      tipoTema = "Projeto";
    }
    TemaDTO temaDto = new TemaDTO();
    temaDto.setId(t.getId());
    temaDto.setTitulo(t.getTitulo());
    temaDto.setDescricao(t.getDescricao());
    temaDto.setRenumeracaoMensal(t.getRenumeracaoMensal());
    temaDto.setMestradosCompativeis(mestradosDTO);
    temaDto.setCriadorTema(criadorTemaDTO);
    temaDto.setTipoTema(tipoTema);
    return temaDto;
  }
}
