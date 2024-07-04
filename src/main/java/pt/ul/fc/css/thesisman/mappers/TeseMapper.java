package pt.ul.fc.css.thesisman.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.thesisman.dtos.AlunoDTO;
import pt.ul.fc.css.thesisman.dtos.EntregaDTO;
import pt.ul.fc.css.thesisman.dtos.TemaDTO;
import pt.ul.fc.css.thesisman.dtos.TeseDTO;
import pt.ul.fc.css.thesisman.entities.AbsTese;

@Component
public class TeseMapper {

  @Autowired
  private TemaMapper temaMapper;

  @Autowired
  private EntregaMapper entregaMapper;

  @Transactional
  public TeseDTO toDTO(AbsTese t) {
    AlunoDTO alunoDTO = new AlunoDTO(
        t.getAluno().getId(),
        t.getAluno().getNome(),
        t.getAluno().getEmail(),
        t.getAluno().getMedia());
    TemaDTO temaDto = temaMapper.toDTO(t.getTema());

    if (t.getEntregaFinal() == null && t.getEntregasPropostas().isEmpty()) {
      return new TeseDTO(t.getId(), alunoDTO, temaDto, t.getStatus().toString(), new ArrayList<>(), null);
    } else if (t.getEntregaFinal() == null) {
      List<EntregaDTO> entregasPropostas = t.getEntregasPropostas().stream().map(entregaMapper::toDTO).toList();
      return new TeseDTO(t.getId(), alunoDTO, temaDto, t.getStatus().toString(), entregasPropostas, null);
    } else {
      EntregaDTO entregaFinal = entregaMapper.toDTO(t.getEntregaFinal());
      List<EntregaDTO> entregasPropostas = t.getEntregasPropostas().stream().map(entregaMapper::toDTO).toList();
      return new TeseDTO(t.getId(), alunoDTO, temaDto, t.getStatus().toString(), entregasPropostas, entregaFinal);
    }
  }
}
